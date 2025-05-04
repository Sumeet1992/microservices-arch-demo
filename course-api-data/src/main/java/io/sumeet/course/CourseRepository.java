package io.sumeet.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    @Query("SELECT c FROM Course c WHERE c.topic.topic_id = :topicId")
    public List<Course> findByTopicId(@Param("topicId") String topicId);

}
