package com.hexaware.hotpot.service;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotpot.dto.PaymentDto;
import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.entities.CartItems;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entities.Menu;
import com.hexaware.hotpot.entities.MenuCategories;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.entities.Payment;
import com.hexaware.hotpot.entities.Restaurant;
import com.hexaware.hotpot.repository.CartItemsRepository;
import com.hexaware.hotpot.repository.CartRepository;
import com.hexaware.hotpot.repository.CustomerRepository;
import com.hexaware.hotpot.repository.MenuCategoriesRepository;
import com.hexaware.hotpot.repository.MenuRepository;
import com.hexaware.hotpot.repository.OrdersRepository;
import com.hexaware.hotpot.repository.RestaurantRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class PaymentImplTest {
	@Autowired
	IPaymentService paymentService;
	@Autowired
    private OrdersRepository orderRepo;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private MenuCategoriesRepository catRepo;

    @Autowired
    private RestaurantRepository rRepo;

    @Autowired
    private CustomerRepository custRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemsRepository cartItemsRepo;

    private Orders setupPaymentOrder() throws Exception {
        MenuCategories category = new MenuCategories();
        category.setCategoryName("Lunch");
        catRepo.save(category);

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName("Test Resto");
        restaurant.setRestaurantAddress("123 St");
        restaurant.setPhoneNo("1234567890");
        rRepo.save(restaurant);

        Menu menu = new Menu();
        menu.setItemName("Burger");
        menu.setPrice(100);
        menu.setRestaurant(restaurant);
        menu.setMenuCategory(category);
        menuRepo.save(menu);

        Customer customer = new Customer();
        customer.setName("John Doe");
        custRepo.save(customer);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cartRepo.save(cart);
        customer.setCart(cart);
        custRepo.save(customer);

        CartItems cartItem = new CartItems();
        cartItem.setCart(cart);
        cartItem.setMenu(menu);
        cartItem.setQuantity(2);
        cartItemsRepo.save(cartItem);

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setDeliveryAddress("123 Delivery St");
        order.setStatus("Pending");
        order.setRestaurant(restaurant);
        order.setOrderItems(new ArrayList<>());

        // Add OrderItem
        com.hexaware.hotpot.entities.OrderItems item = new com.hexaware.hotpot.entities.OrderItems();
        item.setItemName(menu.getItemName());
        item.setQuantity(cartItem.getQuantity());
        item.setPrice(menu.getPrice());
        item.setOrder(order);
        item.setMenu(menu);

        order.getOrderItems().add(item);

        double total = order.getOrderItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalPrice(total);

        orderRepo.save(order);

        return order;
    }

 
    @Test
    void testProcessPaymentSuccess() throws Exception {
        Orders order = setupPaymentOrder();

        PaymentDto dto = new PaymentDto();
        dto.setAmount(order.getTotalPrice());
        dto.setMode("Card");

        Payment payment = paymentService.processPayment(order.getOrderId(), dto);

        Assertions.assertNotNull(payment.getPaymentid());
        Assertions.assertEquals("success", payment.getStatus());
        Assertions.assertEquals(order.getOrderId(), payment.getOrder().getOrderId());
    }

 
    @Test
    void testGetPaymentById() throws Exception {
        Orders order = setupPaymentOrder();

        PaymentDto dto = new PaymentDto();
        dto.setAmount(order.getTotalPrice());
        dto.setMode("Card");

        Payment payment = paymentService.processPayment(order.getOrderId(), dto);

        Payment found = paymentService.getPaymentById(payment.getPaymentid());
        Assertions.assertEquals(payment.getPaymentid(), found.getPaymentid());
    }

    @Test
    void testGetPaymentByOrder() throws Exception {
        Orders order = setupPaymentOrder();

        PaymentDto dto = new PaymentDto();
        dto.setAmount(order.getTotalPrice());
        dto.setMode("Card");

        Payment payment = paymentService.processPayment(order.getOrderId(), dto);

        Payment found = paymentService.getPaymentByOrder(order.getOrderId());
        Assertions.assertEquals(payment.getPaymentid(), found.getPaymentid());
    }
}

	


