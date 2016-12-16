package com.guzman.projects.teamgenerator.teamgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.guzman.projects.teamgenerator.teamgen.dao.DaoFactory;

/**
 * Unit test of CSV Data Object
 */
public class TestTeamGeneratorCsvDao extends TestTeamGenerator {

	@Before
	public void setUp() {
		// Created instance of IDataLoader to pass dao into
		try {
			dataLoader = DaoFactory.getDao("./src/main/resources/members.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test generation of updated csv
	 */
	@Test
	public void testCsvAdd() throws Exception {

		members = dataLoader.getUsers();
		
		// Creates updated csv file
		dataLoader.addMember(new Member(53, "Mayra", "Mavarez", 25));

		assertEquals(members.get(52).toString(), "Mayra Mavarez 25");
	}

	/**
	 * Test delete from csv
	 */
	@Test
	public void testCsvDelete() throws Exception {
		
		members = dataLoader.getUsers();
		Member member = new Member(1, "jen", "smith", 2);
		// Creates updated csv file
		dataLoader.deleteMember(member);

		assertEquals(51, members.size());
	}

	/**
	 * Test update from csv
	 */
	@Test
	public void testCsvUpdate() throws Exception {

		members = dataLoader.getUsers();
		Member member = new Member(1, "jen", "smith", 2);
		
		// Creates updated csv file
//		dataLoader.updateMember("dave, millers", "dave, miller");

//		assertEquals(52, members.size());

		for (Member m : members) {
//			assertTrue(!m.toString().equalsIgnoreCase("dave millers"));
		}
	}
}
