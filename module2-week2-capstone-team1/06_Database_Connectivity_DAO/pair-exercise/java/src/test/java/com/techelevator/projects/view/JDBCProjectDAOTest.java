package com.techelevator.projects.view;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.jdbc.JDBCDepartmentDAO;
import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;
import com.techelevator.projects.model.jdbc.JDBCProjectDAO;

public class JDBCProjectDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCEmployeeDAO dao;
	private JDBCDepartmentDAO deptDao;
	private JDBCProjectDAO projDao;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
		dataSource.setUsername ("postgres");
		dataSource.setPassword("postgres1");		
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		dao = new JDBCEmployeeDAO(dataSource);
		deptDao = new JDBCDepartmentDAO(dataSource);
		projDao = new JDBCProjectDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void removes_employee_from_project() {
		LocalDate sethBirth = LocalDate.of(1981, 9, 25);
		LocalDate sethHire = LocalDate.of(2003, 05, 01);
		
		Employee seth = getEmployee((long) 4, "Seth", "Smith", sethBirth, 'M', sethHire);
		dao.save(seth);
		
		Project newProject = getProject((long) 10, "Java Bootcamp", sethBirth, sethHire);
		projDao.saveProject(newProject);
		
		dao.saveToProjectid(newProject, seth);

		projDao.removeEmployeeFromProject((long) 10, seth.getId());
		
		List<Employee> results = dao.getEmployeesByProjectId(((long) 10));
		
		assertNotNull(results);
		assertEquals(0, results.size());
		
		
		
	}
	
	@Test
	public void adds_employee_to_project() {
		LocalDate sethBirth = LocalDate.of(1981, 9, 25);
		LocalDate sethHire = LocalDate.of(2003, 05, 01);
		
		Employee seth = getEmployee((long) 4, "Seth", "Smith", sethBirth, 'M', sethHire);
		dao.save(seth);
		
		Project newProject = getProject((long) 11, "Java Bootcamp", sethBirth, sethHire);
		projDao.saveProject(newProject);

		projDao.addEmployeeToProject((long) 11, seth.getId());
		
		List<Employee> results = dao.getEmployeesByProjectId(((long) 11));
		
		assertNotNull(results);
		assertEquals(1, results.size());
		
		
		
	}
	
	private Employee getEmployee(Long departmentId, String firstName, String lastName, LocalDate birthDate, Character gender, LocalDate hireDate) {  // a helper method 
		Employee theEmployee = new Employee();
		theEmployee.setDepartmentId(departmentId);
		theEmployee.setFirstName(firstName);
		theEmployee.setLastName(lastName);
		theEmployee.setBirthDay(birthDate);
		theEmployee.setGender(gender);
		theEmployee.setHireDate(hireDate);
		
		return theEmployee;
	}
	
	private Employee saveEmployee(Long employee_id, Long departmentId, String firstName, String lastName, LocalDate birthDate, Character gender, LocalDate hireDate) {  // a helper method 
		Employee theEmployee = new Employee();
		theEmployee.setId(employee_id);
		theEmployee.setDepartmentId(departmentId);
		theEmployee.setFirstName(firstName);
		theEmployee.setLastName(lastName);
		theEmployee.setBirthDay(birthDate);
		theEmployee.setGender(gender);
		theEmployee.setHireDate(hireDate);
		
		return theEmployee;
	}
	
	private Project getProject(Long id, String name, LocalDate from_date, LocalDate to_date) {  // a helper method 
		Project theProject = new Project();
		theProject.setId(id);
		theProject.setName(name);
		theProject.setStartDate(from_date);
		theProject.setEndDate(to_date);
		
		return theProject;
	} 
}
