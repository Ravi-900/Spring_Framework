# Spring Configuration with Java Annotations - Inversion of Control

## What are Java Annotations?
	> Special labels/markers added to java classes
	> Provide mets-data about the class
	> Processed at compile time or run-time for special processing

### example- @Override   

# Why Spring Configuration with Annotations?

	> XML configuration can be verbose
	> Configure your Spring beans with Annotations
	> Annotations minimizes the XML configuration

# Scanning for Component Classes

	> Spring will scan your Java classes for special annotations
	> Automatically register the beans in the Spring Container

# Development Process

	1> Enable component scanning in Spring config file
	2> Add the @Component Annotation to your Java classes
	3> Retrieve bean from Spring Container

# Step 1: Enable component Scanning in Spring config file

	<beans ...>
	  <context:component-scan base-package="com.demo.spring" />
	</beans>

# Step 2: Add the @Component Annotation to your Java Classes

	// thatSillyCoach <---- bean id
	@Component("thatSillyCoach")
	public class TennisCoach implements Coach{
	  @Override
	  public String getDailyWorkout(){
	    return "Practice your backhand volley";
	  }
	}

# Step 3: Retrieve bean from Spring Container

	Coach theCoach = context.getBean("thatSillyCoach",Coach.class);

## Code (New Project)

# Coach.java

	package com.demo.spring;

	public interface Coach {

		public String getDailyWorkout();

	}

# TennisCoach.java

	package com.demo.spring;

	import org.springframework.stereotype.Component;

	@Component("myTennisCoach")
	public class TennisCoach implements Coach {

		@Override
		public String getDailyWorkout() {
			return "Practice your backhand volley";
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
			Coach theCoach = context.getBean("myTennisCoach",Coach.class);

			// call a method on the bean
			System.out.println(theCoach.getDailyWorkout());

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


# Output:- run AnnotationDemoApp.java

	-----------------------------------
	-  Practice your backhand volley  -
	-----------------------------------

# Default Componet Names

	> Spring also supports Default Bean IDs
	  - Default bean id: the class name, make first letter lower-case

	 Class name -> TennisCoach             default Bean Id -> tennisCoach
 
## Code : only changes

# TennisCoach.java

	package com.demo.spring;

	import org.springframework.stereotype.Component;

	@Component
	public class TennisCoach implements Coach {

		@Override
		public String getDailyWorkout() {
			return "Practice your backhand volley";
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

			// close the context
			context.close();
		}

	}

# Output:- run AnnotationDemoApp.java

	-----------------------------------
	-  Practice your backhand volley  -
	-----------------------------------
