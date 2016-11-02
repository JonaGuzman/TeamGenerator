package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.File;
import java.sql.*;
import java.util.*;
import com.flores.h2.spreadbase.*;
import com.guzman.projects.teamgenerator.teamgen.Member;

public class TeamGeneratorDbDao implements IDataLoader {

	File file;
	File outDb;
	String dbPath;

	Member member;
	List<Member> members;

	//TODO: make it CRUD
	
	public TeamGeneratorDbDao(String xlsxFileName) throws Exception {
		Spreadbase.asDataSource(new File(xlsxFileName));
	}

	@Override
	public List<Member> getUsers() {
		members = new ArrayList<Member>();

		try {
			new File(dbPath = "./target/db/").mkdir();
			outDb = new File(dbPath + "members");

			Connection conn = DriverManager.getConnection("jdbc:h2:" + outDb + ";MV_STORE=FALSE;FILE_LOCK=NO");

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from members");

			while (rs.next()) {
				member = new Member(rs.getString(1), rs.getString(2));
				members.add(member);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}
}
