package rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Spring controller for Course RESTful service
 * 
 * @author Yaser Albonni
 *
 */
@RestController
@RequestMapping(path = "courseservice")
public class CourseController {

	Map<String, Course> coursesDB = new HashMap<String, Course>();

	/**
	 * Default constructor
	 */
	public CourseController() {
		Course sample1 = new Course("605.784", "Enterpsise Computing with Java");
		Course sample2 = new Course("605.785", "Web Services: Frameworks, Processes, Applications");
		Course sample3 = new Course("605.786", "Enterprise System Design and Implementation");

		coursesDB.put(sample1.getNumber(), sample1);
		coursesDB.put(sample2.getNumber(), sample3);
		coursesDB.put(sample3.getNumber(), sample3);
	}

	@GetMapping()
	public String greeting() {
		return "Welcome to CourseService RESTful API.";
	}

	/**
	 * Get all courses
	 * 
	 * @return List of courses
	 */
	@GetMapping(path = "/all_courses", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<Course> getAllCourses() {

		System.out.println("Request to CourseService - getAllCourses");

		List<Course> courses = new ArrayList<Course>();

		for (String number : coursesDB.keySet()) {
			courses.add(coursesDB.get(number));
		}

		return courses;
	}

	/**
	 * Get course by ID
	 * 
	 * @param id
	 * @return Course
	 */
	@GetMapping(path = "/course", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public Course getCourse(@RequestParam(value = "id") String id) {

		System.out.println("Request to CourseService - getCourse");

		return coursesDB.get(id);
	}

	/**
	 * Create new course
	 * 
	 * @param course
	 * @return Newly created course ID
	 */
	@PostMapping(path = "/course", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> createCourse(@RequestBody Course course) {

		System.out.println("Request to CourseService - createCourse");

		ResponseEntity<String> response;

		if (coursesDB.get(course.getNumber()) == null) {

			Course newCourse = new Course();
			newCourse.setNumber(course.getNumber());
			newCourse.setTitle(course.getTitle());

			coursesDB.put(newCourse.getNumber(), newCourse);

			response = new ResponseEntity<String>("Course: " + course.getNumber() + " is created successfully.",
					HttpStatus.CREATED);

		} else {
			response = new ResponseEntity<String>("Course: " + course.getNumber() + " already exists.",
					HttpStatus.NOT_MODIFIED);
		}

		return response;
	}

	/**
	 * Update course
	 * 
	 * @param course
	 * @return HTTP Response
	 */
	@PutMapping(path = "/course", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updateCourse(@RequestBody Course course) {

		System.out.println("Request to CourseService - updateCourse");

		Course courseToUpdate = coursesDB.get(course.getNumber());

		ResponseEntity<String> response;

		if (courseToUpdate != null) {

			coursesDB.put(courseToUpdate.getNumber(), course);
			response = new ResponseEntity<String>("Course: " + course.getNumber() + " is updated successfully.",
					HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>("Course: " + course.getNumber() + " does not exist.",
					HttpStatus.NOT_MODIFIED);
		}

		return response;
	}

	/**
	 * Delete course
	 * 
	 * @param id
	 * @return HTTP Response
	 */
	@DeleteMapping("/course")
	public ResponseEntity<String> deleteCourse(@RequestParam(value = "id") String id) {

		System.out.println("Request to CourseService - deleteCourse");

		ResponseEntity<String> response;

		if (coursesDB.get(id) != null) {

			coursesDB.remove(id);
			response = new ResponseEntity<String>("Course: " + id + " is deleted successfully.", HttpStatus.OK);

		} else {
			response = new ResponseEntity<String>("Course: " + id + " does not exist.", HttpStatus.NOT_MODIFIED);
		}

		return response;
	}
}
