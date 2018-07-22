package scrapbook;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
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
	
	@Mock
	private Kid kid;
	
	@Mock
	private Kid anotherKid;
	
	@Test
	public void shouldRouteToSingleKidView() throws Exception{
		long arbitraryKidId = 1;
		when(kidRepo.findById(arbitraryKidId)).thenReturn(Optional.of(kid));
		mvc.perform(get("/kid?id=1")).andExpect(view().name(is("kid")));
	}
	
	
	
	
	

}
