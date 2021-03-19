package com.demo.controller;

import com.demo.entity.Student;
import com.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    @PostMapping("/add")
    public String add(@RequestBody Student student) {
        studentMapper.insert(student);
        return "success -> " + student.getId();
    }

}
