# HIBERNATE DEVELOPMENT PROCESS

## 1. Add hibernate Configuration file

    > The config file basically tells Hibernate how to connect to the database.

    > location -> Root of src directory

```Xml
    <!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

    <hibernate-configuration>

        <session-factory>

            <!-- JDBC Database connection settings -->
            <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
            <property name="connection.url">jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false</property>
            <property name="connection.username">hbstudent</property>
            <property name="connection.password">hbstudent</property>

            <!-- JDBC connection pool settings ... using built-in test pool -->
            <property name="connection.pool_size">1</property>

            <!-- Select our SQL dialect -->
            <property name="dialect">org.hibernate.dialect.MySQLDialect</property>

            <!-- Echo the SQL to stdout -->
            <property name="show_sql">true</property>

            <!-- Set the current session context -->
            <property name="current_session_context_class">thread</property>
    
        </session-factory>

    </hibernate-configuration>  
```

## 2. Annotate Java Class

    > terminology

        Entity Class - Java class that is mapped to a database table
    
    > Two options for mapping

        1. XML confix file (legacy)
        2. Java Annotations (modern, preferred)

    > Java Annotaions

        1. Map class to database table
```Java
            @Entity
            @Table(name="student")
            public class Student{
                ...
            }
```
        2. map fields to database columns
``` Java
            @Entity
            @Table(name="student")
            public class Student{
                
                @Id
                @Column(name="id")
                private int id;

                @Column(name="first_name")
                private String firstName;

                ----
            }
```

## 3. Develop Java Code to perform database operations (CRUD Operations)

    > Two Key Players

        1) SessionFactory - Reads the hibernate config file 
                          - Creates Session objects
                          - Heavy- weight object
                          - Only create once in your app
        
        2) Session - Wraps a JDBC connection
                   - Main object used to save /retieve objects
                   - Short-lived object
                   - Retrieved from SessionFactory
                
### Java code Setup
```Java
    public static void main(String[] args){
        SessionFactory fectory = new Configuration()
                                    .configure("hibernate.cfg.xml")
                                    .addAnnotatedClass(Student.class)
                                    .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try{
            // now use the session object to save/retieve java objects
        }
        finally{
            factory.close();
        }

    }
```

### Save a Java Object
```Java
        public class StudentDemo {

            public static void main(String[] args){
                SessionFactory factory = new Configuration()
                                            .configure("hibernate.cfg.xml")
                                            .addAnnotatedClass(Student.class)
                                            .buildSessionFactory();

                Session session =  factory.getCurrentSession();

                try{
                    // now use the session object to save/retrieve java objects

                    // create a student object
                    Student theStudent = new Student("spporthi","rao","spoo866rao@gmail.com");
                    
                    // call toString method of Student class
                    System.out.println(theStudent);

                    // start transaction
                    session.beginTransaction();

                    // save the student
                    session.save(theStudent);

                    // commit the transaction
                    session.getTransaction().commit();
                }
                finally{
                    factory.close();
                }
            }
        }
```

## Primary Key

    > Uniquely identifies each row in a table 
    > Must be a unique value
    > Cannot contain NULL values

## hibernate Identity - Primary Key
``` Java
            @Entity
            @Table(name="student")
            public class Student{
                
                @Id
                @GeneratedValue(strategy=Generationtype.IDENTITTY) 
                @Column(name="id")
                private int id;

                @Column(name="first_name")
                private String firstName;

                ----
            }
```
## ID Generation Strategies

    +--------------------------+------------------------------------------------------------------------------+
    | Name                     | Description                                                                  |
    +--------------------------+------------------------------------------------------------------------------+
    | GenerationType.AUTO      | Pick an appropriate strategy for the particular database                     |
    +--------------------------+------------------------------------------------------------------------------+
    | GenerationType.IDENTITY  | Assign primary keys using database identity column                           |
    +--------------------------+------------------------------------------------------------------------------+
    | GenerationType.SEQUENCE  | Assign primary keys using database sequence                                  |
    +--------------------------+------------------------------------------------------------------------------+
    | GenerationType.TABLE     | Assign primary keys using and underlying database table to ensure uniqueness |
    +--------------------------+------------------------------------------------------------------------------+



    > You can define your own CUSTOM generation strategy
    > Create implementation of org.hibernate.id.IdentifierGenerator
    > Override the method: public Serializable generate (...)


    Note: ALWAYS generate unique value
          Work in high-volume, multithreaded environment
          If using server clusters, always generate unique value 


