package com.mybignumber.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBigNumber {
    private static final Logger logger = LoggerFactory.getLogger(MyBigNumber.class);
    
    public static String sum(String stn1, String stn2) {
        logger.info("Đang tính tổng của hai số có độ dài {} và {}", stn1, stn2);

        if (stn1 == null || stn2 == null || !stn1.matches("\\d+") || !stn2.matches("\\d+")) {
            throw new IllegalArgumentException("Đầu vào phải là chuỗi ký số hợp lệ và không rỗng.");
        }

        // Tạo mảng chứa kết quả, kích thước tối đa là độ dài lớn nhất + 1 (phòng trường hợp có số nhớ cuối cùng)
        char[] result = new char[Math.max(stn1.length(), stn2.length()) + 1];
        int n1idx = stn1.length() - 1;
        int n2idx = stn2.length() - 1;
        int rIdx = result.length - 1;   // Index của chữ số đang tính, bắt đầu từ cuối chuỗi
        boolean has_carry = false;
        int digit1, digit2, total;

        while (n1idx >= 0 || n2idx >= 0) {
            digit1 = (n1idx >= 0) ? stn1.charAt(n1idx--) - '0' : 0;
            digit2 = (n2idx >= 0) ? stn2.charAt(n2idx--) - '0' : 0;

            total = digit1 + digit2 + (has_carry ? 1 : 0);
            has_carry = total > 9;

            result[rIdx--] = (char)(total % 10 + '0');

            logger.info("Tính toán hàng hiện tại: kết quả tạm thời = {}", has_carry ? "1" + new String(result) : new String(result));
        }

        if (has_carry) {
            result[0] = '1';
        }

        int startIndex = rIdx + 1;
        
        // Loại bỏ các số 0 ở đầu, nhưng giữ lại nếu kết quả là "0"
        while (startIndex < result.length - 1 && result[startIndex] == '0') {
            startIndex++;
        }
        
        logger.info("Tính toán thành công.");
        return new String(result, startIndex, result.length - startIndex);
    }
}