package dev.fringe.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import dev.fringe.model.Href;
import dev.fringe.model.Message;
import dev.fringe.model.OutputMessage;

@Controller
public class HelloWorldController extends AbstractController {

	@GetMapping("/hello")
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView model = new ModelAndView("HelloWorldPage");
		model.addObject("msg", "hello world");
		return model;
	}

	@CrossOrigin
	@GetMapping("/test")
	public String test(Model model) throws Exception {
		model.addAttribute("currencyPair", "sdd");
		return "forex-view";
	}

	@CrossOrigin
	@GetMapping("header")
	public String header(Model model) throws Exception {
		List<Href> hrefs = Arrays.asList(new Href("home", "/"), new Href("Hello {name}", "/hello/world"),
				new Href("update-arrays-and-objects-view", "/update-arrays-and-objects-view"),
				new Href("update-arrays-and-objects-view", "/update-arrays-and-objects-view"),
				new Href("fetching-data-view", "/fetching-data-view"));
		model.addAttribute("name", "test app");
		model.addAttribute("header", hrefs);
		return "header";
	}
	
	@CrossOrigin
	@GetMapping("footer")
	public String footer(Model model) throws Exception {
		return "footer";
	}	

	  @MessageMapping("/chat")
	  @SendTo("/topic/message")
	  public OutputMessage sendMessage(Message message) {
	    System.out.println("Message sent");
	    return new OutputMessage(message, new Date());
	  }
}