package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SongModel {
	
	static Random rand = new Random();	//set up random number
	static int streak;
	static public ArrayList<String> songList = new ArrayList<String>();
	
	public static void makeList() {
		
		String songArray[] = {"yolo", "pokerface", "shakeitoff", "sorry", 
				"don'tstopbelieving", "fancy"};
		
		//add all the songs in the array to the song arrayList
		for (int i = 0; i < songArray.length; i++) {
			songList.add(songArray[i]);
		}
	}
	
	public static String getNextSong() {
		
		//get a random number, max is the size of the songList, this will be the index of song to get
		int randomIndex = rand.nextInt(songList.size());
		
		//get the song name based on the random index
		String nextSong = songList.get(randomIndex);
		
		//remove the song from the list so it won't be played again
		songList.remove(randomIndex);
		
		return nextSong;
		
	}
	
}
