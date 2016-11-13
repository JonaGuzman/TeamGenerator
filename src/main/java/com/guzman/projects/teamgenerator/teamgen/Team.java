package com.guzman.projects.teamgenerator.teamgen;

import java.util.ArrayList;

/**
 * Team is a collection of <strong>members</strong>
 * 
 * @see Member 
 * 
 * @author Jonathan Guzman
 */
public class Team extends ArrayList<Member> {
	private static final long serialVersionUID = 1L;
	
	private int teamIndex;

	public int getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}
	
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Team " + teamIndex)
			.append(System.getProperty("line.separator"));
		
		for (Member e : this) {
			builder.append(e).append(
					System.getProperty("line.separator"));
		}
		
		return builder.toString();
	}
}
