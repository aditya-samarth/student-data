# student-data
CRUD Operations using Spring Boot and REST API

Made 4 Packages Controller, Model, Repository, Exception

# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/student-data
spring.datasource.username=root
spring.datasource.password=
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto = update


#Model

package com.springboot.studentdata.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;
}


#Repository 
package com.springboot.studentdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentDataApplication.class, args);
	}

}



#Exception

package com.springboot.studentdata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);
    }
}





#Controller 

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



Tested using Postman






