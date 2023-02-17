package com.ardevtech.board.controller;

import com.ardevtech.board.config.TestSecurityConfig;
import com.ardevtech.board.service.ArticleService;
import com.ardevtech.board.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View Controller - 인증")
@Import(TestSecurityConfig.class)
@WebMvcTest(Void.class)
public class AuthControllerTest {
    private final MockMvc mvc;

    @Autowired
    public AuthControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @MockBean
    private ArticleService articleService;

    @MockBean
    private PaginationService paginationService;

    @DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenTryingToLogIn_thenReturnsLogInView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andDo(MockMvcResultHandlers.print());
    }
}
