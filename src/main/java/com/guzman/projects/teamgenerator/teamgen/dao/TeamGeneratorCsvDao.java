package com.guzman.projects.teamgenerator.teamgen.dao;

/**
 * 
 * @author Jonathan Guzman
 * 
 */
import java.io.*;
import java.util.*;
import com.guzman.projects.teamgenerator.teamgen.Member;

public class TeamGeneratorCsvDao implements IDataLoader {

	String filePath;

	Member member;
	List<Member> members;

	public TeamGeneratorCsvDao(String csvFileName) {
		filePath = csvFileName;
	}

	/**
	 * Helper method to read csv
	 * 
	 * @throws Exception
	 */
	private void readCsvFile() throws Exception {
		members = new ArrayList<Member>();

		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {

			int count = 0;
			String name = null;

			while ((name = in.readLine()) != null) {

				if (count++ == 0)
					continue;

				String[] temp = name.split(",");
				if (name.contains(",")) {
					member = new Member(temp[0], temp[1]);
				} else {
					member = new Member(temp[0], "");
				}

				members.add(member);
				count++;
			}
		}
	}

	@Override
	public List<Member> getUsers() {
		try {
			readCsvFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	@Override
	public void addToDao(String firstName, String lastName) throws Exception {
		// TODO add to members list as well as csv file
		members.add(new Member(firstName, lastName));
	}

	@Override
	public void deleteFromDao(String firstName, String lastName) throws Exception {
		// TODO delete from members list as well as csv file
		members.remove(new Member(firstName, firstName));
	}

	@Override
	public void updateDao(String oldName, String newName) throws Exception {
		// TODO updates members list as well as csv file
		String[] oldNameSplit = oldName.split("[\\s|,]");
		String[] newNameSplit = newName.split("[\\s|,]");
		
		member = new Member(oldNameSplit[0], oldNameSplit[1]);
		members.set(members.indexOf(member), new Member(newNameSplit[0], newNameSplit[1]));
	}
}
