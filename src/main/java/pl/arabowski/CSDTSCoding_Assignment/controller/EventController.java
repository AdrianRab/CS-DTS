package pl.arabowski.CSDTSCoding_Assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.arabowski.CSDTSCoding_Assignment.model.Event;
import pl.arabowski.CSDTSCoding_Assignment.repository.EventRepository;
import pl.arabowski.CSDTSCoding_Assignment.service.EventServiceImplementation;

@Controller
@RequestMapping("/process-logs")
public class EventController {
	
	@Autowired
	private EventServiceImplementation eventServiceImpl;
	
	@Autowired
	private EventRepository eventRepo;
	
	private String path = "src/main/resources/file/input.txt";
	
	@GetMapping(value = { "/","/home"})
	public ModelAndView homePage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	@GetMapping("/run")
	public ModelAndView processData() {
		ModelAndView mav = new ModelAndView();
		eventServiceImpl.processLogs(path);
		mav.setViewName("redirect:http://localhost:8080/process-logs/results");
		return mav;
	}
	
	@GetMapping("/results")
	public ModelAndView result() {
		ModelAndView mav = new ModelAndView();
		List<Event> events = eventRepo.findAll();
		mav.addObject("eventsList", events);
		mav.setViewName("results");
		return mav;
	}
}
