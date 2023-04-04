import java.util.Comparator;

public class CourseTimeComparator implements Comparator<Course> {

    @Override
    public int compare(Course c1, Course c2) {
        int startTimeComparison = c1.getStartTime().compareTo(c2.getStartTime());
        if (startTimeComparison != 0) {
            return startTimeComparison;
        } else {
            int endTimeComparison = c1.getEndTime().compareTo(c2.getEndTime());
            if (endTimeComparison != 0) {
                return endTimeComparison;
            } else {
                return c1.getName().compareTo(c2.getName());
            }
        }
    }
}
