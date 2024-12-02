package vn.hoctienganh.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleTranslateClient", url = "https://google-translate1.p.rapidapi.com/language/translate/v2/detect")
public interface GoogleTranslateFeignClient {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    String detectLanguage(
        @RequestHeader("Accept-Encoding") String acceptEncoding,
        @RequestHeader("Content-Type") String contentType,
        @RequestHeader("x-rapidapi-host") String host,
        @RequestHeader("x-rapidapi-key") String apiKey,
        @RequestParam("q") String text
    );
}
