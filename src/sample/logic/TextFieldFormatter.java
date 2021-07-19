package sample.logic;

import javafx.scene.control.TextFormatter;

public class TextFieldFormatter {
	//определяем формат ввода данных в поля очков
	public static TextFormatter.Change validatePointInput(TextFormatter.Change change) {
		String oldText = change.getControlText();
		String newText = change.getControlNewText();

		if (newText.length() < oldText.length()) {
			return change;
		}

		if (newText.matches("[1-9][0-9]{0,2}")) {
			if (newText.length() == 3 && Integer.parseInt(newText) > 100) {
				change.setRange(0, 2);
				change.setText(Integer.toString(100));
			}
			return change;
		}
		return null;
	}

	//определяем формат ввода данных в поля времени
	public static TextFormatter.Change validateTimeInput(TextFormatter.Change change, int maxDigits) {
		String oldText = change.getControlText();
		String newText = change.getControlNewText();

		if (newText.length() < oldText.length()) {
			return change;
		}

		if (newText.matches(String.format("[0-9]{0,%d}", maxDigits))) {
			if (newText.length() == maxDigits && Integer.parseInt(newText) > (int) Math.pow(10, maxDigits) - 1) {
				change.setRange(0, maxDigits - 1);
				change.setText(Integer.toString((int) Math.pow(10, maxDigits) - 1));
			}
			return change;
		}
		return null;
	}

	//определяем формат ввода данных в поля времени с верхней границей значений
	public static TextFormatter.Change validateTimeInput(TextFormatter.Change change, int maxDigits, int maxValue) {
		String oldText = change.getControlText();
		String newText = change.getControlNewText();

		if (newText.length() < oldText.length()) {
			return change;
		}

		if (newText.matches(String.format("[0-9]{0,%d}", maxDigits))) {
			if (newText.length() == maxDigits && Integer.parseInt(newText) > maxValue) {
				change.setRange(0, maxDigits - 1);
				change.setText(Integer.toString(maxValue));
			}
			return change;
		}
		return null;
	}
}
