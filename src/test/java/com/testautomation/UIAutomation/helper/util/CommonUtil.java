package com.testautomation.UIAutomation.helper.util;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.testautomation.UIAutomation.helper.constants.FilePath;
import static com.testautomation.UIAutomation.helper.util.ExtentReport.*;

/**
 * @author tm0338
 * Contains methods that help with logging and reporting functionalities
 */
public class CommonUtil {

	//Test data hashmap
	public static HashMap<String, String[]> TestCaseDataMap;
	
	//Allows to create filename suitable for adding to extent report
	public static String GetScreenshotForReport(WebDriver driver, String screenshotFilename) {
		return ReportLog.addScreenCapture(CommonUtil.TakeScreenshot(driver,screenshotFilename));
	}
	
	//Allows to take screenshot of current browser
	public static String TakeScreenshot(WebDriver driver,String screenshotFilename) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String screenshotFile = new File(FilePath.SCREENSHOT_DIR + "/" + screenshotFilename + "_" + System.currentTimeMillis() + ".png").getAbsolutePath();
		// Copy temporary screenshot file to screenshot dir
		try {
			FileUtils.copyFile(source, new File(screenshotFile));
		} catch (Exception e) {
			ReportLog.log(LogStatus.WARNING, "Exception occurred while copying screenshot file to its destination.");
		}
		return screenshotFile;
	}
	
	//Allows to add message at current step in extent report
	public static void appendMsg(Logger log, String msgType, String msg) {
		String logMsg = logFormat(msg);
		String reportMsg = reportFormat(msg);
		switch(msgType.toUpperCase()) {
		case "INFO": case "PASS":
			log.info(logMsg);
			ExtentReport.SuccessMessage =reportMsg;
			break;
		case "ERR": case "ERROR": case "FAIL": case "FAILURE": case "FATAL":
			log.error(logMsg);
			ExtentReport.FailureMessage =reportMsg;
			break;
		case "WARN": case "WARNING": case "SKIP": case "UNKNOWN": default:
			log.warn(logMsg);
			ExtentReport.SuccessMessage =reportMsg;
			ExtentReport.FailureMessage =reportMsg;
			break;
		}
		
	}
	
	//Allows to add a test step in extent report
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
		
		ExtentReport.ReportLog.log(logStatus, reportMsg);
	}

	//Allows to add a test step in extent report with screenshot
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
		
		ExtentReport.ReportLog.log(logStatus, reportMsg + ExtentReport.ReportLog.addScreenCapture(TakeScreenshot(driver,screenshotFilename)));
	}

	//Makes console log more readable by applying start tabs
	public static String logFormat(String msg){
		return msg.replaceAll("\n", "\n\t\t\t\t\t\t");
	}
	
	//Formats text to be html compatible
	public static String reportFormat(String msg){
		return msg.replaceAll("\n", "<br>").replaceAll("\t", "&emsp;");
	}
}
