package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	@FXML
	public TextField enteredAnswer;
	@FXML
	public Label scoreLabel;
	@FXML
	public TextArea HighscoresTextArea;
	@FXML
	public Button LoadScoresButton;
	@FXML
	public TextField ScoreField;
	@FXML
	public Button AddScoreButton;

	
	
	
	public String song;
	public boolean hardmode = true;
	public MediaPlayer mediaPlayer;
	public static int HighScore = 0;
	public int baseScore = 0;
	public boolean alreadyAnswered;
	
	
	@FXML
	public void hardMode(ActionEvent event){
		hardmode = true;
	
	}
	@FXML
	public void easyMode( ActionEvent event){
		hardmode = false;
	
	}
	
	public void displayHighscores(ActionEvent event) throws IOException{
		HighscoresTextArea.clear();
		String item;
		File invFile = new File("src/application/Hiscores.txt");
		if(!invFile.exists())
			invFile.createNewFile();
		
		Scanner scan = new Scanner(invFile);
		while(scan.hasNextLine()){	//parse highscores and add them to textarea
			item = scan.nextLine();
			HighscoresTextArea.setText(HighscoresTextArea.getText() + "\n" + item);
		}
		scan.close();
		
		return;
	}
	public void updateHiscores(ActionEvent event) throws IOException{
		HiscoreModel.updateScores(ScoreField.getText(), HighScore);
		
	}
	
	@FXML
	public void playSong(ActionEvent event) throws IOException{
		
		Duration time = new Duration(6000);			//time is ~ 2 seconds
		
		if (SongModel.songList.size() >= 1 ) { //check if there are still some songs to play
			song = SongModel.getNextSong();	//get song name of song to play
			String song1 = song + ".mp3";
			Media media = new Media(getClass().getResource(song1).toExternalForm());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setStopTime(time);
			mediaPlayer.play();
		}
		
		else {
			Alert alert = new Alert(AlertType.INFORMATION);	//tell user all songs have been played
			alert.setTitle("Play again");
			alert.setHeaderText("all songs have been played");
			alert.setContentText("can play again or quit the game");
			alert.showAndWait();
		}
		
		alreadyAnswered = false;		//user hasn't answered yet
		baseScore = 200;			//base number of the total score to be calculated
	}
	
	@FXML
	public void checkAnswer(ActionEvent event) {
		
		String userGuess = enteredAnswer.getText();
		userGuess = userGuess.toLowerCase();
		
		//save string of user's guess 
		String temp = userGuess;
		
		//edit the guess so that it can be compared without whitespace
		String guessArray[] = userGuess.split(" ");
		userGuess = "";
		
		//trim whitespace of user input in case correct song name is more than one word
		for (int i = 0; i < guessArray.length; i++) { 
			userGuess += guessArray[i];
		}
		
		//check if user input is correct song name and if they didn't already answer
		if (userGuess.equals(song) && !alreadyAnswered) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Correct");
			alert.setHeaderText("your answer was correct!");
			alert.setContentText("song played: " + temp);
			alert.showAndWait();
			
			
			alreadyAnswered = true;							//record that user answered correctly
			HighScore += baseScore * SongModel.streak; //calculate the score based on streak and guesses
			SongModel.streak++;								//increment streak since got answer right
			scoreLabel.setText("Score: " + HighScore);		//set text on fxml so it records current score
		}
		else if (alreadyAnswered) {							//check if already answered, don't let user 
			Alert alert = new Alert(AlertType.INFORMATION);	//click guess song and keep getting points
			alert.setTitle("Already answered");
			alert.setHeaderText("you already correctly guessed this song");
			alert.setContentText("song played: " + temp);
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);		//user guessed wrong, show alert
			alert.setTitle("Incorrect");
			alert.setHeaderText("song entered was not correct");
			alert.setContentText("can attempt to guess again");
			alert.showAndWait();
			
			if (baseScore != 20) {	//user got answer wrong so decrement score to be added
				baseScore -= 20;		//make sure user will still get some points if get it right
			}
			if (SongModel.streak != 1 ) {	//decrement winning streak
				SongModel.streak--;			//make sure it won't fall below zero
			}
		}
		enteredAnswer.clear();
	}
	
	@FXML
	public void quitGame(ActionEvent event) throws IOException{
		
		SongModel.songList.clear();		//clear song list
		SongModel.makeList();			//add all songs back into list
		SongModel.streak = 1;
		
		//take user to the highscore screen so they can be shown how well they did 
		//if user was within top ten, ask user to insert name for their score to be recorded
		HighscoresPane = FXMLLoader.load(getClass().getResource("Highscores.fxml"));
		Scene scene = new Scene(HighscoresPane);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
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
