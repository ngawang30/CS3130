import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

class Song {
	Song left;
	Song right;
	String songTitle;
	String artist;
	String streams;

	/**
		The constructor for Song class.
		@param title - The title of the song.
		@param artist - The artist of the song.
		@param streams - The streamers of the song.
	*/
	public Song(String title, String artist, String streams){
		songTitle = title;
		this.artist = artist;
		this.streams = streams;
	}
}	

public class SongTree {
	Song root;

	/**
		The constructor SongTree class.
		@param root - The root of the SongTree.
	*/
	public SongTree(Song root) {
		this.root = root;
	}

	public static void main(String[] args) throws Exception{
		int SIZE = 200;
		String [][] songInfo = new String[SIZE][3];
		Scanner sc = new Scanner(new File("songList.csv"));
		int counter = 0;

		while(sc.hasNext()){
			String data = sc.nextLine();
			int first = data.indexOf(',');
			int last = data.indexOf(',',first+1);
			songInfo[counter][0] = Character.toUpperCase(data.substring(first+1,last).charAt(0))+data.substring(first+2,last);

			first = last;
			last = data.indexOf(',',first+1);
			songInfo[counter][1] = data.substring(first+1,last);

			first = last;
			last = data.indexOf(',', first+1);
			songInfo[counter][2] = data.substring(first+1,last);

			counter++;
		}

		//Orders songInfo alphabetically.
		songInfo = orderArray(songInfo);	

		//Create binary search tree. 
		int median = (SIZE+1)/2; 
		SongTree binarySongTree = new SongTree(constructBinary(songInfo,0,songInfo.length-1));

		//Writes to file the ordered song.
		PrintStream ps = new PrintStream(new File("ArtistsSorted-WeekOf11052020.txt"));	
		for(int x=0;x<songInfo.length;x++){
			ps.println(songInfo[x][0]);	
		}

		//The subSet reads to the file a range of Strings from the binarySongTree.
		PrintStream ps1 = new PrintStream(new File("subSetDemo.txt"));	
		ps1.println("Here are the songs that are between the Strings \"God\" and \"Juice\":\n");	
		subSet(binarySongTree.root,"God","Juice", ps1);
				
	}

	/**
		The orderArray method orders a 2-dimensional String array.
		@param array - A two-dimensional array to be sorted.
		@return array - An ordered two-dimensional array.   
	*/
	public static String[][] orderArray(String [][] array){
		for(int i=0; i<array.length; i++){
			int smallest = i;
			for(int j=0;j<array.length;j++){
				if(array[smallest][0].compareTo(array[j][0])<0){
					smallest =j;
				}
				String temp = array[smallest][0];
				String temp2 = array[smallest][1];
				String temp3 = array[smallest][2];
				array[smallest][0] = array[i][0];
				array[smallest][1] = array[i][1];
				array[smallest][2] = array[i][2];
				array[i][0] = temp;
				array[i][1] = temp2;
				array[i][2] = temp3;
			}

		}
		return(array);

	}

	/**
		The constructBinary takes an array and its size, making a binary searched tree.
		@param array - A two-dimensional array of songs.
		@param start - The beginning index of the segment.
		@param end - The end index of the segment.
		@return root - Returned node.  
	*/ 
	public static Song constructBinary(String[][] array, int start, int end){

		if(start>end){
			return(null);
		}

		int median = (start+end)/2;
		Song root = new Song(array[median][0],array[median][1],array[median][2]);

		root.left = constructBinary(array, start,median-1);

		root.right = constructBinary(array,median+1,end);

		return(root);

	}

	/**
		The subSet method takes the root node and moves through the binary search tree, writing to a file all nodes that fit between the two strings.
		@param root - The root of the binary search tree.
		@param start - The start key of the search.
		@param end - The end key of the search.
		@param ps1 - The printstream to write to.
	*/
	public static void subSet(Song root, String start, String end, PrintStream ps1) throws Exception{
	
		if(root == null) return;
		else {
			if(root.songTitle.compareTo(start)>0) 
				subSet(root.left, start, end, ps1);
			if (root.songTitle.compareTo(start) > 0 && root.songTitle.compareTo(end) < 0)
				ps1.println(root.songTitle);
			if(root.songTitle.compareTo(end)<0)
				subSet(root.right, start, end, ps1);
				
		}	
		
			

	}
}
