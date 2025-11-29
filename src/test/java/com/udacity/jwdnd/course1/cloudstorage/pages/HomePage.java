package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy( id = "logout")
    private WebElement logoutButton;

    @FindBy( id = "nav-note-tab")
    private WebElement noteTab;

    @FindBy( id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "edit-button")
    private WebElement editNoteButton;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void logout(){
        this.logoutButton.click();
    }

    public void openNoteTab(){
        this.noteTab.click();
    }

    public void editNoteButton(){
        this.editNoteButton.click();
    }

    public void openCredentialTab(){
        this.credentialTab.click();
    }



}
