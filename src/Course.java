import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Course {

    private String name;
    private int credits;
    private Set<Weekday> days;
    private Time startTime;
    private int duration;

    // Constructor that takes a course name (a string), number of credits (an integer),
    // a Set of weekdays on which the course is offered, the time of day at which the course
    // begins (a time), and its duration in minutes (an integer).
    public Course(String name, int credits, Set<Weekday> days, Time startTime, int duration) {
        // check if credits is between 1 and 5 inclusive
        if (credits < 1 || credits > 5) {
            throw new IllegalArgumentException("Invalid number of credits: " + credits);
        }
        // check if the set of days is empty
        if (days.isEmpty()) {
            throw new IllegalArgumentException("Empty set of days");
        }
        // set the fields
        this.name = name;
        this.credits = credits;
        this.days = days;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Course(String name, int credits, Weekday weekday, Time startTime, Time endTime, String instructor, String location) {
        this.name = name;
        this.credits = credits;
        this.days = new HashSet<Weekday>(Arrays.asList(weekday));
        this.startTime = startTime;
        this.duration = endTime.minutesUntil(startTime);
    }
    

    @Override
    public Course clone() {
        try {
            return (Course) super.clone();
        } catch (CloneNotSupportedException e) {
            // should never happen since we are Cloneable
            throw new RuntimeException(e);
          }
    }

    // Returns true if this course is in session during any day(s) and time(s) that overlap with the given course.
    public boolean conflictsWith(Course course) {
        // iterate through each weekday and check if this course is in session during that day
        for (Weekday day : days) {
            // check if the given course is in session during the same day
            if (course.days.contains(day)) {
                // calculate the start time and end time of this course
                Time thisStartTime = startTime.addMinutes(day.ordinal() * 24 * 60);
                Time thisEndTime = thisStartTime.addMinutes(duration);
                // calculate the start time and end time of the given course
                Time otherStartTime = course.startTime.addMinutes(day.ordinal() * 24 * 60);
                Time otherEndTime = otherStartTime.addMinutes(course.duration);
                // check if there is any overlap between the two courses
                if (!(thisEndTime.compareTo(otherStartTime) <= 0 || otherEndTime.compareTo(thisStartTime) <= 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Returns true if this course is in session during the given time on the given day.
    public boolean contains(Weekday day, Time time) {
        // check if this course is in session on the given day
        if (days.contains(day)) {
            // calculate the start time and end time of this course on the given day
            Time thisStartTime = startTime.addMinutes(day.ordinal() * 24 * 60);
            Time thisEndTime = thisStartTime.addMinutes(duration);
            // check if the given time is within this course's duration on the given day
            return time.compareTo(thisStartTime) >= 0 && time.compareTo(thisEndTime) < 0;
        }
        return false;
    }

    // Returns true if and only if o refers to a Course object with exactly equivalent state as this one.
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course other = (Course) o;
        return name.equals(other.name) && credits == other.credits &&
                days.equals(other.days) && startTime.equals(other.startTime) &&
                duration == other.duration;
    }

    // Accessors for the course's various state as passed to the constructor.
    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getDuration() {
        return duration;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public Time getEndTime() {
        return startTime.addMinutes(duration);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Weekday day : days) {
            sb.append(day.toShortName());
        }
        return name + ", " + credits + ", " + sb.toString() + ", " + startTime.toString() + ", " + duration;
    }
}
