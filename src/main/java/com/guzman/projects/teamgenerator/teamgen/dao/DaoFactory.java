package com.guzman.projects.teamgenerator.teamgen.dao;

import java.util.logging.Logger;
import com.guzman.projects.teamgenerator.teamgen.TeamGenerator;

/**
 * 
 * @author Jonathan Guzman
 * 
 */
public class DaoFactory {

	public DaoFactory() {
	}

	/**
	 * Implements interface to generate Data Objects
	 * 
	 * @param filePath
	 * 
	 * @return a csv or database Dao
	 * 
	 * @throws Exception
	 */
	public static IDataLoader getDao(String filePath) throws Exception {

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
