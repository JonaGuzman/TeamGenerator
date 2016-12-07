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
	public void addMember(String firstName, String lastName) throws Exception {
		members.add(new Member(firstName, lastName));
		save();
	}

	@Override
	public void deleteMember(String firstName, String lastName) throws Exception {

		String name = String.format("%s %s", firstName, lastName);

		for (Member m : members) {
			if (m.toString().equalsIgnoreCase(name)) {
				members.remove(m);
				break;
			}
		}

		save();
	}

	@Override
	public void updateMember(String oldName, String newName) throws Exception {
		String[] oldNameSplit = fixNameSplit(oldName).split("[\\s|,]");
		String[] newNameSplit = fixNameSplit(newName).split("[\\s|,]");

		member = new Member(newNameSplit[0], newNameSplit[1]);

		String name = String.format("%s %s", 
				oldNameSplit[0], 
				oldNameSplit[1]);

		for (Member m : members) {
			if (m.toString().equals(name)) {
				members.set(members.indexOf(m), member);
				break;
			}
		}

		save();
	}

	private void save() throws Exception {
		new File("./target/csv").mkdir();
		String outFW = "./target/csv" + filePath.substring(filePath.lastIndexOf('/'));

		try (BufferedWriter out = new BufferedWriter(new FileWriter(outFW))) {
			out.write(headerRow);
			for (Member m : members) {
				out.write(m.getFirstName() + "," + m.getLastName() + "\n");
			}
		}
	}

	private String fixNameSplit(String name) {
		// corrects split for regex
		// just a ',' or just a " "
		if (name.contains(",") && name.contains(" ")) {
			name = name.replace(" ", "");
			return name;
		} else
			return name;
	}
}
