package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.DTO.ProductsDTO;
import com.example.admingiadien.Entity.Users;
import com.example.admingiadien.Service.CategoriesService;
import com.example.admingiadien.Service.ProductService;
import com.example.admingiadien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class UserProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoriesService categoryService;

    // Hiển thị danh sách sản phẩm của một danh mục
    @GetMapping("/users/category/{categoryId}/products")
    public String getCategoryProducts(@PathVariable("categoryId") String categoryId, Model model) {
        // Kiểm tra categoryId không phải là "undefined"
        if ("undefined".equals(categoryId)) {
            model.addAttribute("error", "Invalid category ID.");
            return "errorPage";
        }

        try {
            Long id = Long.parseLong(categoryId);
            CategoriesDTO category = categoryService.getCategoriesById(id);
            List<ProductsDTO> products = productService.getProductsByCategoryId(id);
            model.addAttribute("category", category);
            model.addAttribute("products", products);
            return "Users/pages/userProduct";
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid category ID: " + categoryId);
            return "errorPage";
        }
    }

}
