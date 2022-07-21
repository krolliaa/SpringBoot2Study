package com.kk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebTest {
    @Test
    void test(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books1");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        StatusResultMatchers statusResultMatchers = MockMvcResultMatchers.status();
        ResultMatcher ok = statusResultMatchers.isOk();
        System.out.println(resultActions.andExpect(ok));
    }

    @Test
    void testStatus(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books1");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        StatusResultMatchers statusResultMatchers = MockMvcResultMatchers.status();
        ResultMatcher ok = statusResultMatchers.isOk();
        System.out.println(resultActions.andExpect(ok));
    }

    @Test
    void testContent(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        ContentResultMatchers contentResultMatchers = MockMvcResultMatchers.content();
        ResultMatcher content = contentResultMatchers.string("SpringBoot");
        resultActions.andExpect(content);
    }

    @Test
    void testHeader(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/books");
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        HeaderResultMatchers headerResultMatchers = MockMvcResultMatchers.header();
        ResultMatcher resultMatcher = headerResultMatchers.string("characterSet", "aaa");
        resultActions.andExpect(resultMatcher);
    }
}
