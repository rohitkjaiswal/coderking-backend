package com.coderking.controller.courceController;

import com.coderking.model.cources.Course;
import com.coderking.service.courcesService.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Course> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return service.create(course);
    }
}
