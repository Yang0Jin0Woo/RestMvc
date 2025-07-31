package com.example.sshrestapi.pageable;

import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberPageableTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        for (int i = 1; i <= 25; i++) {
            memberRepository.save(new Member("Name" + i, "email" + i + "@example.com"));
        }
    }

    @Test
    void defaultPagination_shouldReturnFirstPageWithDefaultSize() throws Exception {
        mockMvc.perform(get("/api/members/page")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)))           // 기본 페이지 크기 = 10
                .andExpect(jsonPath("$.totalElements", is(25)))    // 데이터 수
                .andExpect(jsonPath("$.totalPages", is(3)))        // 총 3 페이지 (0~2)
                .andExpect(jsonPath("$.number", is(0)));           // 기본 페이지 번호 = 0
    }

    @Test
    void customPagination_shouldReturnRequestedPageAndSize() throws Exception {
        mockMvc.perform(get("/api/members/page")
                        .param("page", "2")
                        .param("size", "7")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(7)))                     // 요청한 페이지 크기 = 7
                .andExpect(jsonPath("$.totalElements", is(25)))
                .andExpect(jsonPath("$.totalPages", is(4)))                 // 총 4 페이지 (0~3)
                .andExpect(jsonPath("$.number", is(2)))                     // 요청한 페이지 = 2
                .andExpect(jsonPath("$.content[0].name", is("Name15")));    // 페이지2 첫 항목
    }
}
