package com.guzman.projects.teamgenerator.teamgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.guzman.projects.teamgenerator.teamgen.dao.DaoFactory;

public class TestTeamGeneratorDbDao extends TestTeamGenerator {

	@Before
	public void setUp() {
		// Created instance of IDataLoader to pass dao into
		try {
			dataLoader = DaoFactory.getDao("./src/main/resources/members.xlsx");
			dataLoader.getUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests generation of H2 Db as well as add to the DB
	 * @throws Exception
	 */
	@Test
	public void testDbAdd() throws Exception {
		
		dataLoader.addToDao("Mayra", "Mavarez");
		dataLoader.addToDao("Rae", "Sremmund");
		
		//need to reset the list to get changes
		members = dataLoader.getUsers();
		
		//file originally had 52 names before add
		assertEquals(54, members.size());
		assertEquals(members.get(52).toString(), "Mayra Mavarez");
	}
	
	/**
	 * Test delete from database
	 */
	@Test
	public void testDbDelete() throws Exception {
		
		dataLoader.deleteFromDao("carl", "johnson");
		
		members = dataLoader.getUsers();
		
		assertEquals(51, members.size());
		
		for (Member m : members) {
			assertTrue(!m.toString().equalsIgnoreCase("carl johnson"));
		}	
	}
	
	/**
	 * Test update from database
	 */
	@Test
	public void testDbUpdate() throws Exception {
								
		dataLoader.updateDao("dave millers", "dave miller");
		
		members = dataLoader.getUsers();
		
		assertEquals(52, members.size());
		
		for (Member m : members) {
			assertTrue(!m.toString().equalsIgnoreCase("dave millers"));
		}	
	}
}
