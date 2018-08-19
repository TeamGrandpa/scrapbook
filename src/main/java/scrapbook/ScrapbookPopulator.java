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

	@Resource
	private HeartRepository heartRepo;

	@Override
	public void run(String... args) throws Exception {

		// Create Kids
		Kid oliver = new Kid("Oliver", "/portraits/OliverOne.png", 1, true);
		oliver = kidRepo.save(oliver);

		Kid penny = new Kid("Penny", "/portraits/Penny_One.jpg", 2, false);
		penny = kidRepo.save(penny);

		Kid alana = new Kid("Alana", "/portraits/AlanaOne.jpg", 3, false);
		alana = kidRepo.save(alana);

		// Create Images
		Image oliver_jacket = new Image("/img/OliverTwo.jpg",
				"Oliver loves his new leather jacket. Looking cool thanks to Grandma!", "October 24, 2016", oliver);
		oliver_jacket = imageRepo.save(oliver_jacket);

		Image oliver_sleep = new Image("/img/OliverThree.jpg", "Oli at nap time.", "May 12, 2018", oliver);
		oliver_sleep = imageRepo.save(oliver_sleep);

		Image oliver_art = new Image("/img/ocean.jpg", "Drawn at daycare today!",
				new SimpleDateFormat("MMMM d, yyyy").format(new Date()), oliver);
		oliver_art = imageRepo.save(oliver_art);

		Image penny_three = new Image("/img/Penny_Three.jpg", "Halloween 2017", "October 31, 2017", penny);
		penny_three = imageRepo.save(penny_three);

		Image penny_art = new Image("/img/Penny_art.jpg", "Penny's newest masterpiece!", "May 5, 2018", penny);
		penny_art = imageRepo.save(penny_art);

		Image penny_two = new Image("/img/Penny_Two.jpg", "Picking flowers and making wishes", "June 5, 2018", penny);
		penny_two = imageRepo.save(penny_two);

		Image alana_laugh = new Image("/img/AlanaTwo.jpg", "Alana is such a happy baby!", "January 23, 2018", alana);
		alana_laugh = imageRepo.save(alana_laugh);

		Image alana_art = new Image("/img/AlanaArt.jpg",
				"She said this was a picture of daddy. Not his best look...lol.", "February 1, 2018", alana);
		alana_art = imageRepo.save(alana_art);

		Image alana_baby = new Image("/img/AlanaFour.jpg", "Can't believe she's already 5!", "March 9, 2018", alana);
		alana_baby = imageRepo.save(alana_baby);

		Image alana_inPink = new Image("/img/AlanaThree.jpg", "Alana's Easter dress", "April 1, 2018", alana);
		alana_inPink = imageRepo.save(alana_inPink);

		// Create Endusers
		Enduser Grandma = new Enduser("Grandma", false, "asdf");
		Grandma = enduserRepo.save(Grandma);
		Enduser Dad = new Enduser("Dad", true, "hello");
		Dad = enduserRepo.save(Dad);

		Enduser Grandpa = new Enduser("Grandpa", false, "asdf");
		Grandpa = enduserRepo.save(Grandpa);

		// Create Hearts
		Heart heartGrandma = new Heart(Grandma, penny_two);
		heartGrandma = heartRepo.save(heartGrandma);

		Heart heartDad = new Heart(Dad, penny_two);
		heartDad = heartRepo.save(heartDad);

		Heart heartDad7 = new Heart(Dad, penny_three);
		heartDad7 = heartRepo.save(heartDad7);

		Heart heartDad8 = new Heart(Dad, penny_art);
		heartDad8 = heartRepo.save(heartDad8);

		Heart heartDad2 = new Heart(Dad, alana_art);
		heartDad2 = heartRepo.save(heartDad2);

		Heart heartGrandma2 = new Heart(Grandma, oliver_jacket);
		heartGrandma2 = heartRepo.save(heartGrandma2);

		Heart heartDad3 = new Heart(Dad, oliver_jacket);
		heartDad3 = heartRepo.save(heartDad3);

		Heart heartDad4 = new Heart(Dad, oliver_art);
		heartDad4 = heartRepo.save(heartDad4);

		Heart heartDad5 = new Heart(Dad, oliver_sleep);
		heartDad5 = heartRepo.save(heartDad5);

		Heart heartDad6 = new Heart(Dad, alana_laugh);
		heartDad6 = heartRepo.save(heartDad6);

		// Create Comments

		Comment comment1 = commentRepo
				.save(new Comment("I'm glad that it fits! He looks so handsome.", Grandma, oliver_jacket));
		Comment comment2 = commentRepo
				.save(new Comment("Awww, he looks so cute when he's napping.", Grandma, oliver_sleep));
		Comment comment5 = commentRepo
				.save(new Comment("How creative! I love the little jellyfish.", Grandma, oliver_art));

		Comment comment3 = commentRepo.save(new Comment("Photo credit: Dad", Dad, penny_two));
		Comment comment16 = commentRepo.save(new Comment("This is too cute!", Grandma, penny_two));
		Comment comment17 = commentRepo.save(new Comment("Currently hanging on the fridge", Dad, penny_art));
		Comment comment18 = commentRepo.save(new Comment("Have Penny draw something for Grandma", Grandma, penny_art));
		Comment comment19 = commentRepo.save(new Comment("She's on it mom", Dad, penny_art));

		Comment comment4 = commentRepo.save(new Comment("ROAR!", Grandma, penny_three));
		Comment comment6 = commentRepo.save(new Comment("Harsh! I think I look stunning", Dad, alana_art));
		Comment comment7 = commentRepo.save(new Comment("My baby girl is beautiful.", Grandma, alana_laugh));

		Comment comment8 = commentRepo.save(
				new Comment("When are you coming over? I already set up the Easter egg hunt!", Grandma, alana_inPink));
		Comment comment9 = commentRepo.save(new Comment("OMW, Mom", Dad, alana_inPink));
		Comment comment10 = commentRepo.save(new Comment("?", Grandma, alana_inPink));
		Comment comment11 = commentRepo.save(new Comment("On my way", Dad, alana_inPink));
		Comment comment12 = commentRepo.save(new Comment("OK", Grandma, alana_inPink));

		Comment comment13 = commentRepo
				.save(new Comment("Probably dreaming about causing trouble...just cleaned Play-doh out of the bathtub",
						Dad, oliver_sleep));
		Comment comment14 = commentRepo.save(new Comment("LOL", Grandma, oliver_sleep));

		Comment comment15 = commentRepo.save(new Comment("Happy Birthday, Alana!", Dad, alana_baby));
	}

}
