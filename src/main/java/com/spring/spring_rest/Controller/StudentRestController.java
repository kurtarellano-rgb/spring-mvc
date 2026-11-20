package com.spring.spring_rest.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.spring.spring_rest.dto.StudentDto;
import com.spring.spring_rest.service.StudentService;

import jakarta.validation.Valid;

/**
 * REST controller for managing student records.
 * Handles HTTP requests for student CRUD operations.
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentRestController {

    private StudentService studentService;

    /**
     * Constructs a StudentRestController with the required student service.
     *
     * @param studentService the service used to manage student records
     */
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Creates a new student record.
     *
     * @param theStudentDto the student data to be created
     * @return the created student with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<StudentDto> createStudents(
            @Valid @RequestBody StudentDto theStudentDto) {

        StudentDto saved = studentService.createStudent(theStudentDto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Retrieves a student record by its ID.
     *
     * @param id the ID of the student to retrieve
     * @return the student details with HTTP 200 status
     */
    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {

        StudentDto studentDto = studentService.getStudentById(id);

        return ResponseEntity.ok(studentDto);
    }

    /**
     * Retrieves all student records.
     *
     * @return a list of students with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {

        List<StudentDto> students = studentService.getAllStudents();

        return ResponseEntity.ok(students);
    }

    /**
     * Updates an existing student record using the provided student data.
     *
     * @param id the ID of the student to update
     * @param theStudentDto the updated student data
     * @return the updated student with HTTP 200 status
     */
    @PutMapping("{id}")
    public ResponseEntity<StudentDto> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDto theStudentDto) {

    StudentDto studentDto = studentService.updateStudent(id, theStudentDto);

        return ResponseEntity.ok(studentDto);
    }

    /**
     * Partially updates an existing student record.
     *
     * @param id the ID of the student to update
     * @param theStudentDto the student data containing the updates
     * @return the updated student with HTTP 200 status
     */
    @PatchMapping("{id}")
    public ResponseEntity<StudentDto> patchStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDto theStudentDto) {

        StudentDto studentDto = studentService.patchStudent(id, theStudentDto);

        return ResponseEntity.ok(studentDto);
    }

    /**
     * Deletes a student record by its ID.
     *
     * @param id the ID of the student to delete
     * @return a success message with HTTP 200 status
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {

        studentService.deleteStudentById(id);

        return ResponseEntity.ok("Student deleted successfully!");
    }
}