package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.File;
import java.sql.*;
import java.util.*;
import com.flores.h2.spreadbase.*;
import com.guzman.projects.teamgenerator.teamgen.Member;

/**
 * 
 * @author Jonathan Guzman
 * 
 */
public class TeamGeneratorDbDao implements IDataLoader {

	File file;
	String dbPath;

	Member member;
	List<Member> members;

	private final static String CONNECTION_STR = "jdbc:h2:%s;MV_STORE=FALSE;FILE_LOCK=NO";

	/**
	 * 
	 * Constructor initializes spreadbase to create H2 Db
	 * 
	 * @param xlsxFileName - excel file path
	 * @throws Exception
	 * 
	 */
	public TeamGeneratorDbDao(String xlsxFileName) throws Exception {
		// why did this add an extra '_' to first name column,
		// thought it was related to an accidental space
		Spreadbase.asDataSource(new File(xlsxFileName));
		dbPath = xlsxFileName.substring(0, xlsxFileName.lastIndexOf('.'));
	}

	@Override
	public List<Member> getUsers() {
		members = new ArrayList<Member>();

		try (Connection conn = DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "")) {

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

	@Override
	public void addToDao(String firstName, String lastName) throws Exception {

		Connection conn = null;

		try {
			
			conn = DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "");

			Statement stmt = conn.createStatement();

			String query = "INSERT INTO members (First__Name, Last_Name) VALUES ('%s', '%s')";
			stmt.executeUpdate(String.format(query, firstName, lastName));

			members.add(new Member(firstName, lastName));
			
		} finally {
			conn.close();
		}
	}

	@Override
	public void deleteFromDao(String firstName, String lastName) throws Exception {

		Connection conn = null;

		try {
			
			conn = DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "");
			
			Statement stmt = conn.createStatement();

			String query = String.format(
					"DELETE FROM members WHERE First__Name=%s" +
					" AND Last_Name=%s", firstName, lastName);
			
			ResultSet rs = stmt.executeQuery(query);

			members.remove(new Member(rs.getString(1), rs.getString(2)));
			
		} finally {
			conn.close();
		}
	}

	@Override
	public void updateDao(String oldName, String newName) throws Exception {

		String[] oldNameSplit = oldName.split("[\\s|,]");
		String[] newNameSplit = newName.split("[\\s|,]");
		
		Connection conn = null;
		
		try {
			
			conn = DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "");
			
			String updateStatment = String.format(
					"UPDATE members SET First__Name=?,Last_Name=?" + 
					" WHERE First__Name=%s OR Last_Name=%s",
					oldNameSplit[0], oldNameSplit[1]);
			
			PreparedStatement prs = conn.prepareStatement(updateStatment);
			
			prs.setString(1, newNameSplit[0]);
			prs.setString(2, newNameSplit[1]);
			
			member = new Member(oldNameSplit[0], oldNameSplit[1]);
			
			members.set(members.indexOf(member), new Member(newNameSplit[0], newNameSplit[1]));
			
		} finally {
			conn.close();
		}
	}
}
