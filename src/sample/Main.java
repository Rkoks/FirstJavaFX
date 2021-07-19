package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.ControllerManagerStage;
import sample.logic.CoreLogic;

import java.io.IOException;

public class Main extends Application {
	public static Stage managerStage;
	public static Stage displayStage;


	@Override
	public void start(Stage primaryStage) throws Exception {
		managerStage = primaryStage;
        loadManagerWindow();

		displayStage = new Stage();
		loadDisplayWindow();
	}

	private void loadManagerWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader();

		Parent root = loader.load(getClass().getResource("fxml/manager_window.fxml"));
		loader.setController(new ControllerManagerStage());
		managerStage.setScene(new Scene(root, 700, 500));

		managerStage.setTitle("Управление результатами соревнований");

//		managerStage.setAlwaysOnTop(true);
		managerStage.show();
		managerStage.setOnCloseRequest(windowEvent -> displayStage.close());
	}

    private void loadDisplayWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Parent root = loader.load(getClass().getResource("fxml/display_window.fxml"));

        displayStage.setScene(new Scene(root, 300, 275));

        displayStage.setTitle("Отображение результатов соревнований");
        displayStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
				displayStage.toFront();
			}
		});

        displayStage.show();

    }

	public static void changeFullscreen() {
		if (!displayStage.isShowing()) {
			displayStage.show();
		}
		displayStage.setFullScreen(!displayStage.isFullScreen());

		CoreLogic.vBar.resetAnimation();
		CoreLogic.vBar.updateScrollBar();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
