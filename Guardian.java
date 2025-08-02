/**
 * Guardian class represents a guardian/parent of a student
 * Contains contact information and relationship details
 */
public class Guardian {
  // Guardian's full name
  private String name;

  // Relationship to the child
  private String relationshipToChild;

  // Guardian's phone number for contact
  private String phoneNumber;

  /**
   * Constructor to create a new Guardian
   * @param name The guardian's full name
   * @param relationshipToChild The relationship to the student
   * @param phoneNumber The guardian's contact phone number
   */
  public Guardian(String name, String relationshipToChild, String phoneNumber) {
    this.name = name;
    this.relationshipToChild = relationshipToChild;
    this.phoneNumber = phoneNumber;
  }

  // Getter methods to access private fields

  /**
   * @return The guardian's name
   */
  public String getName() {
    return name;
  }

  /**
   * @return The relationship to the child
   */
  public String getRelationshipToChild() {
    return relationshipToChild;
  }

  /**
   * @return The guardian's phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  // Setter methods to modify private fields

  /**
   * Sets the guardian's name
   * @param name The new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the relationship to child
   * @param relationshipToChild The new relationship description
   */
  public void setRelationshipToChild(String relationshipToChild) {
    this.relationshipToChild = relationshipToChild;
  }

  /**
   * Sets the guardian's phone number
   * @param phoneNumber The new phone number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Returns a formatted string representation of the guardian
   * Format: "Name (Relationship), Phone: PhoneNumber"
   * @return Formatted guardian information
   */
  @Override
  public String toString() {
    return name + " (" + relationshipToChild + "), Phone: " + phoneNumber;
  }
}
