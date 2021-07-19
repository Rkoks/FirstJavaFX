package sample.logic;

import java.io.Serializable;

public class Team implements Serializable {
	private String  name; //имя команды
	private Integer firstCirclePoints; //баллы за первый заезд
	private Integer firstCircleTime; //время первого заезда в мс
	private String  firstCircleTimeToDisplay; //время первого заезда в формате HH:mm:ss.millis
	private Integer secondCirclePoints; //баллы за второй заезд
	private Integer secondCircleTime; //время второго заезда в мс
	private String  secondCircleTimeToDisplay; //время второго заезда в формате HH:mm:ss.millis
	private Integer mainCirclesPoints; //сумма очков за два основных заезда для сравнения
	private Integer mainCirclesTime; //суммарное время за два основных заезда для сравнения
	private Integer mainCirclesCount; //количество выполненных основных заездов для первичного сравнения
	private Integer additionalCirclePoints; //баллы за дополнительный заезд
	private Integer additionalCircleTime; //время дополнительного заезда в мс
	private String  additionalCircleTimeToDisplay; //время дополнительного заезда в формате HH:mm:ss.millis
	private boolean additionalCircleDone; //был ли пройден дополнительный заезд

	public Team(String name) {
		this.name = name;
		firstCirclePoints = null;
		firstCircleTime = null;
		firstCircleTimeToDisplay = null;
		secondCirclePoints = null;
		secondCircleTime = null;
		secondCircleTimeToDisplay = null;
		mainCirclesPoints = null;
		mainCirclesTime = null;
		mainCirclesCount = 0;
		additionalCirclePoints = null;
		additionalCircleTime = null;
		additionalCircleTimeToDisplay = null;
		additionalCircleDone = false;
	}

	//Преобразуем числовое значение времени в строку формата HH:mm:ss.millis
	private String convertToTimeForDisplay(Integer time) {
		String result = "";
		Integer millis = time % 1000;
		Integer seconds = time / 1000 % 60;
		Integer minutes = time / 60000 % 60;
		result = String.format("%02d:%02d.%03d", minutes, seconds, millis);
		return result;
	}

	private Integer convertToTimeForRating(String minutes, String seconds, String millis) {
		int mm = (minutes == "" ? 0 : Integer.parseInt(minutes)) * 60000;
		int ss = (seconds == "" ? 0 : Integer.parseInt(seconds)) * 1000;
		int ms = (millis == "" ? 0 : Integer.parseInt(millis));
		return (mm + ss + ms);
	}

	//установить показатели заезда
	public void setCircleResult(String points, String minutes, String seconds, String millis) {
		Integer circleTime = convertToTimeForRating(minutes, seconds, millis);
		Integer intPoints = points == "" ? 0 : Integer.parseInt(points);
		if (mainCirclesCount == 0) {
			firstCirclePoints = intPoints;
			firstCircleTime = circleTime;
			firstCircleTimeToDisplay = convertToTimeForDisplay(circleTime);
			mainCirclesCount = 1;
			mainCirclesPoints = firstCirclePoints;
			mainCirclesTime = firstCircleTime;
		} else if (mainCirclesCount == 1) {
			secondCirclePoints = intPoints;
			secondCircleTime = circleTime;
			secondCircleTimeToDisplay = convertToTimeForDisplay(circleTime);
			mainCirclesCount = 2;
			mainCirclesPoints = firstCirclePoints + secondCirclePoints;
			mainCirclesTime = firstCircleTime + secondCircleTime;
		} else if (mainCirclesCount == 2) {
			additionalCirclePoints = intPoints;
			additionalCircleTime = circleTime;
			additionalCircleTimeToDisplay = convertToTimeForDisplay(circleTime);
			additionalCircleDone = true;
		}
	}

