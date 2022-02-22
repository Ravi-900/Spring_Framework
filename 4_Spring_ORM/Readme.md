# HIBERNATE OVERVIEW

## What is Hibernate
    > A framework for persisting / saving Java objects in a database

    +------+          +-----------+               ----------
    | Your | -------> | Hibernate | ------------> |        |
    | Java |          |           |               |  DB    |
    | App  | <------- |           | <------------ |        |
    +------+          +-----------+               ----------

    > www.hibernate.org

## Benefits of Hibernate

    > Hibernate handles all of the low-level SQL
    > Minimizes the amount of JDBC code you have to develop
    > Hibernate provides the Object-toRelational Mapping (ORM)

## Object-To-Relational Mapping (ORM)

    > The developer defines mapping between Java class and database table 

## Saving a Java object with Hibernate
```Java
    // create Java object
    Student theStudent = new Student("John","Doe","john@gmail.com");

    // save it to database
    int theId = (Integer)session.save(theStudent); // returns the primary key
```

## Retrieving a Java Object with Hibernate
```Java
    // retrieve from database using primary key
    Student myStudent =session.get(Student.class,theId);
```

## Querying for Java Objects
```Java
    Query query = session.createQuery("from Student");
    List<Student> students = query.list(); // returns a list of Student objects from the database
```

## Hibernate CRUD Apps

    > Create objects
    > Read objects
    > Update objects
    > Delete objects

## Hibernate and JDBC

    > hibernate uses JDBC for all database communications
    