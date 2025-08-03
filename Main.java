import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for the School Management System Provides a command-line interface for managing
 * students and staff Supports loading data from files, attendance tracking, and information
 * queries
 */
public class Main {
  // Data storage collections
  private static ArrayList<Student> students = new ArrayList<>();  // List of all students
  private static ArrayList<Staff> staff = new ArrayList<>();       // List of all staff
  private static ArrayList<Room> rooms = new ArrayList<>();        // List of all rooms


  // Hash maps for fast name-based lookups (case-insensitive)
  private static Map<String, Student> studentMap = new HashMap<>();
  private static Map<String, Staff> staffMap = new HashMap<>();
  private static Map<String, Room> roomMap = new HashMap<>();

  // Constants for minimum required fields in CSV files
  private static final int MIN_STUDENT_FIELDS = 7;  // Minimum columns needed for student data
  private static final int MIN_STAFF_FIELDS = 3;    // Minimum columns needed for staff data
  private static final int MIN_ROOM_FIELDS = 2;     // Minimum columns needed for room data

  /**
   * Main method - entry point of the application Sets up the command loop and processes user input
   *
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    boolean running = true;

    // Welcome message and initial help display
    System.out.println("Welcome to School Management! Please enter a command to continue!");
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
        case "LOAD_ROOMS":
          if (parts.length > 1) {
            loadRoomsFromFile(parts[1]);
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
        case "ALL_ROOMS":
          printAllRooms();
          break;
        case "ROOM_INFO":
          if (parts.length > 1) {
            handleRoomInfo(parts[1]);
          } else {
            System.out.println("Please provide a room name.");
          }
          break;
        case "ASSIGN_STUDENT":
          handleAssignStudent(parts);
          break;
        case "ASSIGN_STAFF":
          handleAssignStaff(parts);
          break;
        case "OPEN_ROOM":
          if (parts.length > 1) {
            handleOpenRoom(parts[1]);
          } else {
            System.out.println("Please provide a room name.");
          }
          break;
        case "CLOSE_ROOM":
          if (parts.length > 1) {
            handleCloseRoom(parts[1]);
          } else {
            System.out.println("Please provide a room name.");
          }
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
   * Displays the help message with all available commands Shows command syntax and brief
   * descriptions
   */
  public static void printHelpMessage() {
    System.out.println("\nList of commands:");
    System.out.println("HELP - Prints help message");
    System.out.println("LOAD_STUDENTS (filename) - Loads student data from specified file");
    System.out.println("LOAD_STAFF (filename) - Loads staff data from specified file");
    System.out.println("LOAD_ROOMS (filename) - Loads room data from specified file");
    System.out.println("INFO (FirstName LastName) - Returns info for specified person");
    System.out.println("ALL_STUDENTS - Lists all students and info");
    System.out.println("ALL_STAFF - Lists all staff and info");
    System.out.println("ALL_ROOMS - Lists all rooms and info");
    System.out.println("ROOM_INFO (RoomName) - Shows detailed room information");
    System.out.println("ASSIGN_STUDENT (FirstName LastName) (RoomName) - Assigns student to room");
    System.out.println("ASSIGN_STAFF (FirstName LastName) (RoomName) - Assigns staff to room");
    System.out.println("OPEN_ROOM (RoomName) - Opens a closed room");
    System.out.println("CLOSE_ROOM (RoomName) - Closes an open room");
    System.out.println("CLOCK_IN (FirstName LastName) - Clocks staff in");
    System.out.println("CLOCK_OUT (FirstName LastName) - Clocks staff out");
    System.out.println("MARK_PRESENT (FirstName LastName) - Marks student present");
    System.out.println("MARK_ABSENT (FirstName LastName) - Marks student absent");
    System.out.println("QUIT - Quits program\n");
  }

  /**
   * Prints information for all students in the system Shows numbered list with complete student
   * details
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
   * Prints information for all staff members in the system Shows numbered list with complete staff
   * details
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
   * Prints information for all rooms in the system
   * Shows numbered list with complete room details
   */
  public static void printAllRooms() {
    if (rooms.isEmpty()) {
      System.out.println("No Rooms!");
      return;
    }

    // Loop through all rooms and display their information
    for (int i = 0; i < rooms.size(); i++) {
      System.out.println("----- Room " + (i + 1) + " -----");
      System.out.println(rooms.get(i));
    }
  }

