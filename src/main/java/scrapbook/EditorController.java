package scrapbook;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditorController {

	@Resource
	KidRepository kidRepo;

	@Resource
	private ImageRepository imageRepo;

	@Resource
	private CommentRepository commentRepo;

	@Resource
	private EnduserRepository enduserRepo;

	@RequestMapping("/login")
	public String authLoginPage() {
		// serves as login page
		return "login";
	}

	@RequestMapping("/auth/login")
	public String authLogin(HttpServletResponse response, // We need the response so we can add cookies,
			@RequestParam("username") String username, @RequestParam("password") String password) {
		Optional<Enduser> enduserOpt = enduserRepo.findByUserName(username);
		
		if (!enduserOpt.isPresent()) {
			return "redirect:/login";
		}

		Enduser enduser = enduserOpt.get();
		String savedPassword = enduser.getPassword();

		if(!(savedPassword.equalsIgnoreCase(password))) {
			return "redirect:/login";
		}
		
		String name = enduser.getUserName();
		String role = "";

		if (enduser.getIsEditor()) {
			role = "editor";
		} else {
			role = "viewer";
		}

		Cookie editorRoleCookie = new Cookie("role", role);
		editorRoleCookie.setPath("/");
		editorRoleCookie.setMaxAge(60 * 60); // Expires after 3600 seconds (1 hour)
		response.addCookie(editorRoleCookie);
		
		Cookie userNameCookie = new Cookie("name", name);
		userNameCookie.setPath("/");
		userNameCookie.setMaxAge(60 * 60);
		response.addCookie(userNameCookie);

		return "redirect:/kids";

	}

	@RequestMapping("/auth/logout")
	public String editorLogin(HttpServletRequest request, HttpServletResponse response) {

		// Find "role" cookie, set it to immediately expire, and send that update to the
		// browser
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		return "redirect:/auth";
	}
//
//	@RequestMapping("/auth")
//	public String editorPanel(@CookieValue(name = "role", defaultValue = "") String role, Model model) {
//
//		if (role == null || !role.equals("auth")) {
//			return "redirect:/login";
//		}
//
//		System.out.println("SUCCESS");
//
//		Iterable<Kid> kids = kidRepo.findAll();
//		model.addAttribute("kids", kids);
//
//		return "auth";
//	}

}
