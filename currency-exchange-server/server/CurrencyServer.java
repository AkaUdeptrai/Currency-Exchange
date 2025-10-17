import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class CurrencyServer {
    private static final int PORT = 8888;
    private static final int MAX_THREADS = 10;
    
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("=================================");
            System.out.println("Currency Exchange Server đang chạy");
            System.out.println("Cổng: " + PORT);
            System.out.println("=================================");
            
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client mới kết nối: " + clientSocket.getInetAddress());
                    
                    // Xử lý client trong thread riêng
                    threadPool.execute(new ClientHandler(clientSocket));
                } catch (IOException e) {
                    System.err.println("Lỗi khi chấp nhận kết nối: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không thể khởi động server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
    
    /**
     * Thread xử lý từng client
     */
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );
                PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true
                )
            ) {
                String clientAddress = clientSocket.getInetAddress().toString();
                System.out.println("[" + clientAddress + "] Bắt đầu phiên làm việc");
                
                // Gửi thông báo chào mừng
                out.println("Kết nối thành công đến Currency Exchange Server!");
                out.println("Format: FROM_CURRENCY TO_CURRENCY AMOUNT");
                out.println("Ví dụ: USD VND 100");
                out.println("Gõ 'exit' để thoát.");
                
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("[" + clientAddress + "] Nhận: " + inputLine);
                    
                    if (inputLine.equalsIgnoreCase("exit")) {
                        out.println("Tạm biệt!");
                        break;
                    }
                    
                    // Xử lý request
                    String response = processRequest(inputLine);
                    out.println(response);
                    System.out.println("[" + clientAddress + "] Gửi: " + response);
                }
                
                System.out.println("[" + clientAddress + "] Đã ngắt kết nối");
                
            } catch (IOException e) {
                System.err.println("Lỗi xử lý client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Lỗi đóng socket: " + e.getMessage());
                }
            }
        }
        
        /**
         * Xử lý request từ client
         * Format: FROM_CURRENCY TO_CURRENCY AMOUNT
         */
        private String processRequest(String request) {
            try {
                String[] parts = request.trim().split("\\s+");
                
                if (parts.length != 3) {
                    return "ERROR: Format không đúng. Sử dụng: FROM_CURRENCY TO_CURRENCY AMOUNT";
                }
                
                String fromCurrency = parts[0].toUpperCase();
                String toCurrency = parts[1].toUpperCase();
                double amount;
                
                try {
                    amount = Double.parseDouble(parts[2]);
                    if (amount <= 0) {
                        return "ERROR: Số tiền phải lớn hơn 0";
                    }
                } catch (NumberFormatException e) {
                    return "ERROR: Số tiền không hợp lệ";
                }
                
                // Gọi API để chuyển đổi
                System.out.println("Đang lấy tỉ giá " + fromCurrency + " -> " + toCurrency + "...");
                double result = ExchangeRateAPI.convertCurrency(amount, fromCurrency, toCurrency);
                
                if (result > 0) {
                    return String.format("SUCCESS: %.2f %s = %.2f %s", 
                        amount, fromCurrency, result, toCurrency);
                } else {
                    return "ERROR: Không thể lấy tỉ giá. Kiểm tra mã tiền tệ.";
                }
                
            } catch (Exception e) {
                return "ERROR: " + e.getMessage();
            }
        }
    }
}
