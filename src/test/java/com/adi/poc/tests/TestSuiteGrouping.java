package com.adi.poc.tests;

import com.adi.poc.tests.api.ApiTests;
import com.adi.poc.tests.ui.UiLoginTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Suite
@SelectClasses({
        UiLoginTests.class,
        ApiTests.class
})
public class TestSuiteGrouping {
}
