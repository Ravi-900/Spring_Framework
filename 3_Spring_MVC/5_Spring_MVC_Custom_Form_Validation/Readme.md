# SPRING MVC - CUSTOM FORM VALIDATION

    > Perform custom validation based on your business rules
    > example: Course Code must start with "LUV"

    > Spring MVC Calls our custom validation
    > Custom validation returns boolean value for pass/fail (true/false)


## Create a Custom Java Annotation - From scratch

    > For custom validation - we will create a Custom Java Annotation
    > Ex - @CourceCode
```Java
    @CourceCode(value="LUV", message="must start with LUV")
    private String courceCode;
```
## Development Process
    Step 1: 
       a) Create @CourceCode annotation
       b) Create CourceCodeConstraintValidator - Halper Class -> Contains our custom business logic for validation

    Step 1a: Create @CourceCode Annotation

        Usage Example:
```Java
            @CourceCode(value="LUV", message="must start with LUV")
            private String courceCode; 
```

```Java
        @Constraint(validatedBy=CourceCodeConstraintValidator.class)
        @Target( { ElementType.METHOD, ElementType.FIELD } )  // Can apply our annotation to a method or field
        @Retention(RetentionPolicy.RUNTIME) //Retain this annotation in the Java class file - process it at runtime
        public @interface CourceCode{
            
            // define default cource code
            public String value() default "LUV";

            // define default error message
            public String message() default "must start with LUV";
            
            // define default groups - can group related constraints
            public Class<?>[] groups() default{}; 
            
            // define default payloads - provide custom details about validation failure
            // (severity level,eeoe code etc)
            public Class<? extends Payload>[] payload() default{};
            
        }
```

    Step 1b: Create CourceCodeConstraintValidator
```Java
        public class CourceCodeConstraintValidator implements ConstraintValidator< CourceCode,String>{
            private String courcePrefix;
            
            @Override
            public void initialize(CourceCode theCourceCode){
                courcePrefix = theCourceCode.value();
            }

            @Override
            public boolean isValid(String theCode,ConstraintValidatorContext theConstraintValidatorContext){
                boolean result;
                if(theCode!=null){
                    result=theCode.startsWith(courcePrefix);
                }
                else{
                    result =true;
                }
                return result;
            }
        }
```

    Step2 : Add validation rule to Customer Class
```Java
        public class Customer {

            private String firstName;
            
            @NotNull(message="is required")
            @Size(min=1, message="is required")
            private String lastName;
            
            @NotNull(message="is required")
            @Min(value=0,message="must be greater than or equal to zero")
            @Max(value=10,message="must be less than or equal to 10")
            private Integer freePasses;
            
            @Pattern(regexp="^[a-zA-Z0-9]{5}", message="only 5 chars/digits")
            private String postalCode;
            
            @CourceCode(value="LUV",message="must be start with LUV")
            private String courceCode;
            
            
            public String getCourceCode() {
                return courceCode;
            }

            public void setCourceCode(String courceCode) {
                this.courceCode = courceCode;
            }

            public String getPostalCode() {
                return postalCode;
            }

            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }


            public Integer getFreePasses() {
                return freePasses;
            }

            public void setFreePasses(Integer freePasses) {
                this.freePasses = freePasses;
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
        }
```
    
    Step3 : Displaying error message on HTML form
```Jsp
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <html>
        <head>
            <title>Customer Registration Form</title>
            <style>
                .error {color:red}
            </style>
        </head>
        <body>

        <i>Fill out the form. Asterisk (*) means required.</i>
        <br><br>

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
                Cource Code:<form:input path="courceCode" />
                <form:errors path="courceCode" cssClass="error" />
                
                <br><br>
                <input type="submit" value="Submit" />
                        
            </form:form>

        </body>
        </html>
```
    Step4 : Update Confirmation page
```Jsp
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
            Postal Code: ${customer.postalCode}
            
            <br><br>
            Cource Code: ${customer.courceCode}

        </body>
        </html>
```
