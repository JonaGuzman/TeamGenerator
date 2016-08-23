package com.guzman.projects.teamgenerator.teamgen;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestTeamGenerator {

	@Test
	public void Test() throws Exception {
		TeamGenerator team = new TeamGenerator();
		team.loadMembers("./src/main/resources/Members.txt");
		Assert.assertEquals(36, team.getUsers().size());
		Assert.assertEquals(18, team.createTeams(2).size());
	}

	/**
	 * Ensure each team has a unique members: members cannot have the same first
	 * and last name
	 * @throws Exception 
	 */
	@Test
	public void testUniqueMembers() throws Exception {
		TeamGenerator teamGen = new TeamGenerator();
		
		  teamGen.loadMembers("./src/main/resources/Members.txt"); for(Team t : teamGen.createTeams(3)) {
		  Assert.assertNotEquals(t.get(0), t.get(1)); }
		
	}
}
