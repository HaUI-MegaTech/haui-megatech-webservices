package shop.haui_megatech.utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import shop.haui_megatech.domain.entity.Image;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {
    private static final String   TYPE                  = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String   SHEET                 = "Sheet1";
    private static final String[] USER_HEADER           = {"Username", "Email", "Phone number"};
    private static final String[] UPDATE_PRODUCT_HEADER = {
            "product_id", "name", "old_price", "current_price", "discount_percent",
            "main_img", "short_processor", "short_display", "short_card", "short_weight",

            "processor", "cores", "threads", "frequency", "boost_frequency", "cache_capacity",

            "memory_capacity", "memory_bus", "max_memory_capacity", "storage",

            "display_size", "resolution", "refresh_rate", "color_gamut", "panel_type", "touch_screen",

            "graphics_card", "sound_technology",

            "wireless_connectivity", "sd_card", "webcam", "cooling_fan", "misc_feature", "backlit_keyboard",

            "dimension_weight", "material",

            "battery_capacity", "charger_capacity", "os", "launch_date"
    };

    public static boolean notHasExcelFormat(MultipartFile file) {
        return !TYPE.equals(file.getContentType());
    }

    public static List<User> excelToUsers(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<User> users = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                User user = new User();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            user.setUsername(currentCell.getStringCellValue());
                            break;
                        case 1:
                            user.setEmail(currentCell.getStringCellValue());
                            break;
                        case 2:
                            user.setPhoneNumber(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                users.add(user);
            }
            workbook.close();
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<Product> excelToProducts(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            // Iterator<Row> rows = sheet.iterator();
            List<Product> products = new ArrayList<>();
            int rowNumber = 0;
            for (Row row : sheet) {
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Product product = new Product();
                for (int cellIdx = row.getFirstCellNum(); cellIdx < row.getLastCellNum(); cellIdx++) {
                    Cell currentCell = row.getCell(cellIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    switch (cellIdx) {
                        case 0:
                            product.setId((int) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            product.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            product.setOldPrice((float) currentCell.getNumericCellValue());
                            break;
                        case 3:
                            product.setCurrentPrice((float) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            product.setDiscountPercent((int) currentCell.getNumericCellValue());
                            break;
                        case 5:
                            product.setMainImageUrl(currentCell.getStringCellValue());
                            break;
                        case 6:
                            product.setShortProcessor(currentCell.getStringCellValue());
                            break;
                        case 7:
                            product.setShortDisplay(currentCell.getStringCellValue());
                            break;
                        case 8:
                            product.setShortCard(currentCell.getStringCellValue());
                            break;
                        case 9:
                            product.setShortWeight(currentCell.getStringCellValue());
                            break;

                        case 10:
                            product.setProcessor(currentCell.getStringCellValue());
                            break;
                        case 11:
                            product.setCores((int) currentCell.getNumericCellValue());
                            break;
                        case 12:
                            product.setThreads((int) currentCell.getNumericCellValue());
                            break;
                        case 13:
                            product.setFrequency(currentCell.getStringCellValue());
                            break;
                        case 14:
                            product.setBoostFrequency(currentCell.getStringCellValue());
                            break;
                        case 15:
                            product.setCacheCapacity(currentCell.getStringCellValue());
                            break;

                        case 16:
                            product.setMemoryCapacity(currentCell.getStringCellValue());
                            break;
                        case 17:
                            product.setMemoryType(currentCell.getStringCellValue());
                            break;
                        case 18:
                            product.setMemoryBus(currentCell.getStringCellValue());
                            break;
                        case 19:
                            product.setMaxMemoryCapacity(currentCell.getStringCellValue());
                            break;
                        case 20:
                            product.setStorage(currentCell.getStringCellValue());
                            break;

                        case 21:
                            product.setDisplaySize(currentCell.getStringCellValue());
                            break;
                        case 22:
                            product.setResolution(currentCell.getStringCellValue());
                            break;
                        case 23:
                            product.setRefreshRate(currentCell.getStringCellValue());
                            break;
                        case 24:
                            product.setColorGamut(currentCell.getStringCellValue());
                            break;
                        case 25:
                            product.setPanelType(currentCell.getStringCellValue());
                            break;
                        case 26:
                            product.setTouchScreen(currentCell.getStringCellValue());
                            break;

                        case 27:
                            product.setGraphicsCard(currentCell.getStringCellValue());
                            break;
                        case 28:
                            product.setSoundTechnology(currentCell.getStringCellValue());
                            break;

                        case 29:
                            product.setWirelessConnectivity(currentCell.getStringCellValue());
                            break;
                        case 30:
                            product.setSdCard(currentCell.getStringCellValue());
                            break;
                        case 31:
                            product.setWebcam(currentCell.getStringCellValue());
                            break;
                        case 32:
                            product.setCoolingFan(currentCell.getStringCellValue());
                            break;
                        case 33:
                            product.setMiscFeature(currentCell.getStringCellValue());
                            break;
                        case 34:
                            product.setBacklitKeyboard(currentCell.getStringCellValue());
                            break;

                        case 35:
                            product.setDimensionWeight(currentCell.getStringCellValue());
                            break;
                        case 36:
                            product.setMaterial(currentCell.getStringCellValue());
                            break;

                        case 37:
                            product.setBatteryCapacity(currentCell.getStringCellValue());
                            break;
                        case 38:
                            product.setChargerCapacity(currentCell.getStringCellValue());
                            break;
                        case 39:
                            product.setOs(currentCell.getStringCellValue());
                            break;
                        case 40:
                            product.setLaunchDate((int) currentCell.getNumericCellValue());
                            break;

                        default:
                            if (currentCell.getStringCellValue() != null
                                    && !currentCell.getStringCellValue().isBlank()) {
                                if (product.getImages() == null) {
                                    product.setImages(new ArrayList<>());
                                }

                                product.getImages().add(Image.builder()
                                                             .url(currentCell.getStringCellValue())
                                                             .product(product)
                                                             .build());

                            }
                            break;
                    }
                }
                products.add(product);
            }
            workbook.close();
            return products;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }
}

