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
        pages.put("news Архив день", "news/2018-06-26");
        pages.put("news Архив месяц", "news/2018-06");
        pages.put("news Архив год", "news/2018");
        pages.put("blog", "blog");
        pages.put("blog Категория", "blog/testkategoriya/1-0-1");
        pages.put("blog Архив день", "blog/2018-06-26");
        pages.put("blog Архив месяц", "blog/2018-06");
        pages.put("blog Архив год", "blog/2018");
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
        pages.put("search", "search");
    }
}
