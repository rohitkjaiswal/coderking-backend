package com.coderking.service.courcesService;

import com.coderking.model.cources.Course;
import com.coderking.repository.cources.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository repo;

    public CourseService(CourseRepository repo) {
        this.repo = repo;
    }

    public List<Course> getAll() {
        return repo.findAll();
    }

    public Course getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found: " + id));
    }

    public Course create(Course course) {
        return repo.save(course);
    }
}
