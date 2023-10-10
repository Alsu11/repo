package org.example.tests;

import org.example.ApplicationManager;
import org.junit.After;
import org.junit.Before;

public class TestBase {

    protected ApplicationManager applicationManager;

    @Before
    public void setUp() {
        applicationManager = ApplicationManager.getInstance();
        applicationManager.getNavigationHelper().openHomePage();
    }
}
