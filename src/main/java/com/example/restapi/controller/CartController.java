package com.example.restapi.controller;

import com.example.restapi.entities.*;
import com.example.restapi.repository.IDiscountRepository;
import com.example.restapi.repository.IOrderRepository;
import com.example.restapi.repository.IOrdersDetailRepository;
import com.example.restapi.repository.IProductRepository;
import com.example.restapi.responseobject.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/home")
public class CartController {
    @Autowired
    private HttpSession session;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private Order order;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IDiscountRepository discountRepository;

    @Autowired
    private IOrdersDetailRepository ordersDetailRepository;

    //GET http://localhost:8080/api/home/showCart
    @GetMapping("/showCart")
    public ResponseEntity<ResponseObject> showCart() {
        Order order = (Order) session.getAttribute("order");
        return order == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "There are no products in the cart", "")) : ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Show Cart successfully", order.getOrderdetails()));
    }

    //POST http://localhost:8080/api/home/addToCart
    @PostMapping("/addToCart/{id}")
    public ResponseEntity<ResponseObject> addToCart(@PathVariable Integer id, @RequestParam(name = "quantity", defaultValue = "1", required = false) Integer quantity) {
        Optional<Product> pro = productRepository.findById(id);
        if (!pro.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find product to add Cart", session.getAttribute("order")));
        }
        Product product = pro.get();
        if (session.getAttribute("order") == null) {
            Order order = new Order();
            OrdersDetail ordersDetail = new OrdersDetail();
            ordersDetail.setProduct(product);
            ordersDetail.setQuantity(quantity);
            ordersDetail.setPrice(product.getPrice());
            List<OrdersDetail> list = new ArrayList<>();
            list.add(ordersDetail);
            order.setOrderdetails(list);
            session.setAttribute("order", order);
            System.out.println(order.getOrderdetails() + "ngu");
        } else {
            Order order = (Order) session.getAttribute("order");
            List<OrdersDetail> list = order.getOrderdetails();
            boolean check = false;
            for (OrdersDetail ordersDetail : list) {
                if (ordersDetail.getProduct().getId() == product.getId()) {
                    ordersDetail.setQuantity(ordersDetail.getQuantity() + quantity);
                    check = true;
                }
                if (ordersDetail.getQuantity() > productRepository.findById(ordersDetail.getProduct().getId()).get().getQuantity()) {
                    ordersDetail.setQuantity(ordersDetail.getProduct().getQuantity());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Product has reached maximum quantity", session.getAttribute("order")));
                }
            }
            if (check == false) {
                OrdersDetail ordersDetail = new OrdersDetail();
                ordersDetail.setProduct(product);
                ordersDetail.setQuantity(quantity);
                ordersDetail.setPrice(product.getPrice());
                list.add(ordersDetail);
                order.setOrderdetails(list);
            }
            session.setAttribute("order", order);
            System.out.println(order.getOrderdetails() + "ngu2");
        }
        Order order = (Order) session.getAttribute("order");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Add to cart successfully", order.getOrderdetails()));
    }

    //POST http://localhost:8080/api/home/addToOrder
    @PostMapping("addToOrder")
    public ResponseEntity<ResponseObject> addToOrder(@RequestBody Order newOrder) {
        Order orderSession = (Order) session.getAttribute("order");
        if (orderSession != null) {
//            User user = (User) session.getAttribute("user");
            List<OrdersDetail> listOrder = orderSession.getOrderdetails();
            List<Product> productList = productRepository.findAll();
            List<Discount> discountList = discountRepository.findAll();
            order.setUserOrder(null);
            order.setAddress(newOrder.getAddress());
            order.setPhone(newOrder.getPhone());
            order.setRecipientName(newOrder.getRecipientName());
            order.setOrderdetails(listOrder);
            order.setCreateDate(new Date());
            order.setStatus(1);
            BigDecimal total = BigDecimal.valueOf(0);
            for (Product i : productList) {
                for (OrdersDetail item : listOrder) {
                    if (i.getId() == item.getProduct().getId()) {
                        item.setProduct(i);
                        BigDecimal q = BigDecimal.valueOf(item.getQuantity());
                        total = total.add(i.getPrice().multiply(q));
                    }
                }
            }
            for (Discount discount : discountList) {
                if (newOrder.getDiscount().getCode().equals(discount.getCode())) {
                    double percent = Double.valueOf(discount.getReduce()) / 100;
                    total = total.subtract(total.multiply(BigDecimal.valueOf(percent)));
                    break;
                }
            }
            order.setDiscount(newOrder.getDiscount());
            order.setTotal(total);
            try {
                this.orderRepository.save(order);
                for (OrdersDetail item : listOrder) {
                    OrdersDetail orderDetail = new OrdersDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setQuantity(item.getQuantity());
                    orderDetail.setPrice(item.getPrice());
                    orderDetail.setProduct(item.getProduct());
                    this.ordersDetailRepository.save(orderDetail);
                    for (Product i : productList) {
                        if (item.getProduct().getId() == i.getId()) {
                            i.setQuantity(i.getQuantity() - item.getQuantity());
                            this.productRepository.save(i);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.removeAttribute("order");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Add to order successfully", orderRepository.findAll()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "There are no products in the shopping cart for payment", ""));
        }
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<ResponseObject> removeCart(@PathVariable Integer id) {
        Order order = (Order) session.getAttribute("order");
        if (order != null) {
            List<OrdersDetail> listOrder = order.getOrderdetails();
            for (OrdersDetail item : listOrder) {
                if (item.getProduct().getId() == id) {
                    listOrder.remove(item);
                    break;
                }
            }
            if (listOrder.isEmpty()) {
                session.removeAttribute("order");
            }
            session.setAttribute("order", order);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Remove to cart successfully", order.getOrderdetails()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "There are no products in the shopping cart for Remove", ""));
        }
    }
}
