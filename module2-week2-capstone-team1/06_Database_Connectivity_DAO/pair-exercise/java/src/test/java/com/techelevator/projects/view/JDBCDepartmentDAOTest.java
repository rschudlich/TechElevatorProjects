package com.techelevator.projects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.jdbc.JDBCDepartmentDAO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class JDBCDepartmentDAOTest {

private static SingleConnectionDataSource dataSource;
private JDBCDepartmentDAO dao;

private static final String TEST_NAME = "NEW New new Department";

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
		dao = new JDBCDepartmentDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void returns_department_by_name() {
		Department theDepartment = getDepartment(TEST_NAME);
		dao.save(theDepartment);
 
		List<Department> results = dao.searchDepartmentsByName(TEST_NAME);
		
		assertNotNull(results);
		assertEquals(1, results.size());
		
		Department savedDepartment = results.get(0);
		
		assertEquals(theDepartment.getName(), savedDepartment.getName());
	}
	
	@Test
	public void save_department_returns_updated_department_name() {
		Department theDepartment = new Department();
		theDepartment.setId((long) 1);
		theDepartment.setName("Ryan's Department");
		Department savedDepartment = dao.saveDepartment(theDepartment);
		
		assertNotEquals(null, theDepartment.getId())
;		assertEquals(theDepartment.getName(), savedDepartment.getName());
	}
	
	@Test
	public void create_departnment_returns_department() {
		Department theDepartment = getDepartment("Adell");
		Department savedDepartment = dao.createDepartment(theDepartment);
		
		assertNotEquals(null, theDepartment.getId())
;		assertEquals(theDepartment.getName(), savedDepartment.getName());
	}
	
	@Test
	public void returns_department_by_id() {
		Department theDepartment = new Department();
		theDepartment.setId((long) 3);

		Department savedDepartment = dao.getDepartmentById((long) 3);
		
		assertNotEquals(null, savedDepartment.getId());
		assertEquals(theDepartment.getId(), savedDepartment.getId());
	}
	
	
	
	private Department getDepartment(String name) {  // a helper method 
		Department theDepartment = new Department();
		theDepartment.setName(name);
		
		return theDepartment;
	}

}
