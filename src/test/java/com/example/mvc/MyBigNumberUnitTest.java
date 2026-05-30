package com.example.mvc;

import com.mybignumber.lib.MyBigNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyBigNumberUnitTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void testSum_HaiSoDuongBinhThuong() {
        String result = MyBigNumber.sum("123", "456");
        assertEquals("579", result, "123 + 456 phải bằng 579");
    }

    @Test
    void testSum_CoNhoSangHangTiepTheo() {
        String result = MyBigNumber.sum("99", "1");
        assertEquals("100", result, "99 + 1 phải bằng 100");
    }

    @Test
    void testSum_DoDaiHaiChuoiKhacNhau() {
        String result = MyBigNumber.sum("123456", "78");
        assertEquals("123534", result);
    }
}
