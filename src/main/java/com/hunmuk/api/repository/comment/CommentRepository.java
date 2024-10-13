package com.hunmuk.api.repository.comment;

import com.hunmuk.api.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
