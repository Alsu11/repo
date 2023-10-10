package org.example.helpers;

import static org.example.utils.Settings.getBaseUrl;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.example.ApplicationManager;
import org.openqa.selenium.Dimension;

public class NavigationHelper extends HelperBase {
    public NavigationHelper(ApplicationManager applicationManager) {
        super(applicationManager);
    }

    public void openHomePage() {
        driver.get(getBaseUrl());
        driver.manage().window().setSize(new Dimension(1550, 838));
    }

}
