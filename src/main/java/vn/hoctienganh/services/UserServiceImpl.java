package vn.hoctienganh.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoctienganh.models.UserModel;
import vn.hoctienganh.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserRepository studentRepository;
	@Override
	public List<UserModel> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public UserModel getStudentById(Long id) {
		return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
	}

	@Override
	public UserModel addStudent(UserModel student) {
		return studentRepository.save(student);
	}

	@Override
	public UserModel updateStudent(Long id, UserModel studentDetails) {
		 UserModel existingStudent = getStudentById(id);
	        existingStudent.setName(studentDetails.getName());
	        existingStudent.setFirstName(studentDetails.getFirstName());
	        existingStudent.setEmail(studentDetails.getEmail());
	        existingStudent.setPhone(studentDetails.getPhone());
	        return studentRepository.save(existingStudent);
	}

	@Override
	public void deleteStudent(Long id) {
		UserModel student = getStudentById(id);
        studentRepository.delete(student);
		
	}

	@Override
	public void saveUser(UserModel user) {
		studentRepository.save(user);
	}

}
