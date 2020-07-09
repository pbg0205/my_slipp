package net.slipp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller	//Controller 역할을 한다는 의미의 annotation
public class WelcomeController {
	@GetMapping("/helloworld") //서버 요청을 위해 GetMapping이라는 annotation을 사용
	public String welcome(String name, int age, Model model) { //Model : 브라우저에 객체를 전달하기 위한 인자
		System.out.println("name: " + name + " age: " + age);
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "welcome";
	}

}
