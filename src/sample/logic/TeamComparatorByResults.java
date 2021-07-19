package sample.logic;

import java.util.Comparator;

public class TeamComparatorByResults implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		int result1 = -o1.getMainCirclesCount().compareTo(o2.getMainCirclesCount());
		if (result1 != 0) {
			return result1;
		}

		if (!(o1.getMainCirclesPoints() == null || o1.getMainCirclesTime() == null || o2.getMainCirclesPoints() == null ||
				o2.getMainCirclesTime() == null)) {
			int result2 = -o1.getMainCirclesPoints().compareTo(o2.getMainCirclesPoints());
			if (result2 != 0) {
				return result2;
			}
			int result3 = o1.getMainCirclesTime().compareTo(o2.getMainCirclesTime());
			if (result3 != 0) {
				return result3;
			}
		}

		if (o1.isAdditionalCircleDone() &&
				!(o1.getAdditionalCirclePoints() == null || o1.getAdditionalCircleTime() == null ||
						o2.getAdditionalCirclePoints() == null ||
						o2.getAdditionalCircleTime() == null)) {

			int result4 = -o1.getAdditionalCirclePoints().compareTo(o2.getAdditionalCirclePoints());
			if (result4 != 0) {
				return result4;
			}
			int result5 = o1.getAdditionalCircleTime().compareTo(o2.getAdditionalCircleTime());
			if (result5 != 0) {
				return result5;
			}
		}

		return result1;
	}
}
