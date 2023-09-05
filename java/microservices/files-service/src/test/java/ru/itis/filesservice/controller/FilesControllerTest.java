package ru.itis.filesservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.filesservice.dao.mongo.BlackListRepository;
import ru.itis.filesservice.services.FilesService;

import javax.annotation.Resource;
import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilesService.class)
@DisplayName("FilesController is working when ...")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FilesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilesService filesService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BlackListRepository blackListRepository;

    private File defaultsFile() {
        return new File("file.mp3");
    }

    private File nullFile() {
        return null;
    }

    private final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2YTdlNTg0Zi1mMzg5LTRkZDMtOGU5Yy1mOGU2NTc4MDE3YjYiLCJyb2xlIjoiVVNFUiIsInJlZnJlc2giOiIxMTBlNmI1Ni04NjdhLTQwZjUtYjhiMC01ZTYwNWUxY2JjYzMiLCJzdGF0ZSI6Ik5PVF9DT05GSVJNRUQiLCJleHAiOjE2NTc2NTc4ODMsImVtYWlsIjoidXNlckBtYWlsLmNvbSJ9.bKHY6gynOttbCe7kLzzIwZvH04nnN_oKcXzMqvpkrSk";

    @Test
    public void return_403_without_token() throws Exception {
        mockMvc.perform(get("/users/44487d3a-03bf-11ed-b939-0242ac120002/download"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void return_404_when_user_not_found_by_id() throws Exception {
        mockMvc.perform(get("users/44487d3a-03bf-11ed-b939-0242ac120002/download")
                        .header("Authorization", token))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    public void return_404_when_product_not_found_by_id() throws Exception {
        mockMvc.perform(get("products/44487d3a-03bf-11ed-b939-0242ac120002/download")
                        .header("Authorization", token))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    public void return_471_when_type_of_file_is_wrong() throws Exception {
        mockMvc.perform(post("users/24e55544-67c5-43f0-bf2c-3b1a2e5a3fe3/upload")
                        .header("Authorization", token)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(String.valueOf(defaultsFile())))
                .andExpect(status().is(471))
                .andDo(print());
    }

    @Test
    public void return_470_when_no_file_to_upload() throws Exception {
        mockMvc.perform(post("users/24e55544-67c5-43f0-bf2c-3b1a2e5a3fe3/upload")
                        .header("Authorization", token)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(String.valueOf(nullFile())))
                .andExpect(status().is(470))
                .andDo(print());
    }

    @Test
    public void return_470_when_no_file_to_download() throws Exception {
        mockMvc.perform(get("users/87e55544-56c5-43f0-bf2c-6b1a2e5a3fe5/download")
                        .header("Authorization", token))
                .andExpect(status().is(470))
                .andDo(print());
    }
}

