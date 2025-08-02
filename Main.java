import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for the School Management System
 * Provides a command-line interface for managing students and staff
 * Supports loading data from files, attendance tracking, and information queries
 */
public class Main {
  // Data storage collections
  private static ArrayList<Student> students = new ArrayList<>();  // List of all students
  private static ArrayList<Staff> staff = new ArrayList<>();       // List of all staff

  // Hash maps for fast name-based lookups (case-insensitive)
  private static Map<String, Student> studentMap = new HashMap<>();
  private static Map<String, Staff> staffMap = new HashMap<>();

  // Constants for minimum required fields in CSV files
  private static final int MIN_STUDENT_FIELDS = 7;  // Minimum columns needed for student data
  private static final int MIN_STAFF_FIELDS = 3;    // Minimum columns needed for staff data

  /**
   * Main method - entry point of the application
   * Sets up the command loop and processes user input
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    boolean running = true;

    // Welcome message and initial help display
    System.out.println("Welcome to _________! Please enter a command to continue!");
    printHelpMessage();

    // Main command processing loop
    while (running) {
      System.out.print("> ");
      String[] parts = input.nextLine().split(" ");
      String command = parts[0].toUpperCase();  // Make commands case-insensitive

      // Process different commands using switch statement
      switch (command) {
        case "HELP":
          printHelpMessage();
          break;
        case "LOAD_STUDENTS":
          if (parts.length > 1) {
            loadStudentsFromFile(parts[1]);
          } else {
            System.out.println("Please provide a filename.");
          }
          break;
        case "LOAD_STAFF":
          if (parts.length > 1) {
            loadStaffFromFile(parts[1]);
          } else {
            System.out.println("Please provide a filename.");
          }
          break;
        case "INFO":
          handleInfo(parts);
          break;
        case "ALL_STUDENTS":
          printAllStudents();
          break;
        case "ALL_STAFF":
          printAllStaff();
          break;
        case "MARK_PRESENT":
        case "MARK_ABSENT":
          handleAttendance(command, parts);
          break;
        case "CLOCK_IN":
        case "CLOCK_OUT":
          handleClocking(command, parts);
          break;
        case "QUIT":
          System.out.println("Thank You!");
          running = false;
          break;
        default:
          System.out.println("Unknown Command!");
      }
    }
  }

  // --- HELPER METHODS ---

  /**
   * Displays the help message with all available commands
   * Shows command syntax and brief descriptions
   */
  public static void printHelpMessage() {
    System.out.println("\nList of commands:");
    System.out.println("HELP - Prints help message");
    System.out.println("LOAD_STUDENTS (filename) - Loads data from specified file");
    System.out.println("LOAD_STAFF (filename) - Loads data from specified file");
    System.out.println("INFO (FirstName LastName) - Returns info for specified person");
    System.out.println("ALL_STUDENTS - Lists all students and info");
    System.out.println("ALL_STAFF - Lists all staff and info");
    System.out.println("CLOCK_IN (FirstName LastName) - Clocks staff in");
    System.out.println("CLOCK_OUT (FirstName LastName) - Clocks staff out");
    System.out.println("MARK_PRESENT (FirstName LastName) - Marks student present");
    System.out.println("MARK_ABSENT (FirstName LastName) - Marks student absent");
    System.out.println("QUIT - Quits program\n");
  }

  /**
   * Prints information for all students in the system
   * Shows numbered list with complete student details
   */
  public static void printAllStudents() {
    if (students.isEmpty()) {
      System.out.println("No Students!");
      return;
    }

    // Loop through all students and display their information
    for (int i = 0; i < students.size(); i++) {
      System.out.println("----- Student " + (i + 1) + " -----");
      System.out.println(students.get(i));
    }
  }

  /**
   * Prints information for all staff members in the system
   * Shows numbered list with complete staff details
   */
  public static void printAllStaff() {
    if (staff.isEmpty()) {
      System.out.println("No Staff!");
      return;
    }

    // Loop through all staff and display their information
    for (int i = 0; i < staff.size(); i++) {
      System.out.println("----- Staff " + (i + 1) + " -----");
      System.out.println(staff.get(i));
    }
  }

