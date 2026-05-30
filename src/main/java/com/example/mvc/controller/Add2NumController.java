package com.example.mvc.controller;

import com.example.mvc.model.Add2Num;
import com.mybignumber.lib.MyBigNumber;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @PostMapping("/calculate")
    @ResponseBody
    public Add2Num processWeb(@ModelAttribute("form") Add2Num form) {
        return this.execute(form.getNum1(), form.getNum2());
    }

    @PostMapping("/api/calculate")
    @ResponseBody
    public Add2Num processJSONRequest(@RequestBody Add2Num form) {
        return this.execute(form.getNum1(), form.getNum2());
    }

    private Add2Num execute(String num1, String num2) {

        // Hứng dòng Log từ System.out được gọi từ module MyBigNumber
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream customOut = new PrintStream(buffer);
        PrintStream oldOut = System.out;
        System.setOut(customOut);

        String result = "";
        try {
            result = MyBigNumber.sum(num1, num2);
        } finally {
            System.out.flush();
            System.setOut(oldOut);
        }

        // Tách các dòng Log thành List
        List<String> steps = List.of();
        String logConsole = buffer.toString();
        if (!logConsole.isEmpty()) {
            String[] rawSteps = logConsole.split("\\r?\\n");
            
            steps = Arrays.stream(rawSteps)
                .map(step -> {
                    // Regex tìm đoạn: "Năm-Tháng-Ngày... --- [...] gói_tin : "
                    // và thay thế nó bằng chuỗi rỗng ""
                    return step.replaceAll("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\\+\\d{2}:\\d{2}\\s+\\w+\\s+\\d+\\s+---\\s+\\[.*?\\]\\s+.*?\\s+:\\s+", "");
                })
                .filter(step -> !step.isBlank()) // Loại bỏ dòng trống nếu có
                .collect(java.util.stream.Collectors.toList());
        }

        // Trả về Object
        return new Add2Num(num1, num2, result, steps);
    }

}