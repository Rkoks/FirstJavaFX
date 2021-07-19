package sample.logic;

import java.util.Comparator;

public class TeamComparatorByName implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
