package scrapbook;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class ScrapbookControllerTest {
	
	@InjectMocks
	private ScrapbookController underTest;
	
	@Mock
	private KidRepository kidRepo;
	
	@Mock
	private ImageRepository imageRepo;
	
	@Mock
	private CommentRepository commentRepo;
	
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
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddSingleKidToModel() throws KidNotFoundException {
		long arbitraryKidId = 1;
		when(kidRepo.findById(arbitraryKidId)).thenReturn(Optional.of(kid));
		
		underTest.findOneKid(arbitraryKidId, model);
		verify(model).addAttribute("kids", kid);
	}
	
	@Test
	public void shouldAddAllKidsToModel() {
		Collection<Kid> allKids = Arrays.asList(kid, anotherKid);
		when(kidRepo.findAll()).thenReturn(allKids);
		
		underTest.findAllKids(model);
		verify(model).addAttribute("kids", allKids);
	}
	
	@Test
	public void shouldAddSingleImageToModel() throws ImageNotFoundException {
		long arbitraryImageId = 1;
		when(imageRepo.findById(arbitraryImageId)).thenReturn(Optional.of(image));
		
		underTest.findOneImage(arbitraryImageId, model);
		verify(model).addAttribute("images", image);
		
	}
	
	@Test
	public void shouldAddAllImagesToModel() {
		Collection<Image> allImages = Arrays.asList(image, anotherImage);
		when(imageRepo.findAll()).thenReturn(allImages);
		
		underTest.findAllImages(model);
		verify(model).addAttribute("images", allImages);
	}
	
	@Test
	public void shouldAddSingleCommentToModel() throws CommentNotFoundException {
		long arbitraryCommentId = 1;
		when(commentRepo.findById(arbitraryCommentId)).thenReturn(Optional.of(comment));
		
		underTest.findOneComment(arbitraryCommentId, model);
		verify(model).addAttribute("comments", comment);
	}
	
	@Test
	public void shouldAddallCommentsToModel() {
		Collection<Comment> allComments = Arrays.asList(comment, anotherComment);
		
		when(commentRepo.findAll()).thenReturn(allComments);
		
		underTest.findAllComments(model);
		verify(model).addAttribute("comments", allComments);	
		
	}
	
//	@Test
//	public void shouldRemoveKidByKidIdByOptional() {
//		long arbitraryKidId = 1L;
//		when(kidRepo.findById(arbitraryKidId)).thenReturn(Optional.of(kid));
//		
//		underTest.deleteOneKidById(arbitraryKidId);
//		verify(kidRepo).deleteById(arbitraryKidId);
//		
//	}
	

	
	


}
