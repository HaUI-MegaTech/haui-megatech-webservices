package shop.haui_megatech.controller;

import org.jsoup.Jsoup;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/getDataProductByLink")
public class GetDataProductController {

    @GetMapping("")
    String productInformation() {
        String inputFile = "ListLink.txt";
        String outputFile = "DataLaptop.txt";
        String[] propertyList = {
                "Công nghệ CPU", "Số nhân", "Số luồng", "Tốc độ CPU", "Tốc độ tối đa", "Bộ nhớ đệm",
                "RAM", "Loại RAM", "Tốc độ Bus RAM", "Hỗ trợ RAM tối đa", "Ổ cứng", "Màn hình", "Độ phân giải", "Tần số quét", "Độ phủ màu", "Công nghệ màn hình", "Màn hình cảm ứng",
                "Card màn hình", "Công nghệ âm thanh",
                "Cổng giao tiếp", "Kết nối không dây", "Khe đọc thẻ nhớ", "Webcam", "Tản nhiệt", "Tính năng khác", "Đèn bàn phím",
                "Kích thước, khối lượng", "Chất liệu",
                "Thông tin Pin", "Công suất bộ sạc", "Hệ điều hành", "Thời điểm ra mắt"
        };

        List<String> urls = readUrlsFromFile(inputFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("url;");
            writer.write(String.join(";", propertyList));writer.newLine();
            for (String url : urls) {
                try {
                    Document document = Jsoup.connect(url).get();
                    Element productld = document.getElementById("productld");

                    if (productld != null) {
                        String jsonContent = productld.html();
                        JSONObject jsonObject = new JSONObject(jsonContent);
                        JSONArray additionalProperties = jsonObject.getJSONArray("additionalProperty");

                        List<String> productInfo = new ArrayList<>();
                        productInfo.add(url);
                        int j=0;
                        for (int i = 0; i < additionalProperties.length(); i++) {
                            JSONObject property = additionalProperties.getJSONObject(i);
                            String name = property.getString("name");
                            if (!name.equals(propertyList[j])){
                                productInfo.add("");
                                j++;
                            }
                            String value = Jsoup.parse(property.getString("value")).text();
                            productInfo.add(value);
                            j++;
                        }
                        writer.write(String.join(";", productInfo));
                        writer.newLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static List<String> readUrlsFromFile(String inputFile) {
        List<String> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                urls.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }
}

