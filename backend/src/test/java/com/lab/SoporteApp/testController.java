package com.lab.SoporteApp;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class testController {
    @GetMapping("/test")
    public String test(){
        return "Hello World!";
    }
    
    
}
