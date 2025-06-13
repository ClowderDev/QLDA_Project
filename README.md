
## 🔗 Repository
- GitHub Repository: [QLDA_Project](https://github.com/ClowderDev/QLDA_Project)
- Clone URL: `https://github.com/ClowderDev/QLDA_Project.git`

## 🛠️ Yêu cầu hệ thống
- Java JDK 17 hoặc cao hơn
- Maven 3.8.x hoặc cao hơn
- Oracle Database 19c hoặc cao hơn
- Oracle SQL Developer (khuyến nghị) hoặc công cụ quản lý Oracle khác
- IDE (khuyến nghị: IntelliJ IDEA hoặc Eclipse)

## 📦 Cài đặt và Cấu hình

### 1. Clone repository
```bash
git clone https://github.com/ClowderDev/QLDA_Project.git
cd QLDA_Project
```

### 2. Cấu hình cơ sở dữ liệu Oracle
1. Tạo database Oracle mới với tên `QLTUYENDUNG`
2. Cấu hình kết nối database trong file `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:QLTUYENDUNG
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

3. Khởi tạo dữ liệu:
- File `src/main/resources/data.sql` đã được cung cấp sẵn với dữ liệu mẫu cho Oracle
- Để import dữ liệu, bạn có thể:
  - Sử dụng Oracle SQL Developer để chạy file data.sql
  - Dự án sẽ khởi tạo thông tin user mẫu khi khởi chạy lần đầu.

### 3. Build và chạy ứng dụng
1. Build project:
```bash
mvn clean install
```

2. Chạy ứng dụng:
```bash
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## 📁 Cấu trúc thư mục
```
QLDA_Project/
├── src/
│   ├── main/
│   │   ├── java/                    # Mã nguồn Java
│   │   │   ├── com/qlda/           # Package chính
│   │   │   │   ├── controller/     # Các controller
│   │   │   │   ├── model/          # Các entity
│   │   │   │   ├── repository/     # Các repository
│   │   │   │   ├── service/        # Các service
│   │   │   │   └── config/         # Cấu hình
│   │   └── resources/              # Tài nguyên
│   │       ├── static/             # CSS, JS, images
│   │       ├── templates/          # Thymeleaf templates
│   │       ├── application.properties  # Cấu hình ứng dụng
│   │       └── data.sql            # Dữ liệu mẫu cho Oracle
│   └── test/                       # Unit tests
├── target/                         # Thư mục build
├── pom.xml                         # Cấu hình Maven
└── README.md                       # Tài liệu hướng dẫn
```

## 🔐 Tài khoản mặc định
Sau khi chạy ứng dụng lần đầu, bạn có thể đăng nhập với các tài khoản mặc định sau:

| Vai trò | Tên đăng nhập | Mật khẩu |
|---------|--------------|-----------|
| Admin   | adminUser    | 123       |
| HR      | hrUser       | 123       |
| Ứng viên| normalUser   | 123       |

## 📞 Hỗ trợ
Nếu bạn gặp vấn đề trong quá trình cài đặt hoặc sử dụng, vui lòng:
1. Kiểm tra các vấn đề thường gặp trong repository
2. Tạo issue mới trên GitHub repository

## 📝 Lưu ý về Oracle Database
- Đảm bảo Oracle Database đã được cài đặt và chạy trên máy
- Kiểm tra port mặc định (1521) có đang được sử dụng bởi Oracle Listener
- Cấp đủ quyền cho user database được sử dụng trong ứng dụng
- Nếu gặp lỗi về character set, đảm bảo database sử dụng AL32UTF8
