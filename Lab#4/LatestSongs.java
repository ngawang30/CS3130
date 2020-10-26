import java.io.File;
import java.util.Scanner;
import java.io.PrintStream;

public class LatestSongs{

	public static void main(String[] args) throws Exception{
		int NUMBEROFWEEKS = 13;
		int NUMBEROFSONGS = 200;
		int counter = 0;
		PrintStream ps = new PrintStream(new File("ThirdFiscalQuarterHits.txt"));
		Scanner sc;
		String[][] allWeeks = new String[NUMBEROFWEEKS][NUMBEROFSONGS];	

		//Reads all files into their corresponding week.
		for(int i=0; i<NUMBEROFWEEKS; i++){
			sc = new Scanner(new File("week"+(i+1)+".csv"));
			while(sc.hasNext()){
				String nextLine = sc.nextLine();
				int first = nextLine.indexOf(',');
				String addition = nextLine.substring(first+1,nextLine.indexOf(',',first+1)) + " ";
				allWeeks[i][counter++]= addition.replace(addition.charAt(0),Character.toUpperCase(addition.charAt(0)));
			}	
			counter = 0;

		}

		//Order the double array.
		int maxIndex;
		for(int i=0;i<NUMBEROFWEEKS;i++){
			for(int j=0;j<NUMBEROFSONGS;j++){
				maxIndex=j;
				for(int k=j+1;k<NUMBEROFSONGS;k++){
					if(allWeeks[i][k].compareToIgnoreCase(allWeeks[i][maxIndex])<0){
						maxIndex = k;

					}
				}
				String temp = allWeeks[i][j];
				allWeeks[i][j] = allWeeks[i][maxIndex];
				allWeeks[i][maxIndex] = temp;
			}

		}

		//write to file allWeeks array.
		for(int i=0; i<NUMBEROFWEEKS;i++){
			ps.println("Week " + (i+1) + " ordered songs:");
			for(int j=0; j<NUMBEROFSONGS; j++){
				ps.println("\t" + allWeeks[i][j]);
		}
			ps.println("\n");
		}
		
		//merge all weeks, order them, and remove repeats.
		String[] orderedSongs = removeRepeats(orderArray(mergeArray(allWeeks)));	
	
		for(int i=0; i<orderedSongs.length; i++){
			System.out.println(orderedSongs[i]);
		}


	}

	/**
	The mergeArray method merges the column of each row, returning a single array.
	@param songs - A double String array of all songs each week.
	@return orderedSong - A single array of all songs from every week.
	*/
	public static String[] mergeArray(String[][] songs){
		String [] orderedSong = new String[songs[0].length*songs.length];
		int counter =0;
		for(int i =0; i<songs.length;i++){
			for(int j=0; j<songs[0].length;j++){
				orderedSong[counter++] = songs[i][j];	

			}

		}

			return(orderedSong);
	}

	/**
	The orderArray method orders the songs in alphabetical order.
	@param songs - An unorderd list of songs.
	@return songs - A list of ordered songs.
	*/
	public static String[] orderArray(String[] songs){
		int currentIndex;
		for(int i=1; i<songs.length;i++){
			currentIndex = i;
			while(songs[currentIndex].compareTo(songs[currentIndex-1])<0 && currentIndex > 1){
				String temp = songs[currentIndex-1];
				songs[currentIndex-1] = songs[currentIndex];
				songs[currentIndex] = temp;
				currentIndex--;
			}
			
		}
		
		return(songs);
}

	/**
	The removeRepeats method removes repeated entries in the list.
	@param songs - List of songs.
	@return newList - A list of songs.	
	*/
	public static String[] removeRepeats(String[] songs){

		int counter =0;

	
		for(int i=1; i<songs.length;i++){
			if(!(songs[i-1].equals(songs[i]))){
				counter++;
			}
		}
		
		String[] newList = new String[counter];	
		counter = 0;

		for(int i=1; i<songs.length;i++){
			if(!(songs[i].equals(songs[i-1]))){
				newList[counter++] = songs[i]; 
			}
		}
			
		return(newList);

}
}


//Node class
class Song {
	private String track;
	Song next;

	public Song(String track){
		this.track = track;
	}
}

//Collection class
class Playlist {
	Song first;
	

	public Playlist(){
		
	}

	/**
	The addSong method adds a song to the playlist.
	@param song - Song to be added.
	*/		
	public void addSong(Song song){
	
	if(first == null){
		first = song;
	} else {
	Song a = first;
		while(a.next!=null){
			a = a.next;
	}

	a.next = song;
	}
	}

	/**
	The listenToSong method returns the song, removing it from the linkedlist.
	@return song - First song in the linked list queue. 
	*/
	public Song listenToSong(){
		Song a = first;
		first = first.next;
		return(a);	
	} 

}

class RecentSongs{
	Song first; 
	public RecentSongs(){
		
	}

	/**
	The addSong method adds the song to the recently listen playlist.
	@param song - Song to be added.
	*/	
	public void addSong(Song song){
		if(first == null){
			first = song;
		} else {
			Song a = first;  
			first = song;
			first.next = a;				
	}
	}
}
