package com.example.mvc.controller;

import com.example.mvc.config.Add2NumConfig;
import com.example.mvc.model.Add2Num;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Add2NumController {

    @GetMapping("/")
    public String showAdd2NumPage(Model model) {
        model.addAttribute("form", new Add2Num());
        return "add2num"; // Trả về tệp add2num.html trong thư mục templates
    }

    @PostMapping("/api/calculate")
    @ResponseBody
    public Add2Num processAddition(@RequestBody Add2Num form) {
        return Add2NumConfig.execute(form.getNum1(), form.getNum2());
    }
}