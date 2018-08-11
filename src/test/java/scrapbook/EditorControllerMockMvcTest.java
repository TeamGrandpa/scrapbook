package scrapbook;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import javax.annotation.Resource;
import javax.servlet.http.Cookie;

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
	
	final Cookie editorRoleCookie = new Cookie("role", "editor");
	final Cookie viewerRoleCookie = new Cookie("role", "viewer");
	
	//Editor Home Page
	
//	@Test
//	public void shouldRouteToEditorPanelIfEditor() throws Exception {
//		mvc.perform(get("/editor").cookie(editorRoleCookie)).andExpect(view().name(is("editor")));
//	}
//	
//	@Test
//	public void shouldBeOkForEditorPanelIfEditor() throws Exception {
//		mvc.perform(get("/editor").cookie(editorRoleCookie)).andExpect(status().isOk()); //200
//	}
//	
	@Test
	public void shouldBeUnauthorizedForEditorPanelIfNotEditor() throws Exception {
		mvc.perform(get("/editor")).andExpect(status().isFound()); //302
		mvc.perform(get("/editor").cookie(viewerRoleCookie)).andExpect(status().isFound()); //302
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
