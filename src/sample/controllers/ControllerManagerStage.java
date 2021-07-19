package sample.controllers;

		import javafx.event.ActionEvent;
		import javafx.fxml.FXML;
		import javafx.scene.control.*;
		import javafx.scene.layout.AnchorPane;
		import javafx.stage.FileChooser;
		import sample.Main;
		import sample.logic.CoreLogic;
		import sample.logic.StartInits;
		import sample.logic.Team;

		import java.io.File;

		import static sample.logic.CoreLogic.*;


public class ControllerManagerStage {

	@FXML
	private Button fullscreenBtn;

	@FXML
	private Button loadFileBtn;

	@FXML
	private Label file_address_label;

	@FXML
	private Button saveFileBtn;

	@FXML
	private Button createTeamBtn;

	@FXML
	private Button addNewCircleBtn;

	@FXML
	private TextField newTeamNameField;

	@FXML
	private ComboBox<Team> teamChoiceBar;

	@FXML
	private Button deleteLastCircleBtn;

	@FXML
	private AnchorPane new_circle_menu;

	@FXML
	private TextField newCirclePointsField;

	@FXML
	private TextField newCircleMinutesField;

	@FXML
	private TextField newCircleSecondsField;

	@FXML
	private TextField newCircleMillisField;

	@FXML
	private Button newCircleAcceptBtn;

	@FXML
	private Button changeCircleBtn;

	@FXML
	private AnchorPane change_circle_menu;

	@FXML
	private TextField changeCirclePointsField;

	@FXML
	private TextField changeCircleMinutesField;

	@FXML
	private TextField changeCircleSecondsField;

	@FXML
	private TextField changeCircleMillisField;

	@FXML
	private Button changeCircleAcceptBtn;

	@FXML
	private ComboBox<Integer> changeCircleChoiceBar;

	@FXML
	private Button deleteTeamBtn;

	@FXML
	private AnchorPane circles_control_menu;

	@FXML
	private Label statusLabel;

	@FXML
	void initialize(){
		file_address_label.setText(new File("").getAbsolutePath());
		teamChoiceBar.setConverter(new StartInits.MyTeamConverter());
		teamChoiceBar.setItems(teams); //связываем список команд с выпадающим списком

		//устанавливаем формат полей ввода новых результатов
		StartInits.setFormattersToTextFields(newCirclePointsField, newCircleMinutesField, newCircleSecondsField,
				newCircleMillisField);

		changeCircleChoiceBar.setConverter(new StartInits.MyCircleCountConverter());
		//устанавливаем формат полей ввода измененных результатов
		StartInits.setFormattersToTextFields(changeCirclePointsField, changeCircleMinutesField, changeCircleSecondsField,
				changeCircleMillisField);


	}

	@FXML
	void onClickedAddNewCircleBtn(ActionEvent event) {
		//отображаем меню добавления результатов нового заезда
		if (!new_circle_menu.isVisible()) {
			CoreLogic.clearTextField(newCirclePointsField, newCircleMinutesField, newCircleSecondsField,
					newCircleMillisField);
			new_circle_menu.setVisible(true);
			change_circle_menu.setVisible(false);
		}
	}

	@FXML
	void onClickedChangeCircleAcceptBtn(ActionEvent event) {
		if (!changeCircleChoiceBar.getSelectionModel().isEmpty()) {
			String points = changeCirclePointsField.getText();
			if (points == "") {
				points = null;
			}
			String minutes = changeCircleMinutesField.getText();
			String seconds = changeCircleSecondsField.getText();
			String millis = changeCircleMillisField.getText();
			if (minutes == "" && seconds == "" && millis == "") {
				minutes = null;
				seconds = null;
				millis = null;
			}
			selectedTeam.changeCircleResult(points, minutes, seconds, millis, changeCircleChoiceBar.getValue());
			CoreLogic.refreshResultTable();
		}
	}

	@FXML
	void onClickedChangeCircleBtn(ActionEvent event) {
		//отображаем меню изменения результатов добавленных заездов
		if (!change_circle_menu.isVisible()) {
			CoreLogic.clearTextField(changeCirclePointsField, changeCircleMinutesField, changeCircleSecondsField,
					changeCircleMillisField);
			CoreLogic.setChangeCirclesBar(changeCircleChoiceBar, selectedTeam);
			changeCircleChoiceBar.getSelectionModel().clearSelection();
			change_circle_menu.setVisible(true);
			new_circle_menu.setVisible(false);
		}

	}

