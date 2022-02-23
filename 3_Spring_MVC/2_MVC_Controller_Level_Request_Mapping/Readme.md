# Spring MVC - Request Params and Request Mappings

## Reading HTML Form Data With @RequestParam Annotation

### Code Example

    > We want to create a new method to process data
    > Read the form Data: Student Name
    > Convert the name to upper case
    > Add the uppercase version to the model

 ### Bind variable using @RequestParam Annotation

 ```Java
    @RequestMapping("/processForm)
    public string letsShot(@RequestParam("studentName") String theName, 
    Model model){
        // now we can use the variable: theName
    }
 ```

    > @RequestParam("")
        - Spring will read param from request: studentName
        - Bind it to the variable: theName

## Code

### HelloWorldController.java
```Java
    package com.mvc.springdemo;

    import javax.servlet.http.HttpServletRequest;

    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    @Controller
    public class HelloWorldController{

        // need a controller method to show the initial HTML form
        @RequestMapping("/showForm")
        public String showForm(){
            return "helloWorld-form";
        }

        // need a controller method to process the HTML form
        @RequestMapping("/processForm")
        public String processForm(){
            return "helloWorld";
        }    
        
        @RequestMapping("/processFormVersionThree")
        public String letsShoutDudeAgain(@RequestParam("studentName") String theName, Model model){
            
            // convert the data to all caps
            theName = theName.toUpperCase();

            // create the message 
            String result = "Yo! "+theName;

            // add message to the model
            model.addAttribute("message",result);

            return "helloWorld";
        }
    }
```
### helloWorld.jsp

```Jsp
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html>
    <html>
    <head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
    </head>
    <body>
        Hello World of Spring
        <br><br>
        Student name : ${message}
    </body>
    </html>
```

# Controller Level Request Mapping

## Adding Request mapping to Controller

    > Serves as Parent mapping for controller
    > All request mappings on methods in the controller are relative
    > Similar to folder directory structures

## Controller Request Mapping

```Java
    // controller mapping
    @RequestMapping("/funny") 
    public class FunnyController{
        
        @RequestMapping("/showForm")  // /funny/showForm
        public String showForm(){
        ...
        }

        @RequestMapping("/processForm") // /funny/processForm
        public String process(HttpServletRequest request, Model model){
        ...
        }    
    }
```

