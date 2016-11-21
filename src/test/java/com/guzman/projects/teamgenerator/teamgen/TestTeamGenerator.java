package com.guzman.projects.teamgenerator.teamgen;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import com.guzman.projects.teamgenerator.teamgen.dao.DaoFactory;
import com.guzman.projects.teamgenerator.teamgen.dao.IDataLoader;

/**
 * Unit test for Random Team Generator App.
 */
public class TestTeamGenerator {

	TeamGenerator team = new TeamGenerator();
	IDataLoader dataLoader;
	List<Member> members = new ArrayList<Member>();

	// TODO: need to create more unit test: test csv dao
	@Test
	public void Test() throws Exception {
		assertEquals(26, team.createTeams("./src/main/resources/members.csv", 2).size());
	}

	/**
	 * Tests generation of H2 Db as well as add to the DB
	 * @throws Exception
	 */
	@Test
	public void testDbAdd() throws Exception {
		
		//Created instance of IDataLoader to pass dao into
		dataLoader = DaoFactory.getDao("./src/main/resources/members.xlsx");
		
		members = dataLoader.getUsers();
		
		dataLoader.addToDao("Mayra", "Mavarez");
		dataLoader.addToDao("Rae", "Sremmund");
		
		//need to reset the list to get changes
		members = dataLoader.getUsers();
		
		//file originally had 52 names before add
		assertEquals(54, members.size());
		assertEquals(members.get(52).toString(), "Mayra Mavarez");
	}
	/**
	 * Test generation of updated csv
	 */
	@Test
	public void testCsvAdd() throws Exception {
		
		dataLoader = DaoFactory.getDao("./src/main/resources/members.csv");
		
		members = dataLoader.getUsers();
		
		//Creates updated csv file
		dataLoader.addToDao("Mayra", "Mavarez");
		
		assertEquals(members.get(52).toString(), "Mayra Mavarez");
	}
	
	/**
	 * Ensure each team has a unique members: members cannot have the same name
	 * @throws Exception
	 */
	@Test
	public void testUniqueMembers() throws Exception {
	
		//got indexoutOFBounds When TeamSize == 3
		//is it just a bad unit test because its not an even split of members?
		// TODO: write something to handle that
		for (Team t : team.createTeams("./src/main/resources/members.csv",4))
		assertNotEquals(t.get(0), t.get(1));
	}

	/**
	 * Test to sort on last name using comparator
	 */
	@Test
	public void testLastNameSort() {
		ArrayList<Member> team = new ArrayList<Member>();

		Member m1 = new Member("Jak", "Mack");
		Member m2 = new Member("Jak", "Guzman");

		team.add(m1);
		team.add(m2);

		Collections.sort((List<Member>) team, new MemberComparator());

		assertEquals(m2, team.get(0));
	}
}
