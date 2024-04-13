package shop.haui_megatech.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Item {
    private String link;
    private String title;
    private String price;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    private static final String API_KEY = "AIzaSyBvQShdKz6SVuFY94HW9XOs4joQ0YS9DgU";
    private static final String SEARCH_ENGINE_ID = "04427d5135e124d17";

    @GetMapping("/search")
    public SearchResult search(@RequestParam String keyword) throws Exception {
        String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
        String apiUrl = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY +
                "&cx=" + SEARCH_ENGINE_ID + "&q=" + encodedKeyword + "&num=10";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SearchResult> response = restTemplate.getForEntity(apiUrl, SearchResult.class);

        SearchResult searchResult = response.getBody();

        String[] Classs = {".giakm",".detail-product-old-price", ".gia-km-cu", ".pro-price", ".price", ".product-price",".productPriceMain",".product__price--show"};
        for (Item item : searchResult.getItems()){
            String price ="";
            Document doc = Jsoup.connect(item.getLink()).get();

            for (String Class : Classs) {
                Elements priceElements = doc.select(Class);
                if (!priceElements.isEmpty()) {
                    price = priceElements.first().text();
                    break;
                }
            }
            String pricePattern = "\\d{1,3}(\\.\\d{3})*(,\\d{3})*";
            Pattern pattern = Pattern.compile(pricePattern);
            Matcher matcher = pattern.matcher(price);
            if (matcher.find()) {
                price = matcher.group();
            }
            item.setPrice(price);
        }
        System.out.println(apiUrl);
        return response.getBody();
    }
}


