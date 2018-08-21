package scrapbook;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartRestController {

	@Resource
	ImageRepository imageRepo;

	@Resource
	EnduserRepository enduserRepo;

	@Resource
	private HeartRepository heartRepo;

	public Iterable<Heart> findAllHearts() {
		return heartRepo.findAll();
	}

	// Add heart
	@PostMapping("/hearts/enduserUserName/{enduserUserName}/imageid/{imageId}")
	@Transactional
	public int addHeart(@PathVariable String enduserUserName, @PathVariable Long imageId)
			throws ImageNotFoundException, EnduserNotFoundException {
		Optional<Image> image = imageRepo.findById(imageId);
		if (!image.isPresent()) {
			throw new ImageNotFoundException();
		}		
		
		Optional<Enduser> enduser = enduserRepo.findByUserName(enduserUserName);
		if (!enduser.isPresent()) {
			throw new EnduserNotFoundException();
		}
		Optional<Heart> existing = heartRepo.findByEnduserAndImage(enduser.get(), image.get());

		if (!existing.isPresent()) {
			heartRepo.save(new Heart(enduser.get(), image.get()));
		} else {
			heartRepo.deleteByEnduserAndImage(enduser.get(), image.get());
		}
		
		return image.get().getHearts().size();
	}

	@DeleteMapping("/hearts/enduserUserName/{enduserUserName}/imageid/{imageid}")
	public int removeHeart(@PathVariable String enduserUserName, @PathVariable Long imageId)
			throws EnduserNotFoundException, ImageNotFoundException {
		Optional<Image> image = imageRepo.findById(imageId);
		if (!image.isPresent()) {
			throw new ImageNotFoundException();
		}
		Optional<Enduser> enduser = enduserRepo.findByUserName(enduserUserName);
		if (!enduser.isPresent()) {
			throw new EnduserNotFoundException();
		}
		heartRepo.deleteByEnduserAndImage(enduser.get(), image.get());
		return image.get().getHearts().size();
	}
}
