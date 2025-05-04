package io.sumeet.course;

import io.sumeet.topic.Topic;
import io.sumeet.topic.TopicRepository;
import io.sumeet.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TopicRepository topicRepository;

    public List<Course> getAllCourses(String id){

        return courseRepository.findByTopicId(id);
    }

    public Course getCourse(String topicId, String courseId){
        List<Course> coursesList = courseRepository.findByTopicId(topicId);
        return coursesList.stream().filter(x -> courseId.equals(x.getcourse_id())).findFirst().orElse(null);
    }

    public void addCourse(Course course){
        courseRepository.save(course);
    }

    public void updateCourse(Course course){
        courseRepository.save(course);
    }

    public void deleteCourse(String id){
        courseRepository.deleteById(id);
    }

}
