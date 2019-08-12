public class Qs {
	public static void main(String[] args) {
		//java.util.Locale.setDefault(new java.util.Locale("nb")); // for the sorting

		/* Old list of students
		Student scrap = new Student("  Scraplord Lumberjack", 2);
		Student theFilth = new Student(" Åge Sørensen", 0);
		Student ultimate = new Student(" Ærfugl", 94);
		Student broken = new Student("   ødelagt", 32);
	    Student[] themStudents = {broken, scrap, theFilth, ultimate};
	    */

		Student stud1 = new Student("Åge Sørensen", 5);
		Student stud2 = new Student("Hans Martin Wehler", 15); // hahaha
		Student stud3 = new Student("Napoleon", 13);
		Student stud4 = new Student("Ulf Overvik", 0);
		Student stud5 = new Student("Øyvind Stormast", 3);
		Student stud6 = new Student("Pernille Sørensen", 5);
		Student stud7 = new Student("Gorilla Gundersen", 8);
		Student stud8 = new Student("ærfuglen i egen person \n", 11);
		Student[] themStudents = {stud1, stud2, stud3, stud4, stud5, stud6, stud7, stud8};

	    Overseer queue = new Overseer(themStudents, themStudents.length);
	    UserInterface dumbass = new UserInterface(queue); // never use a reserved word, you moron

	    int choice = dumbass.promptUser();
	    while (choice >= 0) {
	      dumbass.obey(choice);
	      choice = dumbass.promptUser();
	    }
	}

//ALSO, REMEMBER THE FUCKING TEST CLIENT FOR STUDENT.JAVA
}

// note to self: spent a little over an hour slamming together an attempt at this while not having read the chapter
// without changing UI class
// - afterwards I tried implementing sorting, after reading the chapter. look below.

/*

DEBUGGING THE SORT

So... It works perfectly fine for the new students, but the original ones get weird.
They are sorted separately, by *increasing* score, ending up after all the others.

*/