package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.*;
import java.util.*;

import com.guzman.projects.teamgenerator.teamgen.Member;

public class TeamGeneratorCsvDao implements IDataObjectModel {

	File file;
	List<Member> members;

	// TODO: need to get the Business logic out of constructor
	public TeamGeneratorCsvDao(String csvFileName) throws Exception {

		members = new ArrayList<Member>();
		try (BufferedReader in = new BufferedReader(new FileReader(csvFileName))) {

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
	}

	@Override
	public List<Member> getUsers() {
		return members;
	}

}
