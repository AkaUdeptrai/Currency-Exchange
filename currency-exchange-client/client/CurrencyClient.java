import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CurrencyClient {
    private static final String DEFAULT_HOST = "currency-server";
    private static final int DEFAULT_PORT = 8888;
    
    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        
        // Cho phép override host/port từ biến môi trường
        String envHost = System.getenv("SERVER_HOST");
        String envPort = System.getenv("SERVER_PORT");
        
        if (envHost != null && !envHost.isEmpty()) {
            host = envHost;
        }
        if (envPort != null && !envPort.isEmpty()) {
            try {
                port = Integer.parseInt(envPort);
            } catch (NumberFormatException e) {
                System.err.println("Port không hợp lệ, sử dụng mặc định: " + DEFAULT_PORT);
            }
        }
        
        System.out.println("=================================");
        System.out.println("Currency Exchange Client");
        System.out.println("=================================");
        System.out.println("Đang kết nối đến " + host + ":" + port + "...");
        
        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Kết nối thành công!\n");
            
            // Đọc và hiển thị thông báo từ server
            String serverMessage;
            while (in.ready() && (serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }
            
            // Thread để nhận response từ server
            Thread receiveThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("\n" + response);
                        if (response.equals("Tạm biệt!")) {
                            System.exit(0);
                        }
                        System.out.print("\nNhập request: ");
                    }
                } catch (IOException e) {
                    System.err.println("Mất kết nối với server");
                }
            });
            
            receiveThread.setDaemon(true);
            receiveThread.start();
            
            // Gửi request đến server
            System.out.println("\n--- Các mã tiền tệ phổ biến ---");
            System.out.println("USD (Đô la Mỹ), EUR (Euro), GBP (Bảng Anh)");
            System.out.println("VND (Việt Nam Đồng), JPY (Yên Nhật), CNY (Nhân dân tệ)");
            System.out.println("KRW (Won Hàn Quốc), THB (Baht Thái), SGD (Đô la Singapore)");
            System.out.println("-------------------------------\n");
            
            while (true) {
                System.out.print("Nhập request: ");
                String userInput = scanner.nextLine().trim();
                
                if (userInput.isEmpty()) {
                    continue;
                }
                
                out.println(userInput);
                
                if (userInput.equalsIgnoreCase("exit")) {
                    Thread.sleep(500); // Đợi nhận response cuối
                    break;
                }
                
                Thread.sleep(100); // Đợi response
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Không tìm thấy host: " + host);
            System.err.println("Đảm bảo server đang chạy và host name đúng.");
        } catch (ConnectException e) {
            System.err.println("Không thể kết nối đến server: " + host + ":" + port);
            System.err.println("Đảm bảo server đang chạy.");
        } catch (IOException e) {
            System.err.println("Lỗi I/O: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Thread bị gián đoạn");
        }
    }
}
