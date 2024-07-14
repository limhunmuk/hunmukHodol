package com.hunmuk.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PostEdit {

    @NotBlank(message = "title은 필수입니다.")
    String title;

    @NotBlank(message = "contents는 필수입니다.")
    String contents;

    @Builder
    public PostEdit(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
