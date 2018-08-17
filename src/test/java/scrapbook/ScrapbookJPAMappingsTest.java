package scrapbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.h2.engine.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class ScrapbookJPAMappingsTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private KidRepository kidRepo;

	@Resource
	private ImageRepository imageRepo;

	@Resource
	private CommentRepository commentRepo;

	@Resource
	private EnduserRepository enduserRepo;
	
	@Resource
	private HeartRepository heartRepo;

	@Test
	public void shouldSaveAndLoadKid() {
		Kid kid = kidRepo.save(new Kid("sam"));
		long kidId = kid.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Kid> result = kidRepo.findById(kidId);
		kid = result.get();
		assertThat(kid.getName(), is("sam"));
	}
	
	@Test
	public void shouldDeleteKid() {
		Kid kid = kidRepo.save(new Kid("sam"));
		long kidId = kid.getId();

		entityManager.flush();
		entityManager.clear();
		
		Optional<Kid> result = kidRepo.findById(kidId);
		kid = result.get();
		assertThat(kid.getName(), is("sam"));
		
		kidRepo.delete(kid);
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(kidRepo.findById(kidId).isPresent(), is(false));
	}

	@Test
	public void shouldGenerateKidId() {
		Kid kid = kidRepo.save(new Kid("sam"));
		long kidId = kid.getId();

		entityManager.flush();
		entityManager.clear();

		assertThat(kidId, is(greaterThan(0L)));
	}

	@Test
	public void shouldEstablishImagestoKidRelationship() {

		Kid kid = new Kid("name");
		kidRepo.save(kid);
		long kidId = kid.getId();

		Image image1 = new Image("image", "caption", kid);
		imageRepo.save(image1);

		Image image2 = new Image("image2", "caption2", kid);
		imageRepo.save(image2);

		entityManager.flush();
		entityManager.clear();

		Optional<Kid> result = kidRepo.findById(kidId);
		kid = result.get();
		assertThat(kid.getImages(), containsInAnyOrder(image1, image2));
	}

	@Test
	public void shouldEstablishCommentsToImageRelationship() {

		Image image = new Image("image", "caption", null);
		imageRepo.save(image);
		long imageId = image.getId();

		Comment comment = new Comment("comment", image);
		commentRepo.save(comment);

		Comment comment2 = new Comment("comment two", image);
		commentRepo.save(comment2);

		entityManager.flush();
		entityManager.clear();

		Optional<Image> result = imageRepo.findById(imageId);
		image = result.get();
		assertThat(image.getComments(), containsInAnyOrder(comment, comment2));

	}

	@Test
	public void shouldSaveAndLoadEnduser() {
		Enduser enduser = enduserRepo.save(new Enduser("enduser", false));
		long enduserId = enduser.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Enduser> result = enduserRepo.findById(enduserId);
		enduser = result.get();
		assertThat(enduser.getUserName(), is("enduser"));
	}
	
	@Test
	public void shouldGenerateEnduserId() {
		Enduser enduser = enduserRepo.save(new Enduser("enduser", false));
		long enduserId = enduser.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(enduserId, is(greaterThan(0L)));		
		
	}
	
	@Test
	public void shouldEstablishEnduserToCommentsRelationship() {
		Enduser enduser = enduserRepo.save(new Enduser("userName", false));
		long enduserId = enduser.getId();
		
//		Image image = imageRepo.save(new Image("", "", null));
		
		Comment comment = new Comment("comment content", enduser, null);
		commentRepo.save(comment);
		
		Comment comment2 = new Comment("comment content2", enduser, null);
		commentRepo.save(comment2);
		
		entityManager.flush(); 
		entityManager.clear();
		
		Optional<Enduser> result = enduserRepo.findById(enduserId);
		enduser = result.get();
		
		assertThat(enduser.getComments(), containsInAnyOrder(comment, comment2));
	}
	
	@Test
	public void shouldSaveAndLoadHeart() {
		Enduser enduser = enduserRepo.save(new Enduser("userName", false));
		long enduserId = enduser.getId();
		
		Image image = new Image("image", "caption", null);
		imageRepo.save(image);
		long imageId = image.getId();
				
		Heart heart = heartRepo.save(new Heart(enduser, image));
		long heartId = heart.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Heart> result = heartRepo.findById(heartId);
		heart = result.get();
		assertThat(heart.getImage().getId(), is(imageId));
		assertThat(heart.getEnduser().getId(), is(enduserId));
	}

}
