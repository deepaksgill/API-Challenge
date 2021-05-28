package com.Tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.TestFrameWork.ProLogger;
import com.TestFrameWork.ResuableMethods;

import io.restassured.RestAssured;

public abstract class BaseTest {

	protected static ResuableMethods res ;
	
	@BeforeMethod
	public void beforeMethod() {
		ProLogger.startTestCase(this.getClass().getSimpleName());

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.isSuccess()) {
			ProLogger.info("-------" + result.getName() +" Test Executed Successfully" + "-------");
			ProLogger.endTestCase(this.getClass().getSimpleName());
		} else if (!result.isSuccess()) {
			ProLogger.info("-------" + result.getName() +" Test Got Failed" + "-------");
			
			ProLogger.endTestCase(this.getClass().getSimpleName());

		}

	}

	@BeforeTest
	@Parameters("baseURL")
	public void beforetest(String baseURL) {
		RestAssured.baseURI = baseURL;
		res = new ResuableMethods();
	}

	@BeforeSuite
	public void beforeSuite() {
		ProLogger.info("Rest Suite Execution started");
	}

	@AfterSuite
	public void afterSuite() {
		ProLogger.info("Rest Suite Execution started");
	}

}
