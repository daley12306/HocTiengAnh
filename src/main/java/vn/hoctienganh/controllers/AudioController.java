package vn.hoctienganh.controllers;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api/audio")
public class AudioController {
	@GetMapping("/proxy")
    public ResponseEntity<byte[]> proxyAudio(@RequestParam String url) {
        try {
            URL audioUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) audioUrl.openConnection();
            conn.setRequestMethod("GET");
            
            // Đọc response
            try (InputStream is = conn.getInputStream()) {
                byte[] audioBytes = IOUtils.toByteArray(is);
                
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
                headers.setContentLength(audioBytes.length);
                
                return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
