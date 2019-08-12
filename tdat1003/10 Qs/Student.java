/*
 *	Student.java
 *	Holds information about a student
 *	This class is mutable (tasksDone)
 */

public class Student implements Comparable<Student>{
	private final String name; // entydig. forenkler eventuell equals-metode.
	private int tasksDone; // antall godkjente oppgaver
	// use calendar or sumthin for dat 'n time of latest request?
	private final static java.text.Collator COLLATOR =
	java.text.Collator.getInstance(new java.util.Locale("nb"));
	// not specified --> dynamic

	// constructors
	public Student(String theirName) {
		if (theirName == null || theirName.trim().equals("")) {
			throw new IllegalArgumentException("This is way too empty a name");
		}
		name = theirName.trim();
		tasksDone = 0;
	}

	public Student(String theirName, int theirTasks) {
		if (theirName == null || theirName.trim().equals("")) {
			throw new IllegalArgumentException("This is way too empty a name");
		}
		if (theirTasks < 0) {
			throw new IllegalArgumentException("Negative value not accepted");
		}
		name = theirName.trim();
		tasksDone = theirTasks;
	}

	// clone constructor
	public Student(Student original) {
		//this(original.name, original.tasksDone);
		name = original.name;
		tasksDone = original.tasksDone;
	}

	// get methods
	public String getName() {
		return name;
	}

	public int getTasksDone() {
		return tasksDone;
	}

	// add more tasks to the pile; but only those that are done.
	public void moreTasksDone(int increase) {
		if (increase <= 0) {
			throw new IllegalArgumentException("Negative value not accepted");
			//return false; // uthopp
		}
		tasksDone += increase;
		//return true;
	}

	// gimme a string plez
	@Override
	public String toString() {
		return name + " has completed " + tasksDone + " tasks.";
	}

	// equals
	@Override
	public boolean equals(Object theStudent) {
		if (!(theStudent instanceof Student)) {
			return false;
		}

		if (this == theStudent) {
			return true;
		}

		Student thatStudent = (Student) theStudent;
		if (name.equals(thatStudent.name)) {
			return true;
		}

		return false;
	}

	// comparison, using name
	@Override
	public int compareTo(Student other) {
		if (equals(other)) {
			return 0;
		}
		return COLLATOR.compare(name, other.name);
		/* DEPENDS ON WHAT LOCALE YOU'VE SET */
	}

	// corresponding interface
	public interface Comparable<Student> {
		int compareTo(Student obj);
	}

	public static void main(String[] args) {
		//java.util.Locale.setDefault(new java.util.Locale("nb"));
		// apparently doesn't work here - why?

		System.out.println("We will be running some tests, 5 in total");

		// getName() and left trim
		Student stud1 = new Student(" Ole");
		if (stud1.getName().equals("Ole")) {
			System.out.println("Test 1 succeeded (" + stud1 + ")");
		}
		// testing constructor too
		if (stud1.getTasksDone() == 0) {
			System.out.println("Constructor part of test 1 succeeded");
		}

		// getTasksDone()
		Student stud2 = new Student("Arne", 34);
		if (stud2.getTasksDone() == 34) {
			System.out.println("Test 2 succeeded (" + stud2 + ")");
		}

		// moreTasksDone()
		Student stud3 = new Student("Grete", 10);
		stud3.moreTasksDone(5);
		if (stud3.getTasksDone() == 15) {
			System.out.println("Test 3 succeeded");
		}

		// equals() and trimming
		Student stud4 = new Student("Birte  ", 11);
		Student stud4diff = new Student("Birgit Knutsdottir", 2);
		Student stud4same = stud4;
		Student stud4samey = new Student("  Birte", 33);
		if (stud4.equals(stud4same) &&
			stud4.equals(stud4samey) &&
			!(stud4.equals(stud4diff))) {
			System.out.println("Test 4 succeeded");
		}

		// compareTo() with Norwegian locale
		Student stud5a = new Student("Zed", 11);
		Student stud5b = new Student("ælg", 2);
		Student stud5c = new Student("ørihaue", 33);
		Student stud5aa = new Student("Zed");
		if (stud5a.compareTo(stud5b) == -1 &&
			stud5b.compareTo(stud5a) == 1 &&
			stud5b.compareTo(stud5c) == -1 &&
			stud5c.compareTo(stud5b) == 1 &&
			stud5a.compareTo(stud5aa) == 0) {
			System.out.println("Test 5 succeeded");
		} else {
			System.out.println("Test only valid with Norwegian locale; check.");
		}
	}
}