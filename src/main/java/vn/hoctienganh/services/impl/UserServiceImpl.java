package vn.hoctienganh.services.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.User;
import vn.hoctienganh.repository.UserRepository;
import vn.hoctienganh.services.UserService;

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
		return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
	}

	@Override
	public User addStudent(User student) {
		return studentRepository.save(student);
	}

	@Override
	public User updateStudent(Long id, User studentDetails) {
		 User existingStudent = getStudentById(id);
	        existingStudent.setFullName(studentDetails.getFullName());
	        existingStudent.setEmail(studentDetails.getEmail());
	        existingStudent.setPhoneNumber(studentDetails.getPhoneNumber());
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

}
