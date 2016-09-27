package com.guzman.projects.teamgenerator.teamgen;

import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestTeamGenerator {

	TeamGenerator team = new TeamGenerator();

	@Test
	public void Test() throws Exception {
		team.loadMembers("./src/main/resources/Members.txt");
		
		assertEquals(36, team.getUsers().size());
		assertEquals(18, team.createTeams(2).size());
	}

	/**
	 * small test for sort, 
	 * first element is always < second element
	 * @throws Exception
	 */
	@Test
	public void testTeamSize() throws Exception {
		team.loadMembers("./src/main/resources/Members.txt");
		
		Collection<Team> teams = team.createTeams(2);
		
		for(Team t : teams) {
			team.sort(t);
			assertTrue(t.get(0).toString().compareToIgnoreCase(t.get(1).toString()) > 0);
		}
	}
	
	/**
	 * Ensure each team has a unique members: members cannot have the same first
	 * and last name
	 * @throws Exception 
	 */
	@Test
	public void testUniqueMembers() throws Exception {	
		  team.loadMembers("./src/main/resources/Members.txt"); 
		  
		  for(Team t : team.createTeams(3))
			  assertNotEquals(t.get(0), t.get(1)); 
	}
}
