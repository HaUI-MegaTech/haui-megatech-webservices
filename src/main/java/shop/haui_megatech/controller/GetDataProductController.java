package shop.haui_megatech.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/getDataProductByLink")
public class GetDataProductController {

    @GetMapping("")
    String productInformation(@RequestParam String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element productld = document.getElementById("productld");

            String jsonContent = productld.html();
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray additionalProperties = jsonObject.getJSONArray("additionalProperty");

            JSONArray lishInfo = new JSONArray();
            for (int i = 0; i < additionalProperties.length(); i++) {
                JSONObject property = additionalProperties.getJSONObject(i);
                String value = Jsoup.parse(property.getString("value")).text();
                lishInfo.put(value);
            }
            return lishInfo.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

