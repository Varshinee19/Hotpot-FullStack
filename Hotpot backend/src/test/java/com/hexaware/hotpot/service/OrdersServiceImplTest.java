package com.hexaware.hotpot.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotpot.dto.OrdersDto;
import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.entities.CartItems;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entities.Menu;
import com.hexaware.hotpot.entities.MenuCategories;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.entities.Restaurant;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;
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
class OrdersServiceImplTest {
	@Autowired
    private IOrderService orderService;

    @Autowired
    private CustomerRepository custRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemsRepository cartItemsRepo;

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private RestaurantRepository rRepo;

    @Autowired
    private MenuCategoriesRepository catRepo;

    private Customer customer;
    private Cart cart;
    private Restaurant restaurant;
    private MenuCategories category;
    private Menu menuItem;

    @BeforeEach
    void setUp() {
    	category = new MenuCategories();
        category.setCategoryName("Lunch");
        catRepo.save(category);

       
        restaurant = new Restaurant();
        restaurant.setRestaurantName("Test Restaurant");
        rRepo.save(restaurant);

        
        menuItem = new Menu();
        menuItem.setItemName("Burger");
        menuItem.setType("Veg");
        menuItem.setPrice(150);
        menuItem.setRestaurant(restaurant);
        menuItem.setMenuCategory(category);
        menuRepo.save(menuItem);

        
        customer = new Customer();
        customer.setName("John Doe");
        custRepo.save(customer);

        
        cart = new Cart();
        cart.setCustomer(customer);
        cartRepo.save(cart);

       
        customer.setCart(cart);
        custRepo.save(customer);

        CartItems cartItem = new CartItems();
        cartItem.setCart(cart);         
        cartItem.setMenu(menuItem);
        cartItem.setQuantity(2);
        cartItemsRepo.save(cartItem);
        cartItemsRepo.flush();   
    }
    @Test
    void testPlaceOrder() throws RuntimeException {
        OrdersDto dto = new OrdersDto();
        dto.setDeliveryAddress("123 Street");
        dto.setStatus("Pending");

        Orders order = orderService.placeOrder(customer.getCustomerId(), dto);

        Assertions.assertNotNull(order.getOrderId());
        Assertions.assertEquals(customer.getCustomerId(), order.getCustomer().getCustomerId());
        Assertions.assertEquals(300, order.getTotalPrice()); 
        Assertions.assertEquals("Pending", order.getStatus());
        Assertions.assertEquals(restaurant.getRestaurantId(), order.getRestaurant().getRestaurantId());
    }

    @Test
    void testUpdateOrder() throws OrderNotExistException {
        OrdersDto dto = new OrdersDto();
        dto.setDeliveryAddress("123 Street");
        dto.setStatus("Pending");

        Orders order = orderService.placeOrder(customer.getCustomerId(), dto);

        OrdersDto updateDto = new OrdersDto();
        updateDto.setDeliveryAddress("456 Avenue");
        updateDto.setStatus("Delivered");
        updateDto.setTotalPrice(300);

        Orders updated = orderService.updateOrder(order.getOrderId(), updateDto);

        Assertions.assertEquals("456 Avenue", updated.getDeliveryaddress());
        Assertions.assertEquals("Delivered", updated.getStatus());
        Assertions.assertEquals(300, updated.getTotalPrice());
    }

    @Test
    void testGetOrdersByCustomer() throws CustomerNotFoundException, RuntimeException {
        OrdersDto dto = new OrdersDto();
        dto.setDeliveryAddress("123 Street");
        dto.setStatus("Pending");
        orderService.placeOrder(customer.getCustomerId(), dto);

        List<Orders> orders = orderService.getOrdersByCustomer(customer.getCustomerId());
        Assertions.assertEquals(1, orders.size());
    }

    @Test
    void testGetOrdersByRestaurant() throws RestaurantNotFoundException, RuntimeException {
        OrdersDto dto = new OrdersDto();
        dto.setDeliveryAddress("123 Street");
        dto.setStatus("Pending");
        orderService.placeOrder(customer.getCustomerId(), dto);

        List<Orders> orders = orderService.getOrdersByRestaurant(restaurant.getRestaurantId());
        Assertions.assertEquals(1, orders.size());
    }

    @Test
    void testUpdateOrderStatus() throws OrderNotExistException, RuntimeException {
        OrdersDto dto = new OrdersDto();
        dto.setDeliveryAddress("123 Street");
        dto.setStatus("Pending");

        Orders order = orderService.placeOrder(customer.getCustomerId(), dto);

        String result = orderService.updateOrderStatus("Delivered", order.getOrderId());
        Assertions.assertEquals("Updated successfully", result);

        Orders updated = orderService.getById(order.getOrderId());
        Assertions.assertEquals("Delivered", updated.getStatus());
    }

    @Test
    void testCancelOrder() throws OrderNotExistException, RuntimeException {
        OrdersDto dto = new OrdersDto();
        dto.setDeliveryAddress("123 Street");
        dto.setStatus("Pending");

        Orders order = orderService.placeOrder(customer.getCustomerId(), dto);
        String result = orderService.cancelOrder(order.getOrderId());

        Assertions.assertEquals("Order cancelled", result);
        Assertions.assertTrue(ordersRepo.findById(order.getOrderId()).isEmpty());
    }

    @Test
    void testGetById() throws OrderNotExistException, RuntimeException {
        OrdersDto dto = new OrdersDto();
        dto.setDeliveryAddress("123 Street");
        dto.setStatus("Pending");

        Orders order = orderService.placeOrder(customer.getCustomerId(), dto);
        Orders found = orderService.getById(order.getOrderId());

        Assertions.assertEquals(order.getOrderId(), found.getOrderId());
    }

    @Test
    void testGetAll() throws RuntimeException {
        OrdersDto dto1 = new OrdersDto();
        dto1.setDeliveryAddress("123 Street");
        dto1.setStatus("Pending");
        orderService.placeOrder(customer.getCustomerId(), dto1);

        OrdersDto dto2 = new OrdersDto();
        dto2.setDeliveryAddress("456 Avenue");
        dto2.setStatus("Pending");
        orderService.placeOrder(customer.getCustomerId(), dto2);

        List<Orders> allOrders = orderService.getAll();
        Assertions.assertEquals(2, allOrders.size());
    }
	}


