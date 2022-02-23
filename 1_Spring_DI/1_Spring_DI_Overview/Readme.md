# Spring Dependency Injection

	> It allows a program design to follow the dependency inversion principle. 
	> The client delegates to calls to another object the responsibility of providing its dependencies. 
	> "Dependency" same thing as "helper objects" 

# Demo Example

	> Previously in chapter one :- Our Coach already provides daily workouts
	  - Now will also provide daily fortunes
	    * new helper: FortuneService
	    * this is a dependency (helper)

	     -----------
	     -  Coach  -  -------------------
	     -----------                    .
					    .
				   -------------------      
				   - Fortune Service -
				   -------------------
                         
# Injection Types

	> There are many types of injection with Spring
	> We will look at 2 most common type of injections
	  - Constructor Injection
	  - Setter Injection
	> Will Cover "auto-wiring" in the Annotation part

# (1) Development Process - Constructor Injection: 

	1> Define the dependency interface and class
	2> Create a constructor in your class for injections
	3> Configure the dependency injection in Spring config file

# Step 1: define the dependency interface and class

	File: FortuneService.java :-
		public interface FortuneService{
		  public String getFortune();
		}

	File: HappyFortuneServcie.java :-
		public class HappyFortuneService implements FortuneService{
		  public String getFortune(){
		    return "today is yoyr lucky day!";
		  }
		}

# Step 2: Create a constructor in your class for injections

	File: BaseballCoach.java :-
		public class BaseballCoach implements Coach{
		  public FortuneService fortuneService;
		  public BaseballCoach(FortuneService theFortuneServcie){
		    fortuneService = theFortuneServcie;
		  }
		  ---
		  ---
		}

# Step 3: Configure the dependency injection in Spring config file

	File: applicationContext.xml

		<!-- Define dependency/helper -->
		<bean id="myFortuneService"  
		  class="com.demo.spring.HappyFortuneService">
		</bean>

		<bean id="myCoach"
		  class="com.demo.spring.BaseballCoach">
		  <!-- inject the dependency/helper using "constructor injection -->
		  <constructor-arg ref="myFortuneService" />
	</bean>

## Code

# Coach.java

	package com.demo.spring;

	public interface Coach {
		public String getDailyWorkout();
		public String getDailyFortune();
	}

# FortuneService.java

	package com.demo.spring;

	public interface FortuneService {
		public String getFortune();
	}

# BaseballCoach.java

	package com.demo.spring;

	public class BaseballCoach implements Coach{

		// define a private field for dependency
		public FortuneService fortuneService;

		// define a constructor for dependency injection
		public BaseballCoach(FortuneService theFortuneServcie){
		    fortuneService = theFortuneServcie;
		  }

		@Override
		public String getDailyWorkout() {
			return "Spend 30 minutes on batting practice." ;
		}

		@Override
		public String getDailyFortune() {
		// use my fortuneService to get a fortune
			return fortuneService.getFortune();
		}
	}

# TrackCoach.java

	package com.demo.spring;

	public class TrackCoach implements Coach {

		// define a private field for dependency
		public FortuneService fortuneService;

		// define a constructor for dependency injection
		public TrackCoach(FortuneService theFortuneServcie){
			fortuneService = theFortuneServcie;
		}

		@Override
		public String getDailyWorkout() {
			return "Run a hard 5K";
		}

		@Override
		public String getDailyFortune() {
			// TODO Auto-generated method stub
			return "just Do It: "+fortuneService.getFortune();
		}
	}

# HappyfortuneService.java

	package com.demo.spring;

	public class HappyfortuneService implements FortuneService {

		@Override
		public String getFortune() {
			return "Today is your lucky day!";
		}
	}

# HelloSpringApp.java

	package com.demo.spring;

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
			System.out.println(theCoach.getDailyFortune());

			// close the context
			context.close();
		}
	}

