

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Drawing extends Application {

	private VBox drawFrame;
	private StackPane drawArea;
	private Rectangle rectangle;
	private Pane draw;
	private Text penStatus;
	private ToggleGroup group;
	private RadioButton redRadioButton;
	private RadioButton blueRadioButton;
	private RadioButton yellowRadioButton;
	private RadioButton eraserRadioButton;
	private Button clearButton;
	private Button undoButton;
	private Paint color;
	private int clickCount;
	private HBox buttonArea;
	private Circle newCircle;
	private Circle circle;

	/*
	 * ***************************************** 
	 * Stage
	 ******************************************/
	public void start(Stage primaryStage) {

		penStatus = new Text("Pen: OFF");

		draw = new Pane();
		draw.setPrefSize(450, 420);
		rectangle = new Rectangle(450, 420, Color.WHITE);
		rectangle.setStroke(Color.BLACK);
		drawArea = new StackPane(rectangle, draw);
		drawArea.setPrefSize(450, 420);

		drawArea.setOnMouseClicked(this::handleMouseClicks);
		drawArea.setOnMouseMoved(this::handleMouseMove);

		group = new ToggleGroup();
		redRadioButton = new RadioButton("Red");
		redRadioButton.setUserData(Color.RED);

		blueRadioButton = new RadioButton("Blue");
		blueRadioButton.setUserData(Color.BLUE);
		yellowRadioButton = new RadioButton("Yellow");
		yellowRadioButton.setUserData(Color.YELLOW);
		eraserRadioButton = new RadioButton("Eraser");
		eraserRadioButton.setOnAction(this::eraserButtonSelected);

		redRadioButton.setToggleGroup(group);
		blueRadioButton.setToggleGroup(group);
		yellowRadioButton.setToggleGroup(group);
		eraserRadioButton.setToggleGroup(group);
		redRadioButton.setOnAction(this::colorRadioButtonSelected);
		blueRadioButton.setOnAction(this::colorRadioButtonSelected);
		yellowRadioButton.setOnAction(this::colorRadioButtonSelected);

		undoButton = new Button("Undo");
		undoButton.setOnAction(this::undoButtonPressed);
		clearButton = new Button("Clear");
		clearButton.setOnAction(this::clearButtonPressed);

		buttonArea = new HBox(redRadioButton, blueRadioButton, yellowRadioButton, eraserRadioButton, clearButton,
				undoButton);
		buttonArea.setAlignment(Pos.CENTER);
		buttonArea.setSpacing(10);

		clickCount = 0;
		drawFrame = new VBox();
		drawFrame.setSpacing(10);
		drawFrame.setAlignment(Pos.CENTER);
		drawFrame.getChildren().add(penStatus);
		drawFrame.getChildren().add(drawArea);
		drawFrame.getChildren().add(buttonArea);

		Scene scene = new Scene(drawFrame, 500, 500, Color.TRANSPARENT);
		primaryStage.setTitle("Draw something");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/*
	 * ***************************************** 
	 * mouse click
	 ******************************************/
	private void handleMouseClicks(MouseEvent event) {

		if (redRadioButton.isSelected() || blueRadioButton.isSelected() || yellowRadioButton.isSelected()) {
			circle = new Circle(event.getX(), event.getY(), 0);
			if (clickCount % 2 != 0) {
				penStatus.setText("Pen: OFF");
				draw.isVisible();

			} else {
				penStatus.setText("Pen: Draw");
				draw.isDisable();

			}
			draw.getChildren().add(circle);
			clickCount++;
		} else {
			penStatus.setText("Pen: OFF");
		}

	}

	/*
	 * ***************************************** 
	 * mouse move
	 ******************************************/
	private void handleMouseMove(MouseEvent event) {

		if ((redRadioButton.isSelected() || blueRadioButton.isSelected() || yellowRadioButton.isSelected())
				&& (clickCount % 2 != 0)) {
			newCircle = new Circle(event.getX(), event.getY(), 5, color);
			draw.getChildren().add(newCircle);
		}
		if (eraserRadioButton.isSelected()) {
			/*
			 * int count = draw.getChildren().size(); if (count > 0) {
			 * draw.getChildren().remove(count - 1); }
			 */
			draw.getChildren().contains(newCircle);
			Iterator<Node> iterator = draw.getChildren().iterator();
			while (iterator.hasNext()) {
				Node darw = iterator.next();
				iterator.remove();
			}

		}

	}

	/*
	 * ***************************************** 
	 * color choose //connect with mouse
	 * click
	 ******************************************/
	private void colorRadioButtonSelected(ActionEvent event) {
		if (redRadioButton.isSelected()) {
			penStatus.setText("Pen : Red");
		}
		if (blueRadioButton.isSelected()) {
			penStatus.setText("Pen : Blue");
		}
		if (yellowRadioButton.isSelected()) {
			penStatus.setText("Pen : Yellow");

		}

		color = (Color) group.getSelectedToggle().getUserData();

	}

	/*
	 * ***************************************** 
	 * clear button
	 ******************************************/

	private void clearButtonPressed(ActionEvent event) {
		draw.getChildren().clear();
		clickCount = 0;
		draw.setDisable(true);
	}

	/*
	 * ***************************************** 
	 * extraA: eraser//connect with mouse
	 * move
	 ******************************************/
	public void eraserButtonSelected(ActionEvent event) {
		if (eraserRadioButton.isSelected()) {
			penStatus.setText("Pen: Eraser");
		}

	}

	/*
	 * ***************************************** 
	 * extraB: undo button.
	 ******************************************/
	private void undoButtonPressed(ActionEvent event) {
		int count = draw.getChildren().size();
		if (count > 0) {
			draw.getChildren().remove(count - 1);
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
