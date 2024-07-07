package com.hunmuk.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String contents;


    @Builder
    public PostResponse(Long id, String title, String contents) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.contents = contents;
    }
}
