import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Schedule implements Cloneable {

    // fields to store the courses in the schedule
    private List<Course> courses;

    // Constructor that creates a new empty schedule.
    public Schedule() {
        // initialize the courses list
        courses = new ArrayList<>();
    }

    /**
     Adds the given course to this schedule. 
     If the given course conflicts with any of the courses in the schedule as defined previously, 
     a ScheduleConflictException should be thrown.
     @param course The course to add to the schedule.
     @throws ScheduleConflictException If the given course conflicts with any existing course in the schedule.
     */

    public void add(Course course) throws ScheduleConflictException {
        for (Course c : courses) {
            if (c.conflictsWith(course)) {
                throw new ScheduleConflictException(c, course);
            }
        }
        courses.add(course);
    }

    /**
     Returns a copy of the object, following the general contract of clone from the Java API Specification. 
     In particular, it should be a deep and independent copy, such that any subsequent changes to the state of the clone 
     will not affect the original and vice versa.
     @return A deep clone of this schedule.
     */

    @Override
    public Schedule clone() {
        try {
            Schedule copy = (Schedule) super.clone();
            copy.courses = new ArrayList<>(courses.size());
            for (Course c : courses) {
                copy.courses.add((Course) c.clone());
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            // should never happen since we are Cloneable
            throw new RuntimeException(e);
        }
    }

    // Returns the course that occurs on the given day and time, or null if no such course exists.
    public Course getCourse(Weekday day, Time time) {
        for (Course c : courses) {
            if (c.contains(day, time)) {
                return c;
            }
        }
        return null;
    }

    public void remove(Weekday day, Time time) {
        for (Course c : courses) {
            if (c.contains(day, time)) {
                courses.remove(c);
                break;
            }
        }
    }

    // Outputs the courses from this schedule to the given output file (represented as a Print Stream object) 
    public void save(PrintStream out, Comparator<Course> comparator) {
        List<Course> sortedCourses = new ArrayList<>(courses);
        sortedCourses.sort(comparator);
        for (Course c : sortedCourses) {
            out.println(c.toString());
        }
    }

    // Returns the total number of credits for which the student is enrolled. 
    public int totalCredits() {
        int total = 0;
        for (Course c : courses) {
            total += c.getCredits();
        }
        return total;
    }
}
