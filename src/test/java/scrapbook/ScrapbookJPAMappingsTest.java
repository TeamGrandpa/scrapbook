package scrapbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

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
	private MessageRepository messageRepo;

	@Resource
	private CommentRepository commentRepo;

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
	public void shouldEstablishMessagestoKidRelationship() {

		Kid kid = new Kid("name");
		kidRepo.save(kid);
		long kidId = kid.getId();

		Message message = new Message("content", kid);
		messageRepo.save(message);

		Message message2 = new Message("content2", kid);
		messageRepo.save(message2);

		entityManager.flush();
		entityManager.clear();

		Optional<Kid> result = kidRepo.findById(kidId);
		kid = result.get();
		assertThat(kid.getMessages(), containsInAnyOrder(message, message2));

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

}
