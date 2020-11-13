package pro.bolshakov.geekbrains.dz6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController1 {

    @GetMapping("/app")
    public String helloWorld() {
        return "index";
    }

    @GetMapping("/main")
    public String helloWorld2() {
        return "index";
    }
}
