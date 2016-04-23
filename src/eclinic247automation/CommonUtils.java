package eclinic247automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class CommonUtils {
	public String baseUrl = "http://demo.eclinic247.com/";
	
	public void Login(WebDriver driver, String username, String password){
			 driver.manage().window().maximize();
			 driver.get(baseUrl);
			 WebElement id = driver.findElement(By.name("username"));
		     WebElement passwordelement = driver.findElement(By.name("password"));
		     id.sendKeys(username);
		     passwordelement.sendKeys(password);
		     WebElement button = driver.findElement(By.className("btn-success"));         
		     button.submit();
	}
	
	public static FirefoxProfile FirefoxDriverProfile(String basedir) throws Exception {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", basedir);
		profile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
		return profile;
	}

	public static String createTextFile() throws FileNotFoundException, UnsupportedEncodingException {
		String filename = "eclinic-" + new Date().getTime() + ".txt";
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		writer.println("eclinic247");
		writer.close();
		return filename;

	}


	private static String hashFile(File file, String algorithm) throws Exception{
		try (FileInputStream inputStream = new FileInputStream(file)) {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			byte[] bytesBuffer = new byte[1024];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				digest.update(bytesBuffer, 0, bytesRead);
			}

			byte[] hashedBytes = digest.digest();

			return convertByteArrayToHexString(hashedBytes);
		} catch (Exception ex) {
			throw new Exception(
					"Could not generate hash from file", ex);
		}

	}



	public static String generateMD5(File file) throws Exception  {
		return hashFile(file, "MD5");
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return stringBuffer.toString();
	}
}
