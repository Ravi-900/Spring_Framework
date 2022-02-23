# Bean Scope and Life Cycle

	> Scope refers to the lifecycle of a bean
	> How long does the bean live?
	> How many instances are created?
	> How is the bean shared?

# Default Scope: Singleton

	<beans ...>
	  <bean id="myCoach"
	      class="com.demo.spring.TrackCoach">
	      ...
	   </bean>
	</beans>

	-> By default the scope above bean which we have used previously in our code is Singleton.

# What Is a Singleton?

	> Spring container creates only one instance of the bean, by default.
	> It is cached in memory
	> All requests for the bean will return a SHARED reference to the SAME bean


# example:-
                                                                                           Spring
	Coach theCoach = context.getBean("myCoach",Coach.class); <---------------->   +------------------------------+   
	...                                                                           |     +------------------+     | 
	...                                                                           |     |    TrachCoach    |     |
	...                                                                           |     +------------------+     |  
	Coach theCoach = context.getBean("myCoach",Coach.class); <---------------->   +------------------------------+

	> In this example, we have theCoach = context.getBean("myCoach",Coach.class) and 
	later on this code we also do the same thing than it will basically give you a reference to the same bean.
	> we have these two object references here and they point to the same area of memory or
	they point to the same bean, so spring make use of singleton , it will create only one bean and 
	then share it to everyone who request for that bean, so the singleton is default and 
	the best use case for this is for a stateless bean where you don't need to maintain any state.

	> Explicitly Specify Bean Scope

	<beans ...>
	  <bean id="myCoach"
	      class="com.demo.spring.TrackCoach"
	      scope="singleton">
	      ...
	   </bean>
	</beans>

# Additional Spring Bean Scopes

	      Scope       |                        Description
	---------------------------------------------------------------------------------
	singleton         | Create a single shared instance of the bean. Default Scope. |
	---------------------------------------------------------------------------------
	prototype         | Create a new bean instance for each container request.      |
	---------------------------------------------------------------------------------
	request           | Scoped to an HTTP web request. only used for web apps.      |
	---------------------------------------------------------------------------------
	session           | Scoped to an HTTP web session. only used for web apps.      |
	---------------------------------------------------------------------------------
	globale-session   | Scoped to a global HTTP web request. only used for web apps.|
	---------------------------------------------------------------------------------


# Prototype Scope Example

	> New object for each request

	<beans ...>
	  <bean id="myCoach"
	      class="com.demo.spring.TrackCoach"
	      scope="prototype">
	      ...
	   </bean>
	</beans>

# example:-
                                                                                           Spring
	Coach theCoach = context.getBean("myCoach",Coach.class); <---------------->   +------------------------------+   
	...                                                                           |     +------------------+     | 
	...                                                                           |     |    TrachCoach    |     |
	...                                                                           |     +------------------+     |  
	...                                                                           +------------------------------+
	...
	...                                                                                       Spring
	Coach theCoach = context.getBean("myCoach",Coach.class); <---------------->   +------------------------------+   
										      |     +------------------+     | 
										      |     |    TrachCoach    |     |
										      |     +------------------+     |  
										      +------------------------------+

# Code -> For singleton <Additional files>

# beanScope-applicationContext.xml

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

# BeanScopeDemoApp.java

	package com.demo.spring;

	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class BeanScopeDemoApp {

		public static void main(String[] args) {
			// load the spring config file
			ClassPathXmlApplicationContext context =
					new ClassPathXmlApplicationContext("beanScope-applicationContext.xml");

			// retrieve bean from spring container
			Coach theCoach = context.getBean("myCoach",Coach.class);

			Coach alphaCoach = context.getBean("myCoach",Coach.class);

			// check if they are the same 
			boolean result = (theCoach==alphaCoach);

			System.out.println("pointing to the same object: "+result );
			System.out.println("Memory location for theCoach: "+theCoach);
			System.out.println("Memory location for alphaCoach: "+alphaCoach);

			// close the context
			context.close();
		}

	}

