package com.techelevator.projects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.jdbc.JDBCDepartmentDAO;
import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;
import com.techelevator.projects.model.jdbc.JDBCProjectDAO;

public class JDBCEmployeeDAOTest {
	
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
	public void returns_employee_by_name() {
		LocalDate birthDate = LocalDate.of(1993, 06, 15);
		LocalDate hireDate = LocalDate.of(2002, 9, 01);
		
		Department theDepartment = new Department();
		theDepartment.setName("Name");
		theDepartment.setId((long) 10);
		deptDao.create(theDepartment);

		Employee theEmployee = getEmployee((long) 10, "Ryan", "Schudlich", birthDate, 'M', hireDate);
		dao.save(theEmployee);
		List<Employee> results = dao.searchEmployeesByName("Ryan", "Schudlich");
		
		assertNotNull(results);
		assertEquals(1, results.size());
		
		Employee savedEmployee = results.get(0);
		
		assertEmployeesAreEquals(theEmployee, savedEmployee);
	}
	
	@Test
	public void returns_employee_by_department_id() {
		LocalDate sethBirth = LocalDate.of(1981, 9, 25);
		LocalDate sethHire = LocalDate.of(2003, 05, 01);
		LocalDate scottBirth = LocalDate.of(1980, 03, 17);
		LocalDate scottHire = LocalDate.of(1999, 8, 01);
		
		Department theDepartment = new Department();
		theDepartment.setName("Name");
		theDepartment.setId((long) 10);
		deptDao.create(theDepartment);
		
		Employee seth = getEmployee((long) 10, "Seth", "Smith", sethBirth, 'M', sethHire);
		Employee scott = getEmployee((long) 10, "Scott", "Johnson", scottBirth, 'F', scottHire);
		dao.save(seth);
		dao.save(scott);

		List<Employee> results = dao.getEmployeesByDepartmentId((long) 10);
		
		assertNotNull(results);
		assertEquals(2, results.size());
		
		Employee savedSeth = results.get(0);
		Employee savedScott = results.get(1);
		
		assertEmployeesAreEquals(seth, savedSeth);
		assertEmployeesAreEquals(scott, savedScott);
		
	}
	
	@Test
	public void returns_employees_without_projects() {
		LocalDate johnBirth = LocalDate.of(1995, 03, 17);
		LocalDate johnHire = LocalDate.of(2000, 12, 01);
		
		Employee john = saveEmployee((long) 1000, (long) 3, "John", "Appleseed", johnBirth, 'M', johnHire);
		dao.saveEmployee(john);

		List<Employee> results = dao.getEmployeesWithoutProjects();
		
		assertNotNull(results);
		
		boolean isTrue = false;
		
		for (int x = 0; x < results.size(); x++) {
			Employee newEmployee = results.get(x);
			if (newEmployee.getId() == (long) 1000) {
				isTrue = true;
			}
		}
		
		assertTrue(isTrue);
		
	}
	
	@Test
	public void returns_employee_by_project_id() {
		LocalDate sethBirth = LocalDate.of(1981, 9, 25);
		LocalDate sethHire = LocalDate.of(2003, 05, 01);
		
		Employee seth = getEmployee((long) 4, "Seth", "Smith", sethBirth, 'M', sethHire);
		dao.save(seth);
		
		Project newProject = getProject((long) 10, "Java Bootcamp", sethBirth, sethHire);
		projDao.saveProject(newProject);
		
		dao.saveToProjectid(newProject, seth);

		List<Employee> results = dao.getEmployeesByProjectId(((long) 10));
		
		assertNotNull(results);
		assertEquals(1, results.size());
		
		Employee savedSeth = results.get(0);
		
		assertEmployeesAreEquals(seth, savedSeth);
		
	}
	
	@Test
	public void change_employee_department_returns_updates_department() {
		Department theDepartment = new Department();
		theDepartment.setId((long) 1001);
		theDepartment.setName("Ryan's Department");
		Department savedDepartment = deptDao.saveDepartment(theDepartment);
		deptDao.create(savedDepartment);
		
		LocalDate birthDate = LocalDate.of(1993, 06, 15);
		LocalDate hireDate = LocalDate.of(2002, 9, 01);
		
		Employee theEmployee = saveEmployee((long) 10, (long) 1, "Ryan", "Schudlich", birthDate, 'M', hireDate);
		dao.save(theEmployee);
		dao.changeEmployeeDepartment((long) 10, (long) 1001);
		
		List<Employee> results = dao.getEmployeesByDepartmentId(((long) 1001));
		
		assertNotEquals(null, results);		
		assertEquals(1, results.size());
		
		assertEquals((long) 1001,results.get(0).getDepartmentId());
	}
	
	
	private void assertEmployeesAreEquals(Employee theEmployee, Employee savedEmployee) {
		assertEquals(theEmployee.getDepartmentId(), savedEmployee.getDepartmentId());
		assertEquals(theEmployee.getFirstName(), savedEmployee.getFirstName());
		assertEquals(theEmployee.getLastName(), savedEmployee.getLastName());
		assertEquals(theEmployee.getBirthDay(), savedEmployee.getBirthDay());
		assertEquals(theEmployee.getGender(), savedEmployee.getGender());
		assertEquals(theEmployee.getHireDate(), savedEmployee.getHireDate());
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
	
//	private Department getDepartment(String name) {  // a helper method 
//		Department theDepartment = new Department();
//		theDepartment.setName(name);
//		
//		return theDepartment;
//	}

}
