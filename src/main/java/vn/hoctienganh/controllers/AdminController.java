package vn.hoctienganh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.hoctienganh.entity.User;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public String adminHome(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser"); // Lấy thông tin người dùng từ session
        if (loggedInUser != null) {
            String adminName = loggedInUser.getFullName(); // Lấy tên người dùng từ đối tượng User
            model.addAttribute("adminName", adminName); // Truyền tên người dùng vào model
        }
        return "/admin/home"; // Trả về view admin home
    }
}
