package scrapbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EditorController {
	
	@Resource
	KidRepository kidRepo;
	
	@Resource
	private ImageRepository imageRepo;
	
	@Resource
	private CommentRepository commentRepo;
	
	//image uploader may go here??
	
	@RequestMapping("/login")
	public String editorLoginPage() {
		//serves as login page
		
		return "login";
	}
	
	@RequestMapping("/editor/login")
	public String editorLogin(
		HttpServletResponse response // We need the response so we can add cookies,
	) {
		
		Cookie editorRoleCookie = new Cookie("role", "editor");
		editorRoleCookie.setHttpOnly(true); // Only server can modify the cookie
		editorRoleCookie.setMaxAge(300); // Expires after 300 seconds (5 min)
		response.addCookie(editorRoleCookie);
		
		// Redirect the user back to the editor page once login is complete
		// The new cookie will allow the user to access the page
		return "redirect:/editor";
	}

	@RequestMapping("/editor/logout")
	public String editorLogin(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		
		// Find "role" cookie, set it to immediately expire, and send that update to the browser
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		
		return "redirect:/editor";
	}
	
	
	@RequestMapping("/editor")
	public String editorPanel(
		@CookieValue(name = "role", defaultValue = "") String role,
		Model model
	) {
//		
//		System.out.println("ROLE: " + role);
//		
//		if (role == null || !role.equals("editor")) {
//			return "redirect:/login";
//		}
//		
//		System.out.println("SUCCESS");
//		
//		Iterable<Kid> kids = kidRepo.findAll();
//		model.addAttribute("kids", kids);

		return "editor";
	}

}
