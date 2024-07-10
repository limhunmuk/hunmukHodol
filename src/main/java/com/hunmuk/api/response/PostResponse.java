package com.hunmuk.api.response;

import com.hunmuk.api.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String contents;

    //생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
    }

    @Builder
    public PostResponse(Long id, String title, String contents) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.contents = contents;
    }
}
