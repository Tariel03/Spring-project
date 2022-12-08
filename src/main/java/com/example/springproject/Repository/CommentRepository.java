package com.example.springproject.Repository;

import com.example.springproject.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c from Comment c where c.commentt=?1 ")
    Optional<Comment> findCommentsByForeignComment(Comment comment);


}
