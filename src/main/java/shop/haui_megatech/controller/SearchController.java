package shop.haui_megatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


class Item {
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
class SearchResult {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
@RestController
public class SearchController {

    private static final String API_KEY = "AIzaSyBvQShdKz6SVuFY94HW9XOs4joQ0YS9DgU"; // Thay YOUR_API_KEY bằng khóa API của bạn
    private static final String SEARCH_ENGINE_ID = "04427d5135e124d17"; // Thay YOUR_SEARCH_ENGINE_ID bằng ID của công cụ tìm kiếm của bạn

    @GetMapping("/search")
    public List<String> search(@RequestParam String keyword) throws Exception {
        String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
        String apiUrl = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY +
                "&cx=" + SEARCH_ENGINE_ID + "&q=" + encodedKeyword + "&num=10";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SearchResult> response = restTemplate.getForEntity(apiUrl, SearchResult.class);

        SearchResult searchResult = response.getBody();

        List<String> links = new ArrayList<>();
        if (searchResult != null && searchResult.getItems() != null) {
            for (Item item : searchResult.getItems()) {
                links.add(item.getLink());
            }
        }

        String[] Classs = {".detail-product-old-price", ".gia-km-cu", ".pro-price", ".price", ".product-price",".productPriceMain"};
        List<String> Prices = new ArrayList<>();
        for (String link : links){
            String price ="";
            Document doc = Jsoup.connect(link).get();

            for (String Class : Classs) {
                Elements priceElements = doc.select(Class);
                if (!priceElements.isEmpty()) {
                    price = priceElements.first().text();
                    break;
                }
            }
            Prices.add(price);
        }
        System.out.println(links);
        return Prices;
    }
}

