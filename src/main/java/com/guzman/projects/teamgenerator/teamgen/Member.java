package com.guzman.projects.teamgenerator.teamgen;
/**
 * 
 * @author Jonathan Guzman
 *
 */
public class Member {

	private String firstName;
	private String lastName;
	private int age;
	private int id;

	public Member(int id, String firstName, String lastName) {
		this(id, firstName, lastName, 0);
	}
	
	public Member(int id, String firstName, String lastName, int age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
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
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return String.format("%s %s %d", firstName, lastName, age);
	}
}
