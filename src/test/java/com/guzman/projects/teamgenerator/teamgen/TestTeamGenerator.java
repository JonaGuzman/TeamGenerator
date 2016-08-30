package com.guzman.projects.teamgenerator.teamgen;

import java.io.IOException;

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
