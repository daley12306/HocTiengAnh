package vn.hoctienganh.services;

import java.util.List;

import vn.hoctienganh.models.UserModel;

public interface UserService {
	// Lấy danh sách học viên
    List<UserModel> getAllStudents();

    // Lấy học viên theo ID
    UserModel getStudentById(Long id);

    // Thêm mới học viên
    UserModel addStudent(UserModel student);

    // Cập nhật học viên
    UserModel updateStudent(Long id, UserModel studentDetails);

    // Xóa học viên
    void deleteStudent(Long id);
    
    void saveUser(UserModel user);
}
