package com.ib.ibcheckweb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IBController {
 
    @RequestMapping("/ibcheck")
    public String hello() {
        return "Hello Spring Boot!";
    }
}
