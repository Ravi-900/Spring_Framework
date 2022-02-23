# Spring Configuration with Java Annotations and AutoWiring- Dependency Injection

## What is Spring AutoWiring?

### Autowiring example :-

	---------                   ------------------
	- Coach - ----------------- - FortuneService -
	---------                   ------------------

	> Injecting FortuneService into a Coach implementation
	> Spring will scan @Components
	> Any one implementts FortuneService interface ?
	> If so, lt's inject. for example: HappyFortuneService

# Autowiring Injection Types

	1> Constructor Injection
	2> Setter Injection
	3> Field Injection

# CONSTRUCTOR INJECTION WITH ANNOTATIONS AND AUTOWIRING

## Development Process

	> Define the dependency interface and class
	> Create a constructor in your class for injections
	> Configure the dependency injection with @Autowired Annotation

# Step 1: Define the dependency interface and class

	public interface FortuneService{
	  public String getFortune();
	}

	@Component
	public class HappyFortuneService implements fortuneService{
	  public String getFortune(){
	    return "Today is your lucky day!";
	  }
	}

# Step 2: Create a constructor in your class for injection

	@Component
	public class TennisCoach implements Coach{
	  private FortuneService fortuneService;

	  public TennisCoach(FortuneService theFortuneService){
	    fortuneService = theFortuneService;
	  }
	  ... 
	}

# Step 3: Configure the dependency injection with @Autowired Annotation

	@Component
	public class TennisCoach implements Coach{
	  private FortuneService fortuneService;

	  @Autowired
	  public TennisCoach(FortuneService theFortuneService){
	    fortuneService = theFortuneService;
	  }
	  ... 
	}

# CODE

# Coach.java

	package com.demo.spring;

	public interface Coach {

		public String getDailyWorkout();

		public String getDailyFortune();
	}

# TennisCoach.java

	package com.demo.spring;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Component;

	@Component
	public class TennisCoach implements Coach {

	private FortuneService fortuneService;

		@Autowired
		public TennisCoach(FortuneService fortuneService) {
			this.fortuneService = fortuneService;
		}

		@Override
		public String getDailyWorkout() {
			return "Practice your backhand volley";
		}

		@Override
		public String getDailyFortune() {
			return fortuneService.getFortune();
		}	
	}

# FortuneService.java

	package com.demo.spring;

	public interface FortuneService {
		public String getFortune();
	}

# HappyFortuneService.java

	package com.demo.spring;

	import org.springframework.stereotype.Component;

	@Component
	public class HappyFortuneService implements FortuneService {

		@Override
		public String getFortune() {
			return "Today is your lucky Day";
		}
	}

# AnnotationDemoApp.java

	package com.demo.spring;

	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class AnnotationDemoApp {

		public static void main(String[] args) {
			// read spring config file
			ClassPathXmlApplicationContext context = 
					new ClassPathXmlApplicationContext("applicationContext.xml");

			// get the bean from spring container
			Coach theCoach = context.getBean("tennisCoach",Coach.class);

			// call a method on the bean
			System.out.println(theCoach.getDailyWorkout());

			// call a method to get daily fortune
			System.out.println(theCoach.getDailyFortune()); 	

			// close the context
			context.close();
		}
	}

# applicationContext.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	    xmlns:context="http://www.springframework.org/schema/context"
	    xsi:schemaLocation="http://www.springframework.org/schema/beans
	    http://www.springframework.org/schema/beans/spring-beans.xsd
	    http://www.springframework.org/schema/context
	    http://www.springframework.org/schema/context/spring-context.xsd">

		<!-- add entry to enable component scanning -->
		<context:component-scan base-package="com.demo.spring"></context:component-scan>

	</beans>

# Output :- run AnnotationDemoApp.java

	-----------------------------------
	-  Practice your backhand volley  -
	-  Today is your lucky Day        -
	-                                 -
	-----------------------------------

# SETTER INJECTION WITH ANNOTATIONS AND AUTOWIRING

	> Inject dependencies by calling setter method(s) on your class

# Development Process

	> Create setter method(s) in your class for injections
	> Configure the dependency injection with @Autowired Annotation

