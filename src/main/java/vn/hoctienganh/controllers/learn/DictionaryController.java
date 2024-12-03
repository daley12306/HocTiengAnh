package vn.hoctienganh.controllers.learn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DictionaryController {

    @GetMapping("/translate")
    public String dictionary() {
        return "dictionary";
    }
}
