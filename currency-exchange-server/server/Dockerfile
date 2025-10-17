# Sử dụng OpenJDK base image
FROM openjdk:17-slim

# Cài đặt curl và wget để tải thư viện
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# Tạo thư mục làm việc
WORKDIR /app

# Tải thư viện JSON cho Java
RUN wget https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar -O json.jar

# Copy source code
COPY ExchangeRateAPI.java .
COPY CurrencyServer.java .

# Compile với classpath chứa thư viện JSON
RUN javac -cp ".:json.jar" ExchangeRateAPI.java CurrencyServer.java

# Expose port
EXPOSE 8888

# Chạy server
CMD ["java", "-cp", ".:json.jar", "CurrencyServer"]
