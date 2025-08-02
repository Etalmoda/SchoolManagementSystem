/**
 * Staff class represents a staff member in the school system
 * Manages work information, location tracking, and clock in/out functionality
 */
public class Staff {
  // Basic staff information
  private String name;          // Staff member's full name
  private String position;      // Job position/title
  private String shift;         // Work shift information
  private String email;         // Staff email address

  // Location and time tracking
  private String location;      // Current location in building
  private boolean isClockedIn;  // Whether staff is currently clocked in

  /**
   * Constructor to create a new Staff member
   * @param name Staff member's full name
   * @param position Job position or title
   * @param shift Work shift details
   * @param email Staff email address
   */
  public Staff(String name, String position, String shift, String email) {
    this.name = name;
    this.position = position;
    this.shift = shift;
    this.email = email;
    this.location = "Not clocked in";  // Default location when not working
    this.isClockedIn = false;          // Staff starts clocked out
  }

  // Getter methods for accessing private fields

  /**
   * @return Staff member's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return Staff member's position/title
   */
  public String getPosition() {
    return this.position;
  }

  /**
   * @return Staff member's shift information
   */
  public String getShift() {
    return this.shift;
  }

  /**
   * @return Staff member's current location
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * @return True if staff is clocked in, false if clocked out
   */
  public boolean isClockedIn() {
    return this.isClockedIn;
  }

  /**
   * @return Staff member's email address
   */
  public String getEmail() {
    return this.email;
  }

  // Setter methods for modifying private fields

  /**
   * Sets the staff member's name
   * @param name New name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the staff member's position
   * @param position New position/title
   */
  public void setPosition(String position) {
    this.position = position;
  }

  /**
   * Sets the staff member's shift information
   * @param shift New shift details
   */
  public void setShift(String shift) {
    this.shift = shift;
  }

  /**
   * Sets the staff member's email address
   * @param email New email address
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the staff member's current location
   * @param location New location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Sets the staff member's clock in status
   * @param isClockedIn True for clocked in, false for clocked out
   */
  public void setClockedIn(boolean isClockedIn) {
    this.isClockedIn = isClockedIn;
  }

  // Time tracking methods

  /**
   * Clocks the staff member in for their shift
   * Prints confirmation message or warning if already clocked in
   */
  public void clockIn() {
    if (this.isClockedIn) {
      System.out.println("Staff is already clocked in!");
    } else {
      this.isClockedIn = true;
      System.out.println(this.name + " clocked in");
    }
  }

  /**
   * Clocks the staff member out from their shift
   * Prints confirmation message or warning if already clocked out
   */
  public void clockOut() {
    if (!this.isClockedIn) {
      System.out.println("Staff is already clocked out!");
    } else {
      this.isClockedIn = false;
      System.out.println(this.name + " clocked out");
    }
  }

  /**
   * Returns a formatted string with all staff information
   * Includes name, position, shift, location, email, and clock status
   * @return Complete staff information as formatted string
   */
  @Override
  public String toString() {
    return "Staff Member: " + name + "\n" +
        "Position: " + position + "\n" +
        "Shift: " + shift + "\n" +
        "Location: " + location + "\n" +
        "Email: " + email + "\n" +
        "Clocked In: " + (isClockedIn ? "Yes" : "No");
  }
}
