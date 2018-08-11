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
	private MessageRepository messageRepo;

	@Resource
	private EnduserRepository enduserRepo;

	@Override
	public void run(String... args) throws Exception {

		// Create Kids
		Kid sam = new Kid("Sam", "sam_portrait.jpg", 1, true);
		sam = kidRepo.save(sam);

		Kid penny = new Kid("Penny", "penny_portrait.jpg", 2, false);
		penny = kidRepo.save(penny);

		Kid doug = new Kid("Doug", "sam_portrait.jpg", 3, false);
		doug = kidRepo.save(doug);

		// Create Images
		Image sam_baby = new Image("/img/sam_baby.jpg", "Sam's first time seeing fall leaves", "October 24, 2016", sam);
		sam_baby = imageRepo.save(sam_baby);

		Image sam_img2 = new Image("/img/sam_img2.jpg", "Sam making faces", "May 12, 2018", sam);
		sam_img2 = imageRepo.save(sam_img2);

		Image sam_img1 = new Image("/img/sam_img1.jpg", "Sam in the park",
				new SimpleDateFormat("MMMM d, yyyy").format(new Date()), sam);
		sam_img1 = imageRepo.save(sam_img1);

		Image penny_img1 = new Image("/img/penny_img1.jpg", "Penny enjoying watermelon", "June 5, 2018", penny);
		penny_img1 = imageRepo.save(penny_img1);

		Image penny_img2 = new Image("/img/penny_img2.jpg", "Penny playing",
				new SimpleDateFormat("MMMM d, yyyy").format(new Date()), penny);
		penny_img2 = imageRepo.save(penny_img2);

		// Create Endusers
		Enduser Grandma = new Enduser("Grandma", false, "asdf");
		Grandma = enduserRepo.save(Grandma);
		Enduser Dad = new Enduser("Dad", true, "hello");
		Dad = enduserRepo.save(Dad);
		
		Enduser Grandpa = new Enduser("Grandpa", false, "asdf");
		Grandpa = enduserRepo.save(Grandpa);

		// Create Comments
		Comment comment1 = commentRepo.save(new Comment("You are so special!", Grandma, sam_img1));
		Comment comment2 = commentRepo.save(new Comment("So funny!", Dad, sam_img2));
		Comment comment5 = commentRepo.save(new Comment(
				"Why are you wearing a shirt with Leo's name on it?  I thought your name was Sam.", Grandma, sam_img1));
		Comment comment3 = commentRepo.save(new Comment("She loves watermelons so much.", Grandma, penny_img1));
		Comment comment4 = commentRepo.save(new Comment("What a clever girl you are!", Dad, penny_img2));

		// Create Messages
		Message message1 = messageRepo.save(new Message("To My Special Grandson, Happy Birthday. You shine every way! "
				+ "I’m so proud of the amazing person you are, and hope this year brings you everything you’ve been wishing for. "
				+ "The sky’s the limit for you, today and always!", sam));

		Message message2 = messageRepo.save(new Message(
				"My wish for you is simple...Have the very best life imaginable, live like you will never grow old, laugh, giggle cry if you must...Play as hard as you work, "
						+ "maketime for the people and things you adore...Wake up with a smile on your face...And, know that "
						+ "you are always loved. This is my wish for you.", sam));

		Message message3 = messageRepo.save(new Message("Your untouched innocence makes my heart melt.", penny));
//		"heart you've made \nSome very special ties \nYour flawless little face \nFingers that tangle with mine \nBrings a feeling " +
//				"so divine \nYour heart-melting smile \nEverything you do \nMakes my life worthwhile", penny));

		Message message4 = messageRepo.save(
				new Message("I am so proud of you \nand happy for you. \nKeep learning and keep growing.", penny));
	}

}
