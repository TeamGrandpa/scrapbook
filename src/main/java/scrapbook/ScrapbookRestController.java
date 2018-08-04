package scrapbook;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping("/addComment")
	public Iterable<Comment> addComment(
			@RequestParam(name = "commentText") String commentText,
			@RequestParam(name = "authorName") String authorName, 
			@RequestParam(name = "imageId") long imageId) {

		Optional<Image> imageOptional = imageRepo.findById(imageId);
		Image image = imageOptional.get();

		if (commentText != "") {
			commentRepo.save(new Comment(commentText, authorName, image));
		}

		return commentRepo.findByImageId(imageId);
	}

}
