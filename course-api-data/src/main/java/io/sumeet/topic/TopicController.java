package io.sumeet.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("/topics")
    public List<Topic> getAllTopics(){
        return topicService.getallTopics();
    }

    @GetMapping("/topics/{topicId}")
    public Topic GetTopicById(@PathVariable("topicId") String id){
        return topicService.getTopicById(id);
    }

    @PostMapping("/topics")
    public void addNewTopic(@RequestBody Topic topic){
        topicService.addTopic(topic);
    }

    @PutMapping
    public void updateTopic(@RequestBody Topic topic){
        topicService.updateTopic(topic);
    }

    @DeleteMapping("/topics/{topics_id}")
    public void deleteTopic(@PathVariable String id){
        topicService.deleteTopic(id);
    }

}
