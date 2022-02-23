# SPRING MVC FORM VALIDATION

## The Need For Validation

    > Check the user input for :
        - required fields
        - valid numbers in a range
        - valid format (postal code)
        - custom business rule

## Java's Standard Bean Validation API

    > Java has a standard Bean Validation API
    > Defines a metadata model and API for entity validation
    > Not tied to either the web tier or the persistence tier
    > Available for server-side apps and also client side JavaFX/Swing apps.

## Spring and Validation

    > Spring version 4 and higher supports Bean Validation API
    > Preferred method for validation when building Spring apps
    > Simply add Validation JARs to our project

## Validation Annotations

    +-----------------------------------------------------------------------+
    | Annotation             | Description                                  |
    +-----------------------------------------------------------------------+
    | @NotNull               | checks that the annotated value is not null  |
    +-----------------------------------------------------------------------+
    | @Min                   | Must be a number >= value                    |
    +-----------------------------------------------------------------------+
    | @Max                   | Must be a number <= value                    |
    +-----------------------------------------------------------------------+
    | @Size                  | Size must match the given size               |
    +-----------------------------------------------------------------------+
    | @Pattern               | Must match a regular expression pattern      |
    +-----------------------------------------------------------------------+
    | @Future/@Past          | Date must be in future or past of given date |
    +-----------------------------------------------------------------------+

    > Many Others...........

## java's Standard Bean Validation API

    > Java's standard Bean Validation API (JSR-303/309)
    > It is only a specification , vendor independent , portable
    > BUT we still need an implementation...

## The Hibernate Team to the rescue!

    > Hibernate started as an ORM project
    > But in recent years, They have expanded into other area
    > They have a fully compliant JSR-303/309 implementation
        - Not tied to ORM or database work ... separate project


## Required Fields

~~~Java
    // Step1: Adding validation rule
    public class Customer{
        private String firstname;

        @NotNull(message="is required")
        @Size(min=1, message="is required")
        private String lastName;

        // getters-setters
    }
~~~

~~~Jsp
    <!-- Step2: Displaying error message on HTML form -->
    <form:form action="processForm" modelAttribute="customer">
        First Name: <form:input path="firstName" />
        <br><br>

        Last Name (*): <form:input path="lastName" />
        <form:errors path="lastName" cssClass="error" />
        <br><br>

        <input type="submit" value="submit">

    </form:form>    
~~~

~~~Java
    // Step3: Perform validation in Controller class

    @RequestMapping("/processForm")
    public String processForm(
        @valid @ModelAttribute("customer") Customer theCustomer,
        BindingResult theBindingResult){
            if(theBindingResult.hasErrors()){
                return "customer-form";
            }
            else{
                return "customer-confirmation";
            }
        }
~~~

    > @Valid: Perfomr validation rules on customer object
    > BindingResult : Results of validation placed in the BindingResult

~~~Jsp
    <!-- Step4: Update confirmation page -->
    <html>
    <body>
    The Customer is confirmed: ${customer.firstName} ${customer.lastName}
    </body>
    </html>
~~~


## White Space

    > Our example had a problem with white space
    > Last name field with all whitespace passed
    > Should have failed!
    > We need to trim whitespace from input fields

## @InitBinder

    > @InitBinder annotation works as a pre processor
    > It will pre-process each web request to our controller
    > Method annotated with @InitBinder is executed

    > We Will use this to trim Strings
    > Remove leading and trailing white space
    > If String only has white spaces, trim it to null
    > Will resolve our validation problem.
    
## Register custom Editor in Controller

~~~Java
    @InitBinder
    public void initBinder(webDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }
~~~

## Spring MVC Validation - Number Range: @Min and @Max

    > Add a new input field on our form for: Free Passes
    > User can only enter a range: 0 to 10

## Developement Process

```Java
    // Step1: Add validation rule to customer class
    public class Customer{
        @Min(value=0,message="must be greater than or equal to zero")
        @Max(value=10,message="must be less than or equal to 10")
        private int freePasses;

        ....
    }

```

```JSP
    <!-- Step2:  Display error message on HTML form -->
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <form:form action="processForm" modelAttribute="customer">
	
		First name: <form:input path="firstName" />
		
		<br><br>
		
		Last name (*): <form:input path="lastName" />
		<form:errors path="lastName" cssClass="error" />
		
		<br><br>
		Free Passes:<form:input path="freePasses" />
		<form:errors path="freePasses" cssClass="error" />

		<br><br>
		<input type="submit" value="Submit" />
				
	</form:form>

```

```JSP
    <!-- Step3: updating the Customer confirmation page -->
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>
    <head>
        <title>Customer Confirmation</title>
    </head>
    <body>

        The customer is confirmed: ${customer.firstName} ${customer.lastName}
        Passes: ${customer.freePasses}

    </body>
    </html>
```

## Spring MVC Validation - Regular Expression

    > A sequence of characters that define a search pattern
    > This pattern is used to find or match strings 

## Validate a Postal Code

    > Add a new input field on our form for: Postal Code
    > User can only enter 5 chars / digits
    > Apply Regular Expression

## Development Process

```Java
    // Step1: Add validation rule to Customer class
    public class Customer{
        @Pattern(regexp="^[a-zA-Z0-9]{5}", message="only 5 chars/digits")
        private String postalCode;

        ....
    }
```

```JSP
    <!-- Step2:  Display error message on HTML form -->
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <form:form action="processForm" modelAttribute="customer">
	
		First name: <form:input path="firstName" />
		
		<br><br>
		
		Last name (*): <form:input path="lastName" />
		<form:errors path="lastName" cssClass="error" />
		
		<br><br>
		Free Passes:<form:input path="freePasses" />
		<form:errors path="freePasses" cssClass="error" />
		
		<br><br>
		Postal Code:<form:input path="postalCode" />
		<form:errors path="postalCode" cssClass="error" />

		<br><br>
		<input type="submit" value="Submit" />
				
	</form:form>

```

```JSP
    <!-- Step3: updating the Customer confirmation page -->
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <!DOCTYPE html>
    <html>
    <head>
        <title>Customer Confirmation</title>
    </head>
    <body>

        The customer is confirmed: ${customer.firstName} ${customer.lastName}
        <br><br>
        Passes: ${customer.freePasses}
        <br><br>
        Postal: ${customer.postalCode}
    </body>
    </html>
```

## Spring MVC Validation - Make an integer field Required

    Refactor field: Change from int to Integer 
```Java
    @NotNull(message="is required")
	@Min(value=0,message="must be greater than or equal to zero")
    @Max(value=10,message="must be less than or equal to 10")
    private Integer freePasses;
```    

