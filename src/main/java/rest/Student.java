package rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for student representation
 * 
 * @author Yaser Albonni
 *
 */
@XmlRootElement(name = "Student")
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String email;

	/**
	 * Default constructor
	 */
	public Student() {
	}

	/**
	 * Arguments constructor
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 */
	public Student(int id, String firstName, String lastName, String dob, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dob;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	@XmlElement(name = "ID")
	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	@XmlElement(name = "FirstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@XmlElement(name = "LastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	@XmlElement(name = "DateOfBirth")
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement(name = "Email")
	public void setEmail(String email) {
		this.email = email;
	}
}
