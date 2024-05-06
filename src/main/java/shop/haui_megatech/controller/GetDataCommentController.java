package shop.haui_megatech.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/getDataCommentByLink")
public class GetDataCommentController {

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
        String outputFile = "DataComment.txt";

        List<String> urls = readUrlsFromFile(inputFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(String.join(";", "name", "reviewBody", "ratingValue"));
            writer.newLine();
            for (String url : urls) {
                try {
                    Document document = Jsoup.connect(url).get();
                    Element productld = document.getElementById("productld");

                    if (productld != null) {
                        String jsonContent = productld.html();
                        JSONObject jsonObject = new JSONObject(jsonContent);

                        Object reviewObject = jsonObject.get("review");
                        if (reviewObject instanceof JSONArray reviews) {

                            for (int i = 0; i < reviews.length(); i++) {
                                JSONObject jsonObject1 = reviews.getJSONObject(i);
                                String name = jsonObject1.getJSONObject("author").optString("name");
                                String reviewBody = jsonObject1.optString("reviewBody").replace("\r\n", " ");
                                String ratingValue = jsonObject1.getJSONObject("reviewRating").optString("ratingValue");

                                writer.write(String.join(";", name, reviewBody, ratingValue));
                                writer.newLine();
                            }
                        }
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

