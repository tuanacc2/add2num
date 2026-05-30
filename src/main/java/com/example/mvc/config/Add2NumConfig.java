package com.example.mvc.config;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.example.mvc.model.Add2Num;
import com.mybignumber.lib.MyBigNumber;

public class Add2NumConfig {
    public static Add2Num execute(String num1, String num2) {

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
