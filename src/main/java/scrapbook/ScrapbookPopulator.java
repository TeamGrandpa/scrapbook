package scrapbook;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScrapbookPopulator implements CommandLineRunner {

	@Resource
	private KidRepository kidRepo;

	@Resource
	private ImageRepository imageRepo;

	@Resource
	private CommentRepository commentRepo;

	@Resource
	private EnduserRepository enduserRepo;

	@Override
	public void run(String... args) throws Exception {

		// Create Kids
		Kid oliver = new Kid("Oliver", "/portraits/OliverOne.png", 1, true);
		oliver = kidRepo.save(oliver);

		Kid penny = new Kid("Penny", "/portraits/penny_portrait.jpg", 2, false);
		penny = kidRepo.save(penny);

		Kid alana = new Kid("Alana", "/portraits/AlanaOne.jpg", 3, false);
		alana = kidRepo.save(alana);

		// Create Images
		Image oliver_jacket = new Image("/img/OliverTwo.jpg", "Oliver loves his new leather jacket. Looking cool thanks to Grandma!", "October 24, 2016", oliver);
		oliver_jacket = imageRepo.save(oliver_jacket);

		Image oliver_sleep = new Image("/img/OliverThree.jpg", "Oli at nap time.", "May 12, 2018", oliver);
		oliver_sleep = imageRepo.save(oliver_sleep);

		Image oliver_art = new Image("/img/ocean.jpg", "Drawn at daycare today!",
				new SimpleDateFormat("MMMM d, yyyy").format(new Date()), oliver);
		oliver_art = imageRepo.save(oliver_art);

		Image penny_img1 = new Image("/img/penny_img1.jpg", "Penny enjoying watermelon", "June 5, 2018", penny);
		penny_img1 = imageRepo.save(penny_img1);

		Image penny_img2 = new Image("/img/penny_img2.jpg", "Penny playing",
				new SimpleDateFormat("MMMM d, yyyy").format(new Date()), penny);
		penny_img2 = imageRepo.save(penny_img2);
		
		Image alana_laugh = new Image("/img/AlanaTwo.jpg", "Alana is such a happy baby!", "July 9, 2018", alana);
		alana_laugh = imageRepo.save(alana_laugh);
		
		Image alana_art= new Image("/img/AlanaArt.jpg", "She said this was a picture of daddy. Not his best look...lol.", "August 1, 2018", alana);
		alana_art = imageRepo.save(alana_art); 

		// Create Endusers
		Enduser Grandma = new Enduser("Grandma", false, "asdf");
		Grandma = enduserRepo.save(Grandma);
		Enduser Dad = new Enduser("Dad", true, "hello");
		Dad = enduserRepo.save(Dad);
		
		Enduser Grandpa = new Enduser("Grandpa", false, "asdf");
		Grandpa = enduserRepo.save(Grandpa);

		// Create Comments

		Comment comment1 = commentRepo.save(new Comment("I'm glad that it fits! He looks so handsome.", Grandma, oliver_jacket));
		Comment comment2 = commentRepo.save(new Comment("Awww, he looks so cute when he's napping.", Dad, oliver_sleep));
		Comment comment5 = commentRepo.save(new Comment("How creative! I love the little jellyfish.", Grandma, oliver_art));

		Comment comment3 = commentRepo.save(new Comment("She loves watermelons so much.", Grandma, penny_img1));
		Comment comment4 = commentRepo.save(new Comment("What a clever girl you are!", Dad, penny_img2));
		Comment comment6 = commentRepo.save(new Comment("Harsh! I think I look stunning", Dad, alana_art));
		Comment comment7 = commentRepo.save(new Comment("My baby girl is beautiful.", Grandma, alana_laugh));
		
	}

}
