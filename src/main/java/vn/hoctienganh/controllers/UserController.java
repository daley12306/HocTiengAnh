package vn.hoctienganh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vn.hoctienganh.entity.User;
import vn.hoctienganh.models.UserModel;
import vn.hoctienganh.services.UserService;

@Controller
public class UserController {
	@Autowired
    private UserService userService;
	
	@GetMapping("/admin/profile")
	public String index() {
		return "admin/profile_list";
	}
	@GetMapping("/admin/users/add")
	public String showAddStudentForm() {
	   return "admin/add_student"; 
	}
	@PostMapping("/admin/users/add")
	public String saveStudent(@ModelAttribute("user") User user) {
	    // Lưu dữ liệu vào database
	    userService.saveUser(user);

	    // Redirect về danh sách người dùng sau khi thêm thành công
	    return "redirect:/admin/users";
	}
	
	
	
	// Lấy danh sách học viên
    @GetMapping("/list")
    public List<User> getAllStudents() {
        return userService.getAllStudents();
    }

    // Lấy thông tin học viên theo ID
    @GetMapping("/{id}")
    public User getStudentById(@PathVariable Long id) {
        return userService.getStudentById(id);
    }

    // Thêm học viên
    @PostMapping("/add")
    public User addStudent(@RequestBody User student) {
        return userService.addStudent(student);
    }

    // Cập nhật học viên
    @PutMapping("/update/{id}")
    public User updateStudent(@PathVariable Long id, @RequestBody User studentDetails) {
        return userService.updateStudent(id, studentDetails);
    }

    // Xóa học viên
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
    	userService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
