package com.guzman.projects.teamgenerator.teamgen.dao;

import java.util.*;
import com.guzman.projects.teamgenerator.teamgen.Member;

/**
 * 
 * @author Jonathan Guzman
 * 
 */
public interface IDataLoader {

	/**
	 * Loads Data Object
	 * 
	 * @return List of Members
	 */
	public List<Member> getUsers();
	
	
	/**
	 * Adds to the Data Object
	 * 
	 * @param firstName
	 * @param lastName
	 * 
	 * @throws Exception
	 */
	public void addMember(String firstName, String lastName) throws Exception;
	
	
	/**
	 * Delete from Data Object
	 * 
	 * @param firstName
	 * @param lastName
	 * 
	 * @throws Exception
	 */
	public void deleteMember(String firstName, String lastName) throws Exception;
	
	
	/**
	 * Updates Data Object
	 * 
	 * @param oldName 
	 * @param newName
	 * 
	 * @throws Exception
	 */
	public void updateMember(String oldName, String newName) throws Exception;
}
