package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaTypeEditor;
import org.springframework.test.annotation.DirtiesContext;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	String noteTitle = "Udacity";
	String noteDescription = "Java web developer Nanodegree";
	String editedNoteTitle = "The Beatles";
	String editedNoteDescription = "Yesterday";
	String credentialUrl = "https://www.youtube.com/";
	String credentialUsername = "Sliva";
	String credentialPassword = "slivi22";
	String editedCredentialUrl = "https://www.upwork.com/";
	String editedCredentialUsername = "Svyatik";
	String editedCredentialPassword = "slivi11";

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testUnAuthorizedUserCanAccessOnlyLoginAndSignup() {
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testUserSignupLoginLogout(){
		String username = "stoner";
		String password = "slivinskiy";

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Sviat", "Slivinskiy", username,password);

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Home", driver.getTitle());
		HomePage homePage = new HomePage(driver);
		homePage.logout();

		driver.get("http://localhost:" + this.port + "/home");

		assertEquals("Login", driver.getTitle());

	}


	@Test
	@Order(4)
	public void creatingNote() throws InterruptedException {

		signupAndLogin();
		Thread.sleep(1000);

		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.openNoteTab();
		Thread.sleep(1000);

		NotePage notePage = new NotePage(driver);
		notePage.addnote();
		Thread.sleep(1000);
		notePage.createNote(noteTitle, noteDescription);
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1000);
		homePage.openNoteTab();
		Thread.sleep(1000);

		assertEquals(noteTitle, driver.findElement(By.className("note-title")).getText());
		assertEquals(noteDescription, driver.findElement(By.className("note-description")).getText());

	}

	@Test
	@Order(5)
	public void testEditNote() throws InterruptedException {


		signupAndLogin();

		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1000);
		HomePage homePage = new HomePage(driver);
		homePage.openNoteTab();
		Thread.sleep(1000);
		NotePage notePage = new NotePage(driver);
		notePage.editNoteButton();
		Thread.sleep(1000);
		notePage.updateNote(editedNoteTitle, editedNoteDescription);
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1000);
		homePage.openNoteTab();
		Thread.sleep(1000);

		assertEquals(editedNoteTitle, driver.findElement(By.className("note-title")).getText());
		assertEquals(editedNoteDescription, driver.findElement(By.className("note-description")).getText());

	}

	@Test
	@Order(6)
	public void testDeleteNote() throws InterruptedException {
		signupAndLogin();
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1000);
		HomePage homePage = new HomePage(driver);
		homePage.openNoteTab();
		Thread.sleep(1000);
		NotePage notePage = new NotePage(driver);
		notePage.deleteNoteButton();

		assertEquals("The note has been deleted", driver.findElement(By.id("success-msg")).getText());
	}


	@Test
	@Order(7)
	public void testCreateCredential() throws InterruptedException {
		signupAndLogin();
		Thread.sleep(1000);
		HomePage homePage = new HomePage(driver);
		homePage.openCredentialTab();
		Thread.sleep(1000);
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.addCredentialButton();
		Thread.sleep(1000);
		credentialPage.addCredential(credentialUrl,credentialUsername, credentialPassword);

		assertEquals("The credential successfully created", driver.findElement(By.id("success-msg")).getText());

		driver.get("http://localhost:" + this.port + "/home");
		homePage.openCredentialTab();
		Thread.sleep(1000);
		assertNotEquals(credentialPassword, driver.findElement(By.className("credential-password")));
	}

	@Test
	@Order(8)
	public void testEditCredential() throws InterruptedException {
		signupAndLogin();
		Thread.sleep(1000);
		HomePage homePage = new HomePage(driver);
		homePage.openCredentialTab();
		Thread.sleep(1000);

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.editCredentialButton();
		Thread.sleep(1000);

		credentialPage.updateCredential(editedCredentialUrl, editedCredentialUsername, editedCredentialPassword);
		assertEquals("The Credential successfully edited", driver.findElement(By.id("success-msg")).getText());
	}

	@Test
	@Order(9)
	public void testDeleteCredential() throws InterruptedException {
		signupAndLogin();
		Thread.sleep(1000);
		HomePage homePage = new HomePage(driver);
		homePage.openCredentialTab();
		Thread.sleep(1000);
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.deleteCredentialButton();
		assertEquals("The credential has been deleted", driver.findElement(By.id("success-msg")).getText());

	}

	public void signupAndLogin(){
		String username = "stoner";
		String password = "slivinskiy";

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Sviat", "Slivinskiy", username,password);

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}




}
