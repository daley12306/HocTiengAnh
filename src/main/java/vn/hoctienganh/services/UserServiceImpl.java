package vn.hoctienganh.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.User;
import vn.hoctienganh.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserRepository studentRepository;
	
	
	@Override
	public List<User> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public User getStudentById(Long id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Override
	public User addStudent(User student) {
		
	    String avatar = createAvatar(student.getFullName());  // Tạo avatar từ fullName
        student.setAvatar(avatar);
	    return studentRepository.save(student);
	}

	@Override
	public User updateStudent(Long id, User studentDetails) {

		User existingStudent = getStudentById(id);
	        existingStudent.setFullName(studentDetails.getFullName());
	        existingStudent.setUsername(studentDetails.getUsername());
	        existingStudent.setPassword(studentDetails.getPassword());
	        existingStudent.setEmail(studentDetails.getEmail());
	        existingStudent.setPhoneNumber(studentDetails.getPhoneNumber());
	        existingStudent.setAddress(studentDetails.getAddress());
	        
	        if (studentDetails.getIsAdmin() != existingStudent.getIsAdmin()) {
	            existingStudent.setIsAdmin(studentDetails.getIsAdmin());
	        }
	        
	        return studentRepository.save(existingStudent);
	}

	@Override
	public void deleteStudent(Long id) {
		User student = getStudentById(id);
        studentRepository.delete(student);
		
	}

	@Override
	public void saveUser(User user) {
		studentRepository.save(user);
	}

	

	@Override
	public void updateUser(User user) {
		
        // Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu không
        User existingUser = studentRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with id: " + user.getId());
        }

        // Cập nhật thông tin người dùng
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        
        String avatar = createAvatar(user.getFullName());  // Tạo avatar từ fullName
        existingUser.setAvatar(avatar);
        
        // Lưu lại thông tin người dùng đã được cập nhật
        studentRepository.save(existingUser);
    }

	@Override
	public String createAvatar(String fullName) {
		if (fullName == null || fullName.isEmpty()) {
	        return "NN";  
	        }
		String[] nameParts = fullName.split("\\s+");  // Tách tên thành các phần (nếu có nhiều từ)
		String initials = ""; 
		// Lấy chữ cái đầu tiên của phần cuối của tên (phần họ hoặc tên cuối)
		if (nameParts.length > 1) {
		    initials = nameParts[nameParts.length - 2].substring(0, 1).toUpperCase();  
		    initials += nameParts[nameParts.length - 1].substring(0, 1).toUpperCase();  
		} else {
		    initials = nameParts[0].substring(0, 1).toUpperCase();  // Nếu chỉ có một từ (chỉ có tên), lấy chữ cái đầu
		}

	        return initials;
	        
	}

	@Override
	public boolean existsByEmail(String email) {
		return studentRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhoneNumber(String phoneNumber) {
		return studentRepository.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public boolean existsByUsernameAndIdNot(String username, Long id) {
        return studentRepository.existsByUsernameAndIdNot(username, id);
    }

	@Override
	public boolean existsByEmailAndIdNot(String email, Long id) {
		return studentRepository.existsByEmailAndIdNot(email, id);
	}

	@Override
	public boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id) {
		return studentRepository.existsByPhoneNumberAndIdNot(phoneNumber, id);
	}

	@Override
	public boolean existsByUsername(String username) {
		return studentRepository.existsByUsername(username);
	}
}