  /**
   * Handles the INFO command to display information about a person
   * Searches both student and staff lists for the given name
   * @param parts Command parts array containing first and last name
   */
  public static void handleInfo(String[] parts) {
    // Check if enough parameters were provided
    if (parts.length < 3) {
      System.out.println("Please enter first and last name");
      return;
    }

    // Combine first and last name
    String fullName = parts[1] + " " + parts[2];

    // Search in both student and staff collections
    Student foundStudent = findStudentByName(fullName);
    Staff foundStaff = findStaffByName(fullName);

    // Display found information
    if (foundStudent != null) {
      System.out.println(foundStudent);
    }
    if (foundStaff != null) {
      System.out.println(foundStaff);
    }
    if (foundStudent == null && foundStaff == null) {
      System.out.println(fullName + " not found!");
    }
  }

  /**
   * Handles attendance commands (MARK_PRESENT and MARK_ABSENT)
   * Updates student attendance status
   * @param command The attendance command (MARK_PRESENT or MARK_ABSENT)
   * @param parts Command parts array containing the student name
   */
  public static void handleAttendance(String command, String[] parts) {
    // Check if enough parameters were provided
    if (parts.length < 3) {
      System.out.println("Please enter first and last name");
      return;
    }

    // Combine first and last name and search for student
    String fullName = parts[1] + " " + parts[2];
    Student found = findStudentByName(fullName);

    if (found != null) {
      // Call appropriate method based on command
      if (command.equals("MARK_PRESENT")) {
        found.markPresent();
      } else {
        found.markAbsent();
      }
    } else {
      System.out.println(fullName + " Not found!");
    }
  }

  /**
   * Handles clocking commands (CLOCK_IN and CLOCK_OUT)
   * Updates staff clock status
   * @param command The clocking command (CLOCK_IN or CLOCK_OUT)
   * @param parts Command parts array containing the staff name
   */
  public static void handleClocking(String command, String[] parts) {
    // Check if enough parameters were provided
    if (parts.length < 3) {
      System.out.println("Please enter first and last name");
      return;
    }

    // Combine first and last name and search for staff
    String fullName = parts[1] + " " + parts[2];
    Staff found = findStaffByName(fullName);

    if (found != null) {
      // Call appropriate method based on command
      if (command.equals("CLOCK_IN")) {
        found.clockIn();
      } else {
        found.clockOut();
      }
    } else {
      System.out.println(fullName + " Not found!");
    }
  }

  /**
   * Finds a student by name using case-insensitive lookup
   * @param name The full name to search for
   * @return Student object if found, null if not found
   */
  public static Student findStudentByName(String name) {
    return studentMap.get(name.toLowerCase());
  }

  /**
   * Finds a staff member by name using case-insensitive lookup
   * @param name The full name to search for
   * @return Staff object if found, null if not found
   */
  public static Staff findStaffByName(String name) {
    return staffMap.get(name.toLowerCase());
  }

  /**
   * Adds a student to both the ArrayList and HashMap for storage and lookup
   * @param student The student to add
   */
  private static void addStudent(Student student) {
    students.add(student);
    studentMap.put(student.getName().toLowerCase(), student);
  }

  /**
   * Adds a staff member to both the ArrayList and HashMap for storage and lookup
   * @param staffMember The staff member to add
   */
  private static void addStaff(Staff staffMember) {
    staff.add(staffMember);
    staffMap.put(staffMember.getName().toLowerCase(), staffMember);
  }

