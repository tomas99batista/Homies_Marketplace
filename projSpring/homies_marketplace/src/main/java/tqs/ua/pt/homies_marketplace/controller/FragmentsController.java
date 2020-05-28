package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

        @GetMapping("/navbar")
        public String getnavbar() {
            return "navbar.html";
        }
    }