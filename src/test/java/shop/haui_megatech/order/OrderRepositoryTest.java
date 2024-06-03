//package shop.haui_megatech.order;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//import shop.haui_megatech.domain.entity.Order;
//import shop.haui_megatech.domain.entity.OrderDetail;
//import shop.haui_megatech.domain.entity.Product;
//import shop.haui_megatech.domain.entity.User;
//import shop.haui_megatech.domain.entity.enums.PaymentMethod;
//import shop.haui_megatech.repository.OrderRepository;
//import shop.haui_megatech.repository.ProductRepository;
//import shop.haui_megatech.repository.UserRepository;
//
//import java.util.List;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class OrderRepositoryTest {
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private UserRepository    userRepository;
//    @Autowired
//    private TestEntityManager entityManager;
//    @Autowired
//    private OrderRepository   orderRepository;
//
//    @Test
//    public void testAddNewOrder() {
//        Product product = entityManager.find(Product.class, 2);
//        User user = entityManager.find(User.class, 1);
//
//        Order mainOrder = new Order();
//        mainOrder.setUser(user);
//        mainOrder.setTotal(200);
//        mainOrder.setPaymentMethod(PaymentMethod.COD);
//
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setProduct(product);
//        orderDetail.setOrder(mainOrder);
//        orderDetail.setQuantity(10);
//        orderDetail.setPrice(product.getCurrentPrice());
//
//        mainOrder.getOrderDetails().add(orderDetail);
//
//        orderRepository.save(mainOrder);
//    }
//
//    @Test
//    public void findUserById() {
//        User foundUser = userRepository.findById(1).get();
//        System.out.println(foundUser.toString());
//    }
//
//    @Test
//    public void pass() {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("thong"));
//    }
//
//    @Test
//    public void findOrderByAll() {
//        Pageable pageable = PageRequest.of(1, 3);
//        Page<Order> pageOrders = orderRepository.findByAll(pageable);
//        List<Order> orderList = pageOrders.getContent();
//        for (int i = 0; i < orderList.size(); i++)
//            System.out.println(orderList.get(i).getId());
//    }
//}
