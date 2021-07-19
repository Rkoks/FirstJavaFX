package sample.logic;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class StartInits {
	//	private static final ObservableList<Integer> circlesCounts = FXCollections.observableArrayList(1, 2, 3);
	public static TableView<Team> resultTableView;

	public static void setFormattersToTextFields(TextField pointField, TextField minutesField, TextField secondsField,
			TextField millisField) {
		pointField
				.setTextFormatter(new TextFormatter<String>(change -> TextFieldFormatter.validatePointInput(change)));
		minutesField
				.setTextFormatter(new TextFormatter<String>(change -> TextFieldFormatter.validateTimeInput(change, 2)));
		secondsField.setTextFormatter(
				new TextFormatter<String>(change -> TextFieldFormatter.validateTimeInput(change, 2, 59)));
		millisField
				.setTextFormatter(new TextFormatter<String>(change -> TextFieldFormatter.validateTimeInput(change, 3)));
	}

	public static class MyTeamConverter extends StringConverter<Team> {
		@Override
		public String toString(Team team) {
			if (team != null) {
				return team.getName();
			}
			return null;
		}

		@Override
		public Team fromString(String s) {
			return null;
		}
	}

	public static class MyCircleCountConverter extends StringConverter<Integer> {

		@Override
		public String toString(Integer integer) {
			if (integer != null) {
				switch (integer) {
					case 1:
						return "Первый заезд";
					case 2:
						return "Второй заезд";
					case 3:
						return "Доп. заезд";
				}
			}
			return null;
		}

		@Override
		public Integer fromString(String s) {
			return null;
		}
	}

	public static void createResultTable(){
		//создаём таблицу

		resultTableView.setItems(CoreLogic.sortedTeams);
		resultTableView.setEditable(false);
		resultTableView.setStyle("-fx-font-size: 30px;-fx-alignment: center-right;");

		//создаем поле с номером занимаемого места
		resultTableView.getColumns().add(NumberTableCellFactory.createNumberColumn("#", 1));

		//TODO создаём столбцы с полями из Team

		//столбец имя команды
		TableColumn<Team, String> nameCol = new TableColumn<>("Команда");
		nameCol.setSortable(false);
//		nameCol.setCellFactory(cell->cell.setWrapText(true));
		nameCol.setCellFactory(tc->{TableCell<Team, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(nameCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell;});
		nameCol.setCellValueFactory(
				team -> Bindings.createObjectBinding(() -> team.getValue().getName()));
		nameCol.setMinWidth(250);

		//добавляем столбец 1 заезда и в него столбцы с баллами и временем
		TableColumn firstCircleCol = new TableColumn("Первый заезд");
		firstCircleCol.setSortable(false);
		TableColumn<Team, Integer> firstCirclePointsCol = new TableColumn<>("Баллы");
		firstCirclePointsCol.setSortable(false);
		firstCirclePointsCol.setCellValueFactory(
				team -> Bindings.createObjectBinding(() -> team.getValue().getFirstCirclePoints()));
		TableColumn<Team, String> firstCircleTimeCol = new TableColumn<>("Время");
		firstCircleTimeCol.setSortable(false);
		firstCircleTimeCol.setCellValueFactory(
				team -> Bindings.createObjectBinding(() -> team.getValue().getFirstCircleTimeToDisplay()));
		firstCircleCol.getColumns().add(firstCirclePointsCol);
		firstCircleCol.getColumns().add(firstCircleTimeCol);

		//добавляем столбец 2 заезда и в него столбцы с баллами и временем
		TableColumn secondCircleCol = new TableColumn("Второй заезд");
		secondCircleCol.setSortable(false);
		TableColumn<Team, Integer> secondCirclePointsCol = new TableColumn<>("Баллы");
		secondCirclePointsCol.setSortable(false);
		secondCirclePointsCol.setCellValueFactory(
				team -> Bindings.createObjectBinding(() -> team.getValue().getSecondCirclePoints()));
		TableColumn<Team, String> secondCircleTimeCol = new TableColumn<>("Время");
		secondCircleTimeCol.setSortable(false);
		secondCircleTimeCol.setCellValueFactory(
				team -> Bindings.createObjectBinding(() -> team.getValue().getSecondCircleTimeToDisplay()));
		secondCircleCol.getColumns().add(secondCirclePointsCol);
		secondCircleCol.getColumns().add(secondCircleTimeCol);

		//добавляем столбец доп заезда и в него столбцы с баллами и временем
		TableColumn additionalCircleCol = new TableColumn("Дополнительный заезд");
		additionalCircleCol.setSortable(false);
		TableColumn<Team, Integer> additionalCirclePointsCol = new TableColumn<>("Баллы");
		additionalCirclePointsCol.setSortable(false);
		additionalCirclePointsCol.setCellValueFactory(
				team -> Bindings.createObjectBinding(() -> team.getValue().getAdditionalCirclePoints()));
		TableColumn<Team, String> additionalCircleTimeCol = new TableColumn<>("Время");
		additionalCircleTimeCol.setSortable(false);
		additionalCircleTimeCol.setCellValueFactory(
				team -> Bindings.createObjectBinding(() -> team.getValue().getAdditionalCircleTimeToDisplay()));
		additionalCircleCol.getColumns().add(additionalCirclePointsCol);
		additionalCircleCol.getColumns().add(additionalCircleTimeCol);

		//добавляем наши столбцы в таблицу
		resultTableView.getColumns().add(nameCol);
		resultTableView.getColumns().add(firstCircleCol);
		resultTableView.getColumns().add(secondCircleCol);
		resultTableView.getColumns().add(additionalCircleCol);

	}

}
