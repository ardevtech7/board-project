package com.ardevtech.board.controller;

import com.ardevtech.board.config.TestSecurityConfig;
import com.ardevtech.board.service.ArticleService;
import com.ardevtech.board.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestComponent;
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
@WebMvcTest(AuthControllerTest.EmptyController.class)
class AuthControllerTest {
    private final MockMvc mvc;

    @Autowired
    AuthControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @MockBean
    private ArticleService articleService;

    @MockBean
    private PaginationService paginationService;

    @DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
    @Test
    void givenNothing_whenTryingToLogIn_thenReturnsLogInView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 어떤 컨트롤러도 필요하지 않은 테스트임을 나타내기 위해 테스트용 빈 컨포넌트를 사용함
     */
    @TestComponent
    static class EmptyController{}
}
