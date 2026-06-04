package com.mybignumber.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBigNumber {
    private static final Logger logger = LoggerFactory.getLogger(MyBigNumber.class);
    
    public static String sum(String stn1, String stn2) {
        if (stn1 == null || stn2 == null || !stn1.matches("\\d+") || !stn2.matches("\\d+")) {
            throw new IllegalArgumentException("Đầu vào phải là chuỗi ký số hợp lệ và không rỗng.");
        }
        logger.info("Đang tính tổng của hai số có độ dài {} và {}", stn1, stn2);

        StringBuilder result = new StringBuilder();
        
        boolean has_carry = false;
        int digit1, digit2, total;

        for (int n1idx = stn1.length() - 1, n2idx = stn2.length() - 1; n1idx >= 0 || n2idx >= 0; n1idx--, n2idx--) {
            digit1 = (n1idx >= 0) ? stn1.charAt(n1idx) - '0' : 0;
            digit2 = (n2idx >= 0) ? stn2.charAt(n2idx) - '0' : 0;

            total = digit1 + digit2 + (has_carry ? 1 : 0);
            has_carry = total > 9;

            result.insert(0, (char)(total % 10 + '0'));

            logger.info("Tính toán hàng hiện tại: kết quả tạm thời = {}", has_carry ? "1" + result.toString() : result.toString());      
        }

        if (has_carry) {
            result.insert(0, '1');
        }

        String finalResult = result.toString();

        logger.info("Tính toán thành công.");
        return finalResult;
    }
}