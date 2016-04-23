package eclinic247automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
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

public class MedicalHistoryTest {
	public String baseUrl = "http://demo.eclinic247.com/";
	public String uploadfilename = "eclinicfile.txt";
	public String drugname = "Vicks";
	public String drugstrength = "20";
	public String unit = "mg";
	public String drugfrequency = "0-0-2";
	public String timeofmedication = "after food";
	public String drugduration = "30";
	public String doctorsname = "eclinic247";
	public String generalinstruction = "Reduce dosage to 10 mg after 10 days";
	public String institutename = "Eclinic";

	// Test case to verify the data filled in medication form

	@Test
	public void addMedicalHistoryAndVerify() throws Exception{
		CommonUtils commonutils = new CommonUtils();
		WebDriver driver = new FirefoxDriver(commonutils.FirefoxDriverProfile(System.getProperty("user.dir")));
		driver.manage().window().maximize();
		ClassLoader classLoader = getClass().getClassLoader();
		
		//File file = new File(classLoader.getResource("eclinicfile.txt").getFile());
		File file = new File("resources/eclinicfile.txt");
		String absolutepath = file.getAbsolutePath();
		System.out.println(System.getProperty("user.dir"));
		String downloadpath = System.getProperty("user.dir") + "/eclinicfile.txt";
		String uploadedmd5Hash = commonutils.generateMD5(file);
		System.out.println("Uploaded MD5 Hash: " + uploadedmd5Hash);

		commonutils.Login(driver, "testscript@gmail.com", "123456");
		Thread.sleep(5000);
		String URL = driver.getCurrentUrl();
		Assert.assertEquals(true, URL.contains("http://demo.eclinic247.com/patient.html") );

		traverseToForm(driver);
		//System.out.println(classLoader.getResource("eclinicfile.txt").getPath().substring(1));
		//String path = classLoader.getResource("eclinicfile.txt").getPath().substring(1);
		String path = absolutepath.replaceAll("/", "\\\\");
		
		fillForm(driver, path);

		List<WebElement> attachedfiles = driver.findElements(By.className("table-responsive"));
		WebElement uidrugnameelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[2]/div[1]/medication-post-display-patient/div/table/tbody/tr/td[1]/span"));
		String uidrugname = uidrugnameelement.getText();

		WebElement uidrugstrengthelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[2]/div[1]/medication-post-display-patient/div/table/tbody/tr/td[2]/span"));
		String uidrugstrength = uidrugstrengthelement.getText();

		WebElement uidrugfrequencyelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[2]/div[1]/medication-post-display-patient/div/table/tbody/tr/td[3]/span"));
		String uidrugfrequency = uidrugfrequencyelement.getText();

		WebElement uidrugstartdateelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[2]/div[1]/medication-post-display-patient/div/table/tbody/tr/td[4]/span"));
		String uidrugstartdate = uidrugstartdateelement.getText();

		WebElement uidrugdurationelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[2]/div[1]/medication-post-display-patient/div/table/tbody/tr/td[5]/span/duration-display/span"));
		String uidrugduration = uidrugdurationelement.getText();

		WebElement uigeneralinstructionelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[2]/div[3]/p[2]"));
		String uigeneralinstruction = uigeneralinstructionelement.getText();

		WebElement uidoctorinfoelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[1]/span/span[2]"));
		String uidoctorinfo = uidoctorinfoelement.getText();

		WebElement attachedfile = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/div[" + (1 + attachedfiles.size()) + "]/active-medication/div/div/div[1]/div[3]/display-attachments/div/div/div/disp-attachment/a"));
		String link = attachedfile.getAttribute("href");

		attachedfile.click();
		
		Thread.sleep(3000);

		File downloadedfile = new File(downloadpath);
		String downloadedmd5Hash = commonutils.generateMD5(downloadedfile);
		System.out.println("Downloaded MD5 Hash: " + downloadedmd5Hash);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date()); 


		Assert.assertEquals(true , uidoctorinfo.contains(doctorsname + " , " + institutename));
		Assert.assertEquals(uigeneralinstruction, generalinstruction);
		Assert.assertEquals(drugname, uidrugname);
		Assert.assertEquals(uidrugstartdate, date);
		Assert.assertEquals(drugstrength + " " + unit, uidrugstrength);
		Assert.assertEquals(drugfrequency + " " + timeofmedication, uidrugfrequency);
		Assert.assertEquals(drugduration + " days", uidrugduration);
		Assert.assertEquals(uploadedmd5Hash, downloadedmd5Hash);

		driver.close();
	}

	public void traverseToForm(WebDriver driver) throws InterruptedException {
		WebElement medicalhistory = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div/div[1]/div/ul/li[6]/a/span"));
		medicalhistory.click();
		Thread.sleep(1000);

		WebElement medication = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div/div[1]/div/ul/li[6]/ul/li[4]/a/span"));
		medication.click();
		Thread.sleep(1000);

		WebElement addmore = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div[1]/a/b"));
		addmore.click();
		Thread.sleep(1000);
	}

	public void fillForm(WebDriver driver, String uploadpath) throws InterruptedException{

		WebElement drugnameelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[2]/div[1]/div[1]/div/input"));
		drugnameelement.sendKeys(drugname);

		WebElement strengthelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[2]/div[1]/div[2]/div[1]/div/input"));
		strengthelement.sendKeys(drugstrength);

		WebElement unitelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[2]/div[1]/div[2]/div[2]/div/input"));
		unitelement.clear();
		unitelement.sendKeys(unit);



		WebElement frequencyelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[2]/div[1]/div[3]/make-frequency/div[2]/div/div[1]/input"));
		frequencyelement.sendKeys(drugfrequency);

		WebElement timeofmedicationelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[2]/div[1]/div[3]/make-frequency/div[2]/div/div[2]/input"));
		timeofmedicationelement.sendKeys(timeofmedication);

		WebElement durationelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[2]/div[2]/div[2]/div/input"));
		durationelement.sendKeys(drugduration);

		WebElement doctorsnameelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[5]/div[1]/div/input"));
		doctorsnameelement.sendKeys(doctorsname);


		WebElement instruction = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[4]/div/div/textarea"));
		instruction.sendKeys(generalinstruction);

		WebElement institutenameelement = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[5]/div[2]/div/input"));
		institutenameelement.sendKeys(institutename);

		System.out.println(uploadpath);
		driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[7]/div/input")).sendKeys(uploadpath);

		WebElement savebutton = driver.findElement(By.xpath("//*[@id=\"outside-medicine\"]/create-outside-medication/div/div/form/div[9]/input"));         
		savebutton.submit();
		Thread.sleep(5000);

	}

}
