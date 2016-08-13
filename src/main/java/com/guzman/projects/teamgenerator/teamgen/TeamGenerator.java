package com.guzman.projects.teamgenerator.teamgen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

		BufferedReader in = null;
		try {
			String name = null;
			in = new BufferedReader(new FileReader(fileName));
			while ((name = in.readLine()) != null) {
				Member m = new Member();

				String[] temp = name.split(" ");
				if (name.contains(" ")) {
					m.setFirstName(temp[0]);
					m.setLastName(temp[1]);
				} else {
					m.setFirstName(temp[0]);
					m.setLastName("");
				}

				members.add(m);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
	}
	
	/**
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
				int seed;

				// continue to generate the seed to avoid duplicates
				do {seed = (int) (Math.random() * members.size());}
				while (chosenMembers.contains(seed));

				// Reinitialized the team every time the teamCount is 0
				if (i != 0 && teamCount == 0) {
					// add the current team to list before reset
					currentTeam.setTeamIndex(teamIndex++);
					teams.add(currentTeam);
					currentTeam = new Team();
				}

				Member employee = members.get(seed);
				if (!chosenMembers.contains(seed)) {
					// track employee index and add to currentTeam
					chosenMembers.add(seed);
					currentTeam.add(employee);
				}
			}

			// add the final team created
			currentTeam.setTeamIndex(teamIndex++);
			teams.add(currentTeam);
			return teams;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * User passes in file path and team size
	 * @return {@link Collection}
	 * @throws IOException
	 * TODO: put the user prompt in a different class
	 * use args from the main as arguments maybe 
	 * add in a print team
	 */
	public Collection<Team> run() throws IOException {

		Scanner input = null;
		try {
			System.out.println("Filepath:\t");
			input = new Scanner(System.in);
			loadMembers(input.next());
			getUsers();
			System.out.println("Members per Team:\t");
			
			return createTeams(input.nextInt());
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			return null;
		} finally {
			input.close();
		}
	}

	public static void main(String[] args) throws IOException {
		TeamGenerator tg = new TeamGenerator();
		for (Team t : tg.run()) {
			System.out.println(t);
		}
	}
}