package com.blood.nativedemo.webview;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DataHelper {
    private Map<String, String> mMap;

    public DataHelper() {
        mMap = new HashMap<>();
        initData();
    }

    private void initData() {
        String imageDir = "images/";
        String pngSuffix = ".png";
        mMap.put("http://renyugang.io/wp-content/themes/twentyseventeen/style.css?ver=4.9.8", "css/style.css");
        mMap.put("http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg.png", imageDir + "cropped-ryg.png");
    }

    public boolean hasLocalResource(String url) {
        return mMap.containsKey(url);
    }

    public WebResourceResponse getReplacedWebResourceResponse(Context context, String url) {
        String localResourcePath = mMap.get(url);
        if (TextUtils.isEmpty(localResourcePath)) {
            return null;
        }
        InputStream is;
        try {
            is = context.getApplicationContext().getAssets().open(localResourcePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String mimeType;
        if (url.contains("css")) {
            mimeType = "text/css";
        } else if (url.contains("jpg")) {
            mimeType = "image/jpeg";
        } else {
            mimeType = "image/png";
        }
        return new WebResourceResponse(mimeType, "utf-8", is);
    }
}
