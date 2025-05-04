package io.sumeet.topic;

import io.sumeet.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Transactional
    public List<Topic> getallTopics(){
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().forEach(x -> topics.add(x));
        return topics;
    }

    public Topic getTopicById(String id){
        return topicRepository.findById(id).get();
    }

    public void addTopic(Topic topic){
        for (Course course : topic.getCourses()) {
            course.setTopic(topic); // Set the relationship
        }
        topicRepository.save(topic);
    }

    public void updateTopic(Topic topic){
        topicRepository.save(topic);
    }

    public void deleteTopic(String id){
        topicRepository.deleteById(id);
    }

}
