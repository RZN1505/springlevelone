package pro.bolshakov.geekbrains.dz2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("msg", "My message plus random UUID 1-> " + UUID.randomUUID());
        return "index";
    }



}