	//изменить показатели заезда (circleToChange = 1,2,3 -> first, second, additional соответственно)
	//если нет необходимости изменять, то передаём null
	public void changeCircleResult(String points, String minutes, String seconds, String millis,
			Integer circleToChange) {
		Integer circleTime = null;
		if (!(minutes == null || seconds == null || millis == null)) {
			circleTime = convertToTimeForRating(minutes, seconds, millis);
		}

		if (circleToChange == 1) {
			if (points != null && firstCirclePoints != null) {
				mainCirclesPoints -= firstCirclePoints;
				firstCirclePoints = Integer.parseInt(points);
				mainCirclesPoints += firstCirclePoints;
			}
			if (circleTime != null && firstCircleTime != null) {
				mainCirclesTime -= firstCircleTime;
				firstCircleTime = circleTime;
				mainCirclesTime += firstCircleTime;

				firstCircleTimeToDisplay = convertToTimeForDisplay(circleTime);
			}
		} else if (circleToChange == 2) {
			if (points != null && secondCirclePoints != null) {
				mainCirclesPoints -= secondCirclePoints;
				secondCirclePoints = Integer.parseInt(points);
				mainCirclesPoints += secondCirclePoints;
			}
			if (circleTime != null && secondCircleTime != null) {
				mainCirclesTime -= secondCircleTime;
				secondCircleTime = circleTime;
				mainCirclesTime += secondCircleTime;
				secondCircleTimeToDisplay = convertToTimeForDisplay(circleTime);
			}
		} else if (circleToChange == 3) {
			if (points != null && additionalCirclePoints != null) {
				additionalCirclePoints = Integer.parseInt(points);
			}
			if (circleTime != null && additionalCircleTime != null) {
				additionalCircleTime = circleTime;
				additionalCircleTimeToDisplay = convertToTimeForDisplay(circleTime);
			}
		}
	}

	//удалить результаты последнего заезда
	public void deleteLastCircleResult() {
		if (additionalCircleDone) {
			additionalCirclePoints = null;
			additionalCircleTime = null;
			additionalCircleTimeToDisplay = null;
			additionalCircleDone = false;
			return;
		}
		if (mainCirclesCount == 2) {
			secondCirclePoints = null;
			secondCircleTime = null;
			secondCircleTimeToDisplay = null;
			mainCirclesPoints = firstCirclePoints;
			mainCirclesTime = firstCircleTime;
			mainCirclesCount = 1;
			return;
		}
		if (mainCirclesCount == 1) {
			firstCirclePoints = null;
			firstCircleTime = null;
			firstCircleTimeToDisplay = null;
			mainCirclesPoints = null;
			mainCirclesTime = null;
			mainCirclesCount = 0;
		}
	}

	@Override
	public String toString() {
		return "Team{" +
				"name='" + name + '\'' +
				", firstCirclePoints=" + firstCirclePoints +
				", firstCircleTime=" + firstCircleTime +
				", firstCircleTimeToDisplay='" + firstCircleTimeToDisplay + '\'' +
				", secondCirclePoints=" + secondCirclePoints +
				", secondCircleTime=" + secondCircleTime +
				", secondCircleTimeToDisplay='" + secondCircleTimeToDisplay + '\'' +
				", mainCirclesPoints=" + mainCirclesPoints +
				", mainCirclesTime=" + mainCirclesTime +
				", mainCirclesCount=" + mainCirclesCount + '\'' +
				", additionalCirclePoints=" + additionalCirclePoints +
				", additionalCircleTime=" + additionalCircleTime +
				", additionalCircleTimeToDisplay='" + additionalCircleTimeToDisplay +
				", additionalCircleDone=" + additionalCircleDone +
				'}';
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return this.name.equalsIgnoreCase(((Team)obj).getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFirstCirclePoints() {
		return firstCirclePoints;
	}

	public Integer getSecondCirclePoints() {
		return secondCirclePoints;
	}

	public Integer getAdditionalCirclePoints() {
		return additionalCirclePoints;
	}

	public String getFirstCircleTimeToDisplay() {
		return firstCircleTimeToDisplay;
	}

	public String getSecondCircleTimeToDisplay() {
		return secondCircleTimeToDisplay;
	}

	public String getAdditionalCircleTimeToDisplay() {
		return additionalCircleTimeToDisplay;
	}

	public Integer getMainCirclesPoints() {
		return mainCirclesPoints;
	}

	public Integer getMainCirclesTime() {
		return mainCirclesTime;
	}

	public Integer getMainCirclesCount() {
		return mainCirclesCount;
	}

	public Integer getAdditionalCircleTime() {
		return additionalCircleTime;
	}

	public Integer getFirstCircleTime() {
		return firstCircleTime;
	}

	public Integer getSecondCircleTime() {
		return secondCircleTime;
	}

	public boolean isAdditionalCircleDone() {
		return additionalCircleDone;
	}

}
