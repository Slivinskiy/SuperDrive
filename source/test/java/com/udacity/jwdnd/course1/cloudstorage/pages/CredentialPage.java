package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialPage {

    @FindBy(id = "credentials-list")
    private List<WebElement> credentialsList;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(id = "delete-credential-button")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "save-credential-button")
    private WebElement saveCredentialButton;

    private WebDriver driver;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addCredentialButton() {
        this.addCredentialButton.click();
    }

    public void deleteCredentialButton() {
        this.deleteCredentialButton.click();
    }

    public void editCredentialButton() {
        this.editCredentialButton.click();
    }

    public void addCredential(String credentialUrlField, String credentialUsernameField, String credentialPasswordField) {
        this.credentialUrlField.sendKeys(credentialUrlField);
        this.credentialUsernameField.sendKeys(credentialUsernameField);
        this.credentialPasswordField.sendKeys(credentialPasswordField);
        this.saveCredentialButton.click();
    }

    public void updateCredential(String editedCredentialUrl, String editedCredentialUsername, String editedCredentialPassword) {
        credentialUrlField.clear();
        credentialUsernameField.clear();
        credentialPasswordField.clear();
        this.credentialUrlField.sendKeys(editedCredentialUrl);
        this.credentialUsernameField.sendKeys(editedCredentialUsername);
        this.credentialPasswordField.sendKeys(editedCredentialPassword);
        this.saveCredentialButton.click();
    }



}







