package shop.haui_megatech.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/getDataProductByLink")
public class GetDataProductController {

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

    @GetMapping("")
    String productInformation() {
        String inputFile = "ListLink.txt";
        String outputFile = "DataLaptop.txt";
        String[] propertyList = {
                "Công nghệ CPU", "Số nhân", "Số luồng", "Tốc độ CPU", "Tốc độ tối đa", "Bộ nhớ đệm",
                "RAM", "Loại RAM", "Tốc độ Bus RAM", "Hỗ trợ RAM tối đa", "Ổ cứng", "Màn hình", "Độ phân giải", "Tần số quét", "Độ phủ màu",
                "Công nghệ màn hình", "Màn hình cảm ứng",
                "Card màn hình", "Công nghệ âm thanh",
                "Cổng giao tiếp", "Kết nối không dây", "Khe đọc thẻ nhớ", "Webcam", "Tản nhiệt", "Tính năng khác", "Đèn bàn phím",
                "Kích thước, khối lượng", "Chất liệu",
                "Thông tin Pin", "Công suất bộ sạc", "Hệ điều hành", "Thời điểm ra mắt"
        };


        List<String> urls = readUrlsFromFile(inputFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("url;productDetailsImgLink;");
            writer.write(String.join(";", propertyList));
            writer.newLine();
            for (String url : urls) {
                try {
                    List<String> productInfo = new ArrayList<>();
                    productInfo.add(url);
                    Document document = Jsoup.connect(url).get();

                    Element productDetailsImgElement = document.select("div.img-main img.lazyload").first();

                    String productDetailsImgLink = "";
                    if (productDetailsImgElement != null) {
                        productDetailsImgLink = productDetailsImgElement.attr("data-src");
                    }
                    productInfo.add(productDetailsImgLink);

                    Element productld = document.getElementById("productld");
                    if (productld != null) {
                        String jsonContent = productld.html();
                        JSONObject jsonObject = new JSONObject(jsonContent);
                        JSONArray additionalProperties = jsonObject.getJSONArray("additionalProperty");


                        int j = 0;
                        for (int i = 0; i < additionalProperties.length(); i++) {
                            JSONObject property = additionalProperties.getJSONObject(i);
                            String name = property.getString("name");
                            if (!name.equals(propertyList[j])) {
                                productInfo.add("");
                                j++;
                            }
                            String value = Jsoup.parse(property.getString("value")).text();
                            productInfo.add(value);
                            j++;
                        }
                        Elements bannerImgElements = document.select("div.detail-slider img");

                        productInfo.add(bannerImgElements.get(0).attr("src"));
                        for (int i = 1; i < bannerImgElements.size(); i++) {
                            Element bannerImgElement = bannerImgElements.get(i);
                            String dataSrc = bannerImgElement.attr("data-src");
                            productInfo.add(dataSrc);
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
}

