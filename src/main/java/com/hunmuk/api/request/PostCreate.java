package com.hunmuk.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class PostCreate {

    @Builder
    public PostCreate(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @NotBlank(message = "title은 필수입니다.")
    String title;

    @NotBlank(message = "contents는 필수입니다.")
    String contents;



}
