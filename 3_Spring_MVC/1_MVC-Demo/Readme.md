# Creating a Spring Home Controller and view

# Development Process
    
    1. Create Controller class
    2. Define Controller method
    3. Add Request mapping to Controller method
    4. Return View Name
    5. Develop view Page

## Step 1 : Create Controller Class

    > Annotate class with @Controller
        - @Controller inherits from @Component ... supports scanning
```Java
        @Controller
        public class HomeController{

        }
```
## Step 2 : Define Controller Method
```Java
        @Controller
        public class HomeController{

            // Spring MVC is Flexible. (You can use any method name)
            public String showMyPage(){
                ...
            }
        }    
```
## Step 3 : Add Request Mapping to Controller Method
```Java
        @Controller
        public class HomeController{

            // Annotation maps a path to a method name that's why you can choose any method name
            @RequestMapping("/")
            public String showMyPage(){
                ...
            }
        }       
```
## Step 4 :  Return View Name
```Java
        @Controller
        public class HomeController{

            // Annotation maps a path to a method name that's why you can choose any method name
            @RequestMapping("/")
            public String showMyPage(){

                // view name
                return "main-menu";
            }
        } 
```
## Step 5 :  Develop View Page
    
    File:- /WEB-INF/view/main-menu.jsp
```HTML
        <html><body>
        <h2> Spring MVC Demo - Home Page </h2>
        </body></html>
```
# Reading HTML Form Data

## Application Flow
                                                                         hellowworld-form.jsp
    +----------+  REQUEST MAPPING      +-------------+          +--------------------------------------+
    |          |     /showForm         | HelloWorld  |          | What's your name    +--------------+ |
    |          | --------------------> |             | ------>> |                     | SubmitQuesry | |
    |          |                       | Controller  |          |                     +--------------+ |
    +----------+                       +-------------+          +--------------------------------------+


            hellowworld-form.jsp                                                          helloWorld.jsp
    +--------------------------------------+  REQUESTMAPPING   +-------------+         +-----------------------------+
    | What's your name    +--------------+ |  /processForm     | HellowWorld |         |  Hello World of Spring!     |
    |                     | SubmitQuesry | | ---->-----------> |             | ------> |                             |
    |                     +--------------+ |                   | Controller  |         |  Student name : Ravi        |
    +--------------------------------------+                   +-------------+         +-----------------------------+

## Controller Class
```Java
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
    }
``` 

# Development Process
    1. Create Controller class
    2. Show HTML form
        a. Create controller method to show HTML Form
        b. Create View Page for HTML form
    3. Process HTML Form
        a. Create Controller method to process HTML Form
        b. Develop View Page for Confirmation

# Code
## HomeController.java
```Java
    package com.mvc.springdemo;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;

    @Controller
    public class HomeController {
        
        @RequestMapping("/")
        public String displayPage() {
            return "main-menu";
        }
    }

```

## HelloWorldController.java
```Java
    package com.mvc.springdemo;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;

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
    }
```

## main-menu.jsp
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
        <h2>Spring MVC Demo - Hello Framework</h2>
        <hr>
        <a href="showForm">Hello World form</a>
    </body>
    </html>
```

## helloWorld-form.jsp
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
        <form action="processForm" method="GET">
            <input type="text" name="studentName"
                placeholder="What's your name?"
            />
            <input type="submit" />
        </form>
    </body>
    </html>
```

## helloWorld.jsp
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
        Student name : ${param.studentName}
    </body>
    </html>
```

# Adding Data to Spring Model

## Spring Model

    > The Model is a container for your application
    > In your Controller
        - You can put anything in the model
            - strings, objects, info from database, etc ...
    > Your View Page (JSP) can access data from the model

## Code Example

    > We want to create a new method to process form data 
    > Read the form data: student's name
    > Convert the name to upper case 
    > Add the uppercase version to the model

## Parsing Model to your Controller

```Java
    @RequestMapping("/processFormVersionTwo")
    public String letsShoutDude(HttpServletRequest request, Model model){
        
        // read the request parameter from the HTML form
        String theName = request.getParameter("studentName");

        // convert the data to all caps
        theName = theName.toUpperCase();

        // create the message 
        String result = "Yo! "+theName;

        // add message to the model
        model.addAttribute("message",result);

        return "helloworld";
    }
```

    > HttpServletRequest -> Holds HTML form data
    > Model -> container for your data

## View Template - JSP

```Jsp
    <html><body>
    Hello world of spring!
    ...
    The message: ${message}
```

## Adding more data to your Model

```Java
    // get the data
    //
    String result= ...
    List<String> theStudentList = ...
    ShoppingCart theShoppingCart = ...

    //add data to the model
    //
    model.addAttribute("message", result);
    model.addAttribute("students", theStudentList);
    model.addAttribute("shoppingCart", theShoppingCart);
    
```