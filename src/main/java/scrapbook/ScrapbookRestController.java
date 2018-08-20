package scrapbook;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ScrapbookRestController {

	@Resource
	public KidRepository kidRepo;

	@Resource
	public ImageRepository imageRepo;

	@Resource
	public CommentRepository commentRepo;
	
	@Resource
	public EnduserRepository enduserRepo;
	
	@Resource
	public HeartRepository heartRepo;

	@GetMapping("/all-kids")
	public Iterable<Kid> findAllKids() {
		return kidRepo.findAll();
	}

	@GetMapping("/kid-images/{id}")
	public Iterable<Image> findAllImagesByKidId(@PathVariable long id) {
		return imageRepo.findByKidIdOrderByIdDesc(id);
	}

	@GetMapping("/image-comments/{id}")
	public Iterable<Comment> findAllCommentsByImageId(@PathVariable long id) {
		return commentRepo.findByImageId(id);
	}
	
	@PutMapping("/removeImage")
	public Iterable<Image> removeImage(
			@RequestParam(name = "kidId") long kidId,
			@RequestParam(name = "imageId") long imageId) {
		
		Optional<Image> imageOptional = imageRepo.findById(imageId);
		Image imageToRemove = imageOptional.get();
		
		Collection<Comment> comments = imageToRemove.getComments();
		for (Comment comment : comments) {
			commentRepo.delete(comment);
		}
		
		Collection<Heart> hearts = imageToRemove.getHearts();
		for(Heart heart : hearts) {
			heartRepo.delete(heart);
		}
		
		imageRepo.delete(imageToRemove);
		
		return imageRepo.findByKidIdOrderByIdDesc(kidId);
	}

	@PostMapping("/addComment")
	public Iterable<Comment> addComment(
			@RequestParam(name = "commentText") String commentText,
			@RequestParam(name = "authorName") String authorName, 
			@RequestParam(name = "imageId") long imageId) {

		Optional<Image> imageOptional = imageRepo.findById(imageId);
		Image image = imageOptional.get();
		
		Optional<Enduser> enduserOptional = enduserRepo.findByUserName(authorName);
		Enduser enduser = enduserOptional.get();
		
		if (commentText != "") {
			commentRepo.save(new Comment(commentText, enduser, image));
		}

		return commentRepo.findByImageId(imageId);
	}
	
	@PutMapping("/removeComment")
	public Iterable<Comment> removeComment(@RequestParam(name = "commentId") long commentId) {
		Optional<Comment> commentOptional = commentRepo.findById(commentId);
		Comment commentToRemove = commentOptional.get();
		Image image = commentToRemove.getImage();
		long imageId = image.getId();	
		
		commentRepo.delete(commentToRemove);
		
		return commentRepo.findByImageId(imageId);	
	}
	

}
