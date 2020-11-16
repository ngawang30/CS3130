import java.util.Scanner;

public class LabSix {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Grade[] grades = new Grade [new PracticeProblems("",0).amount + new Labs("",0).amount + new Midterm("",0).amount + new Final("",0).amount];
		int counter=0;
		int grade;

		//Fill Array With PracticeProblem Grades.
		for(int i=0; i< new PracticeProblems("",0).amount; i++){
			String assignment = "Practice Problem " + (i+1);
			System.out.println("\nWhat grade did you get on " + assignment + "?\nIf it was incomplete, please enter -1.");
			grade = sc.nextInt();
			grades[counter++] = new PracticeProblems(assignment, grade);
		}

		//Fill Array With Lab Grades.
		for(int i=0; i< new Labs("",0).amount; i++){
			String assignment = "Lab " + (i+1);
			System.out.println("\nWhat grade did you get on " + assignment + "?\nIf it was incomplete, please enter -1.");
			grade = sc.nextInt();
			grades[counter++] = new Labs(assignment, grade);
		}

		//Fill Array With Midterm Grades.
		for(int i=0; i< new Midterm("",0).amount; i++){
			String assignment = "Midterm " + (i+1);
			System.out.println("\nWhat grade did you get on " + assignment + "?\nIf it was incomplete, please enter -1.");
			grade = sc.nextInt();
			grades[counter++] = new Midterm(assignment, grade);
		}

		//Fill Array With Final Grades.
		for(int i=0; i< new Final("",0).amount; i++){
			String assignment = "Final";
			System.out.println("\nWhat grade did you get on " + assignment + "?\nIf it was incomplete, please enter -1.");
			grade = sc.nextInt();
			grades[counter++] = new Final(assignment, grade);
		}

		//Restate Grades Cleanly.
		LabSix.toString(grades);

		System.out.println("\n\nWhat grade would you like to earn?");

		double desiredGrade = sc.nextDouble()/100;
		double currentGrade = getGrade(grades);
		Grade[] ungraded = getUngraded(grades);
		double potentialGrade = 0;		
			
		//Array Of Ungraded Assignments Are Ordered
		ungraded = orderByPoints(ungraded);
		
		for(Grade g: ungraded){
			potentialGrade += (g.worth)*(1/(double)g.amount);
		}
		
		//If Sum Of Missing Grades Plus Current Grade Is Less Than The Desired Grade, It's Impossible.
		if(currentGrade+potentialGrade<=desiredGrade){
			System.out.println("It's impossible to obtain this grade.");	
		} else{
			double difference = desiredGrade - currentGrade;
			counter = 0;
			String decision = "";
			boolean obtained = false;
			
			//Repeat Until Potential Grade Are Filled, Allowing You To Reach Desired Grade.
			while(difference > 0){
			
				Grade current = ungraded[counter++];
				double value = ((current.worth)*(1/(double)current.amount));
 
				if(difference - value <0){
					decision += "\nYou need at least " + String.format("%.2f",(difference*current.possiblePoints*current.amount/current.worth)) + " on " + current.name;
					difference =0;
					obtained = true;
				} else {
					difference -= value;
					decision += "\nYou need a " + current.possiblePoints + " on " + current.name; 
				}
				
				//Conditional To Prevent OutOfBounds.
				if(counter + 1 > ungraded.length && obtained == false){
					decision += "\nIt is impossible to get this grade.";
					difference = 0;
				}
	

			}
			
			System.out.println(decision);

	





}		
	}


	/**
		The toString method takes an array of grades and generates a table.
		@param grades - An array of grades.			

	*/
	public static void toString(Grade[] grades){
		double finalGrade = 0;	
		for(Grade assignment: grades){
			System.out.println("\n"+String.format("%-20s %-10d",assignment.name, assignment.grade));
		}
		
		System.out.println(String.format("\n%-20s %-10.2f", "Your Final Grade:" , getGrade(grades)*100));


	}

	/**
		The getGrade method gets the final grade given an array of grades corresponding to different types of grades.
		@param grades - An array of grades used to calculate current grade.
		@return finalGrade - Current grade.
	*/
	public static double getGrade(Grade[] grades){
	double finalGrade = 0;

	for(Grade assignment: grades){
		if(assignment.grade>=0){
				finalGrade += (assignment.worth)*((double)assignment.grade/assignment.possiblePoints/assignment.amount); 
		}
	
	}

	return(finalGrade);
}

	/**
		The getUngraded method takes an array of grades and returns an array of ungraded grades.
		@param grades - An array of grades to be ordered.
		@return ungraded - An array of ungraded grades.	
	*/
	public static Grade[] getUngraded(Grade[] grades){
		int counter=0;

		for(Grade x: grades){
			if(x.grade==-1) {
				counter++;
			}
		}

		Grade[] ungraded = new Grade[counter];
		counter = 0;
			
		for(Grade x: grades){
			if(x.grade==-1){
				ungraded[counter++] = x;	
			}
		}
		
		return(ungraded);
	}

	/**
		The orderByPoints orders the ungraded grades by the point-value.
		@param grades - An array of unordered grades.
		@return grades - An ordered array of unordered grades. 
	*/
	public static Grade[] orderByPoints(Grade[] grades){
		int maxIndex = 0;
		for(int i= 1; i<grades.length;i++){
			for(int j=i; j<grades.length;j++){
			if(grades[j].grade > grades[maxIndex].grade){
				Grade temp = grades[j];
				grades[j] = grades[maxIndex];
				grades[maxIndex] = temp;
			}
			}
		}

		return(grades);
		
	}
}

class Grade{
	String name;
	int grade;
	double worth;
	int possiblePoints;
	int amount;

	/**
		The constructor of the Grade class.
		@param name - Name of grade.
		@param grade - Numerical grade.
	*/
	public Grade(String name, int grade){
		this.grade = grade;
		this.name = name;
	}

}

class PracticeProblems extends Grade{

	/**
		The constructor of the PracticeProblems class.
		@param name - Name of grade.
		@param grade - Numerical grade.
	*/	
	public PracticeProblems(String name, int grade){
		super(name, grade);
		worth = .44;
		possiblePoints =6;
		amount = 8;
	}
}


class Labs extends Grade {

	/**
		The constructor of the Labs class.
		@param name - Name of grade.
		@param grade - Numerical grade.
	*/	
	public Labs(String name, int grade){
		super(name, grade);
		worth = .16;
		possiblePoints = 2;
		amount = 7;
	}	
}

class Midterm extends Grade {
	/**
		The constructor of the Midterm class.
		@param name - Name of grade.
		@param grade - Numerical grade.
	*/	
	public Midterm(String name, int grade){
		super(name, grade);
		worth = .20;
		possiblePoints = 10;
		amount = 2;
	}
}

class Final extends Grade{
	/**
		The constructor of the Final class.
		@param name - Name of grade.
		@param grade - Numerical grade.
	*/	
	public Final(String name, int grade){
		super(name, grade);
		worth =.20;
		possiblePoints =10;
		amount = 1;
	}	

}
