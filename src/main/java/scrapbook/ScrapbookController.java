package scrapbook;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScrapbookController {

	@Resource
	KidRepository kidRepo;

	@Resource
	ImageRepository imageRepo;

	@Resource
	MessageRepository messageRepo;

	@Resource
	CommentRepository commentRepo;

	@RequestMapping("/kid")
	public String findOneKid(@RequestParam(value = "id") long id, Model model) throws KidNotFoundException {
		Optional<Kid> kid = kidRepo.findById(id);

		if (kid.isPresent()) {
			model.addAttribute("kids", kid.get());
			return "kid";
		}
		throw new KidNotFoundException();

	}

	@RequestMapping("/kids")
	public String findAllKids(Model model) {
		model.addAttribute("kids", kidRepo.findAll());
		return ("kids");
	}
	
	
	@RequestMapping("/image")
	public String findOneImage(@RequestParam(value = "id") long id, Model model) throws ImageNotFoundException {
		Optional<Image> image = imageRepo.findById(id);

		if (image.isPresent()) {
			model.addAttribute("images", image.get());
			return "image";
		}
		throw new ImageNotFoundException();

	}

	@RequestMapping("/images")
	public String findAllImages(Model model) {
		model.addAttribute("images", imageRepo.findAll());
		return ("images");
	}

	@RequestMapping("/message")
	public String findOneMessage(@RequestParam(value = "id") long id, Model model) throws MessageNotFoundException {
		Optional<Message> message = messageRepo.findById(id);

		if (message.isPresent()) {
			model.addAttribute("messages", message.get());
			return "message";
		}
		throw new MessageNotFoundException();
	}

	@RequestMapping("/messages")
	public String findAllMessages(Model model) {
		model.addAttribute("messages", messageRepo.findAll());
		return ("messages");
	}

	@RequestMapping("/comment")
	public String findOneComment(@RequestParam(value = "id") long id, Model model) throws CommentNotFoundException {
		Optional<Comment> comment = commentRepo.findById(id);

		if (comment.isPresent()) {
			model.addAttribute("comments", comment.get());
			return "comment";
		}
		throw new CommentNotFoundException();
	}

	@RequestMapping("/comments")
	public String findAllComments(Model model) {
		model.addAttribute("comments", commentRepo.findAll());
		return("comments");
		
		
	}
	
	@RequestMapping("/add-new-channel")
	public String index() {
		return "newkid";
	}

}
