package com.guzman.projects.teamgenerator.teamgen;

import java.util.Comparator;
/**
 * 
 * @author Jonathan Guzman
 */
public class MemberComparator implements Comparator<Member> {

	@Override
	public int compare(Member o1, Member o2) {
		return (o1.getFirstName() + " " + o1.getLastName())
				.compareToIgnoreCase(o2.getFirstName() + " " + o2.getLastName());
	}
}
