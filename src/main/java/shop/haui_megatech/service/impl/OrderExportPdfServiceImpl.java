package shop.haui_megatech.service.impl;

import com.google.zxing.WriterException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import shop.haui_megatech.domain.dto.order.QrOrderItemResponseDTO;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.utility.QRCodeGeneratorUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
public class OrderExportPdfServiceImpl {
    public static void setHeader() {

    }

    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getIRDCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    public static PdfPCell getBillHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getBillRowCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getBillFooterCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getValidityCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }

    public static PdfPCell getdescCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        return cell;
    }
    //public void export(QrOrderItemResponseDTO qrcode, Order order, HttpServletResponse response)
    public void export(Order order, HttpServletResponse response)
            throws DocumentException, IOException, JSONException, WriterException {
        String content = "http://192.168.0.102:8080/api/v1/orders/admin/detail/" + order.getId();
        String folder = "QrForAPIOrderDetail";
        String name = "Id" + order.getId() + "-OrderDetail";
        QRCodeGeneratorUtil.generateQRCode(content, folder, name, response.getOutputStream());

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedTime = dateFormat.format(new Date());

        String path = "src/main/resources/pdf/Order" + order.getId() + formattedTime + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        //Inserting Image in PDF
        Image image = Image.getInstance("src/main/resources/logo1.jpg");//Header Image
        image.scaleAbsolute(540f, 100f);//image width,height

        //System.out.println("src/main/resources/" + folder + "/" + name + ".png");

        Image imageQr = Image.getInstance("src/main/resources/qrcode/" + folder + "/" + name + ".png");//Header Image

        imageQr.scaleAbsolute(100f, 100f);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{4, 1});

        PdfPCell cell1 = new PdfPCell(image, true);
        cell1.setBorder(0);
        cell1.setFixedHeight(100f);

        PdfPCell cell2 = new PdfPCell(imageQr, true);
        cell2.setFixedHeight(100f);
        cell2.setBorder(0);

        headerTable.addCell(cell1);
        headerTable.addCell(cell2);

        PdfPTable irdTable = new PdfPTable(2);
        irdTable.addCell(getIRDCell("Order No"));
        irdTable.addCell(getIRDCell("Order Time Export"));
        irdTable.addCell(getIRDCell(order.getId().toString())); // pass invoice number
        irdTable.addCell(getIRDCell(new Date().toString())); // pass invoice date

        PdfPTable irhTable = new PdfPTable(3);
        irhTable.setWidthPercentage(100);

        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("Order", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));

        PdfPCell invoiceTable = new PdfPCell(irdTable);
        invoiceTable.setBorder(0);

        irhTable.addCell(invoiceTable);

        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
        fs.addFont(font);
        Phrase bill = fs.process("Detail Information:"); // customer information
        Paragraph firstName = new Paragraph("Firstname: " + order.getUser().getFirstName());
        firstName.setIndentationLeft(20);
        Paragraph lastName = new Paragraph("Lastname: " + order.getUser().getLastName());
        lastName.setIndentationLeft(20);
        Paragraph contact = new Paragraph("Contact: " + order.getUser().getPhoneNumber());
        contact.setIndentationLeft(20);
//        Paragraph address = new Paragraph("Address order: " + order.getAddress());
//        address.setIndentationLeft(20);
        Paragraph orderTime = new Paragraph("Order Time: " + order.getOrderTime());
//        address.setIndentationLeft(20);
        Paragraph status = new Paragraph("Status: " + order.getStatus());
//        address.setIndentationLeft(20);

        PdfPTable billTable = new PdfPTable(6); //one page contains 15 records
        billTable.setWidthPercentage(100);
        billTable.setWidths(new float[]{1, 2, 3, 2, 1, 2});
        billTable.setSpacingBefore(30.0f);
        billTable.addCell(getBillHeaderCell("Index"));
        billTable.addCell(getBillHeaderCell("Product Id"));
        billTable.addCell(getBillHeaderCell("Product Name"));
        billTable.addCell(getBillHeaderCell("Unit Price"));
        billTable.addCell(getBillHeaderCell("Qty"));
        billTable.addCell(getBillHeaderCell("Amount (Unit: VND)"));

        DecimalFormat format = new DecimalFormat();
        for (int i = 0; i < order.getOrderDetails().size(); i++) {
            billTable.addCell(getBillRowCell(String.valueOf(i + 1)));
            billTable.addCell(getBillRowCell(order.getOrderDetails().get(i).getProduct().getId().toString()));
            billTable.addCell(getBillRowCell(order.getOrderDetails().get(i).getProduct().getName().toString()));
            billTable.addCell(getBillRowCell(format.format(order.getOrderDetails().get(i).getPrice())));
            billTable.addCell(getBillRowCell(order.getOrderDetails().get(i).getQuantity().toString()));
            billTable.addCell(getBillRowCell(format.format(order.getOrderDetails().get(i).getPrice()
                                                           * order.getOrderDetails().get(i).getQuantity())));
        }
        System.out.println(order.getOrderDetails().size());
        if (order.getOrderDetails().size() < 10) {
            for (int i = 1; i <= (10 - order.getOrderDetails().size()); i++) {
                billTable.addCell(getBillRowCell(" "));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
            }
        }

        PdfPTable validity = new PdfPTable(1);
        validity.setWidthPercentage(100);
        validity.addCell(getValidityCell(" "));
        validity.addCell(getValidityCell("Warranty"));
        validity.addCell(getValidityCell(" * Products purchased comes with 1 year national warranty \n   (if applicable)"));
        validity.addCell(getValidityCell(" * Warranty should be claimed only from the respective manufactures"));
        PdfPCell summaryL = new PdfPCell(validity);
        summaryL.setColspan(3);
        summaryL.setPadding(1.0f);
        billTable.addCell(summaryL);

        PdfPTable accounts = new PdfPTable(2);
        accounts.setWidthPercentage(100);
        accounts.addCell(getAccountsCell("Subtotal"));
        accounts.addCell(getAccountsCellR(format.format(order.getSubTotal())));
        accounts.addCell(getAccountsCell("Discount (0%)"));
        accounts.addCell(getAccountsCellR("0"));
        accounts.addCell(getAccountsCell("Tax"));
        accounts.addCell(getAccountsCellR(format.format(order.getTax())));
        accounts.addCell(getAccountsCell("Total"));
        accounts.addCell(getAccountsCellR(format.format(order.getTotal())));
        PdfPCell summaryR = new PdfPCell(accounts);
        summaryR.setColspan(3);
        billTable.addCell(summaryR);

        PdfPTable describer = new PdfPTable(1);
        describer.setWidthPercentage(100);
        describer.addCell(getdescCell(" "));
        describer.addCell(getdescCell("Goods once sold will not be taken back or exchanged || Subject to product justification || Product damage no one responsible || "
                                      + " Service only at concarned authorized service centers"));

        document.open();//PDF document opened........

        document.add(headerTable);
        document.add(irhTable);
        document.add(bill);
        document.add(firstName);
        document.add(lastName);
        document.add(contact);
        //document.add(address);
        document.add(orderTime);
        document.add(status);
        document.add(billTable);
        document.add(describer);

        document.close();

        System.out.println("Pdf created successfully..");
    }
}
