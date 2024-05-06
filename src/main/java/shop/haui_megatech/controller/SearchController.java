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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


record PageMap(List<Cse_Image> cse_image) {}

record Cse_Image(String src) {
}

class Item {
    private String  link;
    private String  title;
    private PageMap pagemap;
    private String  price;

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

    public PageMap getPagemap() {
        return pagemap;
    }

    public void setPagemap(PageMap pagemap) {
        this.pagemap = pagemap;
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

//http://localhost:8080/api/v1?compare={tenSP}
@RestController
@RequestMapping(path = "/api/v1")
public class SearchController {

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

