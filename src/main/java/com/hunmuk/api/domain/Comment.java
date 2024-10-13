package com.hunmuk.api.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "comment",
        indexes = {
                @Index(name = "idx_comment_post_id", columnList = "post_id")
        }
)
@NoArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    @Builder
    public Comment(Long id, String author, String password, String content) {
        this.id = id;
        this.author = author;
        this.password = password;
        this.content = content;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
