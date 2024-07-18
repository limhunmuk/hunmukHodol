package com.hunmuk.api.request;

import com.hunmuk.api.exception.InvaildRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void validate() {
        if(this.title.contains("바보")) {
            throw new InvaildRequest("title", "바보는 사용할 수 없습니다.");
        }
    }



}
