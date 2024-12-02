package vn.hoctienganh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoctienganh.external.client.GoogleTranslateFeignClient;

@Service
public class TranslationService {

    @Autowired
    private GoogleTranslateFeignClient googleTranslateFeignClient;

    public void detectLanguage(String text) {
        String response = googleTranslateFeignClient.detectLanguage(
            "application/gzip",
            "application/x-www-form-urlencoded",
            "google-translate1.p.rapidapi.com",
            "c2b333d4cdmshed1b0cdb50fbcbep1c6b3ajsn48226ed1056c",
            text
        );

        System.out.println("API Response: " + response);
    }
}
