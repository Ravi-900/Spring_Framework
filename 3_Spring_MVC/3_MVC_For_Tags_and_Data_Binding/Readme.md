# Spring MVC - Form Tags and Data Binding

    > Spring MVC Form Tags are the building block for a web page
    > Form tags are configurable and reusable for a web page

## Data Binding

    > Spring MVC Form Tags can make use of data binding
    > Automatically setting / retrieving data from a Java object / bean

## Spring MVC Form Tags

    > Form tags will generate HTML for you :-)
    +--------------------+--------------------------+
    |   Form Tag         |  Description             |
    +--------------------|--------------------------+
    |   form:form        |  main from container     |
    |   form:input       |  text field              |
    |   form:textarea    |  multi-line text field   |
    |   form:checkbox    |  check box               |
    |   form:radiobutton |  radio buttons           |
    |   form:select      |  frop down list          |
    +--------------------+--------------------------+

## Web Page Structure
    
    > Jsp page with special Spring MVC Form tags

```JSP

    <html>
        ... regular html ...
        ... Spring MVC form tags ...
        ... more html ...
    </html>

```
## How to Reference Spring MVC Form Tags

    > Specify the Spring namespace at beginning of JSP file

```Jsp

    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
```

## Spring MVC Form Tag - Txt Field

### Showing Form

    In your Spring Controller
        > Before you show the form, you must add a model attribute
        > This is a bean that will hold form data for the data binding

### Show Form - Add Model Attribute

```Java

    @RequestMapping("/showForm")
    public String showForm(Model theModel){
        theModel.addAttribute("student",new Student());
        return "student-form";
    }
```

### Setting up HTML Form - Data Binding

```JSP
    <form:form action="processForm" modelAttribute="student">
        First name: <form:input path="firstName" />
       <br><br>
        Last name: <form:input path="LastName" />
        <br><br>
        <input type="submit" value="Submit" />
    </form:form>
```

    > When form is loaded, Spring MVC will call the getter methods:
     student.getFirstName(), student.getLastName()

    > When form is submitted, Spring MVC will call the setter methods:
     student.setFirstName(), student.setLastName()

### Handling Form Submission in the Controller

```Java

    @RequestMapping("/processForm")
    public String processForm(@ModelAttribute("student") Student theStudent){
            
        // log the input data
        System.out.println("theStudent: "+theStudent.getFirstName()+""+theStudent.getLastName());
        return "student-confirmation";
    }
```

### Confirmation page

```JSP

    <html>
    <body>
        The student is confirmed: ${student.firstName} ${student.lastName}
    </body>
    </html>
```

## Development Process

    1> Create Student class
    2> Create Student Controller class
    3> Create Html form
    4> Create form processing code
    5> Create confirmation page

# Spring MVC Form Tag - Drop Down List

## Spring MVC Tag

    > Drop Down list is represented by the tag
        <form:select>

    > Code Snippet
```Jsp

    <form:select path="country">
        <form:option value="Brazil" label="Brazil" />
        <form:option value="France" label="France" />
        <form:option value="Germany" label="Germany" />
        <form:option value="India" label="India" />
    </form:select>
```
    
## Develoment Process

    1> Update HTML Form
    2> Update Student class -add getter/setter for new property
    3> Update confirmation page

# Spring MVC Form Tag - Radio Buttons

    > A Radio Button is represented by the tag
        <form:radiobutton>
    
```JSP
    Java <form:radiobutton path="favoriteLanguage" value="Java" />
    C# <form:radiobutton path="favoriteLanguage" value="C#" />
    PHP <form:radiobutton path="favoriteLanguage" value="PHP" />
    Ruby <form:radiobutton path="favoriteLanguage" value="Ruby" />
```

## Development Process

    1> Update HTML Form
    2> Update Student class -add getter/setter for new property
    3> Update confirmation page 

# Spring MVC Form Tag - Check BOX

    > A Check Box is represented by the tag
        <form: checkbox>
    
```JSP
    Linux <form:checkbox path="operatingSystems" value "Linux" />
    Mac OS <form:checkbox path="operatingSystems" value "Mac OS" />
    MS Windows <form:checkbox path="operatingSystems" value "MS Windows" />
```
    > check box is used when we want to select more than one option
    > in java Code: we need to add support when user selects multiple options
    > Array of String : Add appropriate get/set methods

