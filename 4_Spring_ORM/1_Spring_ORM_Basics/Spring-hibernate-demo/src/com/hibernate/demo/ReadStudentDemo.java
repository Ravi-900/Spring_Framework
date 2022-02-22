package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.spring.hibernate.Student;


public class ReadStudentDemo {

	public static void main(String[] args) {
		
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
	}

}
