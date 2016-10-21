package com.guzman.projects.teamgenerator.teamgen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;

	/**
	 * A random team generator. 
	 * Reads a file containing employees. 
	 * Generates a random number of person team
	 * @author Jonathan Guzman
	 */
public class TeamGenerator {

	protected ArrayList<Member> members = new ArrayList<Member>();

	/**
	 * Method that uses a file path to read a file
	 * populates member list
	 * @param fileName of the member file
	 * @throws IOException
	 */
	public void loadMembers(String fileName) throws IOException {

		if (fileName.endsWith(".csv")) {
			try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {

				int count = 0;
				String name = null;

				while ((name = in.readLine()) != null) {
					Member m = null;

					if (count++ == 0)
						continue;

					String[] temp = name.split(",");
					if (name.contains(",")) {
						m = new Member(temp[0], temp[1]);
					} else {
						m = new Member(temp[0], "");
					}

					members.add(m);
					count++;
				}
			}
		} else if (fileName.endsWith(".xlsx")) {

			//TODO: implement database stuff
			// h2
			// jdbc
			// logic will be similar just with db
			
		} else
			System.out.print("invalid file type");
	}
	
	/**
	 * TODO: API conflict, need to redesign method
	 * Getter for members list
	 * @return List of Members
	 */
	public List<Member> getUsers() { return members;}

	/**
	 * The main business logic 
	 * organizes teams from list of members
	 * @param teamSize size of the teams to be
	 * @return a non null Collection of teams
	 */
	public Collection<Team> createTeams(int teamSize) {

		List<Team> teams = new ArrayList<>();
		List<Integer> chosenMembers = new ArrayList<>();
		/**
		 * Populate Team of two unique members as team instance
		 */
		try {
			int teamIndex = 1;
			Team currentTeam = new Team();
			for (int i = 0; i < members.size(); i++) {

				int teamCount = i % teamSize;
				int seed = generateSeed(chosenMembers);

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
	
	private int generateSeed(List<Integer> chosenMembers) {
		int seed;
		
		// continue to generate the seed to avoid duplicates
		do {seed = (int) (Math.random() * members.size());}
		while (chosenMembers.contains(seed));
		return seed;
	}
	
	/**
	 * User passes in file path and team size
	 * @return {@link Collection}
	 * @throws IOException
	 * @param path to file of members and team size
	 * TODO: put the user prompt in a different class 
	 * add in a print team
	 */
	public Collection<Team> run(String arg1, String arg2) throws IOException {

		Collection <Team> teams = null;
		
		try {
			loadMembers(arg1);
			
			getUsers();
			
			teams = createTeams(Integer.parseInt(arg2));
		} catch (Exception e) {
			e.getMessage();
		} 
		
		return teams;
	}
	
	public static void main(String[] args) throws Exception{
	Logger logger = Logger.getLogger(TeamGenerator.class.getName());

		if(args.length != 2)
			throw new Exception("Need to pass in file path and team size");
		
		TeamGenerator tg = new TeamGenerator();
		
		Collection<Team> listOfTeams = tg.run(args[0], args[1]);
		for (Team t : listOfTeams ) {
			logger.info(t);
			System.out.println(t.toString());
		}
	}
}