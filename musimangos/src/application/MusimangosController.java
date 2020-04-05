package application;

import java.io.File;
import java.io.IOException; //remember to check these kurt...
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MusimangosController {
	
	@FXML
	private AnchorPane MenuPane;
	@FXML
	private AnchorPane PlayPane;
	@FXML
	private AnchorPane OptionsPane;
	@FXML
	private AnchorPane HighscoresPane;
	@FXML
	private MediaView MediaPane;
	String song;
	
	@FXML
	public void playSong(ActionEvent event) throws IOException{
	
		song = "yolo.mp3";
		Media media = new Media(new File(song).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		
	}
	
	@FXML
	public void showPlayScene(ActionEvent event) throws IOException {
		
		PlayPane = FXMLLoader.load(getClass().getResource("Play.fxml"));// pane you are GOING TO
        Scene scene = new Scene(PlayPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        window.setScene(scene);
        window.show();
	}
	@FXML
	 public void showHighscoresScene(ActionEvent event) throws IOException {
		
	   HighscoresPane = FXMLLoader.load(getClass().getResource("Highscores.fxml"));// pane you are GOING TO
       Scene scene = new Scene(HighscoresPane);// pane you are GOING TO show
       Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
       window.setScene(scene);
       window.show();
	}
	@FXML
	 public void showMenuScene(ActionEvent event) throws IOException {
		
	  MenuPane = FXMLLoader.load(getClass().getResource("Menu.fxml"));// pane you are GOING TO
      Scene scene = new Scene(MenuPane);// pane you are GOING TO show
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
      window.setScene(scene);
      window.show();
	}
	@FXML
	 public void showOptionsScene(ActionEvent event) throws IOException {
		
	  OptionsPane = FXMLLoader.load(getClass().getResource("Options.fxml"));// pane you are GOING TO
      Scene scene = new Scene(OptionsPane);// pane you are GOING TO show
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
      window.setScene(scene);
      window.show();
	}


}
