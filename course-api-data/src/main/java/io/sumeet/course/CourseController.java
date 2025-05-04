package io.sumeet.course;

import io.sumeet.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/topics/{topicId}/courses")
    public List<Course> getAllCourses(@PathVariable String topicId){
        return courseService.getAllCourses(topicId);
    }

    @GetMapping("/topics/{topicId}/courses/{courseId}")
    public Course getCourseById(@PathVariable ("topicId") String topicId, @PathVariable ("courseId") String courseId){
        return courseService.getCourse(topicId,courseId);
    }

    @PostMapping("/topics/{topicId}/courses")
    public void addCourse(@PathVariable String topicId, @RequestBody Course course){
        course.setTopic(new Topic(topicId, "", "", null));
        courseService.addCourse(course);
    }

}
