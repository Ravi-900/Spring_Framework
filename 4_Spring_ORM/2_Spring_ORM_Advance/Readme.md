# HIBERNATE ADVANCED MAPPINGS

    > In the database, you most likely will have
        - Multiple tables
        - relationships between Tables

    > need to model this with Hibernate 

## One-to-One Mapping

    > An instructor can have an "instructor details" entity.
        - Similar to an "instructor profile".

    Instructor ---> Instructor profile
    

## One-to-Many Mapping / Many-to-One Mapping

    > An instructor can have many courses

    Instructor ----> Course
               |
               ----> Course
               |
               -----> Course

    > Many cources can mapps to only Instructor just opposite of one-to-many is many-to-one mapping.

## Many-toMany Mapping

    > A course can have mnay students.
    > A student can have many courses.

## Important Database Concepts

### Primary Key and Foreign key

    > primary key: identify a unique row in a table.

    > Foreign key:
        - link tables together.
        - a field in one table that refers to primary key in another table.

### Cascade

    > you can cascade operations.
    > Apply the same operation to related entities.
    > Example Instructor and Instrucotr detail table.
    > If we delete an instructor, we should also delete their instructor_detail.
    > This is known as "CASCADE DELETE".
    > Cascade delete depends on the use case.

### Fetch Types : Eager vs Lazy Loading

    > When we fecth/retrieve data, should we retrieve Everything?
        - Eager will retrieve everything
        - Lazy will retrieve on request 
