package com.TestFrameWork;


import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ProLogger {

//Initialize Log4j logs

	private static Logger Log = Logger.getLogger(ProLogger.class.getName());//
	FileAppender fileAppender = (FileAppender) Log.getAppender(System.getProperty("user.dir") + "\\src\\test\\resources\\log4j.xml");

// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

	public static void startTestCase(String sTestCaseName) {

		ProLogger.info("****************************************************************************************");
		ProLogger.info("****************************************************************************************");
		ProLogger.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		ProLogger.info("****************************************************************************************");
		ProLogger.info("****************************************************************************************");

	}

	// This is to print log for the ending of the test case

	public static void endTestCase(String sTestCaseName) {

		ProLogger.info("****************************************************************************************");
		ProLogger.info("****************************************************************************************");
		ProLogger.info("XXXXXXXXXXXXXXXXXXXXXXX             \"+\"-E---N---D-\"+\"             XXXXXXXXXXXXXXXXXXXXXX");
		ProLogger.info("****************************************************************************************");
		ProLogger.info("****************************************************************************************");

	}

	// Need to create these methods, so that they can be called

	public static void info(String message) {
		DOMConfigurator.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\log4j.xml");
		Log.info(message);
	}

	public static void warn(String message) {
		Log.warn(message);
	}

	public static void error(String message) {
		Log.error(message);
	}

	public static void fatal(String message) {
		Log.fatal(message);
	}

	public static void debug(String message) {
		Log.debug(message);
	}

	
}