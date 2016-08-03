package com.guzman.projects.teamgenerator.teamgen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * A random team generator. 
 * Reads a file containing employees.
 * Generates a random 2 person team
 * @author Jonathan Guzman
 * 
 *
 */
public class TeamGenerator {

	protected ArrayList<Employee> employees = new ArrayList<Employee>();
	/**
	 * Method that passes a file path
	 * Reads file
	 * populates employee list
	 * @param fileName of the employee file
	 * @throws IOException
	 */
	public void loadEmployees(String fileName) throws IOException {

		BufferedReader in = null;
		try {
			String name = null;
			in = new BufferedReader(new FileReader(fileName));
			while ((name = in.readLine()) != null) {
				Employee emp = new Employee();

				String[] temp = name.split(" ");
				if (name.contains(" ")) {
					emp.setFirstName(temp[0]);
					emp.setLastName(temp[1]);
				} else {
					emp.setFirstName(temp[0]);
					emp.setLastName("");
				}

				employees.add(emp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
	}

	/**
	 * Getter for employees list
	 * @return List of Employees
	 */
	public List<Employee> getUsers() {

		return employees;
	}

	/**
	 * This is the main business logic which will organize
	 * a list of employees into teams.
	 * @return a non null Collection of teams
	 */
	public Collection<Team> createTeams() {

		List<Team> teams = new ArrayList<>();
		List<Integer> chosenEmployees = new ArrayList<>();
		/**
		 * Populate Team of two unique players
		 * employees as team instance
		 */
		int teamIndex = 1;
		Team currentTeam = new Team();
		for (int i = 0; i < employees.size(); i++) {

			int teamCount = i % 2;
			int seed;

			// continue to generate the seed to avoid duplicates
			do {
				seed = (int) (Math.random() * employees.size());
			} while (chosenEmployees.contains(seed));

			// Reinitialized the team every time the teamCount is 0
			if (i != 0 && teamCount == 0) {
				// add the current team to list before reset
				currentTeam.setTeamIndex(teamIndex++);
				teams.add(currentTeam);
				currentTeam = new Team();
			}

			Employee employee = employees.get(seed);
			if (!chosenEmployees.contains(seed)) {
				// track employee index and add to currentTeam
				chosenEmployees.add(seed);
				currentTeam.add(employee);
			}
		}

		// add the final team created
		currentTeam.setTeamIndex(teamIndex++);
		teams.add(currentTeam);
		return teams;
	}

	/**
	 * 
	 * @return 
	 * @throws IOException
	 */
	public Collection<Team> run() throws IOException {

		loadEmployees("./src/main/resources/BeanBagTourneyPlayers.txt");
		getUsers();
		return createTeams();
	}

	public static void main(String[] args) throws IOException {
		TeamGenerator tg = new TeamGenerator();
		for (Team t : tg.run()) {
			System.out.println(t);
		}
	}
}