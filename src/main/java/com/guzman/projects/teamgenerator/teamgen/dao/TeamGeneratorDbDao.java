package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.File;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.flores.h2.spreadbase.*;
import com.guzman.projects.teamgenerator.teamgen.Member;

/**
 * Database implementation of TeamGenerator
 * 
 * @author Jonathan Guzman 
 */
public class TeamGeneratorDbDao implements IDataLoader {

	private String dbPath;
	
	private String query;
	private PreparedStatement preStmt;

	private final static String CONNECTION_STR = "jdbc:h2:%s;MV_STORE=FALSE;FILE_LOCK=NO";

	private static final Logger logger = Logger.getLogger(TeamGeneratorDbDao.class.getName());
	
	/**
	 * initializes spreadbase to create H2 Db
	 * 
	 * @param xlsxFileName - excel file path
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

			preStmt = conn.prepareStatement("select * from members");
			ResultSet rs = preStmt.executeQuery();

			while (rs.next()) {
				members.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}

		} catch (Exception e) {
			logger.error("error when returning users:\n" + e.getStackTrace(), e);
		}
		
		return members;
	}

	@Override
	public void addMember(Member m) throws Exception {

		try (Connection conn = getConnection()) {

			query = "INSERT INTO members (id, First_Name, Last_Name, age)" +
					" VALUES (?,?,?,?)";
			preStmt = conn.prepareStatement(query);
			preStmt.setInt(1, m.getId());
			preStmt.setString(2, m.getFirstName());
			preStmt.setString(3, m.getLastName());
			preStmt.setInt(4, m.getAge());
			executeAndCommit(conn);
		}
	}

	@Override
	public void deleteMember(Member m) throws Exception, SQLException {

		try (Connection conn = getConnection()) {

			query = String.format("DELETE FROM members WHERE id = ?", m.getId());
			preStmt = conn.prepareStatement(query);	
			preStmt.setInt(1, m.getId());
			executeAndCommit(conn);
		}
	}

	@Override
	public void updateMember(Member m) throws Exception {

		try (Connection conn = getConnection()) {

			query = String.format("UPDATE members " + "SET First_Name = ?," +
									" Last_Name = ?, age = ?" + " WHERE id = ?");
			preStmt = conn.prepareStatement(query);
			preStmt.setString(1, m.getFirstName());
			preStmt.setString(2, m.getLastName());
			preStmt.setInt(3, m.getAge());
			preStmt.setInt(4, m.getId());
			executeAndCommit(conn);
		}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(String.format(CONNECTION_STR, dbPath), "sa", "");
	}
	
	private void executeAndCommit (Connection conn) throws Exception {
		preStmt.executeUpdate();
		conn.commit();
	}
}
