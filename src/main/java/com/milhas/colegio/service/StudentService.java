package com.milhas.colegio.service;

import com.milhas.colegio.model.Role;
import com.milhas.colegio.model.Student;
import com.milhas.colegio.model.User;
import com.milhas.colegio.repository.StudentRepository;
import com.milhas.colegio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;

    @Transactional
    public void createStudent(User user,
                              String enrollmentNumber) {

        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(userService.createUserAccount(user));

        Student student = new Student();
        student.setUser(savedUser);

        student.setEnrollmentNumber(enrollmentNumber);
        studentRepository.save(student);

    }


}