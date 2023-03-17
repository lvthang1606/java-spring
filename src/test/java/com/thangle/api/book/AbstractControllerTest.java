package com.thangle.api.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public ResultActions get(final String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(path).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions post(final String path, final BookResponseDTO bookResponseDTO) throws Exception {
        final String requestBody = mapper.writeValueAsString(bookResponseDTO);
        return mockMvc.perform(MockMvcRequestBuilders.post(path).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }

    public ResultActions put(final String path, final BookChangeDTO bookChangeDTO) throws Exception {
        final String requestBody = mapper.writeValueAsString(bookChangeDTO);
        return mockMvc.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }

    public ResultActions delete(final String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(path).contentType(MediaType.APPLICATION_JSON));
    }
}
