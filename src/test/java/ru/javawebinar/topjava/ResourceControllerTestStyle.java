package ru.javawebinar.topjava;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTestStyle extends AbstractControllerTest {
    @Test
    void getStyle() throws Exception {
        perform(get("/resource/css/style"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/css")));
    }

}
