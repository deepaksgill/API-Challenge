package com.TestFrameWork;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class ResuableMethods {
	/**
	 * 
	 * 
	 * This class contains all the common methods for common selenium events which
	 * can be reused through out the project.
	 * 
	 * 
	 */

	// Function :- getJSONToken
	// This function will fetch JSON Token
	public ResponseBody getJSONToken() {
		try {
			JsonObject requestParams = new JsonObject();
			requestParams.addProperty("email", "kaddu@gmail.com");
			requestParams.addProperty("password", "abcs1234");
			ResponseBody body = RestAssured.given().header("Content-Type", "application/json")
					.body(requestParams.toString()).post("/auth/login").body();
			return body;
		} catch (Exception e) {
			ProLogger.info("getJSONToken " + e.toString());
			return null;
		}

	}

	// Function :- getStatusCode
	// This function will fetch Api Response Code
	public int getStatusCode(String uri) {
		int statusCode = -1;
		try {
			statusCode = RestAssured.given().header("Content-Type", "application/json").get(uri).getStatusCode();
			return statusCode;

		} catch (Exception e) {
			ProLogger.info("getStatusCode " + e.toString());
			return statusCode;
		}
	}

	// Function :- postStatusCode
	// This function will fetch Post Api Response Code
	public int postStatusCode(String uri, JsonObject jsonObject) {
		int statusCode = -1;
		try {
			statusCode = RestAssured.given().header("Content-Type", "application/json")
					.header("auth-token", getJSONToken()).body(jsonObject).post(uri).getStatusCode();

			return statusCode;
		} catch (Exception e) {
			ProLogger.info("postStatusCode " + e.toString());
			return statusCode;
		}
	}

	// Function :- verify_PostApiStatus
	// This function will verify Post Api Status
	public boolean verify_PostApiStatus(String uri, JsonObject jsonObject) {
		try {
			RestAssured.given().header("Content-Type", "application/json").header("auth-token", getJSONToken())
					.body(jsonObject).post(uri).then().assertThat().statusCode(204).and().contentType(ContentType.JSON)
					.and().body("status", equalTo("OK"));

			return true;
		} catch (Exception e) {
			ProLogger.info("verify_PostApiStatus " + e.toString());
			return false;
		}
	}

	// Function :- verify_PostApiStatus
	// This function will verify Post Api Status
	public boolean verify_GetApiStatus(String uri) {
		try {
			String res = RestAssured.given().header("Content-Type", "application/json").get(uri).getStatusLine();
			assertEquals(res.split(" ")[2], "OK");

			return true;
		} catch (Exception e) {
			ProLogger.info("verify_GetApiStatus " + e.toString());
			return false;
		}
	}

	public String getApiResponse_SessionId(String uri, JsonObject jsonObject) {
		try {
			String sessionId = RestAssured.given().header("Content-Type", "application/json")
					.header("auth-token", getJSONToken()).body(jsonObject).post(uri).getSessionId();
			ProLogger.info("SessionId : " + sessionId);
			return sessionId;

		} catch (Exception e) {
			ProLogger.info("getApiResponse_SessionId" + e.toString());
			return null;
		}
	}

	public String getApiResponse_ContentType(String uri) {
		try {
			return RestAssured.given().header("Content-Type", "application/json").get(uri).getContentType();

		} catch (Exception e) {
			return null;
		}
	}

	public Response get_GetApiResponse(String uri) {
		try {
			return RestAssured.given().header("Content-Type", "application/json").get(uri);

		} catch (Exception e) {
			return null;
		}
	}

	public Response get_PostApiResponse(String uri, JsonObject jsonObject) {

		try {
			return RestAssured.given().header("Content-Type", "application/json").header("auth-token", getJSONToken())
					.get(uri);

		} catch (Exception e) {
			return null;
		}
	}

	public String get_AnyParameter_Value(String uri, String parameterName) {

		try {
			Response response = get_GetApiResponse(uri);
			JsonPath jsonPathEvaluator = response.jsonPath();
			return jsonPathEvaluator.get("\"" + parameterName + "\"").toString();

		} catch (Exception e) {
			return null;
		}
	}

	public boolean getResponseTime(String uri, long timeinmilliseconds) {

		try {
			long totalTime = RestAssured.given().header("Content-Type", "application/json").get(uri).getTime();
			System.out.println("Response Time : " + totalTime);
			if (totalTime < timeinmilliseconds) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

}
