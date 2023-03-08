package com.springboot.studentdata.controller;

import com.springboot.studentdata.exception.ResourceNotFoundException;
import com.springboot.studentdata.model.Student;
import com.springboot.studentdata.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllEmployees() {
        return studentRepository.findAll();
    }

    // Build CREATE Student REST API
    @PostMapping
    public Student createEmployee(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // Build GET Student by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Student> getEmployeeById(@PathVariable long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id:" + id));
        return ResponseEntity.ok(student);
    }

    // Build UPDATE Student REST API
    @PutMapping("{id}")
    public ResponseEntity<Student> updateEmployee(@PathVariable long id, @RequestBody Student employeeDetails) {
        Student updateStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        updateStudent.setFirstName(employeeDetails.getFirstName());
        updateStudent.setLastName(employeeDetails.getLastName());
        updateStudent.setEmailId(employeeDetails.getEmailId());

        studentRepository.save(updateStudent);

        return ResponseEntity.ok(updateStudent);
    }

    // Build DELETE Student REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id: " + id));

        studentRepository.delete(student);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
