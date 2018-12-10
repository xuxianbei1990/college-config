package college.simple;

import college.DTO.SimpleDTO;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-06
 * VersionV1.0
 * @description
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SimpleController.class)
@WebMvcTest
public class SimpleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test2() throws Exception {
        SimpleDTO simpleDTO = new SimpleDTO();
        simpleDTO.setName("1");
        simpleDTO.setId(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(simpleDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    public void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home").param("name", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("you are nobody..."));
        mockMvc.perform(MockMvcRequestBuilders.get("/home").param("name", "pengjunlee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("This is pengjunlee's home..."));
    }
}