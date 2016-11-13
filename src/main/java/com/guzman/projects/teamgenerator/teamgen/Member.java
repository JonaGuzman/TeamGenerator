package com.guzman.projects.teamgenerator.teamgen;
/**
 * 
 * @author Jonathan Guzman
 *
 */
public class Member {

	private String firstName;
	private String lastName;

	public Member(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName; 
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
			this.lastName = lastName;
	}

	public String toString() {
		return String.format("%s %s", firstName, lastName);
	}
}
