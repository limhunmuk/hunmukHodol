package com.hunmuk.api.response;

import lombok.Getter;

@Getter
public class SessionResponse {

    private String accessToken;

    public SessionResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
