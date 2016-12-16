package com.guzman.projects.teamgenerator.teamgen;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import com.guzman.projects.teamgenerator.teamgen.dao.IDataLoader;

/**
 * Unit test for Random Team Generator App.
 */
public class TestTeamGenerator {

	TeamGenerator team = new TeamGenerator();
	IDataLoader dataLoader;
	List<Member> members = new ArrayList<Member>();

	@Test
	public void Test() throws Exception {
		assertEquals(26, team.createTeams("./src/main/resources/members.csv", 2).size());
	}

	/**
	 * Ensure each team has a unique members members cannot have the same name
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUniqueTeamMembers() throws Exception {

		for (Team t : team.createTeams("./src/main/resources/members.csv", 4)) {

			for (Member m : t) {
				for (int i = t.size() - 1; i > t.indexOf(m); i--) {
					assertNotEquals(t.get(t.indexOf(m)).toString(),
									t.get(i).toString());
				}
			}
		}
	}

	/**
	 * Test each entry in members file
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMembersFile() throws Exception {

		for (Team t : team.createTeams("./src/main/resources/members.csv", 14)) {
			for (Member m : t) {
				members.add(m);
			}
		}

		for (Member m : members) {
			for (int i = members.size() - 1; i > members.indexOf(m); i--) {
				assertNotEquals(members.get(members.indexOf(m)).toString(),
											members.get(i).toString());
			}
		}
	}

	/**
	 * Test to sort on last name using comparator
	 */
	@Test
	public void testLastNameSort() {
		ArrayList<Member> team = new ArrayList<Member>();

		Member m1 = new Member(1, "Jak", "Mack");
		Member m2 = new Member(2, "Jak", "Guzman");

		team.add(m1);
		team.add(m2);

		Collections.sort((List<Member>) team, new MemberComparator());

		assertEquals(m2, team.get(0));
	}
}
