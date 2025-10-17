import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ExchangeRateAPI {
    // Sử dụng API miễn phí từ exchangerate-api.com
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    
    /**
     * Lấy tỉ giá từ API
     * @param fromCurrency Mã tiền tệ nguồn (USD, EUR, VND...)
     * @param toCurrency Mã tiền tệ đích
     * @return Tỉ giá hoặc -1 nếu có lỗi
     */
    public static double getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            // Tạo URL request
            String urlString = API_URL + fromCurrency.toUpperCase();
            URL url = new URL(urlString);
            
            // Mở kết nối HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            // Đọc response
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                // Parse JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject rates = jsonResponse.getJSONObject("rates");
                
                // Lấy tỉ giá
                if (rates.has(toCurrency.toUpperCase())) {
                    return rates.getDouble(toCurrency.toUpperCase());
                } else {
                    System.err.println("Không tìm thấy mã tiền tệ: " + toCurrency);
                    return -1;
                }
            } else {
                System.err.println("API trả về lỗi: " + responseCode);
                return -1;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Chuyển đổi tiền tệ
     * @param amount Số tiền cần chuyển đổi
     * @param fromCurrency Mã tiền tệ nguồn
     * @param toCurrency Mã tiền tệ đích
     * @return Số tiền sau khi chuyển đổi
     */
    public static double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double rate = getExchangeRate(fromCurrency, toCurrency);
        if (rate > 0) {
            return amount * rate;
        }
        return -1;
    }
}