# MyApp.java

	package com.demo.spring;

	public class MyApp {

		public static void main(String[] args) {

			// create the object
			 Coach theCoach = new TrackCoach();   // Error: The constructor TrackCoach() is undefined

			// use the object
			 System.out.println(theCoach.getDailyWorkout());
		}
	}

#
	Now to fix the error in MyApp.java we need to add default constructor in TrackCoach.java file.
	Therefore, TrackCoach.java file become:-

		package com.demo.spring;

		public class TrackCoach implements Coach {

			// define a private field for dependency
			public FortuneService fortuneService;

			public TrackCoach() {

			}

			// define a constructor for dependency injection
			public TrackCoach(FortuneService theFortuneServcie){
				fortuneService = theFortuneServcie;
			}

			@Override
			public String getDailyWorkout() {
				return "Run a hard 5K";
			}

			@Override
			public String getDailyFortune() {
				// TODO Auto-generated method stub
				return "just Do It: "+fortuneService.getFortune();
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


	    <!-- Define your beans here -->

	    <!-- define the dependency  -->
	    <bean id="myFortune"
		class="com.demo.spring.HappyfortuneService">
	    </bean>

	    <bean id="myCoach"
		class="com.demo.spring.BaseballCoach">

		<!-- set up constructor injection -->
		<constructor-arg ref="myFortune" />
	    </bean>

	</beans>

# OUTPUT:- run SetterDemoApp.java
	------------------------------------------------
	-  Spend 30 minutes on batting practice.       -
	-  Today is your lucky day!                    -
	-                                              -
	------------------------------------------------

# Setter injection
	inject dependencies by calling setter method(s) on your class.

# (2) Development process - Setter Injection

	1> Create setter methods(s) in your class for injections
	2> Configure the dependency injection in Spring config file

# Step 1:- create setter method(s) in your class for injections


	File:- CricketCoach.java
		public class CricketCoach implements Coach{
			private FortuneService fortuneService;
			public CricketCoach(){
			}
			public void setFortuneService(FortuneService fortuneService){
				this.fortuneService = fortuneService;
			}
			---
		}

# Step 2:- Configure the dependency injection in Spring config file

	File:- applicationContext.xml
		<bean id="myFortuneService"
			class="com.demo.spring.HappyFortuneService">
		</bean>

		<bean id="myCricketCoach"
			class="com.demo.java.CricketCoach">

			<!-- Setter injection -->
			<property name="fortuneService" ref="myFortuneService" />
		</bean>

# Code - Only changed files

# CricketCoach.java

	package com.demo.spring;

	public class CricketCoach implements Coach {

		private FortuneService fortuneService;

		// create a no-arg constructor
		public CricketCoach() {
			System.out.println("CricketCoach: inside no-arg constructor");
		}

		// setter method
		public void setFortuneService(FortuneService fortuneService) {
			System.out.println("CricketCoach: inside setter method");
			this.fortuneService = fortuneService;
		}


		@Override
		public String getDailyWorkout() {
			return "Practice fast bowling for 15 minutes";
		}

		@Override
		public String getDailyFortune() {
			return fortuneService.getFortune();
		}

	}

# SetterDemoApp.java

	package com.demo.spring;

	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class SetterDemoApp {

		public static void main(String[] args) {
			// load spring config file
			ClassPathXmlApplicationContext context = 
					new ClassPathXmlApplicationContext("applicationContext.xml");

			// retrieve bean from spring container
			CricketCoach theCoach = context.getBean("myCricketCoach",CricketCoach.class);

			// call methods on the bean
			System.out.println(theCoach.getDailyWorkout());
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


	    <!-- Define your beans here -->

	    <!-- define the dependency  -->
	    <bean id="myFortune"
		class="com.demo.spring.HappyfortuneService">
	    </bean>

	    <bean id="myCoach"
		class="com.demo.spring.BaseballCoach">

		<!-- set up constructor injection -->
		<constructor-arg ref="myFortune" />
	    </bean>

	    <bean id="myCricketCoach"
		class="com.demo.spring.CricketCoach">

		<!-- set up a setter injection -->
		<property name="fortuneService" ref="myFortune" />
	    </bean>
	</beans>


# OUTPUT:- run SetterDemoApp.java
	------------------------------------------------
	-  CricketCoach: inside no-arg constructor     -
	-  CricketCoach: inside setter method          -
	-  Practice fast bowling for 15 minutes        -
	-  Today is your lucky day!                    -
	-                                              -
	------------------------------------------------

# Injecting literal Values
	(How to inject literal values into our Spring objects.)

# Read from a Config file

	+--------------+                                            ----
	| CricketCoach |  <--------- emailAddress: theCoach@demo.com   - - - > literal Values
	+--------------+  <--------- team: Channai super king       ----     
		'
		'          +----------------+
		' ' ' ' '  | FortuneService |
			   +----------------+
	
# Development Process

	1> Create setter method(s) in your class for injections
	2> Configure the injection in Spring config file

# Step 1: Create setter method(s) in your class for injections

	File:- CricketCoach.java
		public class CricketCoach implements coach{
			private String emailAddress;
			private String team;

			public void setemailAddresss(String emailAddress) ....

			public void setTeam(String team) .....

			....
		}

# Step 2: Configure the injection Spring config file

	File:- applicationContext.xml
		<bean id="myCricketCoach"
			class="com.demo.spring.CricketCoach">
			<property name="fortuneService" ref="myFortuneService" />

			<property name="emailAddress" value="theCoach@demo.com" />

			<property name="team" value="channai super king" />
		</bean>

## Code -> Only changed files

# CrircketCoach.java

	package com.demo.spring;

	public class CricketCoach implements Coach {

		private FortuneService fortuneService;
		private String emailAddress;
		private String team;

		// create a no-arg constructor
		public CricketCoach() {
			System.out.println("CricketCoach: inside no-arg constructor");
		}

		// setter methods
		public void setFortuneService(FortuneService fortuneService) {
			System.out.println("CricketCoach: inside setter method - setfortuneService");
			this.fortuneService = fortuneService;
		}

		public void setEmailAddress(String emailAddress) {
			System.out.println("CricketCoach: inside setter method-setEmailAddress");
			this.emailAddress = emailAddress;
		}

		public String getEmailAddress() {
			return emailAddress;
		}

		public void setTeam(String team) {
			System.out.println("CricketCoach: inside setter method - setTeam");
			this.team = team;
		}

		public String getTeam() {
			return team;
		}

		@Override
		public String getDailyWorkout() {
			return "Practice fast bowling for 15 minutes";
		}

		@Override
		public String getDailyFortune() {
			return fortuneService.getFortune();
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


	    <!-- Define your beans here -->

	    <!-- define the dependency  -->
	    <bean id="myFortune"
		class="com.demo.spring.HappyfortuneService">
	    </bean>

	    <bean id="myCoach"
		class="com.demo.spring.BaseballCoach">

		<!-- set up constructor injection -->
		<constructor-arg ref="myFortune" />
	    </bean>

	    <bean id="myCricketCoach"  
		class="com.demo.spring.CricketCoach">

		<!-- set up a setter injection -->
		<property name="fortuneService" ref="myFortune" />

		<!-- inject literal values -->
		<property name="emailAddress" value="theCoach@demo.com"/>
		<property name="team" value="channai super king" />
	    </bean>

	</beans>

# SetterDemoApp.java

	package com.demo.spring;

	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class SetterDemoApp {

		public static void main(String[] args) {
			// load spring config file
			ClassPathXmlApplicationContext context = 
					new ClassPathXmlApplicationContext("applicationContext.xml");

			// retrieve bean from spring container
			CricketCoach theCoach = context.getBean("myCricketCoach",CricketCoach.class);

			// call methods on the bean
			System.out.println(theCoach.getDailyWorkout());
			System.out.println(theCoach.getDailyFortune());
			System.out.println(theCoach.getEmailAddress());
			System.out.println(theCoach.getTeam());

			// close the context
			context.close();
		}
	}

# Output:- run SetterDemoApp.java
	------------------------------------------------------------
	-  CricketCoach: inside no-arg constructor                 -
	-  CricketCoach: inside setter method - setfortuneService  -
	-  CricketCoach: inside setter method-setEmailAddress      -
	-  CricketCoach: inside setter method - setTeam            -
	-  Practice fast bowling for 15 minutes                    -
	-  Today is your lucky day!                                -
	-  theCoach@demo.com                                       -
	-  channai super king                                      -
	-                                                          -
	------------------------------------------------------------

# Injecting Values from Properties File

	(How to inject values from a properties files)

# Read from a Properties File

	+--------------+                                       +-----------+     
	| CricketCoach |  			 emailAddress: |           |
	+--------------+  			 	 team: |           |
		'                                              +-----------+
		'          +----------------+                  properties file
		' ' ' ' '  | FortuneService |
			   +----------------+
		   
# Development Process

	1> Create Properties File
	2> Load Properties File in Spring config file
	3> Reference values from properties File

# Step 1: Create Properties File

	File: sport.properties

		foo.email=myeasyCoach@demo.com
		foo.team=Royal Challengers Bangalore

# Step 2: Load Properties file in Spring config file
  
	  File: applicationContext.xml
	  
	  	<context:property-placeholder Location="classpath:sport.properties"/>

# Step 3: Reference Values from Properties File

  	File: applicationContext.xml

		 <bean id="myCricketCoach"
		     class="com.demo.spring.CricketCoach">
		     .....
		     <property name="emailAddress" value="${foo.email}"/>
	      	     <property name="team" value="${foo.team}"/>
	    	</bean>


## Code -> Only changed files

# sport.properties:- (src folder)

	  foo.email=myeasycoach@demo.com
	  foo.team=Royal Challengers Bangalore

# applicationContext.xml

	  <?xml version="1.0" encoding="UTF-8"?>
	  <beans xmlns="http://www.springframework.org/schema/beans"
	      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	      xmlns:context="http://www.springframework.org/schema/context"
	      xsi:schemaLocation="http://www.springframework.org/schema/beans
	      http://www.springframework.org/schema/beans/spring-beans.xsd
	      http://www.springframework.org/schema/context
	      http://www.springframework.org/schema/context/spring-context.xsd">

	    <!-- load the properties file -->
	    <context:property-placeholder location="classpath:sport.properties"/>


	      <!-- Define your beans here -->

	      <!-- define the dependency  -->
	      <bean id="myFortune"
		class="com.demo.spring.HappyfortuneService">
	      </bean>

	      <bean id="myCoach"
		class="com.demo.spring.BaseballCoach">

		<!-- set up constructor injection -->
		<constructor-arg ref="myFortune" />
	      </bean>

	      <bean id="myCricketCoach"  
		class="com.demo.spring.CricketCoach">

		<!-- set up a setter injection -->
		  <property name="fortuneService" ref="myFortune" />

		  <!-- inject literal values -->
		  <property name="emailAddress" value="${foo.email}"/>
		  <property name="team" value="${foo.team}" />
	      </bean>

	  </beans>

# Output:- run SetterDemoApp.java

	  ------------------------------------------------------------
	  -  CricketCoach: inside no-arg constructor                 -
	  -  CricketCoach: inside setter method - setfortuneService  -
	  -  CricketCoach: inside setter method-setEmailAddress      -
	  -  CricketCoach: inside setter method - setTeam            -
	  -  Practice fast bowling for 15 minutes                    -
	  -  Today is your lucky day!                                -
	  -  myeasycoach@demo.com                                    -
	  -  Royal Challengers Bangalore                             -
	  -                                                          -
	  ------------------------------------------------------------
