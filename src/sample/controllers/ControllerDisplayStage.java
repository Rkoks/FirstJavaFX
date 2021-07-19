package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import sample.logic.CoreLogic;
import sample.logic.ScrollBarManger;
import sample.logic.StartInits;
import sample.logic.Team;

public class ControllerDisplayStage {

	@FXML
	private TableView<Team> resultTable;

	@FXML
	void initialize() {
		StartInits.resultTableView = resultTable;
		StartInits.createResultTable();
		CoreLogic.vBar = new ScrollBarManger(resultTable);
	}
}