  /**
   * Handles the ROOM_INFO command to display detailed information about a specific room
   * @param roomName The name of the room to get information for
   */
  public static void handleRoomInfo(String roomName) {
    Room found = findRoomByName(roomName);
    if (found != null) {
      System.out.println(found);
      System.out.println("Staff-to-Student Ratio: " + found.getRatio());

      // List all students in the room
      if (!found.getStudents().isEmpty()) {
        System.out.println("Students in room:");
        for (Student s : found.getStudents()) {
          System.out.println("  - " + s.getName());
        }
      }

      // List all staff in the room
      if (!found.getStaff().isEmpty()) {
        System.out.println("Staff in room:");
        for (Staff s : found.getStaff()) {
          System.out.println("  - " + s.getName() + " (" + s.getPosition() + ")");
        }
      }
    } else {
      System.out.println("Room " + roomName + " not found!");
    }
  }

  /**
   * Handles the ASSIGN_STUDENT command to assign a student to a room
   * @param parts Command parts array containing student name and room name
   */
  public static void handleAssignStudent(String[] parts) {
    if (parts.length < 4) {
      System.out.println("Please enter first name, last name, and room name");
      return;
    }

    String studentName = parts[1] + " " + parts[2];
    String roomName = parts[3];

    Student student = findStudentByName(studentName);
    Room room = findRoomByName(roomName);

    if (student == null) {
      System.out.println("Student " + studentName + " not found!");
      return;
    }

    if (room == null) {
      System.out.println("Room " + roomName + " not found!");
      return;
    }

    // Check if room is closed
    if (room.isClosed()) {
      System.out.println("Cannot assign student to " + roomName + " - room is closed.");
      return;
    }

    // Remove student from any current room first
    removeStudentFromAllRooms(student);

    room.addStudent(student);
  }

  /**
   * Handles the ASSIGN_STAFF command to assign a staff member to a room
   * @param parts Command parts array containing staff name and room name
   */
  public static void handleAssignStaff(String[] parts) {
    if (parts.length < 4) {
      System.out.println("Please enter first name, last name, and room name");
      return;
    }

    String staffName = parts[1] + " " + parts[2];
    String roomName = parts[3];

    Staff staffMember = findStaffByName(staffName);
    Room room = findRoomByName(roomName);

    if (staffMember == null) {
      System.out.println("Staff " + staffName + " not found!");
      return;
    }

    if (room == null) {
      System.out.println("Room " + roomName + " not found!");
      return;
    }

    // Check if room is closed
    if (room.isClosed()) {
      System.out.println("Cannot assign staff to " + roomName + " - room is closed.");
      return;
    }

    // Remove staff from any current room first
    removeStaffFromAllRooms(staffMember);

    room.addStaff(staffMember);
  }

  /**
   * Removes a student from all rooms they might be assigned to
   * @param student The student to remove from all rooms
   */
  private static void removeStudentFromAllRooms(Student student) {
    for (Room room : rooms) {
      if (room.getStudents().contains(student)) {
        room.getStudents().remove(student);
        student.setLocation("N/A");
        System.out.println("Removed " + student.getName() + " from " + room.getName());
        break; // Student should only be in one room at a time
      }
    }
  }

  /**
   * Removes a staff member from all rooms they might be assigned to
   * @param staff The staff member to remove from all rooms
   */
  private static void removeStaffFromAllRooms(Staff staff) {
    for (Room room : rooms) {
      if (room.getStaff().contains(staff)) {
        room.getStaff().remove(staff);
        staff.setLocation("N/A");
        System.out.println("Removed " + staff.getName() + " from " + room.getName());
        break; // Staff should only be in one room at a time
      }
    }
  }

  /**
   * Handles the OPEN_ROOM command to open a closed room
   * @param roomName The name of the room to open
   */
  public static void handleOpenRoom(String roomName) {
    Room room = findRoomByName(roomName);

    if (room == null) {
      System.out.println("Room " + roomName + " not found!");
      return;
    }

    if (!room.isClosed()) {
      System.out.println("Room " + roomName + " is already open!");
    } else {
      room.setClosed(false);
      System.out.println("Room " + roomName + " has been opened.");
    }
  }

  /**
   * Handles the CLOSE_ROOM command to close an open room
   * @param roomName The name of the room to close
   */
  public static void handleCloseRoom(String roomName) {
    Room room = findRoomByName(roomName);

    if (room == null) {
      System.out.println("Room " + roomName + " not found!");
      return;
    }

    if (room.isClosed()) {
      System.out.println("Room " + roomName + " is already closed!");
    } else {
      // Remove all students and staff from the room before closing
      int studentCount = room.getStudents().size();
      int staffCount = room.getStaff().size();

      // Clear students and reset their locations
      for (Student student : room.getStudents()) {
        student.setLocation("N/A");
      }
      room.getStudents().clear();

      // Clear staff
      for (Staff staff : room.getStaff()) {
        staff.setLocation("N/A");
      }
      room.getStaff().clear();

      // Close the room
      room.setClosed(true);

      System.out.println("Room " + roomName + " has been closed.");
      if (studentCount > 0 || staffCount > 0) {
        System.out.println("Removed " + studentCount + " students and " + staffCount + " staff from the room.");
      }
    }
  }

