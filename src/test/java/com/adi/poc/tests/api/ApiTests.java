package com.adi.poc.tests.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiTests {
    @Test
    public void testApi1() {
        System.out.println("First Api test");
    }

    @Test
    public void testApi2() {
        System.out.println("Second Api test");
    }
}
