class StudCompTasksDecreasing implements java.util.Comparator<Student> {
	// somebody used @Override - what does that mean?
	// won't bother writing answer.
	@Override
	public int compare(Student stud1, Student stud2) { // THIS GOES IN REVERSE
		int num1 = stud1.getTasksDone();
		int num2 = stud2.getTasksDone();
		if (num1 == num2) {
			return 0;
		} else if (num1 < num2) {
			return 1;
		} else {
			return -1; // flipp the fortegn since baklengs... any other ways?
		}			   // yes, perhaps - use ArrayUtils.reverse()
	}
}