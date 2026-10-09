package com.example.bai03.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    // Hiển thị trang danh sách sinh viên
    @GetMapping("")
    public String listSinhVien() {
        return "sinhvien/sinhvien-list"; 
    }

    // Hiển thị trang form thêm/nhập sinh viên
    @GetMapping("/add")
    public String showAddSinhVienForm() {
        return "sinhvien/add-sinhvien"; 
    }
}