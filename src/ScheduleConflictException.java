public class ScheduleConflictException extends RuntimeException {

    public ScheduleConflictException(Course course1, Course course2) {
        super("Schedule conflict: " + course1.getName() + " overlaps with " + course2.getName());
    }
}
