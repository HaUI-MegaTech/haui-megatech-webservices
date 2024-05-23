package shop.haui_megatech.utility;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.json.JSONException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class QRCodeGeneratorUtil {
    public static void generateQRCode(String contentQr, String folder, String nameQr, OutputStream outputStream)
            throws WriterException, IOException, JSONException {
        String qrCodePath = "src/main/resources/" + folder + "/";
        String qrCodeName = qrCodePath + nameQr;

        var qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(contentQr,
                BarcodeFormat.QR_CODE, 400, 400);

        MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);

        BufferedImage logo = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);
        // Getting logo image
        BufferedImage logoImage = ImageIO.read(new File("src\\main\\resources\\logo.jpg"));
        int finalImageHeight = logo.getHeight() - logoImage.getHeight();
        int finalImageWidth = logo.getWidth() - logoImage.getWidth();
        //Merging both images
        BufferedImage finalImage = new BufferedImage(logo.getHeight(), logo.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
        graphics.drawImage(logo, 0, 0, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        graphics.drawImage(logoImage, (int) Math.round(finalImageWidth / 2), (int) Math.round(finalImageHeight / 2), null);

        ImageIO.write(finalImage, "PNG", outputStream);
        ImageIO.write(finalImage, "PNG", new File(qrCodeName + ".png"));

        System.out.println("QR Code with Logo Generated Successfully");
    }

    public static String decodeQr(byte[] data) throws IOException, NotFoundException {
        Result result = new MultiFormatReader()
                .decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(
                        ImageIO.read(new ByteArrayInputStream(data))))));
        return result != null ? result.getText() : null;
    }
}
