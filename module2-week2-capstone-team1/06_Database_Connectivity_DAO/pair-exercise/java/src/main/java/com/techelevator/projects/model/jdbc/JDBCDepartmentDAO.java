package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
 
	@Override
	public List<Department> getAllDepartments() {
		List<Department> departments = new ArrayList <> ();
		Department theDepartment = null;
		String sqlSelectDepartments = "SELECT name, department_id FROM department";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectDepartments);
		while (results.next()) {
			theDepartment = mapRowToDepartment(results);
			departments.add(theDepartment);
		} 
		return departments;
	}
	
	private Department mapRowToDepartment(SqlRowSet results) {
		Department theDepartment = new Department();
		theDepartment.setName(results.getString("name"));
		theDepartment.setId(results.getLong("department_id"));
		return theDepartment;
	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		List<Department> departments = new ArrayList <> ();
		Department theDepartment = null;
		String sqlSearchDepartmentName = "SELECT name, department_id FROM department WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchDepartmentName, nameSearch);
		while (results.next()) {
			theDepartment = mapRowToDepartment(results);
			departments.add(theDepartment);
		}
		return departments;
	}

	@Override
	public Department saveDepartment(Department updatedDepartment) {
		String sqlInsertDepartment = "UPDATE department SET name = ? WHERE department_id = ?";
		jdbcTemplate.update(sqlInsertDepartment, updatedDepartment.getName(), updatedDepartment.getId());
		return updatedDepartment;
	}
	

	@Override
	public Department createDepartment(Department newDepartment) {
		String sqlCreateDepartment = "INSERT INTO department(department_id, name) " +
				  "VALUES(?, ?)";
				newDepartment.setId(getNextDepartmentId());
				
				jdbcTemplate.update(sqlCreateDepartment, 
						newDepartment.getId(),
						newDepartment.getName()); 
		return newDepartment;
	}

	private Long getNextDepartmentId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet(" SELECT nextval('seq_department_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Uhoh!  Something went wrong while getting the next id!");
		}
	}
	
	@Override
	public Department getDepartmentById(Long id) {
		Department theDepartment = null;
		String sqlSelectDepartments = "SELECT name, department_id FROM department WHERE department_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectDepartments, id);
		while (results.next()) {
			theDepartment = mapRowToDepartment(results);
		}
		return theDepartment;
	}
	
	
	public void save(Department department) {
		String sqlCreateDepartment = "INSERT INTO department(department_id, name) " +
				  "VALUES(?, ?)";
				department.setId(getNextDepartmentId());
				
				jdbcTemplate.update(sqlCreateDepartment, 
						department.getId(),
						department.getName()); 
	}
	
	public void create(Department department) {
		String sqlCreateDepartment = "INSERT INTO department(department_id, name) " +
				  "VALUES(?, ?)";
		
				jdbcTemplate.update(sqlCreateDepartment, 
						department.getId(),
						department.getName()); 
	}


}
