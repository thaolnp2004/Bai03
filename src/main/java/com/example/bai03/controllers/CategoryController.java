package com.example.bai03.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.bai03.models.Category;
import com.example.bai03.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller // Đánh dấu đây là Controller trả về giao diện HTML (View)
@RequestMapping("/categories") // Định nghĩa tiền tố đường dẫn chung cho toàn bộ Controller này
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService; // Gọi sang lớp Service xử lý nghiệp vụ

    // 1. Hiển thị danh sách danh mục
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories); // Gửi danh sách categories sang file HTML
        return "/categories/categories-list"; // Trả về file: templates/categories/categories-list.html
    }

    // 2. Hiển thị Form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category()); // Gửi một đối tượng rỗng để hứng dữ liệu từ Form
        return "/categories/add-category"; // Trả về file: templates/categories/add-category.html
    }

    // 3. Xử lý lưu Form thêm mới
    @PostMapping("/add")
    public String addCategory(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) { // Nếu dữ liệu nhập vào bị lỗi validation (trống, sai định dạng...)
            return "/categories/add-category"; // Giữ nguyên trang để người dùng sửa lỗi
        }
        categoryService.addCategory(category); // Gọi Service lưu vào DB
        return "redirect:/categories"; // Lưu xong thì quay lại trang danh sách
    }

    // 4. Hiển thị Form cập nhật (Sửa)
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID danh mục không hợp lệ: " + id)); // Tìm danh mục cần sửa
        model.addAttribute("category", category); // Gửi dữ liệu cũ lên Form để sửa
        return "/categories/update-category"; // Trả về file: templates/categories/update-category.html
    }

    // 5. Xử lý lưu Form cập nhật
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, @Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            category.setId(id);
            return "/categories/update-category"; // Có lỗi thì giữ lại trang
        }
        categoryService.updateCategory(category); // Thực hiện cập nhật
        return "redirect:/categories"; // Cập nhật xong quay lại trang danh sách
    }

    // 6. Xóa danh mục
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id); // Thực hiện xóa
        return "redirect:/categories"; // Xóa xong quay lại trang danh sách
    }
}