# Step 1: Create setter methods(s) in your class for injections

	@Component
	public class Tenniscoach implements Coach{
	  private FortuneService fortuneService;

	  public TennisCoach(){
	  }

	  public coid setFortuneService(FortuneService fortuneService){
	    this.fortuneService = fortuneService;
	  }
	  ...
	}

# Step 2: Configure the dependency injection with @Autowired Annotation

	@Component
	public class Tenniscoach implements Coach{
	  private FortuneService fortuneService;

	  public TennisCoach(){
	  }

	  @Autowired
	  public coid setFortuneService(FortuneService fortuneService){
	    this.fortuneService = fortuneService;
	  }
	  ...
	}

## CODE - Only Modified files

# TennisCoach.java:-

	package com.demo.spring;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Component;

	@Component
	public class TennisCoach implements Coach {

	private FortuneService fortuneService;

	//	@Autowired
	//	public TennisCoach(FortuneService fortuneService) {
	//		this.fortuneService = fortuneService;
	//	}

		public TennisCoach() {
			System.out.println("Inside default constructor");
		}

		@Autowired
		public void setfortuneService(FortuneService fortuneService) {
			this.fortuneService=fortuneService;
		}

		@Override
		public String getDailyWorkout() {
			return "Practice your backhand volley";
		}

		@Override
		public String getDailyFortune() {
			return fortuneService.getFortune();
		}	
	}

# Output :- run AnnotationDemoApp.java

	-----------------------------------
	-  Inside default constructor     -
	-  Practice your backhand volley  -
	-  Today is your lucky Day        -
	-                                 -
	-----------------------------------

# METHOD INJECTION WITH ANNOTATION AND AUTOWIRED

	> Inject dependencies by calling Any method on your class

#### we can Inject dependency by calling any method in class not only by setter methods just annotate the method with @Autowired

# FIELD INJECTION WITH ANNOTATION AND AUTOWIRED

	> Inject dependencies by setting field values on your class directly
	 (even privtae fields)
	 Accomplished by using Java Reflection
 
 # Development Process
 
	 > Configure the dependency injection with Autowired Annotation
	  * Applied directly to the field
	  * No need for setter methods

	 public class TennisCoach implements Coach{
	  @Autowired
	  private fortuneService fortuneService

	  public TennisCoach(){
	  }

	  // no need for setter methods
	  ...
	}

## CODE - only modified files

	package com.demo.spring;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Component;

	@Component
	public class TennisCoach implements Coach {

		@Autowired
		private FortuneService fortuneService;

	//	@Autowired
	//	public TennisCoach(FortuneService fortuneService) {
	//		this.fortuneService = fortuneService;
	//	}

		public TennisCoach() {
			System.out.println("Inside default constructor");
		}

	//	@Autowired
	//	public void setfortuneService(FortuneService fortuneService) {
	//		this.fortuneService=fortuneService;
	//	}

		@Override
		public String getDailyWorkout() {
			return "Practice your backhand volley";
		}

		@Override
		public String getDailyFortune() {
			return fortuneService.getFortune();
		}
	}

# Output :- run AnnotationDemoApp.java

	-----------------------------------
	-  Inside default constructor     -
	-  Practice your backhand volley  -
	-  Today is your lucky Day        -
	-                                 -
	-----------------------------------

# QUALIFIERS FOR DEPENDENCY INJECTION


	> Imagine, if we had multiple FortuneService implementations (some 4 like HappyFortuneServive,
	RandomFortuneService,DatabaseFortuneService  and RESTFortuneservice) than How will Spring know 
	which one to pick? So basically, in order to resolve this we need to be able to give Spring a unique bean,
	So we will make use of annotation called Qualifier in order to resolve this issue

# Example

	@Component
	 public class TennisCoach implements Coach{
	  @Autowired
	  @Qualifier("happyFortuneService")   <--- we have used default id of bean (i.e, Class name with first latter small)
	  private fortuneService fortuneService

	  public TennisCoach(){
	  }

	  // no need for setter methods
	  ...
	}

## Can apply @Qualifier annotation to
	  - Constructor injection
	  - Setter injection methods
	  - Field injection
