package com.hunmuk.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.config.HunmukMockUser;
import com.hunmuk.api.domain.Post;
import com.hunmuk.api.repository.post.PostRepository;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.post.PostCreate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "hunmuk.api.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

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

    @AfterEach
    void clean(){
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @HunmukMockUser
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
    @HunmukMockUser
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


