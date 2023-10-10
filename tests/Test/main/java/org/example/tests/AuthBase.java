package org.example.tests;

import org.example.ApplicationManager;
import org.example.models.Account;
import org.junit.Assert;
import org.junit.Before;

import static org.example.utils.Settings.getLogin;
import static org.example.utils.Settings.getPassword;

public class AuthBase extends TestBase {
    @Before
    public void setUp() {
        applicationManager = ApplicationManager.getInstance();
        applicationManager.getNavigationHelper().openHomePage();
        applicationManager.getLoginHelper().login(new Account(getLogin(), getPassword()));
        //Assert.assertTrue(applicationManager.getLoginHelper().isLogged());
    }
}
