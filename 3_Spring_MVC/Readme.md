# What is Spring MVC?
	> Framework for building web application in java
	> Based on Model-View-Controller design pattern
	> Leverages features of the Core Spring Framework (IoC, DI)

# Model-View-Controller (MVC)

	- +-----------+                +--------------+
	- |           | -------------->|    Front     |
	- |           |                |  Controller  | 
	- |           |                +--------------+
	- |           |                       | Model
	- |    Web    |                +--------------+        
	- |  Browser  |                |  Controller  |         
	- |           |                +--------------+
	- |           |                       | Model
	- |           |                +--------------+
	- |           |<-------------- | View Template|
	- +-----------+                +--------------+


	- So basically you have an incoming request coming from the Browser, 
	it will encounter the Spring MVC front Controller.
	- This person will actually delegate the request off to a Controller code.
	- Controller code is a code that you create , that contains business logic.
	- Basically you create a model, and than send the model back to the front controller, and
    	then the front controller will pass that model over to to your view template.
	- View template is basically like a html page, or a JSP page that will take that data, and then render a response to the browser.

# Spring MVC Benefits

	> The Spring way of building web app UIs in java
	> Leverage a set of reusable UI components
	> Help manage application state for web requests
	> Process form data: validation, Conversion etc 
	> Flexible configuration for the view layer

# Components of a Spring MVC application

	> A set of web pages to layout UI components
	> A collection of Spring beans (Controller, services, ect...)
	> Spring Configuration (XML, Annotations or Java)

# Spring MVC Front Controller

	> Front controller known as DespatcherServlet
	  - Part of the Spring Framework
	  - Already developed by Spring Dev Team 

	> you will create
	  - Model objects
	  - View templates -> jsp pages
	  - Controller classes -> business logic

# Controller

	> Code created by developer
	> Contains your business logic
	  - Handle the request
	  - Store/retrieve data (db, web service ..)
	  - Place data in model
	> Send to appropriate view template

# Model

	> Model: Contains your data
	> Store/retrieve via backend systems
	  - database, web services, etc...
	  - Use a Spring bean if you like
	> Place your data in the model
	  - Data can be any java object/collection

# View Template
 
	> Spring MVC is Flexible
	  - Supports many view templates 
	> Most common is JSP+JSTL
	> Developer creates a page
	  - Display data 

# Spring MVC Configuration
  
	> Configuration process - part 1

		Add configuration to file : WEB-INF/web.xml 
		 1 Configure Spring MVC Dispatcher Servlet
			<!-- Step 1: Configure Spring MVC Dispatcher Servlet -->
			<servlet>
				<servlet-name>dispatcher</servlet-name>
				<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
				<init-param>
					<param-name>contextConfigLocation</param-name>
					<param-value>/WEB-INF/spring-mvc-demo-servlet.xml</param-value>
				</init-param>
				<load-on-startup>1</load-on-startup>
			</servlet>

		 2 Set up URL mappings to Spring MVC Dispatcher Servlet
			<!-- Step 2: Set up URL mapping for Spring MVC Dispatcher Servlet -->
			<servlet-mapping>
				<servlet-name>dispatcher</servlet-name>
				<url-pattern>/</url-pattern>
			</servlet-mapping>

	> Configuration process - part 2

		Add configuration to file : WEB-INF/spring-mvc-demo-servlet.xml 
		 3 Add support for Spring component scanning 
			<!-- Step 3: Add support for component scanning -->
			<context:component-scan base-package="com.luv2code.springdemo" />

		 4 Add support for conversion, formatting and validation
			<!-- Step 4: Add support for conversion, formatting and validation support -->
			<mvc:annotation-driven/>

		 5 Configure Spring MVC View Resolver 
			<!-- Step 5: Define Spring MVC view resolver -->
			<bean
				class="org.springframework.web.servlet.view.InternalResourceViewResolver">
				<property name="prefix" value="/WEB-INF/view/" />
				<property name="suffix" value=".jsp" />
			</bean>
