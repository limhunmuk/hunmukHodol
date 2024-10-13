package com.hunmuk.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Builder
    public Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    // 연관관계 주인 mappedBy 적시
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Lob
    private String contents;

   /* public void change(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }*/

    public PostEditor.PostEditorBuilder toEditor(){
        return PostEditor.builder()
                .title(this.title)
                .contents(this.contents);
    }


    public void edit(PostEditor editor) {
        this.title = editor.getTitle();
        this.contents = editor.getContents();
    }

    public Long getUserId() {
        return user.getId();
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);

    }
}
