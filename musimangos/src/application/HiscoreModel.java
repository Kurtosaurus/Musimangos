package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HiscoreModel{
	
	public static void updateScores(String name, int score) throws IOException {
		System.out.println(name);

		System.out.println(score);
		
		
		File Hiscores = new File("src/application/Hiscores.txt"); //open files and make sure they exist
		File temp = new File("src/application/temp.txt");
		//if it exist delete it
		if(temp.exists()){
			temp.delete();
			temp.createNewFile();
		}
		else{
			temp.createNewFile();
		}
		
		int i = 0;		//init variables and objects
		//init varibles
		Scanner sc = new Scanner(Hiscores);
		FileWriter wr = new FileWriter(temp);
		String line;
		int tScore = 0;
		boolean bool = false;
		
		while(sc.hasNextLine() && i < 10){ 	//write 10 scores to a temp file
			line = sc.nextLine();
			
			try{
				tScore = Integer.parseInt(line.substring(line.indexOf(",")+1).trim());
			}
			catch(Exception e) {
				System.out.println("Highscore.txt format does not meet regEx{[^,]*,[0-9]*}, please delete or repair");
				System.exit(1);
			}
			if(score > tScore && !bool){//check if player score is higher than others
				wr.write(name + ", " + (Integer.toString(score)) + "\n");
				i++;
				bool = true;
			}
			wr.write(line + "\n" );
			i++;
			
			
		}
		if(!bool){
			Alert alert = new Alert(AlertType.INFORMATION);	//tell user they're bad
			alert.setTitle("Sorry");
			alert.setHeaderText("Your score is to low");
			alert.setContentText("Get better and try again");
			alert.showAndWait();
		}
		
		sc.close();	//close some stuff
		wr.close();
		
		Hiscores.delete();//delete and write temp to higscores.txt
		temp.renameTo(Hiscores);
	}
	
}
