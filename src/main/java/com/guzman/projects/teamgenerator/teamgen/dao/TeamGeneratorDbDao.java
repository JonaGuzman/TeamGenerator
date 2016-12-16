package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.File;
import java.sql.*;
import java.util.*;

import com.flores.h2.spreadbase.*;
import com.guzman.projects.teamgenerator.teamgen.Member;

/**
 * 
 * @author Jonathan Guzman TODO: implement logging TODO: use prepared statements
 *         TODO: update update method
 */
public class TeamGeneratorDbDao implements IDataLoader {

	File file;
	String dbPath;

	private final static String CONNECTION_STR = "jdbc:h2:%s;MV_STORE=FALSE;FILE_LOCK=NO";

	/**
	 * initializes spreadbase to create H2 Db
	 * 
	 * @param xlsxFileName
	 *            - excel file path
	 * 
	 * @throws Exception
	 */
	public TeamGeneratorDbDao(String xlsxFileName) throws Exception {
		Spreadbase.asDataSource(new File(xlsxFileName));
		dbPath = xlsxFileName.substring(0, xlsxFileName.lastIndexOf('.'));
	}

	@Override
	public List<Member> getUsers() {
		List<Member> members = new ArrayList<Member>();

		try (Connection conn = getConnection()) {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from members");

			while (rs.next()) {
				members.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}

		} catch (Exception e) {
			// TODO: add logging, say something bad happen contact
			e.printStackTrace();
		}
		return members;
	}

	@Override
	public void addMember(Member m) throws Exception {

		try (Connection conn = getConnection()) {

			Statement stmt = conn.createStatement();
			String query = "INSERT INTO members (id, First_Name, Last_Name, age)" + 
							" VALUES ('%d', '%s', '%s', '%d')";
			stmt.executeUpdate(String.format(query, m.getId(), m.getFirstName(),
								m.getLastName(), m.getAge()));
		}
	}

	@Override
	public void deleteMember(Member m) throws Exception, SQLException {

		try (Connection conn = getConnection()) {

			Statement stmt = conn.createStatement();

			String query = String.format("DELETE FROM members WHERE id='%d'", m.getId());

			stmt.executeUpdate(query);
		}
	}

	@Override
	public void updateMember(Member m) throws Exception {

		try (Connection conn = getConnection()) {

			 String sql = String.format(
			 "UPDATE members SET First_Name = '%s',"
			+ " Last_Name = '%s', age = '%d'" + " WHERE id = '%d'",
			m.getFirstName(), m.getLastName(), m.getAge(), m.getId());
			 Statement stmt = conn.createStatement();
			 stmt.executeUpdate(sql);
		}
	}

	// private String fixNameSplit(String name) {
	// // corrects split for regex
	// // just a ',' or just a " "
	// if (name.contains(",") && name.contains(" ")) {
	// name = name.replace(" ", "");
	// return name;
	// } else
	// return name;
	// }

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "");
	}
}
