package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/*SongModel contains the logic for the media player,
and song array
kurt smith, Kalie Davis, Ivan Rivera, Pablo  Perez, Correy Ramsgate
*/
public class SongModel {
	
	private Random rand = new Random();	//set up random number
	private int streak=0;
	private List <String> songList = new ArrayList<String>();
	private int highScore = 0;


	//Constructor to load in all mp3 file names from database folder
	//ArrayList songList will hold the filenames!
	public SongModel(String databaseLocation){
		System.out.println(databaseLocation);
		File dir = new File(databaseLocation);
		File[] files = dir.listFiles();
		if(files == null)System.out.println("Empty Database!");
		else {
			if (files.length == 0) {
				System.out.println("Empty!");
			} else {
				for (File s : files){
					if(s.getName().contains(".mp3")){
						songList.add(s.getName());
						System.out.println(s);
					}
				}
				System.out.println(songList);
			}
		}
	}

	public String getNextSong() {
		
		//get a random number, max is the size of the songList, this will be the index of song to get
		int randomIndex = rand.nextInt(songList.size());
		
		//get the song name based on the random index
		String nextSong = songList.get(randomIndex);
		
		//remove the song from the list so it won't be played again
		songList.remove(randomIndex);
		
		return nextSong;
		
	}
	//getter and setters and stuff
	public void incrementStreak(){
		streak++;
	}
	public void decrementStreak(){
		streak--;
	}
	public void clear(){
		songList.clear();
	}
	public void resetStreak(){
		streak = 1;
	}
	public int getSize(){
		return songList.size();
	}
	public int getStreak() {
		return streak;
	}
	
	public int getHighScore(){
		return highScore;
	}
	public void incrementHighScore(int x){
		highScore += x;
		
	}


	//check if answer is close enough to the proper string
	public static boolean checker(String in, String proper){
		int i = 0;
		float a  =0;
		while( i < in.length() && i < proper.length()){
			if(in.charAt(i) ==proper.charAt(i))
				a++;
				i++;
		}
		if(a == 0)
			return false;
		a = (float)proper.length()/a;
		if(a > .75) 
			return true; 
		else 
			return false;	
		
	}
			
}