## Retrieving an Object - (Read Object from Hibernate)

```Java
    // read from database using the primary key
    Student myStudent = session.get(Student.class,theStudent.getId());
```
    Code for saving and getting student data based on primary key
```Java
    // create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			// create a Student object
			System.out.println("creating new student object...");
			Student tempStudent = new Student("Ravi","Kumawat","ravi900kumawat@gmail.com");
			
			// start a transaction
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the student....");
			session.save(tempStudent);
			
			// printing generated Student id
			System.out.println("Student saved, Generated student id = "+tempStudent.getId());
			
			// commit transaction
			session.getTransaction().commit();
			
			
			// adding new Code from here
			session = factory.getCurrentSession();
			
			// start a transaction
			session.beginTransaction();
			
			// read student info based on primary key
			Student myStudent = session.get(Student.class,tempStudent.getId());
			
			// print student details
			System.out.println("Student Info = "+myStudent);
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
```

## Querying Objects - Hibernate Query Language (HQL)

    > Query language for retrieving objects
    > Similar in nature to SQL
        - where, like, order by, join, in, etc...
    
    Retrieving all Students
```Java
        List<Student> theStudents = session.createQuery("from Student").list();
```

    Retrieving Students: lastName = 'Kumawat'
```Java
        List<Student> theStudents = session.createQuery("from Student s where s.lastName='kumawat'").list();
``` 

    Retrieving Students using OR predicate
```Java
        List<Student> theStudents = session.createQuery("from Student s where s.lastName='kumawat'"+"OR s.firstName='Ravi'").list();
``` 

    Retrieving Students using LIKE predicate
```Java
        List<Student> theStudents = session.createQuery("from Student s where"+" s.email LIKE '%gmail.com'").list();
``` 
    Note: Use Java property names everywhere in query

    Example:-
```Java
        public class QueryStudentDemo {

            public static void main(String[] args) {
                
                // create session factory
                SessionFactory factory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Student.class)
                        .buildSessionFactory();
                
                // create session
                Session session = factory.getCurrentSession();
                
                try {			
                    // start a transaction
                    session.beginTransaction();
                    
                    // query students
                    // Retrieving all Students
                    List<Student> theStudents = session.createQuery("from Student").list();
                    
                    //display the students
                    System.out.println("\nDisplaying all Student details : ");
                    DisplayStudent(theStudents);
                    
                    // Retrieving Students: lastName = 'Kumawat'
                    theStudents = session.createQuery("from Student s where s.lastName='kumawat'").list();
                    System.out.println("\nDisplaying student names ending with 'Kumawat' : ");
                    DisplayStudent(theStudents);
                    
                    // Retrieving Students using OR predicate
                    theStudents = session.createQuery("from Student s where s.lastName='kumawat'"+"OR s.firstName='Spoorthi'").list();
                    System.out.println("\nDisplaying student names ending with 'Kumawat' or starting 'Spoorthi' : ");
                    DisplayStudent(theStudents);

                    // Retrieving Students using LIKE predicate
                    theStudents = session.createQuery("from Student s where"+" s.email LIKE '%gmail.com'").list();
                    System.out.println("\nDisplaying student Data whose email ends with 'gmail.com': ");
                    DisplayStudent(theStudents);
                    
                    
                    // commit transaction
                    session.getTransaction().commit();
                    
                    System.out.println("Done!");
                }
                finally {
                    factory.close();
                }
            }

            private static void DisplayStudent(List<Student> theStudents) {
                for(Student tempStudent : theStudents) {
                    System.out.println(tempStudent);
                }
            }

        }
``` 

