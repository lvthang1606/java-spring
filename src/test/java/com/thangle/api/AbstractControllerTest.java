package com.thangle.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    protected ResultActions get(final String path) throws Exception {
        return performRequestBuilder(MockMvcRequestBuilders.get(path));
    }

    protected ResultActions post(final String path, final Object body) throws Exception {
        final String requestBody = mapper.writeValueAsString(body);
        return performRequestBuilder(MockMvcRequestBuilders.post(path).content(requestBody));
    }

    protected ResultActions put(final String path, final Object body) throws Exception {
        final String requestBody = mapper.writeValueAsString(body);
        return performRequestBuilder(MockMvcRequestBuilders.put(path).content(requestBody));
    }

    protected ResultActions delete(final String path) throws Exception {
        return performRequestBuilder(MockMvcRequestBuilders.delete(path));
    }

    private ResultActions performRequestBuilder(final MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON));
    }
}
