
import java.io.File;
import java.util.Scanner;

public class Artist{

	public static void main(String[] args) throws Exception{

	int effectiveSize = Integer.parseInt(readList()[200][0]);	
	String[][] artFreq = new String[effectiveSize+1][2];
	for(int i=0; i<effectiveSize; i++){
		artFreq[i][0] = readList()[i][0];
		artFreq[i][1] = readList()[i][1];
	}		

	System.out.println(artFreq[0][0]);	

	
}

	public static String[][] readList() throws Exception{
		int MAX = 201;
		int currentIndex = 0;
		Scanner sc = new Scanner(new File("SpotifyData.txt"));
		String [][] artFreq = new String[MAX][2];

		for(int i=0;i<MAX;i++){
			artFreq[i][1] = "0";
		}
			
		while(sc.hasNext()){
			String nextLine = sc.nextLine();
			int firstPos = nextLine.indexOf(',',nextLine.indexOf(',')+1)+1;
			int endPos = nextLine.indexOf(',',firstPos+1);
			boolean exists = false;		
			String artist = nextLine.substring(firstPos,endPos);
			
			for(int i=0; i<currentIndex+1; i++){
				if(artist.equals(artFreq[i][0])){
					exists = true;
					artFreq[i][1] = Integer.toString(Integer.parseInt(artFreq[i][1])+1);	
				}
			}

			if(exists == false){
				artFreq[currentIndex][0] = artist;
				currentIndex ++;
			}


	}

		artFreq[200][0] = Integer.toString(currentIndex); 


		return(artFreq);

}



}
