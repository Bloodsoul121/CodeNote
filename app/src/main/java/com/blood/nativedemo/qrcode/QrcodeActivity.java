package com.blood.nativedemo.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.blood.nativedemo.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public class QrcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ImageView qrcodeIv = findViewById(R.id.qrcode_iv);
        qrcodeIv.post(() -> {
            String url = "https://www.baidu.com";
            int qrcodeWidth = qrcodeIv.getWidth();
            int qrcodeHeight = qrcodeIv.getHeight();
            Bitmap qrcodeBitmap = qrCode2Bitmap(url, qrcodeWidth, qrcodeHeight);
            Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pay);
            Bitmap resultBitmap = addLogo(qrcodeBitmap, logoBitmap, 0.5f);
            qrcodeIv.setImageBitmap(resultBitmap);
        });
    }

    // 将 url 转为二维码图片
    private Bitmap qrCode2Bitmap(String url, int widthPix, int heightPix) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //容错级别
        hints.put(EncodeHintType.MARGIN, 3); //设置空白边距的宽度 //default is 4
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

/**
 * 向二维码中间添加logo图片(图片合成)
 *
 * @param srcBitmap   原图片（生成的简单二维码图片）
 * @param logoBitmap  logo图片
 * @param logoPercent 百分比 (用于调整logo图片在原图片中的显示大小, 取值范围[0,1] )
 */
private Bitmap addLogo(Bitmap srcBitmap, Bitmap logoBitmap, float logoPercent) {
    if (srcBitmap == null) {
        return null;
    }
    if (logoBitmap == null) {
        return srcBitmap;
    }
    //传值不合法时使用0.2F
    if (logoPercent < 0F || logoPercent > 1F) {
        logoPercent = 0.2F;
    }

    /* 1. 获取原图片和Logo图片各自的宽、高值 */
    int srcWidth = srcBitmap.getWidth();
    int srcHeight = srcBitmap.getHeight();
    int logoWidth = logoBitmap.getWidth();
    int logoHeight = logoBitmap.getHeight();

    /* 2. 计算画布缩放的宽高比 */
    float scaleWidth = srcWidth * logoPercent / logoWidth;
    float scaleHeight = srcHeight * logoPercent / logoHeight;

    /* 3. 使用Canvas绘制,合成图片 */
    Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    canvas.drawBitmap(srcBitmap, 0, 0, null);
    canvas.scale(scaleWidth, scaleHeight, srcWidth / 2, srcHeight / 2);
    canvas.drawBitmap(logoBitmap, srcWidth / 2 - logoWidth / 2, srcHeight / 2 - logoHeight / 2, null);

    return bitmap;
}

}