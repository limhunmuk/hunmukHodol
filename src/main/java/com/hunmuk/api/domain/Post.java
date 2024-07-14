package com.hunmuk.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Builder
    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

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
}
