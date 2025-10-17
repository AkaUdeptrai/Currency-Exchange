# Currency Exchange Client-Server Application

Ứng dụng Client-Server tính tỉ giá tiền tệ sử dụng Java Socket và Docker.

## Tính năng
- Server nhận yêu cầu từ Client qua Socket
- Lấy tỉ giá thời gian thực từ API
- Client gửi request chuyển đổi tiền tệ
- Đóng gói hoàn chỉnh trong Docker

## Cấu trúc dự án
```
currency-exchange-server/
    ├── Server/
    │   ├── CurrencyServer.java
    │   │── Dockerfile
    │   └── ExchangeRateAPI.java
    └── docker-compose.yml

currency-exchange-client/
    ├── client/
    │   ├── CurrencyClient.java
    │   └── Dockerfile
    ├── .env
    └── docker-compose.yml

```

## Hướng dẫn chạy

### Sử dụng Docker Compose (Khuyến nghị)
```bash


## Sử dụng
Khi Client chạy, nhập thông tin theo format:
```
FROM_CURRENCY TO_CURRENCY AMOUNT
```

Ví dụ:
```
USD VND 100
EUR USD 50
JPY VND 1000
GBP THB 200
```

Gõ `exit` để thoát.

## Mã tiền tệ phổ biến
- **USD**: Đô la Mỹ
- **EUR**: Euro
- **GBP**: Bảng Anh
- **VND**: Việt Nam Đồng
- **JPY**: Yên Nhật
- **CNY**: Nhân dân tệ Trung Quốc
- **KRW**: Won Hàn Quốc
- **THB**: Baht Thái Lan
- **SGD**: Đô la Singapore

## API sử dụng
- ExchangeRate-API (Free tier): https://www.exchangerate-api.com/

## Xem thêm
Đọc [GUIDE.md](GUIDE.md) để biết thêm chi tiết về kiến trúc, troubleshooting và mở rộng tính năng.
