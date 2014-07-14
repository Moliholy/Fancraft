package se.umu.cs.fancraft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.umu.cs.fancraft.dao.CraftDAO;
 
@Controller
public class HelloWorldController {
	
    @RequestMapping("/hello.do")
    public ModelAndView helloWorld() {
 
        String message = "Hello World, Spring 3.0 + Beanstalk!";
        ModelAndView mav = new ModelAndView("hello", "message", message);
        mav.addObject("crafts", CraftDAO.getInstance().getCrafts());
        return mav;
    }
    
    @RequestMapping("/addCraft.do")
    public ModelAndView addCraft(@RequestParam String craft) {
    	
    	CraftDAO.getInstance().addCraft(craft);
 
        String message = "Hello World, Spring 3.0 + Beanstalk!";
        ModelAndView mav = new ModelAndView("hello", "message", message);
        mav.addObject("crafts", CraftDAO.getInstance().getCrafts());
        return mav;
    }
}
