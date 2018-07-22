package scrapbook;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScrapbookPopulator implements CommandLineRunner {

	@Resource
	private KidRepository kidRepo;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		Kid sam = new Kid("Sam");
		sam = kidRepo.save(sam);

		Kid penny = new Kid("Penny");
		penny = kidRepo.save(penny);
	}

}
