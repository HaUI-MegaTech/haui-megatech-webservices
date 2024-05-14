package shop.haui_megatech.job.crawl;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.SimilarProduct;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.repository.SimilarProductRepository;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class ExternalCrawler {
    private final ProductRepository        productRepository;
    private final SimilarProductRepository similarProductRepository;

    public void crawl() {
        List<Product> list = productRepository.findAll();

        final String[] API_KEYS = {
                "AIzaSyCdnNclHsrs14Zvb0g99tPrXTokRNagHm4",
                "AIzaSyDVR3x7OEGB93c9JRbgZ2uxLSfIjX22GIA",
                "AIzaSyBvQShdKz6SVuFY94HW9XOs4joQ0YS9DgU"
        };
        final String SEARCH_ENGINE_ID = "04427d5135e124d17";

        int i = 1, j = 0;
        while (i < 205) {
            final String API_KEY = API_KEYS[j];
            if (i % 100 == 0) j++;
            final Integer index = i;
            new Thread(() -> {
                String compare = list.get(index).getName();

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

                    if (response.getStatusCode() != HttpStatus.OK
                        || response.getBody() == null
                        || response.getBody().getItems() == null
                        || response.getBody().getItems().isEmpty()) {

                    }

                    SearchResult searchResult = response.getBody();

                    String[] Classs = {
                            ".product-price", ".box-price-present", ".detail-product-old-price", ".pro-price", ".price", ".productPriceMain",
                            ".product__price--show",
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
                        SimilarProduct similarProduct = new SimilarProduct();
                        similarProduct.setProduct(list.get(index));
                        similarProduct.setProductUrl(item.getLink());
                        similarProduct.setProductName(item.getTitle());
                        similarProduct.setProductImageUrl(item.getPagemap().getCse_image().get(0).getSrc());
                        similarProduct.setPrice(price);
                        SimilarProduct saved = similarProductRepository.save(similarProduct);
                    }
                } catch (Exception e) {}
                System.out.println("Completed " + index);
            }).start();
            i++;
        }
    }
}