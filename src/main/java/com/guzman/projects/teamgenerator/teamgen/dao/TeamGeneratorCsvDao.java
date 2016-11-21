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
	String headerRow;

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

			String name = null;

			headerRow = in.readLine() + "\n";
			while ((name = in.readLine()) != null) {

				String[] temp = name.split(",");
				if (name.contains(",")) {
					member = new Member(temp[0], temp[1]);
				} else {
					member = new Member(temp[0], "");
				}

				members.add(member);
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
		members.add(new Member(firstName, lastName));
		writeToCsv();
	}
	
	private void writeToCsv() throws Exception {
		new File("./target/csv").mkdir();
		String outFW = "./target/csv" + filePath.substring(filePath.lastIndexOf('/'));

		try (BufferedWriter out = new BufferedWriter(new FileWriter(outFW))) {
			out.write(headerRow);
			for (Member m : members) {
				out.write(m.getFirstName() + "," + m.getLastName() + "\n");
			}
		}
	}

	@Override
	public void deleteFromDao(String firstName, String lastName) throws Exception {
		// TODO delete from members list as well as csv file
		members.remove(new Member(firstName, firstName));
		writeToCsv();
	}

	@Override
	public void updateDao(String oldName, String newName) throws Exception {
		// TODO updates members list as well as csv file
		String[] oldNameSplit = oldName.split("[\\s|,]");
		String[] newNameSplit = newName.split("[\\s|,]");
		
		member = new Member(oldNameSplit[0], oldNameSplit[1]);
		members.set(members.indexOf(member), new Member(newNameSplit[0], newNameSplit[1]));
		
		writeToCsv();
	}
}
