package com.zl.study.parse.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 周林
 * @Description 解析JSON工具类
 * @email prometheus@noask-ai.com
 * @date 2019/11/21 16:47
 */
public class JsonUtils {

    @Test
    public void getXinHuaZiDianData() throws IOException {
        String commonMapping = "{\n" +
                "  \"mappings\": {\n" +
                "    \"properties\": {\n" +
                "      \"docNameSuggest\" : {\n" +
                "        \"properties\": {\n" +
                "          \"weight\": {\"type\": \"long\"},\n" +
                "          \"contents\": {\"type\": \"completion\"}\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n";

        // 获取当前项目路径
        String property = System.getProperty("user.dir");
        // 当前data所在路径
        String dataPath = property + "\\src\\main\\java\\com\\zl\\study\\parse\\json\\data";
        File dataFile = new File(dataPath);
        File[] dataFiles = dataFile.listFiles();

        List<JSON> wordJsons = new ArrayList<>();
        for (File file : dataFiles) {
            String name = file.getName().replaceAll("\\..+", "");
            // 解析汉字
            switch (name) {
                case "word":
                    String wordJsonData = readJsonFile(file);
                    JSONArray wordArray = JSON.parseArray(wordJsonData);

                    String word = "";
                    for (Object o : wordArray) {
                        JSONObject jsonObject = JSON.parseObject(o.toString());
                        word += jsonObject.get("word").toString()+"\n";
                    }
                    writeText(new File("C:\\Users\\Administrator\\Desktop\\1.txt"), word);
                    System.out.println();
//                    wordJsons.add(JSONObject.parseObject(name));
                    break;
            }
        }
        System.out.println();
    }

    public static String readJsonFile(File jsonFile) {
        String jsonStr;
//        logger.info("————开始读取" + jsonFile.getPath() + "文件————");
        System.out.println("————开始读取" + jsonFile.getPath() + "文件————");
        try {
//            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
//            logger.info("————读取" + jsonFile.getPath() + "文件结束!————");
            System.out.println("————读取" + jsonFile.getPath() + "文件结束!————");
            return jsonStr;
        } catch (Exception e) {
//            logger.info("————读取" + jsonFile.getPath() + "文件出现异常，读取失败!————");
//            System.out.println("————读取" + jsonFile.getPath() + "文件出现异常，读取失败!————");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写内容到文件
     * @param file 文件
     * @param text 内容
     */
    public static void writeText(File file, String text) {
        if (text == null) {
            return;
        }
        try(FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
