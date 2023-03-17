package com.thangle.api.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thangle.common.ResponseDTO;
import com.thangle.common.UpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public ResultActions get(final String path) throws Exception {
        return resultActions(MockMvcRequestBuilders.get(path));
    }

    public ResultActions post(final String path, final ResponseDTO responseDTO) throws Exception {
        final String requestBody = mapper.writeValueAsString(responseDTO);
        return resultActions(MockMvcRequestBuilders.post(path).content(requestBody));
    }

    public ResultActions put(final String path, final UpdateDTO updateDTO) throws Exception {
        final String requestBody = mapper.writeValueAsString(updateDTO);
        return resultActions(MockMvcRequestBuilders.put(path).content(requestBody));
    }

    public ResultActions delete(final String path) throws Exception {
        return resultActions(MockMvcRequestBuilders.delete(path));
    }

    public ResultActions resultActions(final MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON));
    }
}
