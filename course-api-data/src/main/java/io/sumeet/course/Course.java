package io.sumeet.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.sumeet.topic.Topic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Course {

    @Id
    String course_id;
    String name;
    String description;

    @ManyToOne // or @OneToOne depending on your use case
    @JoinColumn(name = "topic_id")
    @JsonBackReference
    Topic topic;

    public Course() {
    }

    public Course(String course_id, String name, String description, Topic topic) {
        this.course_id = course_id;
        this.name = name;
        this.description = description;
        this.topic = topic;
    }

    public String getcourse_id() {
        return course_id;
    }

    public void setcourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_id=" + course_id +
                ", name=" + name +
                ", description=" + description +
                ", topic=" + topic +
                '}';
    }
}
