package vn.hoctienganh.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	 @Value("${upload.dir}")
	 
	    private String uploadDir;

	 public String saveFile(MultipartFile file) throws IOException {
	        // Tạo thư mục nếu chưa tồn tại
	        Path path = Paths.get(uploadDir);
	        if (!Files.exists(path)) {
	            Files.createDirectories(path);
	        }

	        // Lưu file vào thư mục
	        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	        Path targetLocation = path.resolve(fileName);
	        Files.copy(file.getInputStream(), targetLocation);

	        return fileName; // Trả về tên file lưu trữ
	    }
}
