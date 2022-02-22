package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.spring.hibernate.Student;


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
