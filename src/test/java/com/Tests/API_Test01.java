package com.Tests;


import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.TestFrameWork.ResuableMethods;

import io.restassured.RestAssured;

public class API_Test01 extends BaseTest{


	@Test(description="This Test is to verify api response time")
	public void verifyAPIResponseTime() {
     assertEquals(res.getResponseTime("/launches/latest/",5000), true);
	}
	
	@Test(description="This Test is to verify api response code")
	public void verifyResponseCode() {
		assertEquals(res.getStatusCode("/launches/latest/"), 200);
	}
	
	@Test(description="This Test is to verify api response status")
	public void verifyResponseStatus() {
		assertEquals(res.verify_GetApiStatus("/launches/latest/"), true);
	}
	
	@Test(description="This Test is to verify json parameter named flight_number")
	public void verify_FlightNumber() {
     assertEquals(res.get_AnyParameter_Value("/launches/latest/","flight_number"), "128");
	}
	
	@Test(description="This Test is to verify json parameter named id")
	public void verify_ID() {
     assertEquals(res.get_AnyParameter_Value("/launches/latest/","id"), "6079bd399a06446e8c61bf77");
	}

		
}
