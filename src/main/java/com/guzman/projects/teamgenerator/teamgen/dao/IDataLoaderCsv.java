package com.guzman.projects.teamgenerator.teamgen.dao;

/**
 * Seperate implementation of DataLoader for CSV
 * 
 * @author a4qt9zz
 */
public interface IDataLoaderCsv extends IDataLoader {
	
	/**
	 * Method that saves updated csv to /target/csv directory
	 */
	public void save() throws Exception;
}
