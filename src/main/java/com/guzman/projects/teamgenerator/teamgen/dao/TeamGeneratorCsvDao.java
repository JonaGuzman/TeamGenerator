package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.*;
import java.util.*;

import com.guzman.projects.teamgenerator.teamgen.Member;

public class TeamGeneratorCsvDao implements IDataObjectModel {

	String filePath;

	Member m;
	List<Member> members;

	public TeamGeneratorCsvDao(String csvFileName) {
		filePath = csvFileName;
	}

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
					m = new Member(temp[0], temp[1]);
				} else {
					m = new Member(temp[0], "");
				}

				members.add(m);
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
}