## Development Process

    1> Update HTML Form
    2> Update Student class -add getter/setter for new property
    3> Update confirmation page 

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

## Student.java
```Java
    package com.mvc.springdemo;

    import java.util.LinkedHashMap;

    public class Student {

        private String firstName;
        private String lastName;
        private String country;
        private String favoriteLanguage;
        private String[] operatingSystems;
        
        private LinkedHashMap<String, String> countryOptions;
        
        public Student() {
            // populate country options: used ISO country code
            countryOptions = new LinkedHashMap<>();
            
            countryOptions.put("BR", "Brazil");
            countryOptions.put("FR", "France");
            countryOptions.put("DE", "Germany");
            countryOptions.put("IN", "India");
            
        }

        public LinkedHashMap<String, String> getCountryOptions() {
            return countryOptions;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getFavoriteLanguage() {
            return favoriteLanguage;
        }

        public void setFavoriteLanguage(String favoriteLanguage) {
            this.favoriteLanguage = favoriteLanguage;
        }

        public String[] getOperatingSystems() {
            return operatingSystems;
        }

        public void setOperatingSystems(String[] operatingSystems) {
            this.operatingSystems = operatingSystems;
        }    
    }
```

## StudentController.java
```Java
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
```

## main-menu.jsp
```JSP
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
        <a href="student/showForm">Student form</a>
    </body>
    </html>
```

## student-confirmation.jsp
```JSP
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

    <!DOCTYPE html>
    <html>
        <head>
        <meta charset="ISO-8859-1">
        <title>Insert title here</title>
        </head>
        <body>
            The student is confirmed: ${student.firstName} ${student.lastName} 
            <br><br>
            Country Code: ${student.country}
            <br><br>
            Favorite Language: ${student.favoriteLanguage}
            <br><br>
            Operating Systems: 
            <ul>
            <c:forEach var = "temp" items="${student.operatingSystems}" >
                <li> ${temp}</li>
        </c:forEach>
            </ul>
        </body>
    </html>
```

## student-form.jsp
```JSP
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <!DOCTYPE html>
    <html>
        <head>
        <meta charset="ISO-8859-1">
        <title>Insert title here</title>
        </head>
        <body>
            <form:form action="processForm" modelAttribute="student">
                First name: <form:input path="firstName" />
                
                <br><br>
                
                Last name: <form:input path="LastName" />
                
                <br><br>
                
                Counrty: <form:select path="country">
                    <form:options items="${student.countryOptions}" />
                </form:select>
                
                <br><br>
                
                Favorite Language:     
                    Java <form:radiobutton path="favoriteLanguage" value="Java" />
                    C# <form:radiobutton path="favoriteLanguage" value="C#" />
                    PHP <form:radiobutton path="favoriteLanguage" value="PHP" />
                    Ruby <form:radiobutton path="favoriteLanguage" value="Ruby" />
                
                <br><br>
                
                opearting Systems:
                    Linux <form:checkbox path="operatingSystems" value="Linux" />
                    Mac	OS <form:checkbox path="operatingSystems" value="Mac OS" />
                    MS Windows <form:checkbox path="operatingSystems" value="MS Windows" />
                
                <br><br>
                <input type="submit" value="Submit" />
                
            </form:form>
        </body>
    </html>
```

## spring-mvc-demo-servlet.xml
```Xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:mvc="http://www.springframework.org/schema/mvc"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/mvc
            http://www.springframework.org/schema/mvc/spring-mvc.xsd">

        <!-- Step 3: Add support for component scanning -->
        <context:component-scan base-package="com.mvc.springdemo" />

        <!-- Step 4: Add support for conversion, formatting and validation support -->
        <mvc:annotation-driven/>

        <!-- Step 5: Define Spring MVC view resolver -->
        <bean
            class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/WEB-INF/view/" />
            <property name="suffix" value=".jsp" />
        </bean>

    </beans>
```

## web.xml
```Xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
    <display-name>spring-mvc-demo</display-name>
    <absolute-ordering/>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/spring-mvc-demo-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    </web-app>
```