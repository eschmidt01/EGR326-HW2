import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SchedulerMain {
    public static void main(String[] args) {
        // Load courses from the courses.txt file
        List<Course> courses = loadCoursesFromFile("src/courses.txt");
        
        // Create a new schedule
        Schedule schedule = new Schedule();
        
        // Add courses to the schedule
        for (Course course : courses) {
            try {
                schedule.add(course);
            } catch (ScheduleConflictException e) {
                System.out.println("Schedule conflict detected: " + e.getMessage());
            }
        }
        
        // Sort the courses by name, credit, and time
        CourseNameComparator nameComparator = new CourseNameComparator();
        CourseCreditComparator creditComparator = new CourseCreditComparator();
        CourseTimeComparator timeComparator = new CourseTimeComparator();
        
        courses.sort(nameComparator);
        courses.sort(creditComparator);
        courses.sort(timeComparator);
        
        // Save the courses to the output file
        saveCoursesToFile("output.txt", courses, nameComparator);
    }
    
    private static List<Course> loadCoursesFromFile(String filename) {
        List<Course> courses = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                int credits = Integer.parseInt(parts[1].trim());
                Weekday weekday = Weekday.fromString(parts[2]);
                Time startTime = Time.fromString(parts[3]);
                Time endTime = Time.fromString(parts[4]);
                String instructor = parts[5];
                String location = parts[6];
                courses.add(new Course(name, credits, weekday, startTime, endTime, instructor, location));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found: " + filename);
        }
        return courses;
    }
    
    private static void saveCoursesToFile(String filename, List<Course> courses, Comparator<Course> comparator) {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(filename));
            courses.sort(comparator);
            for (Course course : courses) {
                ps.println(course.toString());
            }
            ps.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found: " + filename);
        }
    }
}
