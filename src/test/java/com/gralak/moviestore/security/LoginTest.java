package com.gralak.moviestore.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties(value = JwtUtility.class)
@TestPropertySource("classpath:application.properties")
public class LoginTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoginAndGetJWT() throws Exception
    {
        MvcResult login = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("username=user&password=user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String token = login.getResponse().getHeader("access_token");

        mockMvc.perform(get("/api/movie/all")
                .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        mockMvc.perform(get("/api/movie/all"))
                .andDo(print())
                .andExpect(status().is(403));

        mockMvc.perform(get("/api/movie/all")
                .header("Authorization", token + "a"))
                .andDo(print())
                .andExpect(status().is(403));
    }
}
