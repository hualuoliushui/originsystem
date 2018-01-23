package com.scut.originsystem.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class QRCodeUtil {
    private static final String sub_dir = "static/qrcode/";
    private static String FILE_PATH = PathUtil.getSaveDir(sub_dir) + File.separator;
    private static final String QRCODE_PREFIX = "img-";
    private static final String QRCODE_POSTFIX = ".jpg";
    private static final String MERCHANT_FOLDER = "merchant-";
    private static final String GOOD_FOLDER = "good-";
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static void createQRCode(List<String > urls,int good_id,int merchant_id){
        System.setProperty("java.specification.version","1.9");
        int width = 200;
        int height = 200;
        String format = "jpg";
        String filePath = FILE_PATH + MERCHANT_FOLDER + merchant_id + File.separator + GOOD_FOLDER + good_id + File.separator;

        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN,2);

        for (String content : urls){
            String fileNmae = content.substring(content.length()-32);
            try {
                BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                File file = new File( filePath + QRCODE_PREFIX + fileNmae + QRCODE_POSTFIX);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                MatrixToImageWriter.writeToPath(matrix,format,file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String createWord(List<String > valids,List<String> urls,int good_id,int merchant_id) throws IOException {
        String filePath = FILE_PATH + MERCHANT_FOLDER + merchant_id + File.separator + GOOD_FOLDER + good_id + File.separator;
        String today = format.format(new Date());
        // 设置纸张大小
        Document document = new Document(PageSize.A4,0L,0L,0L,0L);
        File file = new File(filePath + today + "-" + good_id +"-" + merchant_id + "-" + urls.size() + ".doc");
        if (!file.exists()){
            file.createNewFile();
        }
        RtfWriter2.getInstance(document, new FileOutputStream(file));
        try {
            document.open();

            BaseFont bfChinese = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 30, Font.BOLD);
            Image img = null;
            String content = "";
            Table table = new Table(3,2);
            table.setWidth(100);
            Cell cell = new Cell();
            Paragraph text = new Paragraph();

            int k = 1;
            while(valids.size() >= k){
                int time = 0;
                for (int i = 0; i < 3 && valids.size() >= k; i++,k++,time++) {
                    String fileNmae = urls.get(k - 1).substring(urls.get(k - 1).length()-32);
                    img = Image.getInstance(filePath + QRCODE_PREFIX + fileNmae + QRCODE_POSTFIX);
                    cell.add(img);
                    table.addCell(cell);
                    cell= new Cell();
                }
                for (int i = 0; i < 3 -time ;i++) {
                    text.add("\n");
                    cell.add(text);
                    table.addCell(cell);
                    cell= new Cell();
                    text = new Paragraph();
                }
                for (int i = 0; i < time; i++) {
                    text.setAlignment(Element.ALIGN_CENTER);
                    text.setFont(titleFont);
                    content = "\n" + "\n" + valids.get(k - time - 1 + i) + "\n" +"\n";
                    text.add(content);
                    cell.add(text);
                    table.addCell(cell);
                    text = new Paragraph();
                    cell = new Cell();
                }
                document.add(table);
                table = new Table(3,2);
                table.setWidth(100);
                if(k % 6 == 0){
                    document.newPage();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            document.close();
        }
        return file.getAbsolutePath();
    }

    public static String getQRCodeFilePath(int good_id,int merchant_id){
        String filePath = FILE_PATH + MERCHANT_FOLDER + merchant_id + File.separator + GOOD_FOLDER + good_id + File.separator;
        return filePath;
    }
}
