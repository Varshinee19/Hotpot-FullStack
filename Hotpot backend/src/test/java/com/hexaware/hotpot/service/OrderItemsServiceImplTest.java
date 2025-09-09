package com.hexaware.hotpot.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotpot.dto.OrderItemsDto;
import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.entities.CartItems;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entities.Menu;
import com.hexaware.hotpot.entities.MenuCategories;
import com.hexaware.hotpot.entities.OrderItems;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.entities.Restaurant;
import com.hexaware.hotpot.repository.CartItemsRepository;
import com.hexaware.hotpot.repository.CartRepository;
import com.hexaware.hotpot.repository.CustomerRepository;
import com.hexaware.hotpot.repository.MenuCategoriesRepository;
import com.hexaware.hotpot.repository.MenuRepository;
import com.hexaware.hotpot.repository.OrderItemsRepository;
import com.hexaware.hotpot.repository.OrdersRepository;
import com.hexaware.hotpot.repository.RestaurantRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class OrderItemsServiceImplTest {

    @Autowired
    private IOrderItemService orderItemsService;

    @Autowired
    private OrdersRepository orderRepo;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private OrderItemsRepository repo;

    @Autowired
    private CustomerRepository custRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemsRepository cartItemsRepo;

    @Autowired
    private RestaurantRepository rRepo;

    @Autowired
    private MenuCategoriesRepository catRepo;

    private Orders setupOrder() throws Exception {
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

        // ----------------- Create Order -----------------
        Orders order = new Orders();
        order.setCustomer(customer);
        order.setDeliveryAddress("123 Delivery St");
        order.setStatus("Pending");
        order.setRestaurant(restaurant);

        // Initialize orderItems list before saving
        order.setOrderItems(new ArrayList<>());

        // Add OrderItem
        OrderItems item = new OrderItems();
        item.setItemName(menu.getItemName());
        item.setQuantity(cartItem.getQuantity());
        item.setPrice(menu.getPrice());
        item.setOrder(order);
        item.setMenu(menu);

        order.getOrderItems().add(item);

        
        double total = order.getOrderItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        order.setTotalPrice(total);

        
        orderRepo.save(order);
        repo.save(item); 

        return order;
    }

    @Test
    void testAddItem() throws Exception {
        Orders order = setupOrder();

        Menu menu = order.getOrderItems().get(0).getMenu(); // reuse menu

        OrderItemsDto dto = new OrderItemsDto();
        dto.setMenuId(menu.getMenuId());
        dto.setItemName("Burger");
        dto.setQuantity(3);
        dto.setPrice(120);

        OrderItems added = orderItemsService.addItem(order.getOrderId(), dto);

        Assertions.assertNotNull(added.getOrderItemId());
        Assertions.assertEquals("Burger", added.getItemName());

        Orders updatedOrder = orderRepo.findById(order.getOrderId()).get();
        Assertions.assertEquals(2, updatedOrder.getOrderItems().size());
        Assertions.assertEquals(120*3 + 100*2, updatedOrder.getTotalPrice());
    }

    @Test
    void testGetItemsByOrder() throws Exception {
        Orders order = setupOrder();

        List<OrderItems> items = orderItemsService.getItemsByOrder(order.getOrderId());
        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals("Burger", items.get(0).getItemName());
    }

    @Test
    void testUpdateItem() throws Exception {
        Orders order = setupOrder();
        OrderItems item = order.getOrderItems().get(0);

        OrderItemsDto dto = new OrderItemsDto();
        dto.setItemName("Cheese Burger");
        dto.setQuantity(5);
        dto.setPrice(150);

        OrderItems updated = orderItemsService.updateItem(item.getOrderItemId(), dto);

        Assertions.assertEquals("Cheese Burger", updated.getItemName());
        Assertions.assertEquals(5, updated.getQuantity());
        Assertions.assertEquals(150, updated.getPrice());

        Orders updatedOrder = orderRepo.findById(order.getOrderId()).get();
        Assertions.assertEquals(150*5, updatedOrder.getTotalPrice());
    }

    @Test
    void testGetById() throws Exception {
        Orders order = setupOrder();
        OrderItems item = order.getOrderItems().get(0);

        OrderItems found = orderItemsService.getById(item.getOrderItemId());
        Assertions.assertEquals(item.getOrderItemId(), found.getOrderItemId());
    }

    @Test
    void testRemoveItem() throws Exception {
        Orders order = setupOrder();
        OrderItems item = order.getOrderItems().get(0);

        String result = orderItemsService.removeItem(item.getOrderItemId());
        Assertions.assertEquals("Deleted successfully", result);

        Orders updatedOrder = orderRepo.findById(order.getOrderId()).get();
        Assertions.assertEquals(0, updatedOrder.getOrderItems().size());
        Assertions.assertEquals(0, updatedOrder.getTotalPrice());
    }
}
