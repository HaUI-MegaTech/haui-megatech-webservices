package shop.haui_megatech.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.user.CreateUserRequestDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class UserRestControllerTest {
    @Autowired
    private UserRestController underTest;

//    private MockMvc mockMvc = new MockMvc();


    @Test
    @Disabled
    public void contextLoads() {
//        assertNotNull(underTest);
//        assertNotNull(mockMvc);
//        assertNotNull(objectMapper);
    }

    @Test
    @Disabled
    public void givenBlankUsername_whenCreateUser_thenReturn400BadRequest() throws Exception {
        // setup
        CreateUserRequestDTO request = CreateUserRequestDTO.builder()
                                                           .firstName("Hoang")
                                                           .lastName("Nguyen Viet")
                                                           .password("123")
                                                           .confirmPassword("123")
                                                           .build();
//        String requestBody = objectMapper.writeValueAsString(request);

        // run
//        mockMvc.perform(post(UrlConstant.User.CREATE_USER).contentType("application/json")
//                                                          .content(requestBody))
//               .andExpect(status().isBadRequest())
//                .andDo(print());

        // assert

    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Inside beforeAll() method");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Before each test");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("After each test");
    }

    @Test
    public void test1() {
        System.out.println("Test1");
    }

    @Test
    public void test2() {
        System.out.println("Test2");
    }

}