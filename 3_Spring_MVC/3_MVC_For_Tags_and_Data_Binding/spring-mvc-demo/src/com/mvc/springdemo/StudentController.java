package com.mvc.springdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@RequestMapping("/showForm")
    public String showForm(Model theModel){
        
		// create a new Student object
		Student theStudent = new Student();
		
		// add student object to the model
		theModel.addAttribute("student",theStudent);
        
		return "student-form";
    }
	
	@RequestMapping("/processForm")
    public String processForm(@ModelAttribute("student") Student theStudent){
        
        // log the input data
		
        System.out.println("theStudent: "+theStudent.getFirstName()+" "+theStudent.getLastName());
        System.out.println("Country Code: "+theStudent.getCountry());
        System.out.println("Favorite language: "+theStudent.getFavoriteLanguage());
        System.out.println("Operating Systems: "+theStudent.getOperatingSystems().length);
        
		return "student-confirmation";
    }
}
