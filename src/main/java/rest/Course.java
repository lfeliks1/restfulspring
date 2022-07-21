package rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for course representation
 * 
 * @author Yaser Albonni
 *
 */
@XmlRootElement(name = "Course")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	private String number;
	private String title;

	/**
	 * Default constructor
	 */
	public Course() {
	}

	/**
	 * Arguments constructor
	 * 
	 * @param number
	 * @param title
	 */
	public Course(String number, String title) {
		this.number = number;
		this.title = title;
	}

	public String getNumber() {
		return number;
	}

	@XmlElement(name = "Number")
	public void setNumber(String number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement(name = "Title")
	public void setTitle(String title) {
		this.title = title;
	}
}
