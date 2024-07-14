package com.hunmuk.api.repository;

import com.hunmuk.api.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCostom {
}
