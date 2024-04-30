package com.example.Twitter.repo;

import com.example.Twitter.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //Optional<Comment> findByCommentId(int commentID);

}
