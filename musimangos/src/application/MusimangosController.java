package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URI;
import java.net.URL;


/*musimangosController
handles the main game logic of the application
Kurt Smith, Kalie Davis, Ivan Rivera, Pablo Perez, Corry Ramsgate
*/
public class MusimangosController{// implements Initializable{
	
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
	@FXML 
	public RadioButton RBEZ;
	@FXML 
	public RadioButton RBHard;
	@FXML
	public TextArea LyricBox;
	@FXML
	public RadioButton rb1,rb2,rb3,rb4;
	
	public String song;
	public static boolean hardmode = true;
	public MediaPlayer mediaPlayer;
	//public static int HighScore = 0;
	public int baseScore = 0;
	public boolean alreadyAnswered;
	private SongModel songModelStruct;
	private String current = System.getProperty("user.dir");
	private static int holder = 0;
	
	public MusimangosController(){
		songModelStruct = new SongModel("src/application");
	}
	
	@FXML
	public void hardMode(ActionEvent event){
		hardmode = true;
		
	
	}
	@FXML
	public void easyMode( ActionEvent event){
		hardmode = false;
		System.out.println(hardmode);
	
	}
	
	
	public void displayHighscores(/*ActionEvent event*/) throws IOException{
		HighscoresTextArea.clear();
		String item;
		File invFile = new File("src/application/Hiscores.txt");
		if(!invFile.exists())
			invFile.createNewFile();
		
		Scanner scan = new Scanner(invFile);
		while(scan.hasNextLine()){	//parse highscores and add them to textarea
			item = scan.nextLine();
			String str = String.format("%-16s\t%d",item.substring(0, item.indexOf(',')), (Integer.parseInt(item.substring(item.indexOf(',')+ 2))));
			HighscoresTextArea.setText(HighscoresTextArea.getText() + "\n" + str);
		}
		scan.close();
		
		return;
	}
	
	public String cleanString(String str, char insert, int position){
		
		return str.substring(0, position) + insert + str.substring(position); 
	}
	
	public void displayLyrics(String in) throws IOException{
		LyricBox.clear();
		String item;
		File inFile = new File("src/application/Lyrics/" + in.substring(0, in.indexOf(".")) + ".txt");
		if(!inFile.exists()){
			System.out.println("There's no lyric data for this song :/");
			return;
		}
		
		Scanner scan = new Scanner(inFile);
		while(scan.hasNextLine()){	//parse highscores and add them to textarea
			item = scan.nextLine();
			LyricBox.setText(LyricBox.getText() + "\n" + item);
		}
		scan.close();
		
		return;
	}
	public void updateHiscores(ActionEvent event) throws IOException{
		System.out.println("updateHighscores call");
		System.out.println(holder);//songModelStruct.getHighScore());
		HiscoreModel.updateScores(ScoreField.getText(),holder);// songModelStruct.getHighScore());
		
	}
	
	@FXML
	public void playSong(ActionEvent event) throws IOException{

		Duration time = new Duration(9999);			//time is ~ 2 seconds
		
		System.out.println(hardmode);
		
		if (songModelStruct.getSize() >= 1 ) { //check if there are still some songs to play
			
			song =   songModelStruct.getNextSong();	//get song name of song to play
			
			System.out.println(song);
			
			if(!hardmode){
				displayLyrics(song);				
			}			
			
			Media media = new Media(getClass().getResource(song).toExternalForm()); //open media player
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setStopTime(time);
			mediaPlayer.setVolume(.15);
			mediaPlayer.play();

		}
		
		else {
			System.out.println(songModelStruct.getSize());
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
		
		if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
			mediaPlayer.stop();
		
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
		if (SongModel.checker(userGuess.toLower(), song.toLower()) && !alreadyAnswered) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Correct");
			alert.setHeaderText("your answer was correct!");
			alert.setContentText("song played: " + temp);
			alert.showAndWait();
			
			
			alreadyAnswered = true;	
			songModelStruct.incrementStreak();	//record that user answered correctly
			System.out.println(baseScore);
			System.out.println(songModelStruct.getStreak());
			songModelStruct.incrementHighScore(baseScore * songModelStruct.getStreak()); //calculate the score based on streak and guesses
				
			String str = "Score: " + songModelStruct.getHighScore();//increment streak since got answer right
			scoreLabel.setText(str);	
			holder += songModelStruct.getHighScore();
			
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
			if (songModelStruct.getStreak() != 1 ) {	//decrement winning streak
				songModelStruct.decrementStreak();			//make sure it won't fall below zero
			}
		}
		enteredAnswer.clear();
	}
	
	@FXML
	public void quitGame(ActionEvent event) throws IOException{
		
		songModelStruct.clear();		//clear song list
		songModelStruct. resetStreak();
		
		//take user to the highscore screen so they can be shown how well they did 
		//if user was within top ten, ask user to insert name for their score to be recorded
		HighscoresPane = FXMLLoader.load(getClass().getResource("Highscores.fxml"));
		Scene scene = new Scene(HighscoresPane);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
	}
	
	
	@FXML
	public void showPlayScene(ActionEvent event) throws IOException, InterruptedException {
        
		if(!RBEZ.isSelected() && !RBHard.isSelected() ){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Please select a difficulty.");
			alert.showAndWait();
			return;
		}

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
       scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

       window.setScene(scene);
       window.show();
	}
	@FXML
	 public void showMenuScene(ActionEvent event) throws IOException {
	  MenuPane = FXMLLoader.load(getClass().getResource("Menu.fxml"));// pane you are GOING TO
      Scene scene = new Scene(MenuPane);// pane you are GOING TO show
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
      window.setScene(scene);
      window.show();
	}
	@FXML
	 public void showOptionsScene(ActionEvent event) throws IOException {
		
		
	  OptionsPane = FXMLLoader.load(getClass().getResource("Options.fxml"));// pane you are GOING TO
      Scene scene = new Scene(OptionsPane);// pane you are GOING TO show
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
      window.setScene(scene);
      window.show();

	}
	
	public static void startAnimation(AnchorPane root, Circle cir) throws InterruptedException{
		ScaleTransition st = new ScaleTransition(Duration.seconds(1), cir) ;
			st.setByX(50000000);
			st.setByY(50000000);
			st.play();
			st.setCycleCount(Animation.INDEFINITE);;
			st.setAutoReverse(true);
			st.play();
			
		cir.setVisible(false);
	}
}
