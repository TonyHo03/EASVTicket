package dk.easv.easvticket.BLL.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class QRGenerator {

    public static BufferedImage generate(String payload) throws Exception {

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(payload, BarcodeFormat.QR_CODE, 300, 300);
        return MatrixToImageWriter.toBufferedImage(matrix);

    }

    public static void saveAsPNG(String payload, String filePath) throws Exception {

        BufferedImage image = generate(payload);
        ImageIO.write(image, "png", new File(filePath));

    }

}
