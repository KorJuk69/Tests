package com.repository;

import com.data.Test;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestRepository {

    private List<Test> tests = new ArrayList<>();

    public TestRepository() {
        tests.add(new Test("News add test"));
        tests.add(new Test("News delete test"));
        tests.add(new Test("Blog add test"));
        tests.add(new Test("Blog delete test"));
        tests.add(new Test("Dir add test"));
        tests.add(new Test("Dir delete test"));
        tests.add(new Test("FAQ add test"));
        tests.add(new Test("FAQ delete test"));
        tests.add(new Test("Board add test"));
        tests.add(new Test("Board delete test"));
        tests.add(new Test("Publ add test"));
        tests.add(new Test("Publ delete test"));
        tests.add(new Test("Load add test"));
        tests.add(new Test("Load delete test"));
        tests.add(new Test("Login test"));
        tests.add(new Test("UID registration test"));
    }

    public List<Test> getTests() {
        return tests;
    }

    public Test getTest(String name) {
        if (tests.stream().anyMatch(test -> test.getName().equals(name))) {
            return tests.stream()
                    .filter(test -> test.getName().equals(name))
                    .findFirst().get();
        } else {
            throw new RuntimeException("Such test doesn't exist");
        }
    }
}
