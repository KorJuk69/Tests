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
        tests.add(new Test("Login test"));
        tests.add(new Test("UID registration test"));
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
