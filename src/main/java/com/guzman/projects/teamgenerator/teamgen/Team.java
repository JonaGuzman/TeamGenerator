package com.guzman.projects.teamgenerator.teamgen;

import java.util.ArrayList;
import java.util.List;

/**
 * Team is a collection of <strong>members</strong>
 * 
 * @see Member 
 */
public class Team {
	private int teamIndex;
	private List<Member> members;

	public Team() {
		this.members = new ArrayList<>();
	}

	public void add(Member mem) {
			members.add(mem);
	}

	public Member get(int i) {
		//Returns a single employee from team
		return members.get(i);
	}
	
	public void set(int index, Member member) {
		if (index >= members.size())
            throw new IndexOutOfBoundsException("invalid index:" + (index));
		members.set(index, member);
	}

	public int getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}
	
	public int size() {
		return members.size();
	}

	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Team " + teamIndex)
			.append(System.getProperty("line.separator"));
		
		for (Member e : members) {
			builder.append(e).append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}
}
