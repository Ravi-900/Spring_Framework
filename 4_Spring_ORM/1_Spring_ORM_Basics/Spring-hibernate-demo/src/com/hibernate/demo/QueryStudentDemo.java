package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.spring.hibernate.Student;


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
