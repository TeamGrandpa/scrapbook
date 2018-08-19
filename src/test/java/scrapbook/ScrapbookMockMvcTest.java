package scrapbook;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;



@RunWith(SpringRunner.class)
@WebMvcTest(ScrapbookController.class)
public class ScrapbookMockMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private KidRepository kidRepo;
	
	@MockBean
	private ImageRepository imageRepo;
	
	@MockBean 
	private CommentRepository commentRepo;
	
	@MockBean
	private HeartRepository heartRepo;
	
	@Mock
	private Kid kid;
	
	@Mock
	private Kid anotherKid;
	
	@Mock
	private Image image;
	
	@Mock
	private Image anotherImage;
	
	@Mock
	private Comment comment;
	
	@Mock
	private Comment anotherComment;
	
	@Test
	public void shouldRouteToSingleKidView() throws Exception{
		long arbitraryKidId = 1;
		when(kidRepo.findById(arbitraryKidId)).thenReturn(Optional.of(kid));
		mvc.perform(get("/kid?id=1")).andExpect(view().name(is("kid")));
	}
	
	@Test
	public void shouldBeOkForSingleKid() throws Exception {
		long arbitraryKidId = 1;
		when(kidRepo.findById(arbitraryKidId)).thenReturn(Optional.of(kid));
		mvc.perform(get("/kid?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldNotBeOkForSingleKid() throws Exception {
		mvc.perform(get("/kid?id=1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleKidIntoModel() throws Exception {
		when(kidRepo.findById(1L)).thenReturn(Optional.of(kid));
		mvc.perform(get("/kid?id=1")).andExpect(model().attribute("kids", is(kid)));
	}
	
	@Test
	public void shouldRouteToAllKidsView() throws Exception {
		mvc.perform(get("/kids")).andExpect(view().name(is("kids")));
	}
	
	@Test
	public void shouldBeOkForAllKids() throws Exception {
		mvc.perform(get("/kids")).andExpect(status().isOk());
	}

	@Test
	public void shouldPutAllKidsIntoModel() throws Exception {
		Collection<Kid> allKids = Arrays.asList(kid, anotherKid);
		when(kidRepo.findAll()).thenReturn(allKids);
		mvc.perform(get("/kids")).andExpect(model().attribute("kids", is(allKids)));
	}
	
	@Test
	public void shouldRouteToSingleImageView() throws Exception {
		long arbitraryImageId = 1;
		when(imageRepo.findById(arbitraryImageId)).thenReturn(Optional.of(image));
		mvc.perform(get("/image?id=1")).andExpect(view().name(is("image")));
	}
	
	@Test
	public void shouldBeOkForSingleImage() throws Exception {
		long arbitraryImageId = 1;
		when(imageRepo.findById(arbitraryImageId)).thenReturn(Optional.of(image));
		mvc.perform(get("/image?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldNotBeOkForSingleImage() throws Exception {
		mvc.perform(get("/image?id=1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleImageIntoModel() throws Exception {
		when(imageRepo.findById(1L)).thenReturn(Optional.of(image));
		mvc.perform(get("/image?id=1")).andExpect(model().attribute("images", is(image)));
	}
	
	@Test
	public void shouldRouteToAllImagesView() throws Exception {
		mvc.perform(get("/images")).andExpect(view().name(is("images")));
	}
	
	@Test
	public void shouldBeOkForAllImages() throws Exception {
		mvc.perform(get("/images")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldPutAllImagesIntoModel() throws Exception {
		Collection<Image> allImages = Arrays.asList(image, anotherImage);
		when(imageRepo.findAll()).thenReturn(allImages);
		mvc.perform(get("/images")).andExpect(model().attribute("images", is(allImages)));
	}
	
	@Test
	public void shouldBeOkForSingleCommentView() throws Exception {
		long arbitraryCommentId = 1;
		when(commentRepo.findById(arbitraryCommentId)).thenReturn(Optional.of(comment));
		mvc.perform(get("/comment?id=1")).andExpect(view().name(is("comment")));
	}
	
	@Test
	public void shouldBeOkForSingleComment() throws Exception {
		long arbitraryCommentId = 1;
		when(commentRepo.findById(arbitraryCommentId)).thenReturn(Optional.of(comment));
		mvc.perform(get("/comment?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldNotBeOkForSingleComment() throws Exception {
		mvc.perform(get("/comment?id=1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleCommentIntoModel() throws Exception {
		when(commentRepo.findById(1L)).thenReturn(Optional.of(comment));
		mvc.perform(get("/comment?id=1")).andExpect(model().attribute("comments", is(comment)));
	}
	
	@Test
	public void shouldRouteToAllCommentsView() throws Exception {
		mvc.perform(get("/comments")).andExpect(view().name(is("comments")));
	}
	
	@Test
	public void shouldBeOkForAllComments() throws Exception {
		mvc.perform(get("/comments")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldPutAllCommentsIntoModel() throws Exception {
		Collection<Comment> allComments = Arrays.asList(comment, anotherComment);
		when(commentRepo.findAll()).thenReturn(allComments);
		mvc.perform(get("/comments")).andExpect(model().attribute("comments", is(allComments)));
	}
	
}
