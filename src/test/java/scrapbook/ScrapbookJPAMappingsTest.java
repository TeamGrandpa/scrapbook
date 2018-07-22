package scrapbook;

import static org.hamcrest.CoreMatchers.is;
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

	@Test
	public void shouldSaveAndLoadChild() {
		Kid kid = kidRepo.save(new Kid("sam"));
		long kidId = kid.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Kid> result = kidRepo.findById(kidId);
		kid = result.get();
		assertThat(kid.getName(), is("sam"));
	}

	@Test
	public void shouldGenerateChildId() {
		Kid kid = kidRepo.save(new Kid("sam"));
		long kidId = kid.getId();

		entityManager.flush();
		entityManager.clear();

		assertThat(kidId, is(greaterThan(0L)));
	}

}
