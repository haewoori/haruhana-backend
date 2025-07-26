package hae.woori.onceaday;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/global")
public class GlobalController {

    @GetMapping("/noauth")
    public String config() {
        return "Connection Successful";
    }
}
