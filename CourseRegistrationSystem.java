import java.util.*;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public boolean isAvailable() {
        return enrolledStudents < capacity;
    }

    public void enroll() {
        enrolledStudents++;
    }

    public void drop() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    public void display() {
        System.out.println(courseCode + " - " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Slots: " + (capacity - enrolledStudents) + " remaining");
    }
}

class Student {
    String studentId;
    String name;
    List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.isAvailable()) {
            registeredCourses.add(course);
            course.enroll();
            System.out.println("Successfully registered for " + course.title);
        } else {
            System.out.println("Course is full. Cannot register.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.drop();
            System.out.println("Dropped course: " + course.title);
        } else {
            System.out.println("You are not registered in this course.");
        }
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + " (ID: " + studentId + "):");
        for (Course course : registeredCourses) {
            course.display();
        }
    }
}

public class CourseRegistrationSystem {
    private static List<Course> courses = new ArrayList<>();
    private static Map<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        initializeCourses();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        Student student = students.computeIfAbsent(studentId, id -> new Student(studentId, name));

        int choice;
        do {
            System.out.println("\n1. List Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerForCourse(scanner, student);
                    break;
                case 3:
                    dropCourse(scanner, student);
                    break;
                case 4:
                    student.displayRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void initializeCourses() {
        courses.add(new Course("CS101", "Introduction to Computer Science", "Basic programming and algorithms", 3, "Mon-Wed 10:00 AM"));
        courses.add(new Course("MATH201", "Calculus I", "Differential and Integral Calculus", 2, "Tue-Thu 12:00 PM"));
        courses.add(new Course("PHY301", "Physics I", "Mechanics and Thermodynamics", 3, "Mon-Wed 2:00 PM"));
    }

    private static void listCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            course.display();
            System.out.println("------------------------");
        }
    }

    private static void registerForCourse(Scanner scanner, Student student) {
        listCourses();
        System.out.print("Enter course code to register: ");
        String courseCode = scanner.nextLine();

        Course course = findCourseByCode(courseCode);
        if (course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void dropCourse(Scanner scanner, Student student) {
        student.displayRegisteredCourses();
        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.nextLine();

        Course course = findCourseByCode(courseCode);
        if (course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.courseCode.equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
