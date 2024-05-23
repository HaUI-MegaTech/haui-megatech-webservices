package shop.haui_megatech.utility;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {
    private static final String   TYPE           = "text/csv";
    private static final String[] USER_HEADER    = {"Username", "Email", "Phone number"};
    private static final String[] PRODUCT_HEADER = {"Name", "Price"};

    public static boolean hasCsvFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<User> csvToUsers(InputStream is) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(bufferedReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader()
                                         .withIgnoreHeaderCase()
                                         .withTrim())
        ) {
            List<User> users = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                User user = new User();
                user.setUsername(csvRecord.get("Username"));
                user.setEmail(csvRecord.get("Email"));
                user.setPhoneNumber(csvRecord.get("Phone number"));
                users.add(user);
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
        }
    }

    public static List<Product> csvToProducts(InputStream is) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(bufferedReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader()
                                         .withIgnoreHeaderCase()
                                         .withTrim())
        ) {
            List<Product> products = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Product product = new Product();
                product.setName(csvRecord.get("Username"));
                product.setOldPrice(Float.parseFloat(csvRecord.get("Email")));
                products.add(product);
            }
            return products;
        } catch (IOException e) {
            throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
        }
    }
}