## Updating Objects(s) - using hibernate

```Java
        int studentId = 1;
        Student myStudent = session.get(Student.class,studentId);

        // update first name to "scooby"
        myStudent.seFirstName("Scooby");

        // commit the transaction
        session.getTransaction().commit();
```

    Update email for all students
```Java
        session.createQuery("update Student set email='foo@gmail.com'").executeUpdate();
```
    Example: 
```Java
        public class UpdateStudentDemo {

            public static void main(String[] args) {
                
                // create session factory
                SessionFactory factory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Student.class)
                        .buildSessionFactory();
                
                // create session
                Session session = factory.getCurrentSession();
                
                try {
                    int studentId = 16;
                    
                    // start a transaction
                    session.beginTransaction();
                    
                    Student myStudent = session.get(Student.class, studentId);
                    
                    // displaying student data before update
                    System.out.println("\nbefore update: "+myStudent); 
                    
                    // update first name to "Scooby"
                    myStudent.setFirstName("Scooby");
                    
                    // displaying student data after update
                    System.out.println("\nafter update: "+myStudent); 
                    
                    // commit transaction
                    session.getTransaction().commit();
                    
                    session = factory.getCurrentSession();
                    // start a transaction
                    session.beginTransaction();
                    
                    // before all student details
                    System.out.println("\nbefore all student info : ");
                    List<Student> theStudents = session.createQuery("from Student").list();
                    DisplayStudent(theStudents);
                    
                    // Update email for all students
                    session.createQuery("update Student set email='foo@gmail.com'").executeUpdate();
                    
                    // after all student details
                    System.out.println("\nafter all student info : ");
                    theStudents = session.createQuery("from Student").list();
                    DisplayStudent(theStudents);			
                    
                    // commit transaction
                    session.getTransaction().commit();
            
                    System.out.println("Done!");
                }
                finally {
                    factory.close();
                }
            }
            private static void DisplayStudent(List<Student> theStudents) {
                for(Student tempStudent : theStudents) {
                    System.out.println(tempStudent);
                }
            }
        }
```

## Deleting objects - using hibernate
```Java
        int studentId = 1;
        Student myStudent = session.get(Student.class,studentId);

        // delete the student
        session.delete(myStudent)

        // commit the transaction
        session.getTransaction().commit();
```

    Example:-
```Java
        public class DeleteStudentDemo {

            public static void main(String[] args) {
                
                // create session factory
                SessionFactory factory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Student.class)
                        .buildSessionFactory();
                
                // create session
                Session session = factory.getCurrentSession();
                
                try {
                    // start a transaction
                    session.beginTransaction();
                    
                    // before all student details
                    System.out.println("\nbefore all student info : ");
                    List<Student> theStudents = session.createQuery("from Student").list();
                    DisplayStudent(theStudents);
                    
                    int studentId=17;
                    Student myStudent = session.get(Student.class, studentId);
                    
                    // delete the student
                    session.delete(myStudent);
                    /**
                    * or we can use following statement
                    * session.createQuery("delete from table where id=2").executeUpdate();
                    */
                    
                    // after all student details
                    System.out.println("\nafter all student info : ");
                    theStudents = session.createQuery("from Student").list();
                    DisplayStudent(theStudents);		
                    
                    // commit transaction
                    session.getTransaction().commit();
                    
                    
                    // adding new Code from here
                    session = factory.getCurrentSession();
                    
                    // start a transaction
                    session.beginTransaction();
                    
                    // deleting students whose last name is kumawat
                    session.createQuery("delete from Student where lastName='kumawat'").executeUpdate();
                    
                    // after all student details
                    System.out.println("\nafter all student info : ");
                    theStudents = session.createQuery("from Student").list();
                    DisplayStudent(theStudents);	
                    
                    // commit transaction
                    session.getTransaction().commit();			
                    
                }
                finally {
                    factory.close();
                }
            }
            private static void DisplayStudent(List<Student> theStudents) {
                for(Student tempStudent : theStudents) {
                    System.out.println(tempStudent);
                }
            }
        }
```
