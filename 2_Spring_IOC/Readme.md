# Inversion of Control (IoC)
	{The Approach of outsourcing the construction and management of objects}
	
	> IoC is the design process of externalizing, the construction and management of our objects.

# Coding Scenario

			     getDailyWorkout()
	-----------       ---------------------->  ----------------- 
	- My App  -				   - BaseballCoach -
	-----------	  <----------------------  -----------------

	-> App should be configurable
	-> Easily change the coach for another sport
	    Hockey, Cricket, Tennis, Gymnastics etc ...

# Code Demo

	-> MyApp.java : main method
	-> BaseballCoach.java
	-> Coach.java: interface after refactoring
	-> TrackCoach.java

# Coach.java

	public interface Coach {
		public String getDailyWorkout();
	}


# BaseballCoach.java

	public class BaseballCoach implements Coach{

		@Override
		public String getDailyWorkout() {
			return "Spend 30 minutes on batting practice." ;
		}
	}

# MyApp.java

	public class MyApp {

		public static void main(String[] args) {

			// create the object
			Coach theCoach = new BaseballCoach();

			// use the object
			System.out.println(theCoach.getDailyWorkout());
		}

	}

# Now let's modify the code and implement the TrackCoach.java class

# TrackCoach.java

	public class TrackCoach implements Coach {

		@Override
		public String getDailyWorkout() {
			return "Run a hard 5K";
		}
	} 

#

	> At this point of time everything is hardcoded
	> now it's time to implement our requirement that is the App should be configurable.
	> therefore let out App should automatically read the config file and read the actual implementation name from a config file, 
	and then make use of it, so then we could easily swap by only changing a config file instead of having to change the source 
	code.

# Ideal Solution
	+--------------------------------------------------------------------------------------+
	|										       |
	|			   give me a "Coach" object				       |
	|	+---------+       ------------------------->  +----------------+               |
	|	| My App  |			     	      | Object Factory |               |
	|	+---------+	  <-------------------------  +----------------+               |
	|						       -                               |
	|					          -                                    |
	|					    -                                          |
	|					 +------+                                      |
	|				         |      | configuration                        |
	|				         |      |                                      |
	|			                 +------+                                      |  <-------- Spring Container
	|					     .                                         |
	|				             .                                         |
	|				+------------------+------------------+                | 
	|				|                  |                  |                |
	|				|                  |                  |                |
	|			+---------------+    +-------------+   +--------------+        |
	|	                | BaseballCoach |    | HockeyCoach |   | CricketCoach |	       |
	|			+---------------+    +-------------+   +--------------+        |
	|                                                                                      |
	|                                                                                      |
	+--------------------------------------------------------------------------------------+

	Spring provides an object factory so we can have our application talk to Spring. let's say hey, give me an object, 
	Based on a configuration file or annotation, Spring will give you the appropriate implementation. 
	So then your app is configurable and will have full support for that based on our application requirement.

# Spring Container
	> Primary functions
	 	- create and manage objects (Inversion of Control)
	 	- Inject object's dependencies (Dependency Injection)
 
# Configuring Spring Container
	1) XML configuration file (legacy, but most legacy apps still use this)
	2) Java Annotations (modern)
	3) Java Source Code (modern)

# Spring Development Process
	> Configure your Spring Beans
	> Create a Spring Container
	> Retrieve Beans from Spring Container


## XML Configuration file
# Step 1: Configure your Spring Beans

	example :- File: applicationContext.xml

		<beans ...>
			<bean id = "myCoach"
				class ="com.demo.spring.BaseballCoach">
			</bean>
		</bean>

# bean id
	The id that your Java application will use when they want to retrieve a beanfrom the Spring container.

# bean class
	fully qualified class name of implementation class

# Step 2: Create a Spring Container

	> Spring container is generically known as ApplicationContext
	> Specialized implementations
	 - ClassPathXmlApplicationContext
	 - AnnotationConfigApplicationContext
	 - GenericWebApplicationContext
	 - others ...

	 example :- reading XML file that's on class path

		 ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
 
 
# Step 3: Retrieve Beans from Container

	example:- 

		// create a spring container
		 ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");

		// retrieve bean from spring container
		 Coach theCoach = context.getBean("myCoach",Coach.class);

		 myCoach -> bean id defined in a config file
		 Coach.class -> that's the name of the actual interface that BaseballCoach implements.


# Adding new files in our project 

	Add applicationContext.xml file in src folder

# applicationContext.xml 
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	    xmlns:context="http://www.springframework.org/schema/context"
	    xsi:schemaLocation="http://www.springframework.org/schema/beans
	    http://www.springframework.org/schema/beans/spring-beans.xsd
	    http://www.springframework.org/schema/context
	    http://www.springframework.org/schema/context/spring-context.xsd">


	    <!-- Define your beans here -->
	    <bean id="myCoach"
		class="com.demo.spring.TrackCoach">
	    </bean>	
	</beans>

# instead of using MyApp.java, let's create new class HelloSpringApp.java

# HelloSpringApp.java
	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class HelloSpringApp {

		public static void main(String[] args) {

			// load the spring configuration file
			ClassPathXmlApplicationContext context = 
					new ClassPathXmlApplicationContext("applicationContext.xml");

			// retrieve bean from spring container
			Coach theCoach = context.getBean("myCoach",Coach.class);

			// call method on the bean
			System.out.println(theCoach.getDailyWorkout());

			// close the context
			context.close();
		}
	}
