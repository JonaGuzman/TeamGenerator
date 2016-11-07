package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.File;
import java.sql.*;
import java.util.*;
import com.flores.h2.spreadbase.*;
import com.guzman.projects.teamgenerator.teamgen.Member;

public class TeamGeneratorDbDao implements IDataLoader {

	File file;
	String dbPath;

	Member member;
	List<Member> members;

	//TODO: make it CRUD
	
	public TeamGeneratorDbDao(String xlsxFileName) throws Exception {
		Spreadbase.asDataSource(new File(xlsxFileName));
		dbPath = xlsxFileName.substring(0, xlsxFileName.lastIndexOf('.'));
	}

	@Override
	public List<Member> getUsers() {
		members = new ArrayList<Member>();

		try {

			//how do i opt to not have the db files in resource
			Connection conn = DriverManager.getConnection("jdbc:h2:" + dbPath + ";MV_STORE=FALSE;FILE_LOCK=NO", "sa", "");

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
