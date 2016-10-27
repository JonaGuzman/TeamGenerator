package com.guzman.projects.teamgenerator.teamgen.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

import org.h2.tools.RunScript;

import com.flores.h2.spreadbase.*;
import com.flores.h2.spreadbase.io.TableDefinitionWriter;
import com.flores.h2.spreadbase.model.ITable;
import com.flores.h2.spreadbase.model.impl.h2.DataDefinitionBuilder;
import com.flores.h2.spreadbase.util.BuilderUtil;
import com.guzman.projects.teamgenerator.teamgen.Member;

public class TeamGeneratorDbDao implements IDataObjectModel {

	File file;
	File outDb;
	String dbPath;

	Member member;
	List<Member> members;

	public TeamGeneratorDbDao(String xlsxFileName) {
		file = new File(xlsxFileName);
	}

	@Override
	public List<Member> getUsers() {
		members = new ArrayList<Member>();

		try {

			new File(dbPath = "./target/db/").mkdir();
			outDb = new File(dbPath + "members");

			List<ITable> tables = Spreadbase.analyze(file);

			Spreadbase.write(file, new File(dbPath));

			File sqlOut = new File(dbPath, BuilderUtil.fileAsSqlFile(file).getName());

			TableDefinitionWriter w = new TableDefinitionWriter(sqlOut, new DataDefinitionBuilder());

			w.write(tables);
			w.close();

			Connection conn = DriverManager.getConnection("jdbc:h2:" + outDb + ";MV_STORE=FALSE;FILE_LOCK=NO");

			RunScript.execute(conn, new InputStreamReader(new FileInputStream(sqlOut)));

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
