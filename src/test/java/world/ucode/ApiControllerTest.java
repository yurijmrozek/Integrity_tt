package world.ucode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import world.ucode.controller.ApiController;
import world.ucode.model.Words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Мои извинения. Мало опыта в тестировании, но я исправлюсь!
//Мне правда самому больно смотреть на это(

@WebMvcTest(ApiController.class)
public class ApiControllerTest {
    private final List<String> test1 = Arrays.asList("fish", "horse", "egg", "goose", "eagle");
    private final List<String> test2 = Arrays.asList("fish", "horse", "snake", "goose", "eagle");
    private final List<String> test3 = Arrays.asList("fish", "horse", "", "goose", "eagle");
    private final List<String> test4 = Arrays.asList("", "horse", "", "goose", "eagle");
    private final List<String> answerTo2_3 = test2.subList(0, 2);

    private MockMvc mockMvc;
    private Words words;
    private ObjectMapper objectMapper;
    private MockHttpServletRequestBuilder builder;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ApiController())
                .setControllerAdvice()
                .build();
        words = new Words();
        objectMapper = new ObjectMapper();
        builder = MockMvcRequestBuilders.post("/api")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }

    @Test
    public void setTest1() throws Exception {
        words.setWords(test1);

        builder.content(objectMapper.writeValueAsString(words));

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(words)));
    }

    @Test
    public void setTest2() throws  Exception {
        words.setWords(test2);

        builder.content(objectMapper.writeValueAsString(words));

        words.setWords(answerTo2_3);
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(words)));
    }

    @Test
    public void setTest3() throws Exception {
        words.setWords(test3);

        builder.content(objectMapper.writeValueAsString(words));

        words.setWords(answerTo2_3);
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(words)));
    }

    @Test
    public void setTest4() throws Exception {
        words.setWords(test4);

        builder.content(objectMapper.writeValueAsString(words));

        words.setWords(new ArrayList<>());
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(words)));
    }

}
