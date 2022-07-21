package rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for registrar representation
 * 
 * @author Yaser Albonni
 *
 */
@XmlRootElement(name = "Registrar")
public class Registrar implements Serializable {

	private static final long serialVersionUID = 1L;

	private String courseNumber;
	private int studentID;
	private String status;

	/**
	 * Default constructor
	 */
	public Registrar() {
	}

	/**
	 * Arguments constructor
	 * 
	 * @param course
	 * @param id
	 */
	public Registrar(String course, int id) {
		this.courseNumber = course;
		this.setStudentID(id);
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	@XmlElement(name = "CourseNumber")
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public int getStudentID() {
		return studentID;
	}

	@XmlElement(name = "StudentID")
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getStatus() {
		return status;
	}

	@XmlElement(name = "RegistrationStatus")
	public void setStatus(String status) {
		this.status = status;
	}
}
