/*
 *	Overseer.java
 *	Is in charge of a pack of students
 *	This class is mutable (students[] and numStudents)
 */


public class Overseer {
	private Student[] students;
	private int numStudents = 0;

	private byte sortMode = 0; // we only have two, could've been boolean, but 4 future flex, u know
	private final static byte SORT_MODES = 3; // CHANGE IF TEST REMOVED
	private final static byte NAME = 0;
	private final static byte TASKS = 1;
	private final static byte TASKS_DECREASING = 2;
	//private final static Sorting sorter = new Sorting();

	// constructo meow
	/*
	public Student(int numStudents) {
		students = new Student[numStudents];
		for (int i = numStudents; i > 0; i--) {
			students[i] = new Student("å nei");
		}
	}
	*/

	/*
	 * Should perhaps make registerStudent() and other methods return booleans instead of throwing errors.
	 *
	 *
	 */

	// constructor, doesn't check for duplicates
	public Overseer(Student[] themStudents, int thatAmount) {
		if(thatAmount > themStudents.length) {
			throw new IllegalArgumentException("There aren't that many students, yo.");
		}

		students = new Student[thatAmount + 10];
		for (int i = 0; i < thatAmount; i++) { // DEEP COPY OF CODY
			students[i] = new Student(themStudents[i]); // using clone constructor in Student class
		}
		numStudents = thatAmount;
		sort();
	}

	// get methods
	public int getNumStudents() {
		return numStudents;
	}

	public int getTasksDone(Student theStudent) {
		int index = searchTable(theStudent);
		if (index < 0) {
			throw new IllegalArgumentException("Student not in table");
		}
		return students[index].getTasksDone();
	}

	// make it seem like these lazy bois have done shiet
	/* NOT IN USE but could be handy in other cases
	public void increaseTasksDone(Student theStudent, int increase) {
		int index = searchTable(theStudent);
		if (index < 0) {
			throw new IllegalArgumentException("Student not in table");
		}

		students[index].moreTasksDone(increase);

		if (sortMode == TASKS) { // only if we actually sort by tasks
			sort();
		}
	}
	*/


	// the one in actual USE
	public void increaseTasksDone(String studName, int increase) {
		int index = searchTable(studName);
		if (index < 0) {
			throw new IllegalArgumentException("Student not in table"); // or return false
			//return false;
		}

		students[index].moreTasksDone(increase);

		if (sortMode == TASKS) { // only if we actually sort by tasks
			sort();
		}
		//return true;
	}

	// register new student
	public void registerStudent(String theirName) {
		Student test = new Student(theirName);
		if (searchTable(test) >= 0) { // if it's already there
			throw new IllegalArgumentException("Student already registered"); // or return false?
			//return false;
		}

		if (numStudents == students.length) { // if we need to extend (check AFTER first check)
			expandTable(); // then do so
		}

		students[numStudents] = test; // can use the raw value of that integer, it's good.
		numStudents++;

		sort(); // should be done regardless of sort mode when a new member arrives
		//return true;
	}

	// help if needed
	private void expandTable() {
		Student[] oldStudents = students;
		students = new Student[numStudents + 10]; // expand

		for (int i = 0; i < numStudents; i++) { // and fill
			students[i] = oldStudents[i]; // using references, no need for depth
		}
	}

	private int searchTable(Student theStudent) { // search with object
		for (int i = 0; i < numStudents; i++) {
			if (students[i].equals(theStudent)) {
				return i;
			}
		} // end for
		return -1;
	}

	private int searchTable(String theirName) { // search with string --> object
		if (theirName != null) {
			Student theStudent = new Student(theirName);
			for (int i = 0; i < numStudents; i++) {
				if (students[i].equals(theStudent)) {
					return i;
				}
			} // end for
		} // end nullcheck
		return -1;
	}

	private Student[] copyTable() { // specifically for sorting
		Student[] res = new Student[numStudents];
		for (int i = 0; i < numStudents; i++) { // DEEP COPY OF CODY
			res[i] = new Student(students[i]); // using clone constructor in Student class
		}
		return res;
	}

	private void mergeTable(Student[] dummy) { // again for sorting
		students = new Student[students.length]; // recreate table of same length

		for (int i = 0; i < numStudents; i++) { // and fill
			students[i] = dummy[i]; // using references, no need for depth
		}
	}

	// sorting
	public void setSortMode(byte toSort) {
		if (toSort < SORT_MODES && toSort > -1) { // not much need for this check (yet)
			sortMode = toSort;
			sort(); // make them BE sorted, of course.
		} // not much need for errors here
	}

	private void sort() {
		switch (sortMode) {
			case NAME:
				sortName();
				break; // YOU FORGOT BREAKKKK
			case TASKS:
				sortTasks();
				break;
			case TASKS_DECREASING:
				sortTasksDecreasing();
			default:
				break;
		}
	}

	private void sortName() {
		Student[] dummy = copyTable();
		Sorting.sortObjects(dummy); // using the native name-sorting of Student.java
		//java.util.Arrays.sort(dummy);
		mergeTable(dummy);
	}

	private void sortTasks() {
		Student[] dummy = copyTable();
		Sorting.sortObjects(dummy, new StudCompTasks()); // using specific sorting class
		//java.util.Arrays.sort(dummy, new StudCompTasks());
		mergeTable(dummy);
	}

	private void sortTasksDecreasing() {
		Student[] dummy = copyTable();
		Sorting.sortObjects(dummy, new StudCompTasksDecreasing()); // using specific sorting class
		//java.util.Arrays.sort(dummy, new StudCompTasksDecreasing());
		mergeTable(dummy);
	}


	/*
	private void sortTest() { // TESTING only
		Student[] dummy = copyTable();
		java.util.Arrays.sort(dummy, (a,b) -> a.getName().compareTo(b.getName()));
		mergeTable(dummy);
	}
	*/

	// throw me a stringling
	@Override
	public String toString() {
		StudCompTasks heh = new StudCompTasks();
		String res = "";
		for (int i = 0; i < numStudents; i++) {
			res += students[i].toString() +
			/*students[i].compareTo(students[0]) + " " + heh.compare(students[i], students[0]) +*/
			"\n";
		}
		return res + numStudents + " students in total";
	} // end toString
} // end class