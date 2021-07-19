package sample.logic;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CoreLogic {
	public static String               resultFilePath;
	public static Team selectedTeam = null;
	public static Comparator<Team> teamComparator = new TeamComparatorByResults();
	public static ObservableList<Team> teams = FXCollections.observableArrayList();
	public static ObservableList<Team> sortedTeams = FXCollections.observableArrayList();
	public static ScrollBarManger vBar;

	public static AnimationTimer animationTimer = new AnimationTimer() {
		@Override
		public void handle(long l) {
			vBar.animateScrollBar(l);
		}
	};

	public static void addTeam(String newTeamName) {
		if (newTeamName != null && newTeamName != "") {//проверка на пустоту
			Team newTeam = new Team(newTeamName);
			if (CoreLogic.teams.contains(newTeam)) { //проверка на дублирование команды
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Добавление команды");
				alert.setHeaderText(null);
				alert.setContentText("Команда с таким названием уже существует. Попробуйте ввести другое название!");
				alert.showAndWait();
			} else {
				CoreLogic.teams.add(newTeam);
				CoreLogic.teams.sort(new TeamComparatorByName());
			}

		}
	}

	public static void clearTextField(TextField pointField, TextField minutesField, TextField secondsField,
			TextField millisField) {
		pointField.clear();
		minutesField.clear();
		secondsField.clear();
		millisField.clear();
	}

	public static void saveResults() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(resultFilePath));
			List<Team> teamsToFile = new ArrayList<>(teams);
			objectOutputStream.writeObject(teamsToFile);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadResults(){
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(resultFilePath));
			List<Team> teamsFromFile = (ArrayList<Team>) objectInputStream.readObject();
			teams.clear();
			teams.addAll(teamsFromFile);
			refreshResultTable();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void setChangeCirclesBar(ComboBox<Integer> comboBar, Team team) {
		comboBar.getItems().clear();
		Integer circleCount = team.getMainCirclesCount();
		if (team.isAdditionalCircleDone()) {
			circleCount++;
		}
		ObservableList<Integer> circles = FXCollections.observableArrayList();

		for (int i = 1; i < circleCount + 1; i++) {
			circles.add(i);
		}
		comboBar.getItems().addAll(circles);
	}

	public static void setResultToFields(Integer selectedCircle, TextField pointField, TextField minutesField,
			TextField secondsField, TextField millisField) {
		Integer time;
		switch (selectedCircle) {
			case 1:
				pointField.setText(selectedTeam.getFirstCirclePoints().toString());
				time = selectedTeam.getFirstCircleTime();
				minutesField.setText(time / 60000 % 60 + "");
				secondsField.setText(time / 1000 % 60+"");
				millisField.setText(time % 1000+"");
				break;
			case 2:
				pointField.setText(selectedTeam.getSecondCirclePoints().toString());
				time = selectedTeam.getSecondCircleTime();
				minutesField.setText(time / 60000 % 60+"");
				secondsField.setText(time / 1000 % 60+"");
				millisField.setText(time % 1000+"");
				break;
			case 3:
				pointField.setText(selectedTeam.getAdditionalCirclePoints().toString());
				time = selectedTeam.getAdditionalCircleTime();
				minutesField.setText(time / 60000 % 60+"");
				secondsField.setText(time / 1000 % 60+"");
				millisField.setText(time % 1000+"");
				break;
		}
	}

	public static void refreshResultTable() {
		sortedTeams.clear();
		sortedTeams.addAll(teams);
		FXCollections.sort(sortedTeams, teamComparator);
		StartInits.resultTableView.refresh();
	}
}
