package net.slipp.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		
		if(user == null) {
			System.out.println("Login Failure! ");
			return "redirect:/users/loginForm";
		}
		
		if(!password.equals(user.getPassword())) {
			System.out.println("Login Failure! ");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("Login Success!!");
		session.setAttribute("sessionedUser", user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String form() {
		return "user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("user: " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("") //조회창을 호출하는 메소드
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("{id}/form") //수정하고자 하는 정보를 호출하는 메소드
	public String updateform(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if(tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User)tempUser;
		if(!id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "user/updateform";
	}
	
	@PutMapping("/{id}") //수정하고자  하는 id 정보를 호출하는 메소드
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if(tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User)tempUser;
		if(!id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userRepository.findById(id).get();
		user.update(updatedUser); //user class에서 수정하고 싶은 정보를 수정하는 메소드 
		userRepository.save(user);
		return "redirect:/users";
	}
}
