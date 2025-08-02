/**
 * Student class represents a student in the school system
 * Contains personal information, medical details, and attendance tracking
 */
public class Student {
  // Basic student information
  private String name;           // Student's full name
  private int grade;            // Grade level
  private String gender;        // Student's gender

  // Guardian and pickup information
  private Guardian[] authPickUp; // Array of authorized guardians for pickup

  // Medical and special needs information
  private String allergies;     // Known allergies (comma-separated if multiple)
  private boolean needsPara;    // Whether student needs a paraprofessional
  private String meds;         // Current medications

  // Location and attendance tracking
  private String location;      // Current location in school
  private boolean isPresent;    // Whether student is currently present

  /**
   * Constructor to create a new Student
   * @param name Student's full name
   * @param grade Grade level
   * @param gender Student's gender
   * @param authPickUp Array of authorized guardians for pickup
   * @param allergies Known allergies
   * @param needsPara Whether student needs paraprofessional support
   * @param meds Current medications
   */
  public Student(String name, int grade, String gender, Guardian[] authPickUp, String allergies,
      boolean needsPara, String meds ) {
    this.name = name;
    this.grade = grade;
    this.gender = gender;
    this.isPresent = false;      // Students start as absent by default
    this.authPickUp = authPickUp;
    this.allergies = allergies;
    this.needsPara = needsPara;
    this.location = "N/A";       // Default location when not present
    this.meds = meds;
  }

  // Getter methods for accessing private fields

  /**
   * @return Student's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return Student's grade level
   */
  public int getGrade() {
    return this.grade;
  }

  /**
   * @return Student's gender
   */
  public String getGender() {
    return this.gender;
  }

  /**
   * @return Array of authorized guardians for pickup
   */
  public Guardian[] getAuthPickUp() {
    return this.authPickUp;
  }

  /**
   * @return Student's known allergies
   */
  public String getAllergies() {
    return this.allergies;
  }

  /**
   * @return Student's current location
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * @return True if student is present, false if absent
   */
  public boolean isPresent() {
    return this.isPresent;
  }

  /**
   * @return True if student needs paraprofessional support
   */
  public boolean needsPara() {
    return this.needsPara;
  }

  /**
   * @return Student's current medications
   */
  public String getMeds() {
    return this.meds;
  }

  // Setter methods for modifying private fields

  /**
   * Sets the student's name
   * @param name New name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the student's grade level
   * @param grade New grade level
   */
  public void setGrade(int grade) {
    this.grade = grade;
  }

  /**
   * Sets the student's gender
   * @param gender New gender
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * Sets the authorized pickup guardians
   * @param authPickUp New array of authorized guardians
   */
  public void setAuthPickUp(Guardian[] authPickUp) {
    this.authPickUp = authPickUp;
  }

  /**
   * Sets the student's allergies
   * @param allergies New allergy information
   */
  public void setAllergies(String allergies) {
    this.allergies = allergies;
  }

  /**
   * Sets the student's current location
   * @param location New location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Sets the student's attendance status
   * @param isPresent True for present, false for absent
   */
  public void setPresent(boolean isPresent) {
    this.isPresent = isPresent;
  }

  /**
   * Sets whether student needs paraprofessional support
   * @param needsPara True if para support needed
   */
  public void setNeedsPara(boolean needsPara) {
    this.needsPara = needsPara;
  }

  /**
   * Sets the student's medication information
   * @param meds New medication details
   */
  public void setMeds(String meds) {
    this.meds = meds;
  }

  // Attendance management methods

  /**
   * Marks the student as present
   * Prints confirmation message or warning if already present
   */
  public void markPresent() {
    if (this.isPresent) {
      System.out.println("Student is already present!");
    } else {
      this.isPresent = true;
      System.out.println(this.name + " marked present");
    }
  }

  /**
   * Marks the student as absent
   * Prints confirmation message or warning if already absent
   */
  public void markAbsent() {
    if (!this.isPresent) {
      System.out.println("Student is already absent!");
    } else {
      this.isPresent = false;
      System.out.println(this.name + " marked absent");
    }
  }

  /**
   * Returns a formatted string with all student information
   * Includes personal details, medical info, attendance, and guardian list
   * @return Complete student information as formatted string
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Name: ").append(name).append("\n");
    sb.append("Grade: ").append(grade).append("\n");
    sb.append("Gender: ").append(gender).append("\n");
    sb.append("Allergies: ").append(allergies).append("\n");
    sb.append("Medications: ").append(meds).append("\n");
    sb.append("Needs Para: ").append(needsPara ? "Yes" : "No").append("\n");
    sb.append("Is Present: ").append(isPresent ? "Yes" : "No").append("\n");
    sb.append("Location: ").append(location).append("\n");
    sb.append("Authorized Pickups:\n");

    // List all authorized guardians, or "None" if empty
    if (authPickUp.length == 0) {
      sb.append("  None\n");
    } else {
      for (Guardian g : authPickUp) {
        sb.append("  ").append(g).append("\n");
      }
    }

    return sb.toString();
  }
}
