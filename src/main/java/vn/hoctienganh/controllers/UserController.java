package vn.hoctienganh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoctienganh.entity.User;
import vn.hoctienganh.services.UserService;

@Controller
@RequestMapping("/admin")
public class UserController {
	@Autowired
    private UserService userService;
	
	 // Trang chỉnh sửa hồ sơ người dùng (User Profile)
	@GetMapping("/user/edit-profile/{id}")
    public String showEditProfileForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getStudentById(id);
        model.addAttribute("user", user);
        return "user/editProfile"; // Trả về view editProfile.html
    }
	
	 @GetMapping("/user/profile/{id}")
	    public String getProfile( @PathVariable("id") Long id,Model model) {
		 User user = userService.getStudentById(id);;// Lấy user từ service
	        model.addAttribute("user", user); // Truyền user sang view
	        return "user/userProfile"; // Trả về view userProfile.html
	    }
	 @PostMapping("/user/update-profile/{id}")
	 public String updateProfile(@PathVariable("id") Long id,@ModelAttribute User user) {
	     // Xử lý cập nhật thông tin người dùng
		 user.setId(id);
	     userService.updateUser(user);
	     
	     return "redirect:/admin/user/profile/" + id; // Quay lại trang profile của người dùng
	 }

	@GetMapping("/profile")
	public String getAllStudents(Model model) {
        List<User> users = userService.getAllStudents();
        
        model.addAttribute("users", users);
        return "admin/profile_list"; 
    }
	@GetMapping("/users/add")
	public String showAddUserForm(Model model) {
	    User user = new User();
	    model.addAttribute("user", user);
	    return "admin/add_student";  
	}
	@PostMapping("/users/add")
	public String saveStudent(@ModelAttribute("user") User user) {
	    // Lưu dữ liệu vào database
	    userService.addStudent(user);

	    // Redirect về danh sách người dùng sau khi thêm thành công
	    return "redirect:/admin/profile";
	}
	
	

    // Lấy thông tin học viên theo ID
    @GetMapping("/students/{id}")
    public User getStudentById(@PathVariable Long id) {
        return userService.getStudentById(id);
    }


    // Cập nhật học viên
    @GetMapping("/users/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        User user = userService.getStudentById(id); // Lấy người dùng từ DB
        model.addAttribute("user", user);
        return "admin/edit_student"; // Tên của trang form chỉnh sửa
    }
    @PostMapping("/users/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute User user) {
    	        user.setId(id); // Gán lại id cho đối tượng user
    	        userService.updateStudent(id, user); // Cập nhật người dùng trong DB
    	        return "redirect:/admin/profile" ; // Chuyển hướng về trang danh sách người dùng
    	    
    }
    // Xóa học viên
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteStudent(id);
        return "redirect:/admin/profile"; // Chuyển hướng lại danh sách người dùng
    }
}
