# Sử dụng OpenJDK base image
FROM openjdk:17-slim

# Tạo thư mục làm việc
WORKDIR /app

# Copy source code
COPY CurrencyClient.java .

# Compile
RUN javac CurrencyClient.java

# Chạy client
CMD ["java", "CurrencyClient"]
