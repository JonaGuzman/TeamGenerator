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
		team.loadEmployees("./src/main/resources/BeanBagTourneyPlayers.txt");
		Assert.assertEquals(34, team.getUsers().size());
		Assert.assertEquals(17, team.createTeams().size());
	}

	/**
	 * Ensure each team has a unique members: members cannot have the same first
	 * and last name
	 */
	@Test
	public void testUniqueMembers() {
		TeamGenerator teamGen = new TeamGenerator();
		/*
		 * teamGen.loadEmployees(""); for(Team t : teamGen.createTeams()) {
		 * Assert.assertNotEquals(t.get(0), t.get(1)); }
		 */
	}
}
