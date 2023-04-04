import java.util.Comparator;

public class CourseCreditComparator implements Comparator<Course> {

    @Override
    public int compare(Course c1, Course c2) {
        int creditComparison = Integer.compare(c1.getCredits(), c2.getCredits());
        if (creditComparison != 0) {
            return creditComparison;
        } else {
            return c1.getName().compareTo(c2.getName());
        }
    }
}