	@FXML
	void onClickedCreateTeamBtn(ActionEvent event) {
		String newTeamName = newTeamNameField.getText();
		CoreLogic.addTeam(newTeamName);
		teamChoiceBar.getSelectionModel().select(teams.indexOf(new Team(newTeamName)));
		newTeamNameField.clear();
		CoreLogic.refreshResultTable();
		vBar.resetAnimation();
		CoreLogic.vBar.updateScrollBar();
	}

	@FXML
	void onClickedDeleteLastCircleBtn(ActionEvent event) {
		selectedTeam.deleteLastCircleResult();
		CoreLogic.refreshResultTable();
		if (change_circle_menu.isVisible()) {
			CoreLogic.setChangeCirclesBar(changeCircleChoiceBar, selectedTeam);
		}
	}

	@FXML
	void onClickedDeleteTeamBtn(ActionEvent event) {
		teams.remove(teamChoiceBar.getValue());
		teamChoiceBar.getSelectionModel().clearSelection();
		CoreLogic.refreshResultTable();
	}

	@FXML
	void onClickedFullscreenBtn(ActionEvent event) {
		Main.changeFullscreen();
	}

	@FXML
	void onClickedLoadFileBtn(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Game File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Result files (*.rsl", "*.rsl");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialDirectory(new File("").getAbsoluteFile());
		File file = fileChooser.showOpenDialog(Main.managerStage);
		if (file != null) {
			CoreLogic.resultFilePath = file.getAbsolutePath();
			file_address_label.setText(CoreLogic.resultFilePath);
		}
		CoreLogic.loadResults();
		teamChoiceBar.setVisibleRowCount(10);
	}

	@FXML
	void onClickedNewCircleAcceptBtn(ActionEvent event) {
		selectedTeam.setCircleResult(newCirclePointsField.getText(), newCircleMinutesField.getText(),
				newCircleSecondsField.getText(), newCircleMillisField.getText());
		CoreLogic.refreshResultTable();
	}

	@FXML
	void onClickedSaveFileBtn(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Game File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Result files (*.rsl", "*.rsl");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialDirectory(new File("").getAbsoluteFile());
		File file = fileChooser.showSaveDialog(Main.managerStage);
		if (file != null) {
			CoreLogic.resultFilePath = file.getAbsolutePath();
			file_address_label.setText(CoreLogic.resultFilePath);
		}
		CoreLogic.saveResults();
	}

	public void onClickedTeamChoiceBar(ActionEvent actionEvent) {
		if (teamChoiceBar.getSelectionModel().isEmpty()) {
			circles_control_menu.setVisible(false);
			change_circle_menu.setVisible(false);
			new_circle_menu.setVisible(false);
			statusLabel.setText("");
			selectedTeam = null;
			CoreLogic.clearTextField(newCirclePointsField, newCircleMinutesField, newCircleSecondsField,
					newCircleMillisField);
			CoreLogic.clearTextField(changeCirclePointsField, changeCircleMinutesField, changeCircleSecondsField,
					changeCircleMillisField);
		}
		if (teamChoiceBar.getValue() != null) {
			selectedTeam = teamChoiceBar.getValue();
			circles_control_menu.setVisible(true);
			statusLabel.setText(teamChoiceBar.getValue().getName());
			CoreLogic.clearTextField(newCirclePointsField, newCircleMinutesField, newCircleSecondsField,
					newCircleMillisField);
			CoreLogic.clearTextField(changeCirclePointsField, changeCircleMinutesField, changeCircleSecondsField,
					changeCircleMillisField);
			CoreLogic.setChangeCirclesBar(changeCircleChoiceBar, selectedTeam);
		}
	}

	public void onClickCircleChoiceBar(ActionEvent actionEvent) {
		if (changeCircleChoiceBar.getValue() != null) {
			setResultToFields(changeCircleChoiceBar.getValue(), changeCirclePointsField, changeCircleMinutesField,
					changeCircleSecondsField, changeCircleMillisField);
		}
	}
}

