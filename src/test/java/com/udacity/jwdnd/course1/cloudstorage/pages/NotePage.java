package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePage {

    @FindBy(id = "add-note-button")
    private WebElement addNewNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "saveNoteButton")
    private WebElement saveNoteChanges;

    @FindBy(id = "note-list")
    private List<WebElement> notesList;

    private WebDriver driver;
    public NotePage (WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void editNoteButton(){
        this.editNoteButton.click();
    }

    public void deleteNoteButton(){
        this.deleteNoteButton.click();
    }


    public void addnote(){
        this.addNewNoteButton.click();
    }

    public void createNote(String noteTitleField, String noteDescriptionField){
        this.noteTitleField.sendKeys(noteTitleField);
        this.noteDescriptionField.sendKeys(noteDescriptionField);
        this.saveNoteChanges.click();
    }


    public void updateNote(String editedNoteTitle, String editedNoteDescription){

        noteTitleField.clear();
        noteDescriptionField.clear();

        this.noteTitleField.sendKeys(editedNoteTitle);
        this.noteDescriptionField.sendKeys(editedNoteDescription);
        this.saveNoteChanges.click();

    }







}
