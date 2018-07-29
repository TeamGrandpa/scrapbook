package scrapbook;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.HeaderAssertions;

import antlr.MakeGrammar;

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

	@Override
	public void run(String... args) throws Exception {
		
		//Create Kids
		Kid sam = new Kid("Sam");
		sam = kidRepo.save(sam);
		
		Kid penny = new Kid("Penny");
		penny = kidRepo.save(penny);
		
		//Create Images
		Image sam_img1 = new Image("/images/sam_img1.jpg", "Sam in the park", sam);
		sam_img1 = imageRepo.save(sam_img1);
		
		Image sam_img2 = new Image("/images/sam_img2.jpg", "Sam making faces", sam );
		sam_img1 = imageRepo.save(sam_img2);
		
		Image penny_img1 = new Image("/images/penny_img1.jpg", "Penny enjoying watermelon", penny);
		penny_img1 = imageRepo.save(penny_img1);
		
		Image penny_img2 = new Image("/images/penny_img2.jpg", "Penny playing", penny);
		penny_img2 = imageRepo.save(penny_img2);
		
		//Create Comments
		Comment comment1 = commentRepo.save(new Comment("You are so special!", sam_img1));
		Comment comment2 = commentRepo.save(new Comment("So funny!", sam_img2));
		Comment comment3 = commentRepo.save(new Comment("She loves watermelons so much", penny_img1));
		Comment comment4 = commentRepo.save(new Comment("What a clever girl you are!", penny_img2));
	
		//Create Messages
		Message message1 = messageRepo.save(new Message("To My Special Grandson, Happy Birthday. You shine every way! " +
				"I�m so proud of the amazing person you are, and hope this year brings you everything you�ve been wishing for. "+
				"The sky�s the limit for you, today and always!", sam));
		
		Message message2 = messageRepo.save(new Message("My wish for you is simple...Have the very best life imaginable!", sam));
//		"imaginable, live like you will never grow old, laugh, giggle cry if you must...Play as hard as you work, " +
//				"maketime for the people and things you adore...Wake up with a smile on your face...And, know that " +
//		"you are always loved. This is my wish for you.", sam));
		
		Message message3 = messageRepo.save(new Message("Your untouched innocence makes my heart melt.", penny));
//		"heart you've made \nSome very special ties \nYour flawless little face \nFingers that tangle with mine \nBrings a feeling " +
//				"so divine \nYour heart-melting smile \nEverything you do \nMakes my life worthwhile", penny));
		
		Message message4 = messageRepo.save(new Message("I am so proud of you \nand happy for you. \nKeep learning and keep growing.", penny));
	}

}