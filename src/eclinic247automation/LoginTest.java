package eclinic247automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {
	// Test Case to verify if login is successful
	@Test
	public void verifyLoginSuccess() throws InterruptedException{
		 WebDriver driver = new FirefoxDriver();
		 CommonUtils commonutils = new CommonUtils();
		 commonutils.Login(driver, "testscript@gmail.com", "123456");
	     Thread.sleep(5000);
	     String URL = driver.getCurrentUrl();
	     Assert.assertEquals(URL, "http://demo.eclinic247.com/patient.html#/home" );
	     driver.close();
	}
	
	// Test Case to verify if the failure message is correctly showing on screen upon login failure
	@Test
	public void verifyLoginFailure() throws InterruptedException{
		 WebDriver driver = new FirefoxDriver();
		 CommonUtils commonutils = new CommonUtils();
		 commonutils.Login(driver, "testscript@gmail.com", "1234");
	     Thread.sleep(1000);
	     String bodyText = driver.findElement(By.tagName("body")).getText();
	     Assert.assertEquals(true, bodyText.contains("You have entered an invalid email id or password"));
	     
	     Thread.sleep(3000);
	     String URL = driver.getCurrentUrl();
	     Assert.assertEquals(URL, "http://demo.eclinic247.com/login.html" );
	     driver.close();
	}
	

}