  /**
   * Handles the INFO command to display information about a person Searches both student and staff
   * lists for the given name
   *
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
   * Handles attendance commands (MARK_PRESENT and MARK_ABSENT) Updates student attendance status
   *
   * @param command The attendance command (MARK_PRESENT or MARK_ABSENT)
   * @param parts   Command parts array containing the student name
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
        removeStudentFromAllRooms(found);
      }
    } else {
      System.out.println(fullName + " Not found!");
    }
  }

  /**
   * Handles clocking commands (CLOCK_IN and CLOCK_OUT) Updates staff clock status
   *
   * @param command The clocking command (CLOCK_IN or CLOCK_OUT)
   * @param parts   Command parts array containing the staff name
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
        removeStaffFromAllRooms(found);
      }
    } else {
      System.out.println(fullName + " Not found!");
    }
  }

  /**
   * Finds a student by name using case-insensitive lookup
   *
   * @param name The full name to search for
   * @return Student object if found, null if not found
   */
  public static Student findStudentByName(String name) {
    return studentMap.get(name.toLowerCase());
  }

  /**
   * Finds a staff member by name using case-insensitive lookup
   *
   * @param name The full name to search for
   * @return Staff object if found, null if not found
   */
  public static Staff findStaffByName(String name) {
    return staffMap.get(name.toLowerCase());
  }

  /**
   * Finds a room by name using case-insensitive lookup
   * @param name The room name to search for
   * @return Room object if found, null if not found
   */
  public static Room findRoomByName(String name) {
    return roomMap.get(name.toLowerCase());
  }

  /**
   * Adds a student to both the ArrayList and HashMap for storage and lookup
   *
   * @param student The student to add
   */
  private static void addStudent(Student student) {
    students.add(student);
    studentMap.put(student.getName().toLowerCase(), student);
  }

  /**
   * Adds a staff member to both the ArrayList and HashMap for storage and lookup
   *
   * @param staffMember The staff member to add
   */
  private static void addStaff(Staff staffMember) {
    staff.add(staffMember);
    staffMap.put(staffMember.getName().toLowerCase(), staffMember);
  }

  /**
   * Adds a room to both the ArrayList and HashMap for storage and lookup
   * @param room The room to add
   */
  private static void addRoom(Room room) {
    rooms.add(room);
    roomMap.put(room.getName().toLowerCase(), room);
  }

  /**
   * Loads student data from a CSV file Expected format:
   * name,grade,gender,guardians,allergies,needsPara,meds
   *
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
   * Loads staff data from a CSV file Expected format: name,position,shift,email
   *
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
   * Loads room data from a CSV file
   * Expected format: name,capacity
   * @param filename The path to the CSV file containing room data
   */
  public static void loadRoomsFromFile(String filename) {
    try (Scanner fileScanner = new Scanner(new File(filename))) {
      int loadedCount = 0;

      // Process each line in the file
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        String[] parts = line.split(",");

        // Skip lines that don't have enough fields
        if (parts.length < MIN_ROOM_FIELDS) {
          System.out.println("Skipping malformed line: " + line);
          continue;
        }

        // Create room from the parsed data
        Room r = createRoomFromParts(parts);
        if (r != null) {
          addRoom(r);  // Adds to both ArrayList and HashMap
          System.out.println("Loaded room: " + r.getName());
          loadedCount++;
        }
      }

      System.out.println("Finished loading " + loadedCount + " rooms.");

    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filename);
    }
  }

  /**
   * Creates a Student object from parsed CSV data Handles data validation and error checking
   *
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
   * Creates a Staff object from parsed CSV data Handles data validation and error checking
   *
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
   * Creates a Room object from parsed CSV data
   * Handles data validation and error checking
   * @param parts Array of strings from CSV line split by commas
   * @return Room object if successful, null if parsing failed
   */
  public static Room createRoomFromParts(String[] parts) {
    try {
      // Parse and trim the room fields
      String name = parts[0].trim();
      int capacity = Integer.parseInt(parts[1].trim());  // May throw NumberFormatException

      return new Room(name, capacity);

    } catch (NumberFormatException e) {
      System.out.println("Error parsing room capacity - invalid number format, skipping.");
      return null;
    } catch (Exception e) {
      System.out.println("Error creating room from line, skipping.");
      return null;
    }
  }

  /**
   * Parses guardian information from a specially formatted string Expected format:
   * ((name1;relationship1;phone1) (name2;relationship2;phone2))
   *
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
