//package shop.haui_megatech.domain.mapper;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import shop.haui_megatech.domain.dto.user.UserDTO;
//import shop.haui_megatech.domain.entity.User;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@SpringBootTest
//class UserMapperTest {
//    @Autowired
//    private UserMapper mapper;
//
//    @Test
//    public void givenUser_whenMapToUserDTO_thenReturnUserDTO() {
//        // setup
//        User givenUser = User.builder()
//                             .id(1)
//                             .username("viethoang")
//                             .firstName("Hoang")
//                             .lastName("Nguyen Viet")
//                             .avatar("avatar_link1")
//                             .email("viethoang@gmail.com")
//                             .phoneNumber("0123456789")
//                             .build();
//
//        // run
//        UserDTO expected = mapper.toUserDTO(givenUser);
//
//        // assert
//        assertEquals(expected.id(), givenUser.getId());
//        assertEquals(expected.avatarImageUrl(), givenUser.getAvatar());
//        assertEquals(expected.email(), givenUser.getEmail());
//        assertEquals(expected.firstName(), givenUser.getFirstName());
//        assertEquals(expected.lastName(), givenUser.getLastName());
//        assertEquals(expected.phoneNumber(), givenUser.getPhoneNumber());
//    }
//
//    @Test
//    public void givenNullUser_whenMapToUserDTO_thenReturnNullUserDTO() {
//        // run
//        UserDTO expected = mapper.toUserDTO(null);
//
//
//        // assert
//        assertNull(expected);
//    }
//
//    @Test
//    public void givenUserDTO_whenMapToUser_thenReturnUser() {
//        // setup
//        UserDTO dto = UserDTO.builder()
//                             .id(1)
//                             .username("viethoang")
//                             .firstName("Hoang")
//                             .lastName("Nguyen Viet")
//                             .avatar("LinkImage1.png")
//                             .email("viethoang@gmail.com")
//                             .phoneNumber("01234567899")
//                             .build();
//
//        // run
//        User expected = mapper.toUser(dto);
//
//        // assert
//        assertEquals(expected.getUsername(), dto.username());
//        assertEquals(expected.getFirstName(), dto.firstName());
//        assertEquals(expected.getLastName(), dto.lastName());
//        assertEquals(expected.getAvatar(), dto.avatarImageUrl());
//        assertEquals(expected.getEmail(), dto.email());
//        assertEquals(expected.getPhoneNumber(), dto.phoneNumber());
//    }
//
//    @Test
//    public void givenNullUserDTO_whenMapToUser_thenReturnNull() {
//        // run
//        User expected = mapper.toUser(null);
//
//        // assert
//        assertNull(expected);
//    }
//}