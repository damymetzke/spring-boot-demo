package nl.brightboost.demo.index;

import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class IndexController {

    @GetMapping
    public Map<String, Object> index() {
        final List<String> routes = List.of("/employees");

        return Map.of(
            "repository", "https://github.com/damymetzke/spring-boot-demo", 
            "resources", routes
        );
    }
}
