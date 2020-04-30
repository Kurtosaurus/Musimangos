package application;
	
import java.io.File;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


//display settings for start screen
/*MusimangosMain is the main call fuction for the musimangos application
it creates the scenes. and handles menu screen animations
the application allows the user to play a music guessing game
Kurt Smith, Ivan Rivera, Coorey Ramsgates, Kalie Davis Pablo Perez*/

public class MusimangosMain extends Application {
	public Circle cir1 = new Circle();
	public Circle cir2 = new Circle();
	public Circle cir3 = new Circle();
	public Circle cir4 = new Circle();
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println(System.getProperty("user.dir"));
			
			
					
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene scene = new Scene(root,600,400);
			root.getChildren().add(cir1);
			root.getChildren().add(cir2);

			root.getChildren().add(cir3);

			root.getChildren().add(cir4);



			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Musimangos");
			System.out.println(System.getProperty("user.dir"));
			primaryStage.setResizable(false);
			primaryStage.show();
			//cool circle move thing 

			cir1.setFill(Color.TEAL);
			cir1.setRadius(30);
			cir1.setLayoutX(1);
			cir1.setLayoutY(1);
			cir1.toBack();
			
			cir2.setFill(Color.CYAN);
			cir2.setRadius(30);
			cir2.setLayoutX(1);
			cir2.setLayoutY(400);
			cir2.toBack();
			
			cir3.setFill(Color.AQUAMARINE);
			cir3.setRadius(30);
			cir3.setLayoutX(600);
			cir3.setLayoutY(400);
			cir3.toBack();
			
			cir4.setFill(Color.LIGHTBLUE);
			cir4.setRadius(30);
			cir4.setLayoutX(600);
			cir4.setLayoutY(1);
			cir4.toBack();
			
			ScaleTransition st = new ScaleTransition(Duration.seconds(7), cir1) ;
			st.setByX(20f);
			st.setByY(20f);
			st.setCycleCount(Animation.INDEFINITE);;
			st.setAutoReverse(true);
			st.play();
			
			ScaleTransition st1 = new ScaleTransition(Duration.seconds(7), cir2) ;
			st1.setDelay(Duration.seconds(1));

			st1.setByX(20f);
			st1.setByY(20f);
			st1.setCycleCount(Animation.INDEFINITE);;
			st1.setAutoReverse(true);
			st1.play();
			
			ScaleTransition st2 = new ScaleTransition(Duration.seconds(7), cir3) ;
			st2.setDelay(Duration.seconds(2));
			st2.setByX(20f);
			st2.setByY(20f);
			st2.setCycleCount(Animation.INDEFINITE);;
			st2.setAutoReverse(true);
			st2.play();
			
			ScaleTransition st3 = new ScaleTransition(Duration.seconds(7), cir4) ;
			st3.setDelay(Duration.seconds(3));

			st3.setByX(20f);
			st3.setByY(20f);
			st3.setCycleCount(Animation.INDEFINITE);;
			st3.setAutoReverse(true);
			st3.play();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
