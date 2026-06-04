package com.mybignumber.lib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyBigNumberTest {

    @ParameterizedTest(name = "{index} => Tính tổng: {0} + {1} phải bằng {2}")
    @DisplayName("Các trường hợp tính toán đúng (Happy Paths)")
    @CsvSource({
        "123, 456, 579",          // Độ dài bằng nhau, không nhớ
        "58, 67, 125",            // Độ dài bằng nhau, có nhớ
        "100020, 35, 100055",     // Số thứ nhất dài hơn
        "12, 99988, 100000",      // Số thứ hai dài hơn
        "999, 1, 1000",           // Nhớ dây chuyền lên hàng đầu tiên
        "0, 0, 0",                // Cộng hai số 0
        "8675309, 0, 8675309",    // Cộng với số 0
        "00123, 05, 128"          // Trường hợp số có các số 0 vô nghĩa ở đầu (Leading zeros)
    })
    void testValidAdditions(String stn1, String stn2, String expected) {
        assertEquals(expected, MyBigNumber.sum(stn1, stn2));
    }

    @Test
    @DisplayName("Bounda: Kiểm tra với số siêu lớn (Tránh tràn mảng/bộ nhớ)")
    void testExtremelyLargeNumbers() {
        String num1 = "9".repeat(1000); // 1000 chữ số 9
        String num2 = "1";
        String expected = "1" + "0".repeat(1000); // 1 và 1000 chữ số 0
        
        assertEquals(expected, MyBigNumber.sum(num1, num2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Thất bại: Đầu vào là null hoặc chuỗi rỗng")
    void testNullOrEmptyInput(String invalidInput) {
        assertThrows(IllegalArgumentException.class, () -> {
            MyBigNumber.sum(invalidInput, "123");
        }, "Phải ném IllegalArgumentException khi đầu vào null hoặc rỗng");

        assertThrows(IllegalArgumentException.class, () -> {
            MyBigNumber.sum("123", invalidInput);
        }, "Phải ném IllegalArgumentException khi đầu vào null hoặc rỗng");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12a3", "-123", "1.23", "1 23", "   ", "abc"})
    @DisplayName("Thất bại: Đầu vào chứa ký tự không phải số hoặc số âm")
    void testInvalidCharactersInput(String invalidInput) {
        assertThrows(IllegalArgumentException.class, () -> {
            MyBigNumber.sum(invalidInput, "456");
        }, "Phải ném IllegalArgumentException khi chứa ký tự lỗi: " + invalidInput);
    }
}