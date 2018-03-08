/// Looks good. 

/*
 * Ty McMurry
 * Excercise 15.32 Control clock
 * Copyright Feb 2018
 */
package controlclock;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;



public class ControlClock extends Application {
   @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ClockPane clock = new ClockPane(); // Create a clock

    HBox hBox = new HBox(5);//create start and stop button
    Button btStop = new Button("Stop");
    btStop.setStyle("-fx-background-color: maroon; -fx-text-fill: white;");
    Button btStart = new Button("Start");
    btStart.setStyle("-fx-background-color: green; -fx-text-fill: white;");
    hBox.getChildren().addAll(btStop, btStart);
    hBox.setAlignment(Pos.CENTER);
    BorderPane pane = new BorderPane();
    pane.setCenter(clock);
    pane.setBottom(hBox);

   
    Scene scene = new Scene(pane, 250, 250); // Create a scene and place it in the stage
    primaryStage.setTitle("Control Clock"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    

    btStart.setOnAction(e -> clock.start());
    btStop.setOnAction(e -> clock.stop());
  }

  
  public static void main(String[] args) {
    launch(args);
  }

   /// start putting each class into a separate .java file
   
  class ClockPane extends Pane {
    private int hour;
    private int minute;
    private int second;

    private EventHandler<ActionEvent> eventHandler = e -> {
      setCurrentTime(); // Set a new clock time
    };

    // Create an animation for a running clock
    private Timeline animation = new Timeline(
      new KeyFrame(Duration.millis(1000), eventHandler));

    /**
     * Construct a default clock with the current time
     */
    public ClockPane() {
      setCurrentTime();

      animation.setCycleCount(Timeline.INDEFINITE);
      animation.play(); // Start animation
    }

  
    public ClockPane(int hour, int minute, int second) {
      this.hour = hour;
      this.minute = minute;
      this.second = second;
      paintClock();

      animation.setCycleCount(Timeline.INDEFINITE);
      animation.play(); // Start animation
    }

    public void pause() {
      animation.pause();
    }

    public void start() {
      animation.play();
    }

    public void stop() {
      animation.stop();
    }

  
    public int getHour() {
      return hour;
    }

    public void setHour(int hour) {
      this.hour = hour;
      paintClock();
    }

    /**
     * Return minute
     */
    public int getMinute() {
      return minute;
    }

    public void setMinute(int minute) {
      this.minute = minute;
      paintClock();
    }

    public int getSecond() {
      return second;
    }

    public void setSecond(int second) {
      this.second = second;
      paintClock();
    }

    public void setCurrentTime() {
      // Construct a calendar for the current date and time
      Calendar calendar = new GregorianCalendar();

      // Set current hour, minute and second
      this.hour = calendar.get(Calendar.HOUR_OF_DAY);
      this.minute = calendar.get(Calendar.MINUTE);
      this.second = calendar.get(Calendar.SECOND);

      paintClock(); 
    }

    
    private void paintClock() {
      double clockRadius = 
          Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
      double centerX = getWidth() / 2;
      double centerY = getHeight() / 2;

      
      Circle circle = new Circle(centerX, centerY, clockRadius);// create circle
      circle.setFill(Color.SILVER);
      circle.setStroke(Color.MAGENTA);
      Text t1 = new Text(centerX - 5, centerY - clockRadius + 12, "12");
      Text t2 = new Text(centerX - clockRadius + 3, centerY + 5, "9");
      Text t3 = new Text(centerX + clockRadius - 10, centerY + 3, "3");
      Text t4 = new Text(centerX - 3, centerY + clockRadius - 3, "6");

      // Draw second hand
      double sLength = clockRadius * 0.8;
      double secondX = centerX + sLength
              * Math.sin(second * (2 * Math.PI / 60));
      double secondY = centerY - sLength
              * Math.cos(second * (2 * Math.PI / 60));
      Line sLine = new Line(centerX, centerY, secondX, secondY);
      sLine.setStroke(Color.RED);

      // Draw minute hand
      double mLength = clockRadius * 0.65;
      double xMinute = centerX + mLength
              * Math.sin(minute * (2 * Math.PI / 60));
      double minuteY = centerY - mLength
              * Math.cos(minute * (2 * Math.PI / 60));
      Line mLine = new Line(centerX, centerY, xMinute, minuteY);
      mLine.setStroke(Color.BLUE);

      // Draw hour hand
      double hLength = clockRadius * 0.5;
      double hourX = centerX + hLength
              * Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
      double hourY = centerY - hLength
              * Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
      Line hLine = new Line(centerX, centerY, hourX, hourY);
      hLine.setStroke(Color.GREEN);

      getChildren().clear();
      getChildren().addAll(circle, t1, t2, t3, t4, sLine, mLine, hLine);
    }
  
    @Override
    public void setWidth(double width) {
      super.setWidth(width);
      paintClock();
    }
    
    @Override
    public void setHeight(double height) {
      super.setHeight(height);
      paintClock();
    }
  }
}
