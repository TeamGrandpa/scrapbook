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
	private Kid kid;
	
	@Mock
	private Kid anotherKid;
	
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

}
