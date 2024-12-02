package vn.hoctienganh.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String saveFile(MultipartFile file) throws IOException;

}
