package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;
import com.techelevator.projects.model.Project;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	 
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList <> ();
		Employee theEmployee = null;
		String sqlSelectEmployees = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectEmployees);
		while (results.next()) {
			theEmployee = mapRowToEmployees(results);
			employees.add(theEmployee);
		}
		return employees; 
	}
	
	private Employee mapRowToEmployees(SqlRowSet results) {
		Employee theEmployee = new Employee();
		theEmployee.setId(results.getLong("employee_id"));
		theEmployee.setDepartmentId(results.getLong("department_id"));
		theEmployee.setFirstName(results.getString("first_name"));
		theEmployee.setLastName(results.getString("last_name"));
		theEmployee.setBirthDay(results.getDate("birth_date").toLocalDate());
		theEmployee.setGender(results.getString("gender").charAt(0));
		theEmployee.setHireDate(results.getDate("hire_date").toLocalDate());
		return theEmployee;
	}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> employees = new ArrayList <> ();
		Employee theEmployee = null;
		String sqlSearchEmployeeName = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, "
				+ "hire_date FROM employee WHERE first_name = ? AND last_name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchEmployeeName, firstNameSearch, lastNameSearch);
		while (results.next()) {
			theEmployee = mapRowToEmployees(results);
			employees.add(theEmployee);
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		List<Employee> employees = new ArrayList <> ();
		Employee theEmployee = null;
		String sqlSelectEmployee = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, "
				+ "hire_date FROM employee WHERE department_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectEmployee, id);
		while (results.next()) {
			theEmployee = mapRowToEmployees(results);
			employees.add(theEmployee);
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> employees = new ArrayList <> ();
		Employee theEmployee = null;
		String sqlSelectEmployeesWithoutProjects = "SELECT a.* FROM employee a WHERE a.employee_id NOT IN (SELECT b.employee_id FROM project_employee b)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectEmployeesWithoutProjects);
		while (results.next()) {
			theEmployee = mapRowToEmployees(results);
			employees.add(theEmployee);
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> employees = new ArrayList <> ();
		Employee theEmployee = null;
		String sqlSelectEmployees = "SELECT * FROM employee JOIN project_employee ON project_employee.employee_id = employee.employee_id "
				+ "WHERE project_employee.project_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectEmployees, projectId);
		while (results.next()) {
			theEmployee = mapRowToEmployees(results);
			employees.add(theEmployee);
		}
		return employees;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		String sqlInsertDepartment = "UPDATE employee SET department_id = ? WHERE employee_id = ?";
		jdbcTemplate.update(sqlInsertDepartment, departmentId, employeeId);
	}
	
	public void save(Employee employee) {
		String sqlCreateDepartment = "INSERT INTO employee(employee_id, department_id, first_name, last_name"
				+ ", birth_date, gender, hire_date) " +
				  "VALUES(?, ?, ?, ?, ?, ?, ?)";
				employee.setId(getNextEmployeeId());
				
				jdbcTemplate.update(sqlCreateDepartment, 
						employee.getId(),
						employee.getDepartmentId(),
						employee.getFirstName(),
						employee.getLastName(),
						employee.getBirthDay(),
						employee.getGender(),
						employee.getHireDate());
	}
	
	public void saveEmployee(Employee employee) {
		String sqlCreateDepartment = "INSERT INTO employee(employee_id, department_id, first_name, last_name"
				+ ", birth_date, gender, hire_date) " +
				  "VALUES(?, ?, ?, ?, ?, ?, ?)";
				
				jdbcTemplate.update(sqlCreateDepartment, 
						employee.getId(),
						employee.getDepartmentId(),
						employee.getFirstName(),
						employee.getLastName(),
						employee.getBirthDay(),
						employee.getGender(),
						employee.getHireDate());
	}
	
	public void saveToProjectid(Project project, Employee employee) {
		String sqlCreateDepartment = "INSERT INTO project_employee(project_id, employee_id) " +
				  "VALUES(?, ?)";
				
				jdbcTemplate.update(sqlCreateDepartment, project.getId(), employee.getId());
	}
	
	
	
	private Long getNextEmployeeId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet(" SELECT nextval('seq_employee_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Uhoh!  Something went wrong while getting the next id!");
		}
	}

}
