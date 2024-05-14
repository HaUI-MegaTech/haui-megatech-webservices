package shop.haui_megatech.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import shop.haui_megatech.job.crawl.Item;
import shop.haui_megatech.job.crawl.SearchResult;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//http://localhost:8080/api/v1/outer-search/?compare={tenSP}
@RestController
@RequestMapping(path = "/api/v1")
public class OuterSearchRestController {

    private static final String API_KEY          = "AIzaSyBvQShdKz6SVuFY94HW9XOs4joQ0YS9DgU";
    private static final String SEARCH_ENGINE_ID = "04427d5135e124d17";


    @GetMapping("/outer-search")
    public SearchResult search(@RequestParam String compare) throws Exception {
        try {
            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher matcher = pattern.matcher(compare);
            String id = "";
            if (matcher.find()) {
                id = matcher.group(1);
            } else {
                id = compare + " giá";
            }

            String encodedKeyword = URLEncoder.encode(id, StandardCharsets.UTF_8);
            String apiUrl = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY +
                            "&cx=" + SEARCH_ENGINE_ID + "&q=" + encodedKeyword + "&num=10";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SearchResult> response = restTemplate.getForEntity(apiUrl, SearchResult.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody().getItems() == null || response.getBody().getItems().isEmpty()) {
                return new SearchResult();
            }

            SearchResult searchResult = response.getBody();


            String[] Classs = {
                    ".product-price", ".box-price-present", ".detail-product-old-price", ".pro-price", ".price", ".productPriceMain", ".product__price--show",
                    ".giakm", ".gia-km-cu"
            };
            for (Item item : searchResult.getItems()) {
                String price = "";
                Document doc = Jsoup.connect(item.getLink()).get();

                for (String Class : Classs) {
                    Elements priceElements = doc.select(Class);
                    if (!priceElements.isEmpty()) {
                        price = priceElements.first().text();
                        break;
                    }
                }
                String pricePattern = "\\d{1,3}(\\.\\d{3})*(,\\d{3})*";
                Pattern pattern1 = Pattern.compile(pricePattern);
                Matcher matcher1 = pattern1.matcher(price);
                if (matcher1.find()) {
                    price = matcher1.group();
                    price = price.replaceAll(",", ".");
                    long numPrice = Long.parseLong(price.replace(".", ""));
                    if (numPrice <= 3000000) {
                        price = "";
                    }
                }
                item.setPrice(price);
            }
            return searchResult;
        } catch (Exception e) {
            return new SearchResult();
        }
    }
}
