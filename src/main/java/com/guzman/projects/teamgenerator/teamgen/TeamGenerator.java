package com.guzman.projects.teamgenerator.teamgen;

import java.util.*;
import org.apache.log4j.Logger;
import com.guzman.projects.teamgenerator.teamgen.dao.*;

/**
 * Randomly generates members into teams
 * 
 * @author Jonathan Guzman
 */
public class TeamGenerator {

	
	private static final Logger logger = Logger.getLogger(TeamGenerator.class.getName());
	/**
	 * The main business logic organizes teams from list of members
	 * 
	 * @param filePath - path to csv or xlsx
	 * @param teamSize - size of the teams
	 * 
	 * @return a non null Collection of teams
	 */
	public List<Team> createTeams(String filePath, int teamSize) {

		List<Team> teams = new ArrayList<>();
		List<Integer> chosenMembers = new ArrayList<>();
		List<Member> members = new ArrayList<Member>();

		/**
		 * Populate Team of teamSize unique members as team instance
		 */
		try {
			members = loadMembers(filePath);

			int teamIndex = 1;
			Team currentTeam = new Team();

			for (int i = 0; i < members.size(); i++) {

				int teamCount = i % teamSize;
				int seed = generateSeed(chosenMembers, members);

				// Reinitialized the team every time the teamCount is 0
				if (i != 0 && teamCount == 0) {
					// add the current team to list before reset
					currentTeam.setTeamIndex(teamIndex++);

					Collections.sort((List<Member>) currentTeam, new MemberComparator());

					teams.add(currentTeam);
					currentTeam = new Team();
				}

				Member member = members.get(seed);
				if (!chosenMembers.contains(seed)) {
					// track member index and add to currentTeam
					chosenMembers.add(seed);
					currentTeam.add(member);
				}
			}

			// add + sort the final team created
			currentTeam.setTeamIndex(teamIndex++);

			Collections.sort((List<Member>) currentTeam, new MemberComparator());

			teams.add(currentTeam);

			return teams;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private int generateSeed(List<Integer> chosenMembers, List<Member> members) {
		int seed;

		// continue to generate the seed to avoid duplicates
		do {
			seed = (int) (Math.random() * members.size());
		} while (chosenMembers.contains(seed));
		return seed;
	}

	/**
	 * @param path - from create teams method
	 * 
	 * @return {@link List}
	 *
	 * @throws Exception
	 */
	private List<Member> loadMembers(String path) throws Exception {
		return DaoFactory.getDao(path).getUsers();
	}

	public static void main(String[] args) throws Exception {

		List<Team> listOfTeams = new ArrayList<Team>();

		if (args.length != 2)
			throw new Exception("Need to pass in file path and team size");

		TeamGenerator tg = new TeamGenerator();

		listOfTeams = tg.createTeams(args[0], Integer.parseInt(args[1]));

		for (Team t : listOfTeams) {
			logger.info(t);
			System.out.println(t.toString());
		}
	}
}