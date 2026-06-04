package com.mybignumber.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBigNumber {
    private static final Logger logger = LoggerFactory.getLogger(MyBigNumber.class);

    private static final ThreadLocal<List<String>> stepLogs = ThreadLocal.withInitial(ArrayList::new);

    public static void clearLogs() {
        logger.info("Đang dọn dẹp log trước khi chạy kịch bản mới.");
        stepLogs.get().clear();
    }

    public static List<String> getStepLogs() {
        // Trả về danh sách các bước tính toán thực tế đã được ghi lại
        return Collections.unmodifiableList(stepLogs.get());
    }
    
    public static String sum(String stn1, String stn2) {
        if (stn1 == null || stn2 == null || !stn1.matches("\\d+") || !stn2.matches("\\d+")) {
            throw new IllegalArgumentException("Đầu vào phải là chuỗi ký số hợp lệ và không rỗng.");
        }

        // Dọn dẹp log trước khi bắt đầu tính toán mới
        stepLogs.get().clear();
        List<String> currentSteps = stepLogs.get();
        logger.info("Đang tính tổng của hai số có độ dài {} và {}", stn1, stn2);
        int stepNum = 1;


        StringBuilder result = new StringBuilder();
        boolean has_carry = false;
        int digit1, digit2, total;

        for (int n1idx = stn1.length() - 1, n2idx = stn2.length() - 1; n1idx >= 0 || n2idx >= 0; n1idx--, n2idx--) {
            digit1 = (n1idx >= 0) ? stn1.charAt(n1idx) - '0' : 0;
            digit2 = (n2idx >= 0) ? stn2.charAt(n2idx) - '0' : 0;

            total = digit1 + digit2 + (has_carry ? 1 : 0);
            has_carry = total > 9;

            result.insert(0, (char)(total % 10 + '0'));
            stepNum++;

            // Logging chi tiết bước tính toán hiện tại
            String msgStep;
            if (stepNum == 1 && !has_carry) {
                msgStep = String.format("Bước %d: Lấy %d cộng với %d được %d. Lưu %d vào kết quả và nhớ %d.",
                        stepNum, digit1, digit2, total, total % 10, has_carry ? 1 : 0);
            } else {
                msgStep = String.format("Bước %d: Lấy %d cộng với %d được %d. Cộng tiếp với nhớ %d được %d. Lưu %d vào kết quả và nhớ %d.",
                        stepNum, digit1, digit2, (digit1 + digit2), has_carry ? 1 : 0, total, total % 10, has_carry ? 1 : 0);
            }

            // Lưu vết log vào cả cơ chế ThreadLocal và logger để có thể kiểm tra trong unit test
            currentSteps.add(msgStep);
            logger.info(msgStep);
        }

        if (has_carry) {
            result.insert(0, '1');

            // Logging bước hạ số nhớ cuối cùng xuống hàng kết quả nếu có
            String leftoverMsg = String.format("Bước %d: Hạ số nhớ cuối cùng (%d) xuống hàng kết quả.", stepNum, has_carry ? 1 : 0);
            currentSteps.add(leftoverMsg);
            logger.info(leftoverMsg);
        }

        String finalResult = result.toString();

        logger.info("Tính toán thành công.");
        return finalResult;
    }
}