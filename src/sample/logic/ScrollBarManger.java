package sample.logic;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;

public class ScrollBarManger {
	private double          minPosition;
	private double          maxPosition;
	private double          value; //текущее положение скроллбара
	private ScrollBar       verticalBar;
	private ScrollBar       horizontalBar;
	private TableView<Team> table;
	private boolean         isAnimated = false; //происходит ли анимация
	private int             difTeams; //количество скрытых команд от экрана
	private double          difValue; //смещение скроллбара на 1 команду
	private double          difPosition; //смещение за одну наносекунду
	private long            previousTime; //время предшествующей анимации

	private final int SPEED_OF_TEAMS_ANIMATION = 2; //за сколько секунд должна появляться невидимая команда на экране
	public final long NANOS_IN_SEC = 1000000000;
//	private boolean wasChanged = false;

	public ScrollBarManger(TableView<Team> table) {
		this.table = table;
		previousTime = 0;
//		CoreLogic.animationTimer.start();
	}

	public void updateScrollBar(){
		if (verticalBar == null) {
			verticalBar = (ScrollBar) table.lookup(".scroll-bar:vertical");
			verticalBar.setStyle("-fx-min-width: 0; -fx-pref-width: 0; -fx-max-width: 0; -fx-opacity: 0;");

//			((ScrollBar) table.lookupAll(".scroll-bar")).setStyle("-fx-min-width: 0; -fx-pref-width: 0; -fx-max-width: 0;-fx-min-height: 0; -fx-pref-height: 0; -fx-max-height: 0; -fx-opacity: 0;");

//			wasChanged = true;
		}

		minPosition = verticalBar.getMin();
		maxPosition = verticalBar.getMax();
		value = verticalBar.getValue();
		checkAnimationNeed();
//		System.out.println(this.toString());
//		wasChanged = true;
	}

	private void checkAnimationNeed() {
		if (verticalBar.getVisibleAmount() > 0) {
			int teamCount = CoreLogic.teams.size();
			int visibleTeams = (int) Math.round((teamCount - 1) * verticalBar.getVisibleAmount());
			difTeams = teamCount - visibleTeams;
			difValue = (maxPosition - minPosition) / difTeams;
			difPosition = difValue / (SPEED_OF_TEAMS_ANIMATION * NANOS_IN_SEC);
			if (!isAnimated) {
				CoreLogic.animationTimer.start();
				isAnimated = true;
			}
		} else {
			CoreLogic.animationTimer.stop();
			previousTime = 0;
			isAnimated = false;
		}
	}

	public void animateScrollBar(long now) {
//		System.out.println("now: " + now);
		if (previousTime == 0) {
			previousTime = now;
		}

		if (value > maxPosition + difPosition * SPEED_OF_TEAMS_ANIMATION * NANOS_IN_SEC) {
			value = minPosition - difPosition * SPEED_OF_TEAMS_ANIMATION * NANOS_IN_SEC * 2;
		} else {
			value += (now - previousTime) * difPosition;
			previousTime = now;
		}
		updatePosition();
	}

	private void updatePosition(){
		if (value > maxPosition) {
			verticalBar.setValue(maxPosition);
		} else if (value < minPosition) {
			verticalBar.setValue(minPosition);
		} else {
			verticalBar.setValue(value);
		}
	}

	@Override
	public String toString() {
		return "ScrollBarManger{" +
				"minPosition=" + minPosition +
				", maxPosition=" + maxPosition +
				", value=" + value +
				", isAnimated=" + isAnimated +
				", visibleTeams=" + difTeams +
				", difValue=" + difValue +
				", difPosition=" + difPosition +
				", previousTime=" + previousTime +
				'}';
	}

	public void resetAnimation() {
		isAnimated = false;
	}
	//	public void showScrollBarData(){
//		if (wasChanged) {
//			System.out.println("min: " + minPosition + " max: " + maxPosition + " value: " + value);
//			wasChanged = false;
//		}
//
//	}
}
