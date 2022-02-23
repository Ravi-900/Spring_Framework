# Spring Configuration with Java Source Code

	> Instead of configuring Spring container using XML
	> Configure the Spring container with Java Source code.
	> no XML , only pure java

# Development Process

	1> create a Java class and annotate as @Configuration
	2> Add component Scanning support: @ComponentScan (optional)
	3> Read Spring Java configuration class
	4> Retrieve bean from Spring container

# Step 1: Create a Java Class and Annotate as @Configuration

	@Configuration
	public class SportConfig{
	}

# Step 2: Add Component Scanning support: @ComponentScan

	@Configuration
	@ComponentScan("com.demo.spring")
	public class SportConfig{
	}

# Step 3: Read Spring Java configuration class

	AnnotationConfigApplicationContext context = new AnnotationConfigAppliationContext(SportConfig.class);

# Step 4: Retrieve bean from Spring container

	Coach thecoach = context.getBean("tennisCoach",Coach.class);

## Code

# SportConfig.java

	package com.demo.spring;

	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;

	@Configuration
	@ComponentScan("com.demo.spring")
	public class SportConfig {
	}

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

		@Autowired
		private FortuneService fortuneService;

		public TennisCoach() {
			System.out.println("Inside default constructor");
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

# JavaConfigDemoApp.java

	package com.demo.spring;

	import org.springframework.context.annotation.AnnotationConfigApplicationContext;
	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class JavaConfigDemoApp {

		public static void main(String[] args) {
			// read spring config file
			AnnotationConfigApplicationContext context = 
					new AnnotationConfigApplicationContext(SportConfig.class);

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

# Ouptut:- run JavaConfigDemoApp.java

	-  Inside default constructor     -
	-  Practice your backhand volley  -
	-  Today is your lucky Day        -
	-                                 -
	-----------------------------------


# Defining Spring Bean With Java Code

# Code - Only Changed files

# SadFortuneService.java

	package com.demo.spring;

	public class SadfortuneService implements FortuneService {

		@Override
		public String getFortune() {
			return "Today is a sad day";
		}

	}

# SportsConfig.java

	package com.demo.spring;

	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;

	@Configuration
	public class SportConfig {
		@Bean
		public FortuneService sadFortuneService() {
			return new HappyFortuneService();
		}

		@Bean
		public Coach swimCoach() {
			return new SwimCoach(sadFortuneService());

		}
	}

# SwimCoach.java

	package com.demo.spring;

	public class SwimCoach implements Coach {

		private FortuneService fortuneService;

		public SwimCoach(FortuneService fortuneService) {
			this.fortuneService = fortuneService;
		}

		@Override
		public String getDailyWorkout() {
			return "Swim 1000 meters as a warmup.";
		}

		@Override
		public String getDailyFortune() {
			return fortuneService.getFortune();
		}

	}

# SwimJavaConfigdemoApp.java

	package com.demo.spring;

	import org.springframework.context.annotation.AnnotationConfigApplicationContext;
	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class SwimJavaConfigDemoApp {

		public static void main(String[] args) {
			// read spring config file
			AnnotationConfigApplicationContext context = 
					new AnnotationConfigApplicationContext(SportConfig.class);

			// get the bean from spring container
			Coach theCoach = context.getBean("swimCoach",Coach.class);

			// call a method on the bean
			System.out.println(theCoach.getDailyWorkout());

			// call a method to get daily fortune
			System.out.println(theCoach.getDailyFortune()); 	

			// close the context
			context.close();
		}

	}

# Output:- run SwimJavaConfigDemoApp.java

	-----------------------------------
	-  Swim 1000 meters as a warmup.  -
	-  Today is your lucky Day        -
	-                                 -
	-----------------------------------
