package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.spring.hibernate.Student;


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
