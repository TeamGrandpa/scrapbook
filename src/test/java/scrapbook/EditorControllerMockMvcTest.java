package scrapbook;

import static org.hamcrest.CoreMatchers.is;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(EditorController.class)
public class EditorControllerMockMvcTest {
	
	//Setup
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private KidRepository kidRepo;
	
	@MockBean
	private ImageRepository imageRepo;
	
	@MockBean
	private CommentRepository commentRepo;
	
	//Mock Stuff
	@Mock
	private Kid kid;
	
	@Mock
	private Image image;
	
	@Mock
	private Comment comment;
	
	//Editor Home Page
	
	@Test
	public void shouldRouteToEditorPanelIfEditor() throws Exception {
		mvc.perform(get("/editor?role=editor")).andExpect(view().name(is("editor")));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
