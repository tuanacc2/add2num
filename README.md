# Add2Num - Ứng dụng mô phỏng cộng 2 số lớn (Big Number)

Ứng dụng Web sử dụng kiến trúc **Spring Boot MVC** kết hợp với giao diện **Thymeleaf & Tailwind CSS** để mô phỏng từng bước thuật toán cộng hai số nguyên lớn cực kỳ trực quan. Hệ thống kết nối và trích xuất dữ liệu log I/O thời gian thực từ một module thư viện `.jar` đóng gói sẵn.

<img width="1911" height="919" alt="image" src="https://github.com/user-attachments/assets/49effb38-f596-47b4-b1f8-a4a008e29e18" />


---

## Tính năng nổi bật

- **Xử lý số cực lớn:** Vượt qua giới hạn lưu trữ dữ liệu của các kiểu dữ liệu nguyên thủy (`long`, `double`) nhờ tích hợp lõi xử lý chuỗi.
- **Terminal Animation (Hoạt họa trực quan):** Giao diện Console mô phỏng phong cách ma trận (Cyberpunk) chạy khớp nhịp 1:1 với tiến trình xử lý từ Backend.
- **Hiệu ứng kĩ thuật số nâng cao:** Highlight cột số đang tính toán, tự động hạ số dư cuối cùng (`Leftover`).
- **RESTful API Ready:** Hỗ trợ xử lý và phản hồi dữ liệu cấu trúc JSON chuẩn hóa, dễ dàng kiểm thử qua Postman.
- **Kiểm thử tự động (CI/CD Ready):** Tích hợp đầy đủ các bài Integration Test tự động sử dụng `MockMvc` và `JsonPath`.

---

## Công nghệ sử dụng

- **Backend:** Java 17 / 21, Spring Boot (Web, ThymeLeaf).
- **Frontend:** HTML5, JavaScript (Fetch API, DOM Manipulation), Tailwind CSS.
- **Thư viện lõi:** File JAR ngoài `com.mybignumber.lib.MyBigNumber`.
- **Testing:** JUnit 5, MockMvc, Hamcrest, JsonPath.

---

## Kiến trúc thư mục dự án

```text
Add2Num
 ├── .gitignore
 ├── pom.xml
 ├── Dockerfile
 ├── README.md
 └── src
     ├── main
     │   ├── java
     │   │   └── com.example.mvc
     │   │        ├── config           # Xử lý dữ liệu thông qua module MyBigNumber
     │   │        ├── controller       # Điều hướng Request (HTML View & JSON API)
     │   │        └── model            # Cấu trúc dữ liệu (Add2Num)
     │   └── resources
     │       ├── static/css            # File style.css sau khi build từ Tailwind v4
     │       └── templates             # Giao diện add2num.html (Thymeleaf)
  	 └── test.java.com.example.mvc     # Các kịch bản kiểm thử tự động

```

# Triển khai trực tuyến (Deployment trên Render)

Dự án đã được cấu hình sẵn sàng để deploy tự động lên Render.com:

URL: https://add2num-app.onrender.com

# Hướng dẫn cài đặt và chạy Local

## Yêu cầu hệ thống

Máy tính đã cài đặt JDK 17 hoặc JDK 21.

Đã cài đặt Maven (hoặc sử dụng trực tiếp bộ cài ./mvnw đính kèm trong dự án).

## Khởi chạy ứng dụng

Mở Terminal tại thư mục gốc của dự án và chạy các lệnh sau:

- **Bước 1:** Biên dịch ứng dụng

```bash
./mvnw clean package -DskipTests
```
- **Bước 2:** Chạy ứng dụng
```bash
./mvnw spring-boot:run
```
Sau khi hệ thống khởi động xong, bạn mở trình duyệt và truy cập: http://localhost:8080/

## Cấu hình Kiểm thử API bằng Postman

Ứng dụng cung cấp API endpoint phục vụ kiểm thử dữ liệu độc lập:

Method: POST

URL: http://localhost:8080/api/calculate

Content-Type: application/x-www-form-urlencoded

Body Params:

```json
{
    "num1": "128",
    "num2": "960"
}
```

Response mẫu (JSON):

```json
{
    "num1": "128",
    "num2": "960",
    "result": "1088",
    "steps": [
        "Đang tính tổng của 128 và 960",
        "Tính toán hàng hiện tại: kết quả tạm thời = 8",
        "Tính toán hàng hiện tại: kết quả tạm thời = 88",
        "Tính toán hàng hiện tại: kết quả tạm thời = 1088",
        "Tính toán hàng hiện tại: kết quả tạm thời = 1088",
        "Kết quả cuối cùng: 1088"
    ]
}
```
## Chạy Kiểm thử Tự động (Unit Test)

Để thực thi toàn bộ các ca kiểm thử tích hợp (Integration Test) nhằm kiểm tra tính toàn vẹn của API điều hướng và cấu trúc JSON trả về, sử dụng lệnh:

```bash
./mvnw test
```
