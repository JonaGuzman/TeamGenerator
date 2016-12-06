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
	public void deleteFromDao(String firstName, String lastName) throws Exception, SQLException {

		String name = String.format("%s %s", firstName, lastName);
		
		Connection conn = null;

		try {
			
			conn = DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "");
			
			Statement stmt = conn.createStatement();

			String query = String.format(
					"DELETE FROM members WHERE First__Name='%s'" +
					" AND Last_Name='%s'", firstName, lastName);
			
			stmt.executeUpdate(query);
			
			// tried but no success with a stream
			// resultList = members.stream()
			// .filter(m -> !m.toString().equals(name))
			// .collect(Collectors.toList());
						
			for(Member m : members) {
				if(m.toString().equalsIgnoreCase(name)) {
					members.remove(m);
					break;
				}		
			}
		} finally {
			conn.close();
		}
	}
	
	@Override
	public void updateDao(String oldName, String newName) throws Exception {

		String[] oldNameSplit = fixNameSplit(oldName).split("[,|\\s]");
		String[] newNameSplit = fixNameSplit(newName).split("[,|\\s]");

		member = new Member(newNameSplit[0], newNameSplit[1]);

		Connection conn = null;
		Statement stmt = null;

		try {

			conn = DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "");

			String sql = String.format(
					"UPDATE members SET First__Name = '%s', Last_Name = '%s'"
							+ " WHERE First__Name = '%s' OR Last_Name = '%s'",
					newNameSplit[0], newNameSplit[1],
					oldNameSplit[0], oldNameSplit[1]);

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

			ResultSet rs = stmt.executeQuery("select * from members");

			while (rs.next()) {

				if (rs.getString(1).equals(member.getFirstName()) && rs.getString(2).equals(member.getLastName())) {

					String name = rs.getString(1) + " " + rs.getString(2);

					for (Member m : members) {
						if (m.toString().equalsIgnoreCase(name)) {
							members.set(members.indexOf(m), member);
							break;
						}
					}
				}
			}

		} finally {
			conn.close();
		}
	}
	
	private String fixNameSplit(String name) {
		// corrects split for regex
		// just a ',' or just a " "
		if(name.contains(",") && name.contains(" ")) {
			name = name.replace(" ", "");
			return name;
		}
		else
			return name;
	}
}
