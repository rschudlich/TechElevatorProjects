package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		List<Project> projects = new ArrayList <> ();
		Project theProject = null;
		String sqlSelectProjects = "SELECT * FROM project WHERE (now() BETWEEN from_date AND to_date) OR "
				+ "(now() > from_date AND to_date IS NULL) OR (now() < to_date AND from_date IS NULL)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectProjects);
		while (results.next()) {
			theProject = mapRowToProjects(results);
			projects.add(theProject);
		}
		return projects;
	} 

	private Project mapRowToProjects(SqlRowSet results) {
		Project theProject = new Project();
		theProject.setId(results.getLong("project_id"));
		theProject.setName(results.getString("name"));
		if (results.getDate("from_date") != null) {
			theProject.setStartDate(results.getDate("from_date").toLocalDate());
		}
		if (results.getDate("to_date") != null) {
			theProject.setEndDate(results.getDate("to_date").toLocalDate());
		}
		return theProject;
	}
	
	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlSelectProjects = "DELETE FROM project_employee WHERE project_id = ? AND employee_id = ?";
		jdbcTemplate.update(sqlSelectProjects, projectId, employeeId);
		
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId){
		String sqlSelectProjects = "INSERT INTO project_employee VALUES (?, ?)";
		jdbcTemplate.update(sqlSelectProjects, projectId, employeeId);
	} 
	
	public void saveProject(Project project) {
		String sqlCreateDepartment = "INSERT INTO project(project_id, name, from_date, to_date) "
				+ "VALUES(?, ?, ?, ?)";
				
				jdbcTemplate.update(sqlCreateDepartment, 
						project.getId(),
						project.getName(),
						project.getStartDate(),
						project.getEndDate());
						
	}

}