# Output :- run BeanScopeDemoApp.java

	----------------------------------------------------------------------------
	-  pointing to the same object: true                                       -
	-  Memory location for theCoach: com.demo.spring.BaseballCoach@6c779568    -
	-  Memory location for alphaCoach: com.demo.spring.BaseballCoach@6c779568  -
	-                                                                          -
	----------------------------------------------------------------------------

## Code -> For prototype <Additional files>

# beanScope-applicationContext.xml:-

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
		class="com.demo.spring.BaseballCoach" 
		scope="prototype">

		<!-- set up constructor injection -->
		<constructor-arg ref="myFortune" />
	    </bean>

	</beans>

# Ouptut:- run BeanScopeDemoApp.java

	----------------------------------------------------------------------------
	-  pointing to the same object: false                                      -
	-  Memory location for theCoach: com.demo.spring.BaseballCoach@6c779568    -
	-  Memory location for alphaCoach: com.demo.spring.BaseballCoach@f381794   -
	-                                                                          -
	----------------------------------------------------------------------------

# Bean Lifecycle Methods
	
	container started --> Bean Instantiated --> Dependencies Injection --> InternalSpring Processing 
											|
											|
									     Your Custom Init Method
											 /
											/
										       /
									    Bean Is Ready For Use	
							  ---------------------------------------------------
									Container Is shutdown
										|
										|
								   your custom destroy method  --> STOP


# Bean Lifecycle Methods/Hooks
	> You can add custom code during bean initialization
	  - Calling custom business logic methods
	  - Setting up handles to resources (db, sockets, file etc)

	> You can add custom code during bean destruction
	  - Calling custom business logic methods
	  - Clean up handles to resources (db, sockets, files etc)
  
# Init : Method configuration

	<beans ...>
		<bean id="myCoach"
			class="com.demo.spring.TrackCoach"

			<!-- Set up bean initialization -->
			<!-- method name (to do some work) -->
			init-method="doMyStartupStuff">
			...
		</bean>
	</beans>

# Destroy: Method configuration

	<beans ...>
		<bean id="myCoach"
			class="com.demo.spring.TrackCoach"

			<!-- Set up bean initialization -->
			<!-- method name (to do some work) -->
			init-method="doMyStartupStuff">

			<!-- Set up bean destroy method -->
			destroy-method="doMyCleanupStuff">
			...
		</bean>
	</beans>


# Development Process

	1> Define your methods for init and destroy
	2> Configure the method names in Spring config file

## Code -> (Only changes)

# TrackCoach.java

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
			return "just Do It: "+fortuneService.getFortune();
		}

		// add init method
		public void doMyStartupStuff() {
			System.out.println("TrackCoach: inside method doMyStartupStuff");
		}

		// add destroy method
		public void doMyCleanupStuff() {
			System.out.println("TrackCoach: inside method doMyCleanupStuff");
		}
	}

# beanLifecycle-applicationContext.xml

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
		class="com.demo.spring.TrackCoach" 

		init-method="doMyStartupStuff"

		destroy-method="doMyCleanupStuff">


		<!-- set up constructor injection -->
		<constructor-arg ref="myFortune" />
	    </bean>

	</beans>

# BeanLifecycleDemoApp.java

	package com.demo.spring;

	import org.springframework.context.support.ClassPathXmlApplicationContext;

	public class BeanLifecycleDemoApp {

		public static void main(String[] args) {
			// load the spring config file
			ClassPathXmlApplicationContext context =
					new ClassPathXmlApplicationContext("beanLifecycle-applicationContext.xml");

			// retrieve bean from spring container
			Coach theCoach = context.getBean("myCoach",Coach.class);

			System.out.println(theCoach.getDailyWorkout());
			// close the context
			context.close();
		}

	}

# Output:- run BeanLifecycleDemoApp.java

	------------------------------------------------
	-  TrackCoach: inside method doMyStartupStuff  -
	-  Run a hard 5K                               -
	-  TrackCoach: inside method doMyCleanupStuff  -
	-                                              -
	------------------------------------------------
	
