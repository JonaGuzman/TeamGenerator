package com.guzman.projects.teamgenerator.teamgen.dao;

import java.util.logging.Logger;

import com.guzman.projects.teamgenerator.teamgen.TeamGenerator;

public class DaoFactory {

	public DaoFactory() {
	}

	public IDataObjectModel getDao(String filePath) throws Exception {

		switch (filePath.substring(filePath.lastIndexOf('.'))) {
		case ".csv":
			return new TeamGeneratorCsvDao(filePath);
		case ".xlsx":
			return new TeamGeneratorDbDao(filePath);
		default:
			Logger.getLogger(TeamGenerator.class.getName()).severe("invalid file type");
			return null;
		}

	}
}
