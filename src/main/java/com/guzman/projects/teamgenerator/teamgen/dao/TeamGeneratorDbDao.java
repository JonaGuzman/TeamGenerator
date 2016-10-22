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

//TODO: remove comments with questions
public class TeamGeneratorDbDao implements IDataObjectModel {

	File file;
	File outDb;
	String dbPath;

	List<Member> members;

	public TeamGeneratorDbDao(String xlsxFileName) {

		Member member;
		members = new ArrayList<Member>();

		String query = "select * from members";

		try {
			file = new File(xlsxFileName);

			new File(dbPath = "./target/db/").mkdir();
			outDb = new File(dbPath + "members");
			List<ITable> tables = Spreadbase.analyze(file);

			//this writes the h2 file?
			Spreadbase.write(file, new File(dbPath));
			
			//why do we need the .sql file? is that how the 
			File sqlOut = new File(dbPath, BuilderUtil.fileAsSqlFile(file).getName());

			// write the definitions from analysis, whats an h2 definition
			TableDefinitionWriter w = new TableDefinitionWriter(sqlOut, new DataDefinitionBuilder());

			w.write(tables);
			w.close();

			// dont know what that MV stuff and file lock are
			Connection conn = DriverManager.getConnection("jdbc:h2:" + outDb + ";MV_STORE=FALSE;FILE_LOCK=NO");

			// run the output script of the table definition process
			// didnt get why i had to use this.
			RunScript.execute(conn, new InputStreamReader(new FileInputStream(sqlOut)));

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// columns are 1 based?
				member = new Member(rs.getString(1), rs.getString(2));
				members.add(member);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Member> getUsers() {
		return members;
	}
}