  /**
   * Loads student data from a CSV file
   * Expected format: name,grade,gender,guardians,allergies,needsPara,meds
   * @param filename The path to the CSV file containing student data
   */
  public static void loadStudentsFromFile(String filename) {
    try (Scanner fileScanner = new Scanner(new File(filename))) {
      int loadedCount = 0;

      // Process each line in the file
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        String[] parts = line.split(",");

        // Skip lines that don't have enough fields
        if (parts.length < MIN_STUDENT_FIELDS) {
          System.out.println("Skipping malformed line: " + line);
          continue;
        }

        // Create student from the parsed data
        Student s = createStudentFromParts(parts);
        if (s != null) {
          addStudent(s);  // Adds to both ArrayList and HashMap
          System.out.println("Loaded student: " + s.getName());
          loadedCount++;
        }
      }

      System.out.println("Finished loading " + loadedCount + " students.");

    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filename);
    }
  }

  /**
   * Loads staff data from a CSV file
   * Expected format: name,position,shift,email
   * @param filename The path to the CSV file containing staff data
   */
  public static void loadStaffFromFile(String filename) {
    try (Scanner fileScanner = new Scanner(new File(filename))) {
      int loadedCount = 0;

      // Process each line in the file
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        String[] parts = line.split(",");

        // Skip lines that don't have enough fields
        if (parts.length < MIN_STAFF_FIELDS) {
          System.out.println("Skipping malformed line: " + line);
          continue;
        }

        // Create staff member from the parsed data
        Staff s = createStaffFromParts(parts);
        if (s != null) {
          addStaff(s);  // Adds to both ArrayList and HashMap
          System.out.println("Loaded staff: " + s.getName());
          loadedCount++;
        }
      }

      System.out.println("Finished loading " + loadedCount + " staff.");

    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filename);
    }
  }

  /**
   * Creates a Student object from parsed CSV data
   * Handles data validation and error checking
   * @param parts Array of strings from CSV line split by commas
   * @return Student object if successful, null if parsing failed
   */
  public static Student createStudentFromParts(String[] parts) {
    try {
      // Parse and trim all the student fields
      String name = parts[0].trim();
      int grade = Integer.parseInt(parts[1].trim());  // May throw NumberFormatException
      String gender = parts[2].trim();
      Guardian[] guardians = parseGuardians(parts[3].trim());  // Parse complex guardian data
      String allergies = parts[4].trim();
      boolean needsPara = parts[5].trim().equalsIgnoreCase("yes");  // Convert to boolean
      String meds = parts[6].trim();

      return new Student(name, grade, gender, guardians, allergies, needsPara, meds);

    } catch (NumberFormatException e) {
      System.out.println("Error parsing grade - invalid number format, skipping.");
      return null;
    } catch (Exception e) {
      System.out.println("Error creating student from line, skipping.");
      return null;
    }
  }

  /**
   * Creates a Staff object from parsed CSV data
   * Handles data validation and error checking
   * @param parts Array of strings from CSV line split by commas
   * @return Staff object if successful, null if parsing failed
   */
  public static Staff createStaffFromParts(String[] parts) {
    try {
      // Parse and trim all the staff fields
      String name = parts[0].trim();
      String position = parts[1].trim();
      String shift = parts[2].trim();
      String email = parts[3].trim();

      return new Staff(name, position, shift, email);

    } catch (Exception e) {
      System.out.println("Error creating staff from line, skipping.");
      return null;
    }
  }

  /**
   * Parses guardian information from a specially formatted string
   * Expected format: ((name1;relationship1;phone1) (name2;relationship2;phone2))
   * @param guardiansStr The formatted guardian string from CSV
   * @return Array of Guardian objects
   */
  public static Guardian[] parseGuardians(String guardiansStr) {
    // Handle empty or "none" guardian cases
    if (guardiansStr.equalsIgnoreCase("none") || guardiansStr.isEmpty()) {
      return new Guardian[0];
    }

    guardiansStr = guardiansStr.trim();

    // Check for expected format with double parentheses
    if (guardiansStr.startsWith("((") && guardiansStr.endsWith("))")) {
      // Remove outer parentheses
      guardiansStr = guardiansStr.substring(2, guardiansStr.length() - 2);
    } else {
      System.out.println("Warning: Guardians string format unexpected: " + guardiansStr);
      return new Guardian[0];
    }

    // Split individual guardian entries by ") ("
    String[] guardianParts = guardiansStr.split("\\) \\(");
    Guardian[] guardians = new Guardian[guardianParts.length];

    // Parse each guardian entry
    for (int i = 0; i < guardianParts.length; i++) {
      String[] fields = guardianParts[i].split(";");  // Split by semicolon

      // Handle malformed guardian data
      if (fields.length < 3) {
        System.out.println("Malformed guardian info: " + guardianParts[i]);
        guardians[i] = new Guardian("Unknown", "Unknown", "Unknown");
        continue;
      }

      // Extract guardian information
      String name = fields[0].trim();
      String relation = fields[1].trim();
      String phone = fields[2].trim();

      guardians[i] = new Guardian(name, relation, phone);
    }

    return guardians;
  }
}
