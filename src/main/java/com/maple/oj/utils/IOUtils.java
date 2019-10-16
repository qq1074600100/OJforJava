package com.maple.oj.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class IOUtils {

    //递归删除文件夹中的文件
    public static void deleteFolder(File folder) {
        File[] fileList = folder.listFiles();
        for (File file : fileList) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteFolder(file);
            }
        }
        folder.delete();
    }

    //把传入的程序写入.java文件中
    public static void write2File(String content, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    //把传入的程序写入.java文件中
    public static void testCase2PackageJava(File testCaseFile, File javaFile, String header) {
        try (BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(testCaseFile)));
             BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)))) {
            fw.write(header);
            String line = "";
            while ((line = fr.readLine()) != null) {
                fw.write(line + "\n");
                fw.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从控制台输入流中读取全部结果到字符串中
    public static String readCmd2String(InputStream is, String charSet) {
        StringBuilder msg = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName(charSet)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                msg.append(line + "\n");
            }
            if (msg.toString().length() > 0) {
                msg.deleteCharAt(msg.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg.toString();
    }

    //将字符串换行转为HTML换行，便于前端显示
    public static String string2Html(String msg) {
        return msg.replace("\n", "<br/>");
    }

    public static <T extends Comparable> T getMax(List<T> list) {
        T max = list.get(0);
        for (T item : list) {
            if (item.compareTo(max) > 0) {
                max = item;
            }
        }
        return max;
    }

    //从文件中读取全部结果到字符串中
    public static String readFile2String(File file, String charSet) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), Charset.forName(charSet)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
            if (content.toString().length() > 0) {
                content.deleteCharAt(content.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
