package com.example.mvc.controller;

import com.example.mvc.config.Add2NumConfig;
import com.example.mvc.model.Add2Num;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Add2NumCalculateController {

    @PostMapping("/calculate")
    public Add2Num calculateApi(@RequestParam("num1") String num1, @RequestParam("num2") String num2) {     
        return Add2NumConfig.execute(num1, num2);
    }
}