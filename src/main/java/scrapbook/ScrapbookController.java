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

}
