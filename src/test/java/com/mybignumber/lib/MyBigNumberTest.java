package com.mybignumber.lib;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MyBigNumberTest {

    @BeforeEach
    void setUp() {
        MyBigNumber.clearLogs();
    }

    @AfterEach
    void tearDown() {
        MyBigNumber.clearLogs();
    }

    @ParameterizedTest(name = "Testcase {index}: {0} + {1} = {2}")
    @DisplayName("Kiểm thử các phân vùng dữ liệu hợp lệ (Equivalence Partitioning)")
    @CsvSource({
        "1234, 897, 2131",      // Kịch bản mẫu trong tài liệu đặc tả
        "0, 0, 0",              // Biên giá trị nhỏ nhất
        "9, 9, 18",            // Phép cộng có nhớ đơn giản
        "12, 34, 46",          // Độ dài bằng nhau, không nhớ
        "999, 1, 1000",        // Tràn hàng nhớ liên tục tạo leftover ở đầu
        "000123, 45, 000168"   // Giữ nguyên đệm số 0 theo tiến trình lặp tiểu học
    })
    void testSum_ValidInputs(String num1, String num2, String expectedResult) {
        String actualResult = MyBigNumber.sum(num1, num2);

        // Assert 1: Kiểm tra tính chính xác của kết quả toán học
        assertEquals(expectedResult, actualResult, 
            String.format("Sai kết quả khi cộng %s và %s", num1, num2));

        // Assert 2: Kiểm tra độ phủ của log tiến trình
        List<String> steps = MyBigNumber.getStepLogs();
        assertNotNull(steps, "Danh sách bước tính không được null");
        assertTrue(steps.size() > 0, "Phải sinh ra ít nhất 1 dòng log bước tính toán");

        // Ghi nhận ra màn hình test kết quả log cụ thể của kịch bản này
        System.out.println(String.format("=== LOGS FOR TESTCASE: %s + %s ===", num1, num2));
        steps.forEach(step -> System.out.println("   > " + step));
    }

    @ParameterizedTest(name = "Testcase lỗi {index}: num1=''{0}'', num2='''{1}'''")
    @DisplayName("Kiểm thử phân vùng dữ liệu bẩn / không hợp lệ (Boundary Value Analysis)")
    @CsvSource({
        ", 123",               // Tham số thứ nhất null
        "123, ",               // Tham số thứ hai null
        "12a3, 456",           // Chứa ký tự chữ cái
        "123, 45 6",           // Chứa khoảng trắng ngầm
        "123, 456-",           // Chứa ký tự đặc biệt
        "-123, 456",           // Số âm (không hợp lệ)
        "'', 123"              // Chuỗi rỗng
    })
    void testSum_InvalidInputs_ShouldThrowException(String num1, String num2) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MyBigNumber.sum(num1, num2);
        });

        assertEquals("Đầu vào phải là chuỗi ký số hợp lệ và không rỗng.", exception.getMessage());
    }

    @Test
    @DisplayName("Kiểm thử biên dữ liệu cực lớn (Extreme Big Number Processing)")
    void testSum_ExtremeLargeNumbers() {
        String num1 = "9".repeat(100); 
        String num2 = "1";
        String expected = "1" + "0".repeat(100);

        String actual = MyBigNumber.sum(num1, num2);
        List<String> steps = MyBigNumber.getStepLogs();

        assertEquals(expected, actual);
        
        // Đếm chính xác số bước logic chạy qua để xác thực cấu trúc lặp
        // 100 chữ số thực hiện vòng lặp + 1 bước hạ số nhớ carry cuối cùng lên đầu = 101 bước
        assertEquals(101, steps.size(), "Số lượng dòng log tiến trình ghi nhận không khớp với số bước tính");
    }
}