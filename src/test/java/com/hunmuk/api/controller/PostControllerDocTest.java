package com.hunmuk.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.domain.Post;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "hunmuk.api.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

/*
    @Autowired
    private WebApplicationContext context;
*/


 /*   @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }*/


    @Test
    @DisplayName("글 단건 조회")
    void test1() throws Exception {

        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .contents("내용입니다.")
                .build();

        postRepository.save(post);

        //expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-inquiry",
                        pathParameters(
                                parameterWithName("postId").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 아이디"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("contents").description("내용")
                        )
                ));

    }

    @Test
    @DisplayName("글 등록")
    void test2() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .contents("내용")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(request);
        //System.out.println("json = " + json);

        //expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").description("제목").optional(),
                                fieldWithPath("contents").description("내용")
                                        .attributes(Attributes.key("constraints").value("최대 300자"))
                        )
                ));

    }
}


