package com.seekers.seekerback.util.stream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

/**
 * program: seeker-back
 * description: stream util
 * author: Zituo Yan
 * create: 2020-11-02
 **/
public class StreamUtil {

    public static String uploadImage(String url){
        byte[] bytes = loadImage(url);
        return compress(byteToString(bytes));
    }

    private static byte[] loadImage(String url){
        File file = new File(url);
        byte[] data = null;
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream outputStream = null;
        try{
            fileInputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream((int) file.length());
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0 , len);
            }
            data = outputStream.toByteArray();
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static String byteToString(byte[] data) {
        String dataString = null;
        dataString = new String(data, StandardCharsets.ISO_8859_1);
        return dataString;
    }

    private static String compress(String data) {
        String finalData = null;
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            GZIPOutputStream gout = new GZIPOutputStream(bout);
            gout.write(data.getBytes(StandardCharsets.ISO_8859_1));
            gout.finish();
            gout.close();
            finalData = bout.toString(StandardCharsets.ISO_8859_1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalData;
    }
}
