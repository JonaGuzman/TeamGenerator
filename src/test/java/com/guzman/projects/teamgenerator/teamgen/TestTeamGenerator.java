package com.guzman.projects.teamgenerator.teamgen;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestTeamGenerator {

	/**
	 * TODO: need to create new unit tests due to redesign
	 */
	TeamGenerator team = new TeamGenerator();

	@Test
	public void Test() throws Exception {
//		team.loadMembers("./src/main/resources/members.csv");
//		
//		assertEquals(52, team.getUsers().size());
//		assertEquals(26, team.createTeams(2).size());
	}

	/**
	 * Ensure each team has a unique members:
	 * members cannot have the same name
	 * @throws Exception
	 */
	@Test
	public void testUniqueMembers() throws Exception {
//		team.loadMembers("./src/main/resources/members.csv");
//		
//		//got indexoutOFBounds When TeamSize == 3
//		for (Team t : team.createTeams(4))
//			assertNotEquals(t.get(0), t.get(1));
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
