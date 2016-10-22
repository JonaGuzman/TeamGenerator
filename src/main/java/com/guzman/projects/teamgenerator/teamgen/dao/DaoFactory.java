package com.guzman.projects.teamgenerator.teamgen.dao;

import java.util.logging.Logger;

import com.guzman.projects.teamgenerator.teamgen.TeamGenerator;

public class DaoFactory {

	public DaoFactory() { }
	
	public IDataObjectModel getDao(String filePath) throws Exception {

		if (filePath.endsWith(".csv"))
			return new TeamGeneratorCsvDao(filePath);

		else if (filePath.endsWith(".xlsx"))
			return new TeamGeneratorDbDao(filePath);

		else {
			Logger.getLogger(TeamGenerator.class.getName()).severe("invalid file type");
			return null;
		}
	}
}
