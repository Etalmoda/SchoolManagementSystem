import java.util.ArrayList;

/**
 * Represents a room in a school or educational facility that can contain students and staff.
 * Manages room capacity, occupancy tracking, and room status.
 */
public class Room {
  // Room identification
  private String name;

  // Capacity constraints
  private int studentCapacity;

  // Collections to track current occupants
  private ArrayList<Student> students;  // Students currently in the room
  private ArrayList<Staff> staff;       // Staff members currently assigned to the room

  // Room status
  private boolean isClosed;             // Whether the room is closed/unavailable

  /**
   * Constructor to create a new room with specified name and student capacity.
   * Initializes empty collections for students and staff, and sets room as open by default.
   *
   * @param name The name/identifier of the room
   * @param studentCapacity Maximum number of students the room can accommodate
   */
  public Room(String name, int studentCapacity) {
    this.name = name;
    this.studentCapacity = studentCapacity;
    this.students = new ArrayList<>();  // Initialize empty student list
    this.staff = new ArrayList<>();     // Initialize empty staff list
    this.isClosed = false;              // Room starts as open
  }

  // Getters

  /**
   * @return The name of the room
   */
  public String getName() {
    return name;
  }

  /**
   * @return The maximum student capacity of the room
   */
  public int getStudentCapacity() {
    return studentCapacity;
  }

  /**
   * @return ArrayList of students currently in the room
   */
  public ArrayList<Student> getStudents() {
    return students;
  }

  /**
   * @return ArrayList of staff currently assigned to the room
   */
  public ArrayList<Staff> getStaff() {
    return staff;
  }

  /**
   * @return true if the room is closed, false if open
   */
  public boolean isClosed() {
    return isClosed;
  }

  /**
   * Gets a formatted string showing the staff-to-student ratio in the room.
   *
   * @return String in format "X staff : Y students"
   */
  public String getRatio() {
    return this.staff.size() + " staff : " + this.students.size() + " students";
  }

  // Room Management Methods

  /**
   * Attempts to add a student to the room with validation checks.
   *
   * @param student The student to add to the room
   * @return true if student was successfully added, false otherwise
   */
  public boolean addStudent(Student student) {
    // Check if student is present/available
    if (!student.isPresent()) {
      System.out.println("Cannot add student " + student.getName() + " - student is not present.");
      return false;
    }

    // Check for duplicate assignment
    if (students.contains(student)) {
      System.out.println("Student " + student.getName() + " is already in this room.");
      return false;
    }

    // Check if room has reached student capacity
    if (students.size() >= studentCapacity) {
      System.out.println("Cannot add student " + student.getName() + " - room " + this.name +
          " is at capacity (" + studentCapacity + " students).");
      return false;
    }

    // Add student and update their location
    students.add(student);
    student.setLocation(this.name);     // Update student's current location
    System.out.println("Student " + student.getName() + " added to " + this.name);
    return true;
  }

  /**
   * Attempts to add a staff member to the room with validation checks.
   *
   * @param staffMember The staff member to assign to the room
   * @return true if staff was successfully added, false otherwise
   */
  public boolean addStaff(Staff staffMember) {
    // Check if staff member is clocked in/available
    if (!staffMember.isClockedIn()) {
      System.out.println("Cannot add staff " + staffMember.getName() + " - staff is not clocked in.");
      return false;
    }

    // Check for duplicate assignment
    if (staff.contains(staffMember)) {
      System.out.println("Staff " + staffMember.getName() + " is already assigned to this room.");
      return false;
    }

    // Add staff and update their location
    staff.add(staffMember);
    staffMember.setLocation(this.name); // Update staff member's current location
    System.out.println("Staff " + staffMember.getName() + " assigned to " + this.name);
    return true;
  }

  /**
   * Provides a formatted string representation of the room's current state.
   * Includes room name, capacity, current occupancy, and status.
   *
   * @return Multi-line string with room details
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Room Name: ").append(name).append("\n");
    sb.append("Student Capacity: ").append(studentCapacity).append("\n");
    sb.append("Current Students: ").append(students.size()).append("\n");
    sb.append("Current Staff: ").append(staff.size()).append("\n");
    sb.append("Is Closed: ").append(isClosed ? "Yes" : "No").append("\n");
    return sb.toString();
  }

  // Setters

  /**
   * Sets the room name
   * @param name New name for the room
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the student capacity for the room
   * @param studentCapacity New maximum student capacity
   */
  public void setStudentCapacity(int studentCapacity) {
    this.studentCapacity = studentCapacity;
  }

  /**
   * Replaces the entire student list with a new list
   * @param students New ArrayList of students
   */
  public void setStudents(ArrayList<Student> students) {
    this.students = students;
  }

  /**
   * Replaces the entire staff list with a new list
   * @param staff New ArrayList of staff members
   */
  public void setStaff(ArrayList<Staff> staff) {
    this.staff = staff;
  }

  /**
   * Sets the room's closed status
   * @param isClosed true to close the room, false to open it
   */
  public void setClosed(boolean isClosed) {
    this.isClosed = isClosed;
  }
}
