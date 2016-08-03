package com.guzman.projects.teamgenerator.teamgen;

import java.util.ArrayList;
import java.util.List;

/**
 * Team is a collection of <strong>employees</strong>
 * 
 * @see Employee 
 */
public class Team {
	private int teamIndex;
	private List<Employee> employees;

	public Team() {
		this.employees = new ArrayList<>();
	}

	public void add(Employee emp) {
			employees.add(emp);
	}

	public Employee get(int i) {
		//Returns a single employee from team
		return employees.get(i);
	}

	public int getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}

	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Team " + teamIndex)
			.append(System.getProperty("line.separator"));
		
		for (Employee e : employees) {
			builder.append(e).append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}
}
