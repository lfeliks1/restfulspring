package rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring controller for Student RESTful service
 * 
 * @author Yaser Albonni
 *
 */
@RestController
@RequestMapping(path = "studentservice")
public class StudentController {

	int startID = 111;
	Map<Integer, Student> studentsDB = new HashMap<Integer, Student>();

	/**
	 * Default constructor
	 */
	public StudentController() {
		Student sample1 = new Student(startID++, "Jon", "Doe", "04/18/1973", "jdoe@mail.com");
		Student sample2 = new Student(startID++, "Jane", "Doe", "11/05/1977", "janed@mail.com");

		studentsDB.put(sample1.getId(), sample1);
		studentsDB.put(sample2.getId(), sample2);
	}

	@GetMapping()
	public String greeting() {
		return "Welcome to StudentService RESTful API.";
	}

	/**
	 * Get all students
	 * 
	 * @return List of students
	 */
	@GetMapping(path = "/all_students", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<Student> getAllStudents() {

		System.out.println("Request to StudentService - getAllStudents");

		List<Student> students = new ArrayList<Student>();

		for (int id : studentsDB.keySet()) {
			students.add(studentsDB.get(id));
		}

		return students;
	}

	/**
	 * Get student by ID
	 * 
	 * @param id
	 * @return HTTP Response
	 */
	@GetMapping(path = "/student", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getStudent(@RequestParam(value = "id") String id) {

		System.out.println("Request to StudentService - getStudent");

		try {
			int idNumber = Integer.parseInt(id);
			Student student = studentsDB.get(idNumber);

			return new ResponseEntity<>(student, HttpStatus.OK);

		} catch (NumberFormatException ex) {
			System.out.println("Exception at StudentService - getStudent: " + ex.getMessage());

			return new ResponseEntity<>("Invalid format of ID.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Create new student
	 * 
	 * @param student
	 * @return HTTP response
	 */
	@PostMapping(path = "/student", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> createStudent(@RequestBody Student student) {

		System.out.println("Request to StudentService - createStudent");

		// Validate data
		DateFormat dateValidator = new SimpleDateFormat("MM/dd/yyyy");
		dateValidator.setLenient(false);
		try {
			dateValidator.parse(student.getDateOfBirth());
		} catch (ParseException e) {
			return new ResponseEntity<String>("Invalid date of birth. Format should be MM/dd/yyyy.",
					HttpStatus.BAD_REQUEST);
		}

		String regexPattern = "^\\S+@\\S+\\.\\S+$";
		if (!Pattern.compile(regexPattern).matcher(student.getEmail()).matches()) {
			return new ResponseEntity<String>("Invalid email format", HttpStatus.BAD_REQUEST);
		}

		// Create new student
		Student newStudent = new Student();
		newStudent.setFirstName(student.getFirstName());
		newStudent.setLastName(student.getLastName());
		newStudent.setDateOfBirth(student.getDateOfBirth());
		newStudent.setEmail(student.getEmail());
		newStudent.setId(startID++);

		studentsDB.put(newStudent.getId(), newStudent);

		return new ResponseEntity<String>("Created new student with ID: " + newStudent.getId(), HttpStatus.CREATED);
	}

	/**
	 * Update student
	 * 
	 * @param student
	 * @return HTTP Response
	 */
	@PutMapping(path = "/student", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updateStudent(@RequestBody Student student) {

		System.out.println("Request to StudentService - updateStudent");

		Student studentToUpdate = studentsDB.get(student.getId());

		ResponseEntity<String> response;

		if (studentToUpdate != null) {

			studentsDB.put(studentToUpdate.getId(), student);
			response = new ResponseEntity<String>("Student: " + student.getId() + " is updated successfully.",
					HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>("Student: " + student.getId() + " does not exist.",
					HttpStatus.NOT_MODIFIED);
		}

		return response;
	}

	/**
	 * Delete student
	 * 
	 * @param id
	 * @return HTTP Response
	 */
	@DeleteMapping("/student")
	public ResponseEntity<String> deleteStudent(@RequestParam(value = "id") String id) {

		System.out.println("Request to StudentService - deleteStudent");

		ResponseEntity<String> response;

		try {
			int idNumber = Integer.parseInt(id);

			if (studentsDB.get(idNumber) != null) {

				studentsDB.remove(idNumber);
				response = new ResponseEntity<String>("Student: " + id + " is deleted successfully.", HttpStatus.OK);

			} else {
				response = new ResponseEntity<String>("Student: " + id + " does not exist.", HttpStatus.NOT_MODIFIED);
			}

			return response;

		} catch (NumberFormatException ex) {
			System.out.println("Exception at StudentService - deleteStudent: " + ex.getMessage());

			return new ResponseEntity<String>("Invalid format of ID.", HttpStatus.BAD_REQUEST);
		}
	}
}
