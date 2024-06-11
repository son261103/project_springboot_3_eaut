package com.example.admingiadien.controller;

import com.example.admingiadien.Entity.Products;
import com.example.admingiadien.Service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    // Xử lý yêu cầu Ajax để thêm sản phẩm vào giỏ hàng
    @PostMapping("/users/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody Products product) {
        // Thực hiện logic để thêm sản phẩm vào giỏ hàng
        cartService.addToCart(product);
        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng!");
    }

    @GetMapping("/users/checkout")
    public String showCheckoutPage() {
        // Trả về tên của view để hiển thị trang thanh toán
        return "Users/pages/checkout"; // Kiểm tra xem đường dẫn này có chính xác không
    }

    @PostMapping("/users/processCheckout")
    public String processCheckout(@RequestParam String cartData) {
        // Xử lý dữ liệu và chuyển hướng người dùng đến trang thanh toán
        return "redirect:/payment?cartData=" + cartData;
    }

    @GetMapping("/users/api/cart")
    @ResponseBody
    public ResponseEntity<String> getCart(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    String cartValue = cookie.getValue();
                    if (isValidJSON(cartValue)) {
                        return ResponseEntity.ok(cartValue);
                    } else {
                        // Nếu giá trị không hợp lệ, trả về một đối tượng JSON rỗng
                        return ResponseEntity.ok("{}");
                    }
                }
            }
        }
        // Trong trường hợp không có cookie hoặc cookie không chứa dữ liệu giỏ hàng, trả về một đối tượng JSON rỗng
        return ResponseEntity.ok("{}");
    }

    // Phương thức kiểm tra chuỗi JSON có hợp lệ hay không
    private boolean isValidJSON(String jsonStr) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(jsonStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
