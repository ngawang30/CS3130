import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

//Node Class
class Artist{
	String name;
	Artist next;

	public Artist(String name){
		this.name = name;
	}


}

//Collection Class
public class TopStreamingArtists {
	private Artist first;


	public TopStreamingArtists(){
		first = null;
	}

	public static void main(String[] args) throws Exception{
		String [] topArtists = insertionSort(artistArray());
		TopStreamingArtists artistList = new TopStreamingArtists();
		PrintStream ps = new PrintStream(new File("ArtistsSorted-WeekOf10012020.txt"));
		artistList.first = new Artist(topArtists[0]);
		Artist current = artistList.first;

		//Creates linked list.
		for(int i=1; i<topArtists.length; i++){
			current.next = new Artist(topArtists[i]);
			current = current.next;				
		}

		current = artistList.first;

		//Writes to file using the linked list.
		for(int i=0; i<topArtists.length;i++){
			ps.println(current.name);
			current = current.next;

		}



	}

	/**
	  This method reads from the CSV spotify text file into array.
	  @return arrTwo - An array of the top 200 songs' artists.
	 */	
	public static String[] artistArray() throws Exception{
		String [] arrOne = new String[200];
		String [] arrTwo;
		int counter = 0;
		boolean exists = false;
		Scanner sc = new Scanner(new File("SpotifyCSV.txt"));

		while(sc.hasNext()){
			exists = false;
			String nextLine = sc.nextLine();
			int location = nextLine.indexOf(',',5);
			String name = nextLine.substring(location+1, nextLine.indexOf(',',location+1)).trim();

			for(int i=0; i<counter;i++){
				if(name.equals(arrOne[i])){
					exists = true;
				}
			}

			//Checks if it is a repeating artists, and, if it is, it does not add it to the array.
			if(exists == false ){
				arrOne[counter++] = name;
			}
		}

		//Creates second array with the exact amount of non-repeating artists
		arrTwo = new String[counter];

		for(int i = 0; i<counter;i++){
			arrTwo[i] = arrOne[i];
		}
		return(arrTwo);

	}	

	/**
	  The insertionSort method sorts the argument array using insertion sort process.
	  @param x - The input array.
	  @return sorted - A sorted version of the input array.
	 */
	public static String[] insertionSort(String[] x){
		String[] sorted = x;
		String temp; 
		int index;
		for(int i=1; i<x.length;i++){
			temp = sorted[i];
			index = i; 
			while(sorted[index].compareTo(sorted[index-1]) < 0 && index >1){
				sorted[index] = sorted[index-1];
				sorted[index-1] = temp;
				index--; 		
			}
		}

		return(sorted);

	}

}
