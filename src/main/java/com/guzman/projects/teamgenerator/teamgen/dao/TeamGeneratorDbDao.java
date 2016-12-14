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

	private final static String CONNECTION_STR = "jdbc:h2:%s;MV_STORE=FALSE;FILE_LOCK=NO";

	/**
	 * initializes spreadbase to create H2 Db
	 * 
	 * @param xlsxFileName - excel file path
	 * 
	 * @throws Exception
	 */
	public TeamGeneratorDbDao(String xlsxFileName) throws Exception {
		// why did this add an extra '_' to first name column,
		// thought it was related to an accidental space
		Spreadbase.asDataSource(new File(xlsxFileName));
		dbPath = xlsxFileName.substring(0, xlsxFileName.lastIndexOf('.'));
	}

	@Override
	public List<Member> getUsers() {
		List<Member> members = new ArrayList<Member>();

		try (Connection conn =  getConnection()) {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from members");

			while (rs.next()) {
				members.add(new Member(rs.getString(1), rs.getString(2)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	@Override
	public void addMember(String firstName, String lastName) throws Exception {

		try (Connection conn =  getConnection()) {

			Statement stmt = conn.createStatement();
			String query = "INSERT INTO members (First__Name, Last_Name) VALUES ('%s', '%s')";
			stmt.executeUpdate(String.format(query, firstName, lastName));

		}
	}

	@Override
	public void deleteMember(String firstName, String lastName)
			throws Exception, SQLException {

		try (Connection conn =  getConnection()) {

			Statement stmt = conn.createStatement();

			String query = String.format(
					"DELETE FROM members WHERE First__Name='%s'"
							+ " AND Last_Name='%s'", firstName, lastName);

			stmt.executeUpdate(query);
		}
	}

	@Override
	public void updateMember(String oldName, String newName) throws Exception {

		String[] oldNameSplit = fixNameSplit(oldName).split("[,|\\s]");
		String[] newNameSplit = fixNameSplit(newName).split("[,|\\s]");

		try (Connection conn =  getConnection())  {

			String sql = String.format(
					"UPDATE members SET First__Name = '%s', Last_Name = '%s'"
							+ " WHERE First__Name = '%s' OR Last_Name = '%s'",
					newNameSplit[0], newNameSplit[1], oldNameSplit[0],
					oldNameSplit[1]);

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				String.format(CONNECTION_STR, dbPath), "sa", "");
	}
}
