package com.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResponseCodeService {

    private Map<String, String> pages;

    public ResponseCodeService() {
        createPagesMap();
    }

    public Map<String, Integer> getAllResponseCodes(){
        Map<String, Integer> codes = new LinkedHashMap<>();
        codes.put("dir", getResponseCode("dir"));
        codes.put("dir Раздел", getResponseCode("dir/testrazdel/19"));
        codes.put("dir Категория", getResponseCode("dir/testrazdel/testkategoriya/20"));
        codes.put("faq", getResponseCode("faq"));
        codes.put("faq Категория", getResponseCode("faq/1-1"));
        codes.put("board", getResponseCode("board"));
        codes.put("board Раздел", getResponseCode("board/testrazdel/1"));
        codes.put("board Категория", getResponseCode("board/testrazdel/testkategoriya/2"));
        codes.put("shop", getResponseCode("shop"));
        codes.put("shop Категория", getResponseCode("shop/testkategoriya"));
        codes.put("news", getResponseCode("news"));
        codes.put("news Категория", getResponseCode("news/testkategoriya/1-0-1"));
        codes.put("blog", getResponseCode("blog"));
        codes.put("blog Категория", getResponseCode("blog/testkategoriya/1-0-1"));
        codes.put("tests", getResponseCode("tests"));
        codes.put("tests Категория", getResponseCode("tests/testkategoriya/1"));
        codes.put("video", getResponseCode("video"));
        codes.put("video Категория", getResponseCode("video/vic/testkategoriya"));
        codes.put("forum", getResponseCode("forum"));
        codes.put("forum Категория", getResponseCode("forum/3"));
        codes.put("photo", getResponseCode("photo"));
        codes.put("photo Раздел", getResponseCode("photo/testrazdel/3"));
        codes.put("photo Категория", getResponseCode("photo/testrazdel/testkategoriya/4"));
        codes.put("publ", getResponseCode("publ"));
        codes.put("publ Раздел", getResponseCode("publ/testrazdel/3"));
        codes.put("publ Категория", getResponseCode("publ/testrazdel/testkategoriya/4"));
        codes.put("stuff", getResponseCode("stuff"));
        codes.put("stuff Раздел", getResponseCode("stuff/testrazdel/9"));
        codes.put("stuff Категория", getResponseCode("stuff/testrazdel/testkategoriya/10"));
        codes.put("load", getResponseCode("load"));
        codes.put("load Раздел", getResponseCode("load/testrazdel/2"));
        codes.put("load Категория", getResponseCode("load/testrazdel/testkategoriya/3"));
        codes.put("gb", getResponseCode("gb"));
        return codes;
    }

    public Map<String, Integer> getResponseCodes(List<String> selectedPages) {
        Map<String, Integer> codes = new LinkedHashMap<>();
        for (String page : selectedPages){
            codes.put(page, getResponseCode(pages.get(page)));
        }
        return codes;
    }

    private Integer getResponseCode(String page){
        Integer code = 0;
        try {
            String domain = "http://selenium.at.ua/";
            URL url = new URL(domain + page);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            code = httpCon.getResponseCode();
        } catch (IOException e) {
            System.out.println("Exception: " + e + "; Can't take response code.");
        }
        return code;
    }

    public Map<String, String> getPages() {
        return pages;
    }

    private void createPagesMap(){
        pages = new LinkedHashMap<>();
        pages.put("dir", "dir");
        pages.put("dir Раздел", "dir/testrazdel/19");
        pages.put("dir Категория", "dir/testrazdel/testkategoriya/20");
        pages.put("faq", "faq");
        pages.put("faq Категория", "faq/1-1");
        pages.put("board", "board");
        pages.put("board Раздел", "board/testrazdel/1");
        pages.put("board Категория", "board/testrazdel/testkategoriya/2");
        pages.put("shop", "shop");
        pages.put("shop Категория", "shop/testkategoriya");
        pages.put("news", "news");
        pages.put("news Категория", "news/testkategoriya/1-0-1");
        pages.put("blog", "blog");
        pages.put("blog Категория", "blog/testkategoriya/1-0-1");
        pages.put("tests", "tests");
        pages.put("tests Категория", "tests/testkategoriya/1");
        pages.put("video", "video");
        pages.put("video Категория", "video/vic/testkategoriya");
        pages.put("forum", "forum");
        pages.put("forum Категория", "forum/3");
        pages.put("photo", "photo");
        pages.put("photo Раздел", "photo/testrazdel/3");
        pages.put("photo Категория", "photo/testrazdel/testkategoriya/4");
        pages.put("publ", "publ");
        pages.put("publ Раздел", "publ/testrazdel/3");
        pages.put("publ Категория", "publ/testrazdel/testkategoriya/4");
        pages.put("stuff", "stuff");
        pages.put("stuff Раздел", "stuff/testrazdel/9");
        pages.put("stuff Категория", "stuff/testrazdel/testkategoriya/10");
        pages.put("load", "load");
        pages.put("load Раздел", "load/testrazdel/2");
        pages.put("load Категория", "load/testrazdel/testkategoriya/3");
        pages.put("gb", "gb");
    }
}
