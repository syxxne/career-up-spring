package com.careerup.careerupspring.service;

import com.careerup.careerupspring.entity.NicknameFirstEntity;
import com.careerup.careerupspring.entity.NicknameSecondEntity;
import com.careerup.careerupspring.repository.NicknameFirstRepository;
import com.careerup.careerupspring.repository.NicknameSecondRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
@Service
public class CrawlingService {
    @Autowired
    NicknameFirstRepository nicknameFirstRepository;
    @Autowired
    NicknameSecondRepository nicknameSecondRepository;
    public void firstNickname() throws IOException {
        Document document = Jsoup.connect("https://ko.wiktionary.org/wiki/%EB%B6%84%EB%A5%98:%ED%95%9C%EA%B5%AD%EC%96%B4_%EA%B4%80%ED%98%95%EC%82%AC%ED%98%95(%ED%98%95%EC%9A%A9%EC%82%AC)").get();
        Iterator<Element> content = document.select(".mw-category-group li a").iterator();
        while(content.hasNext()){
            String name = content.next().text();
            NicknameFirstEntity nicknameFirst = new NicknameFirstEntity();
            nicknameFirst.setAdj(name);
            nicknameFirstRepository.save(nicknameFirst);
        }
    }

    public void secondNickname() throws IOException{
        Document document = Jsoup.connect("https://ko.wiktionary.org/wiki/%EB%B6%84%EB%A5%98:%ED%95%9C%EA%B5%AD%EC%96%B4_%ED%8F%AC%EC%9C%A0%EB%A5%98").get();
        Iterator<Element> content = document.select(".mw-category-group li a").iterator();
        while(content.hasNext()){
            String name = content.next().text();
            NicknameSecondEntity nicknameSecond = new NicknameSecondEntity();
            nicknameSecond.setNoun(name);
            nicknameSecondRepository.save(nicknameSecond);
        }
    }

}
