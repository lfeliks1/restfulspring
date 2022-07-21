package rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring controller for Registrar RESTful service
 * 
 * @author Yaser Albonni
 *
 */
@RestController
@RequestMapping(path = "registrarservice")
public class RegistrarController {

	Map<String, List<Integer>> registrarDB = new HashMap<String, List<Integer>>();

	/**
	 * Default constructor
	 */
	public RegistrarController() {
		registrarDB.put("605.784", new ArrayList<>(Arrays.asList(111, 112, 113, 114, 115, 116, 117)));
		registrarDB.put("605.785", new ArrayList<>(Arrays.asList(112, 115, 116)));
		registrarDB.put("605.786", new ArrayList<>(Arrays.asList(111, 116, 117)));
	}

	@GetMapping()
	public String greeting() {
		return "Welcome to RegistrarService RESTful API.";
	}

	/**
	 * Get list of all students registered to a given course
	 * 
	 * @param course
	 * @return List of student IDs
	 */
	@GetMapping(path = "/registrar", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<Integer> getRegisteredStudents(@RequestParam(value = "course") String course) {

		System.out.println("Request to RegistrarService - getRegisteredStudents");

		return registrarDB.get(course);
	}

	/**
	 * Register student/s to a course/s
	 * 
	 * @param List of registrars
	 * @return HTTP response
	 */
	@PostMapping(path = "/registrar", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> registerStudents(@RequestBody List<Registrar> registrars) {

		System.out.println("Request to RegistrarService - registerStudents");

		try {
			if (registrars.size() > 15) {
				return new ResponseEntity<>("Error - registrar list should not exceed 15 entries.",
						HttpStatus.BAD_REQUEST);
			}

			for (Registrar registrar : registrars) {

				if (registrarDB.get(registrar.getCourseNumber()) == null) {
					registrar.setStatus("Course does not exist in system.");
					continue;
				}
				if (registrarDB.get(registrar.getCourseNumber()).contains(registrar.getStudentID())) {
					registrar.setStatus("Student is already registered for course.");
					continue;
				}
				registrarDB.get(registrar.getCourseNumber()).add(registrar.getStudentID());
				registrar.setStatus("Student registered successfully.");
			}

			return new ResponseEntity<>(registrars, HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>("Error " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Drop student from a given course
	 * 
	 * @param Registrar
	 * @return HTTP Response
	 */
	@DeleteMapping("/registrar")
	public ResponseEntity<String> dropStudentFromCourse(@RequestBody Registrar registrar) {

		System.out.println("Request to RegistrarService - dropStudentFromCourse");

		if (registrarDB.get(registrar.getCourseNumber()) == null) {
			return new ResponseEntity<String>("Course: " + registrar.getCourseNumber() + " does not exist in system.",
					HttpStatus.NOT_MODIFIED);
		}

		if (!registrarDB.get(registrar.getCourseNumber()).contains(registrar.getStudentID())) {
			return new ResponseEntity<String>("Student: " + registrar.getStudentID() + " is not registered in course "
					+ registrar.getCourseNumber(), HttpStatus.NOT_MODIFIED);
		}

		registrarDB.get(registrar.getCourseNumber()).remove(Integer.valueOf(registrar.getStudentID()));
		return new ResponseEntity<String>(
				"Student: " + registrar.getStudentID() + " is dropped from course " + registrar.getCourseNumber(),
				HttpStatus.OK);
	}
}
