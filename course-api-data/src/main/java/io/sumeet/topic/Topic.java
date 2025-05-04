package io.sumeet.topic;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.sumeet.course.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Topic {

    @Id
    String topic_id;
    String name;
    String description;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Course> courses;

    public Topic() {
    }

    public Topic(String topic_id, String name, String description, List<Course> courses) {
        this.topic_id = topic_id;
        this.name = name;
        this.description = description;
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
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

    @Override
    public String toString() {
        return "Topic{" +
                "topic_id='" + topic_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", courses=" + courses +
                '}';
    }
}
