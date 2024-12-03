package vn.hoctienganh.controllers.learn;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class TranslateController {

    private static final String API_URL = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
    private static final String API_KEY = "d84ad095f9mshce7e401b29676c5p149591jsn074a43c7c1b3";

    @RequestMapping(value = "/translate", method = RequestMethod.POST)
    public TranslationResponse translateText(@RequestBody TranslateRequest request) {
        String text = request.getText();
        String sourceLang = request.getSourceLang();
        String targetLang = request.getTargetLang();

        String translatedText = translateUsingAPI(text, sourceLang, targetLang);

        return new TranslationResponse(translatedText);
    }

    private String translateUsingAPI(String text, String sourceLang, String targetLang) {
        String responseText = "";

        try {
            // URL endpoint của API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức và headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("x-rapidapi-key", API_KEY);
            connection.setRequestProperty("x-rapidapi-host", "google-translate113.p.rapidapi.com");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Tạo JSON body để gửi
            String requestBody = String.format("{\"from\": \"%s\", \"to\": \"%s\", \"text\": \"%s\"}", sourceLang, targetLang, text);

            // Gửi dữ liệu
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Nhận phản hồi từ API
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    responseText = parseTranslationResponse(response.toString());
                }
            } else {
                responseText = "Error occurred while translating.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseText = "Error occurred while connecting to translation API.";
        }

        return responseText;
    }

    private String parseTranslationResponse(String jsonResponse) {
        try {
            // Sử dụng Jackson ObjectMapper để phân tích cú pháp JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Lấy giá trị 'trans' từ JSON
            JsonNode transNode = rootNode.get("trans");
            if (transNode != null) {
                return transNode.asText();  // Trả về giá trị của "trans"
            } else {
                return "Translation not available";  // Nếu không có trường "trans"
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing JSON response";
        }
    }

    // DTO cho request
    public static class TranslateRequest {
        private String text;
        private String sourceLang;
        private String targetLang;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getSourceLang() {
            return sourceLang;
        }

        public void setSourceLang(String sourceLang) {
            this.sourceLang = sourceLang;
        }

        public String getTargetLang() {
            return targetLang;
        }

        public void setTargetLang(String targetLang) {
            this.targetLang = targetLang;
        }
    }

    // DTO cho response
    public static class TranslationResponse {
        private String translatedText;

        public TranslationResponse(String translatedText) {
            this.translatedText = translatedText;
        }

        public String getTranslatedText() {
            return translatedText;
        }

        public void setTranslatedText(String translatedText) {
            this.translatedText = translatedText;
        }
    }
}
