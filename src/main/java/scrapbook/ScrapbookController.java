package scrapbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ScrapbookController {

	@Resource
	KidRepository kidRepo;

	@Resource
	ImageRepository imageRepo;

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
	public String addNewChannel() {
		return "newkid";
	}

	@RequestMapping("/add-image")
	public String addImage(@RequestParam(value = "id") long id, Model model) throws KidNotFoundException {
		Optional<Kid> kid = kidRepo.findById(id);

		if (kid.isPresent()) {
			model.addAttribute("kids", kid.get());
			return "add-image";
		}
		throw new KidNotFoundException();
	}
	
	@RequestMapping("/add-status")
	public String addStatus(@RequestParam(value = "id") long id, Model model) throws KidNotFoundException {
		Optional<Kid> kid = kidRepo.findById(id);

		if (kid.isPresent()) {
			model.addAttribute("kids", kid.get());
			return "add-status";
		}
		throw new KidNotFoundException();
	}

	private String getUploadDirectory() {
		// Determine where uploads should be saved
        String userHomeDirectory = System.getProperty("user.home");
        String uploadDirectory = Paths.get(userHomeDirectory, "scrapbook-uploads").toString();
        
        // Create if needed
        new File(uploadDirectory).mkdirs();
        
        // Return path
        return uploadDirectory;
	}
	
	@PostMapping("/uploadImage")
	public String uploadImage(
		@RequestParam("caption") String caption,
		@RequestParam("childName") String kidName,
		@RequestParam("file") MultipartFile imageFile,
		Model model
	) throws IOException {
		
		// Upload image - stream uploaded data to a temporary file
		String fileName = imageFile.getOriginalFilename();
		if ("".equalsIgnoreCase(fileName)) {
			Optional<Kid> kidOptional = kidRepo.findByName(kidName);
			Kid kidForImage = kidOptional.get();
			long kidId = kidForImage.getId();
			return "redirect:/kid?id=" + kidId;
		}
		File tempFile = File.createTempFile(fileName, "");
        FileOutputStream fos = new FileOutputStream(tempFile); 
        fos.write(imageFile.getBytes());
        fos.close(); 
		
        // Transfer the temporary file to its permanent location
        String uploadDirectory = getUploadDirectory();
		File fileUpload = new File(uploadDirectory, fileName); // TODO: ensure it doesn't already exist
		imageFile.transferTo(fileUpload);
		
		
		//Add image to imageRepo
		Optional<Kid> kidOptional = kidRepo.findByName(kidName);
		Kid kidForImage = kidOptional.get();
		long kidId = kidForImage.getId();
		
		String imageUrl = "/uploadedimage/" + fileName;
		Image image = imageRepo.save(new Image(imageUrl, caption, new SimpleDateFormat("MMMM d, yyyy").format(new Date()), kidForImage));
		
		model.addAttribute("flashMessage", "File uploaded successfully.");
		
		return "redirect:/kid?id=" + kidId;
	}
	
	@GetMapping("/uploadedimage/{file}")
	public void serveImage(
		HttpServletRequest request,
		HttpServletResponse response,
		@PathVariable("file") String fileName
	) throws Exception {
		
		// Determine path of requested file
		Path filePath = Paths.get(getUploadDirectory(), fileName);
		String filePathString = filePath.toString();
		File requestedFile = new File(filePathString);
		
		// Ensure requested item exists and is a file
		if (!requestedFile.exists() || !requestedFile.isFile()) {
			throw new Exception();
		}
		
		// Determine and set correct content type of response
		String fileContentType= Files.probeContentType(filePath);
	    response.setContentType(fileContentType);
		
		// Serve file by streaming it directly to the response
		InputStream in = new FileInputStream(filePathString);		
	    IOUtils.copy(in, response.getOutputStream());

	}

}
