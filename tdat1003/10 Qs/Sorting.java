public class Sorting { // public???? it shouldn't be?
	public Sorting() {}

	public static void sortObjects(Object[] objects) {
		if (objects != null && objects.length > 0) {
			for (int start = 0; start < objects.length; start++) {
				int currentLeast = start;
				for (int i = start + 1; i < objects.length; i++) {
					Comparable thing = (Comparable) objects[i];
					Comparable currentLeastObject = (Comparable) objects[currentLeast];
					if (thing.compareTo(currentLeastObject) < 0) {
						currentLeast = i;
					}
				} // end i-for
				Object helper = objects[currentLeast];
				objects[currentLeast] = objects[start]; //swap
				objects[start] = helper; //swaparoo
			} // end start-for
		} // end if
	} // end self-sort

	public static void sortObjects(Object[] objects, java.util.Comparator comp) {
		if (comp == null) {
			sortObjects(objects);
		}
		if (objects != null && objects.length > 0 && comp != null) { // I suspect the null check is needed.
			for (int start = 0; start < objects.length; start++) {
				int currentLeast = start;
				for (int i = start + 1; i < objects.length; i++) {
					Object thing = objects[i];
					Object currentLeastObject = objects[currentLeast];
					if (comp.compare(thing, currentLeastObject) < 0) { // using the specified comparator
						currentLeast = i;
					}
				} // end i-for
				Object helper = objects[currentLeast];
				objects[currentLeast] = objects[start]; //swap
				objects[start] = helper; //swaparoo
			} // end start-for
		} // end if
	} // end self-sort
}