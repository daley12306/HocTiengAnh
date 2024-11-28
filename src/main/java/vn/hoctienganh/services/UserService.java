package vn.hoctienganh.services;

import java.util.List;

import vn.hoctienganh.entity.*;

public interface UserService {
	// Lấy danh sách học viên
    List<User> getAllStudents();

    // Lấy học viên theo ID
    User getStudentById(Long id);

    // Thêm mới học viên
    User addStudent(User student);

    // Cập nhật học viên
    User updateStudent(Long id, User studentDetails);

    // Xóa học viên
    void deleteStudent(Long id);
    
    void saveUser(User user);
}
