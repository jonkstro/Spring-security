/* 
 * CLASSE DE CONFIGURAÇÃO.
 * Iremos usar essa classe para poder realizar o Seeding do banco de dados.
*/

package com.educandoweb.course.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.OrderItem;
import com.educandoweb.course.entities.Payment;
import com.educandoweb.course.entities.Product;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.OrderStatus;
import com.educandoweb.course.entities.enums.UserRoles;
import com.educandoweb.course.repositories.CategoryRepository;
import com.educandoweb.course.repositories.OrderItemRepository;
import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.repositories.ProductRepository;
import com.educandoweb.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Tudo que implementar nesse método será executado quando a aplicação for
        // iniciada by default

        User u1 = new User(null, "Maria Brow", "maria@gmail.com", "98888888888", "123456", UserRoles.ADMIN);
        User u2 = new User(null, "Jon Snow", "jon@gmail.com", "989999999999", "123456", UserRoles.USER);

        Order o1 = new Order(null, Instant.parse("2023-10-02T21:14:10Z"), u1, OrderStatus.PAID);
        Order o2 = new Order(null, Instant.parse("2023-11-06T22:14:10Z"), u2, OrderStatus.WAITING_PAYMENT);
        Order o3 = new Order(null, Instant.parse("2023-12-22T23:14:10Z"), u1, OrderStatus.WAITING_PAYMENT);

        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Computers");

        Product p1 = new Product(null, "Lord of the Rings", "Lorem ipsum dolor", 120.00, "");
        Product p2 = new Product(null, "Smart tv", "Lorem ipsum dolor", 220.00, "");
        Product p3 = new Product(null, "Acer nitro", "Lorem ipsum dolor", 180.00, "");
        Product p4 = new Product(null, "PC Gamer", "Lorem ipsum dolor", 150.00, "");
        Product p5 = new Product(null, "Xiaomi", "Lorem ipsum dolor", 125.00, "");

        // Instanciar os objetos by default
        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        // Adicionando as categorias
        p1.getCategories().add(cat3);
        p2.getCategories().add(cat1);
        p2.getCategories().add(cat3);
        p3.getCategories().add(cat3);
        p4.getCategories().add(cat3);
        p5.getCategories().add(cat2);
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());
        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

        Payment pay1 = new Payment(null, Instant.parse("2023-10-02T22:14:10Z"), o1);
        o1.setPayment(pay1);
        orderRepository.save(o1);




    }

}
