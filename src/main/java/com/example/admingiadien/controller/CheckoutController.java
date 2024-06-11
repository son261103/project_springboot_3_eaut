package com.example.admingiadien.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CheckoutController {

    @PostMapping("/checkout")
    public OrderResponse checkout(@RequestBody CartRequest cartRequest) {
        // Xử lý đơn hàng và tính toán tổng tiền
        double total = cartRequest.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Trả về thông tin đơn hàng
        OrderResponse response = new OrderResponse(total, cartRequest.getItems());
        return response;
    }

    // Định nghĩa lớp CartRequest
    public static class CartRequest {
        private List<CartItem> items;

        // Getters and Setters
        public List<CartItem> getItems() {
            return items;
        }

        public void setItems(List<CartItem> items) {
            this.items = items;
        }
    }

    // Định nghĩa lớp CartItem
    public static class CartItem {
        private String name;
        private double price;
        private int quantity;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // Định nghĩa lớp OrderResponse
    public static class OrderResponse {
        private double total;
        private List<CartItem> items;

        public OrderResponse(double total, List<CartItem> items) {
            this.total = total;
            this.items = items;
        }

        // Getters
        public double getTotal() {
            return total;
        }

        public List<CartItem> getItems() {
            return items;
        }

        // Setters
        public void setTotal(double total) {
            this.total = total;
        }

        public void setItems(List<CartItem> items) {
            this.items = items;
        }
    }
}
