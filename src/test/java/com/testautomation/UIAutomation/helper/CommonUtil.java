package com.testautomation.UIAutomation.helper;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CommonUtil {

	//Extent Report variables
	public static ExtentReports report;
	public static ExtentTest reportLog;
	public static String successMessage;
	public static String failureMessage;
	public static String screenshotFile;
	
	
	//Test data hashmap
	public static HashMap<String, String[]> testCaseDataMap;
		
	//Directory List
	public static String testResultDir = "test-output/extent-reports";
	public static String screenshotDir = "test-output/extent-reports/screenshots";
	
	
	public static String TakeScreenshot(WebDriver driver,String screenshotFilename) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String screenshotFile = new File(screenshotDir + "/" + screenshotFilename + "_" + System.currentTimeMillis() + ".png").getAbsolutePath();
		// Copy temporary screenshot file to screenshot dir
		try {
			FileUtils.copyFile(source, new File(screenshotFile));
		} catch (Exception e) {
			reportLog.log(LogStatus.WARNING, "Exception occurred while copying screenshot file to its destination.");
		}
		return screenshotFile;
	}
	
	public static void appendMsg(Logger log, String msgType, String msg) {
		String logMsg = logFormat(msg);
		String reportMsg = reportFormat(msg);
		switch(msgType.toUpperCase()) {
		case "INFO": case "PASS":
			log.info(logMsg);
			successMessage =reportMsg;
			break;
		case "ERR": case "ERROR": case "FAIL": case "FAILURE": case "FATAL":
			log.error(logMsg);
			failureMessage =reportMsg;
			break;
		case "WARN": case "WARNING": case "SKIP": case "UNKNOWN": default:
			log.warn(logMsg);
			successMessage =reportMsg;
			failureMessage =reportMsg;
			break;
		}
		
	}
	
	public static void writeMsg(Logger log,String msgType,String msg){
		LogStatus logStatus = LogStatus.INFO;
		String reportMsg = reportFormat(msg);
		String logMsg = logFormat(msg);
		
		switch(msgType.toUpperCase()){
		case "INFO":
			logStatus = LogStatus.INFO;
			log.info(logMsg);
			break;
		case "WARN": case "WARNING":
			logStatus = LogStatus.WARNING;
			log.warn(logMsg);
			break;
		case "ERR": case "ERROR":
			logStatus = LogStatus.ERROR;
			log.error(logMsg);
			break;
		case "FAIL": case "FAILURE":
			logStatus = LogStatus.FAIL;
			log.error(logMsg);
			break;
		case "PASS":
			logStatus = LogStatus.PASS;
			log.info(logMsg);
			break;
		case "SKIP":
			logStatus = LogStatus.SKIP;
			log.warn(logMsg);
			break;
		case "FATAL":
			logStatus = LogStatus.FATAL;
			log.fatal(logMsg);
			break;
		default:
			logStatus = LogStatus.UNKNOWN;
			log.info(logMsg);
		}
		
		reportLog.log(logStatus, reportMsg);
	}

	public static void writeMsg(Logger log,String msgType,String msg,WebDriver driver, String screenshotFilename){
		LogStatus logStatus = LogStatus.INFO;
		String reportMsg = reportFormat(msg);
		String logMsg = logFormat(msg);
		
		switch(msgType.toUpperCase()){
		case "INFO":
			logStatus = LogStatus.INFO;
			log.info(logMsg);
			break;
		case "WARN": case "WARNING":
			logStatus = LogStatus.WARNING;
			log.warn(logMsg);
			break;
		case "ERR": case "ERROR":
			logStatus = LogStatus.ERROR;
			log.error(logMsg);
			break;
		case "FAIL": case "FAILURE":
			logStatus = LogStatus.FAIL;
			log.error(logMsg);
			break;
		case "PASS":
			logStatus = LogStatus.PASS;
			log.info(logMsg);
			break;
		case "SKIP":
			logStatus = LogStatus.SKIP;
			log.warn(logMsg);
			break;
		case "FATAL":
			logStatus = LogStatus.FATAL;
			log.fatal(logMsg);
			break;
		default:
			logStatus = LogStatus.UNKNOWN;
			log.info(logMsg);
		}
		
		reportLog.log(logStatus, reportMsg + reportLog.addScreenCapture(TakeScreenshot(driver,screenshotFilename)));
	}

	
	public static String logFormat(String msg){
		return msg.replaceAll("\n", "\n\t\t\t\t\t\t");
	}
	
	public static String reportFormat(String msg){
		return msg.replaceAll("\n", "<br>").replaceAll("\t", "&emsp;");
	}
}
