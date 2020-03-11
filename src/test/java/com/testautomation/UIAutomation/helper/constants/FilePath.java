package com.testautomation.UIAutomation.helper.constants;

import java.io.File;

public class FilePath {

	//Directory List
	public static final String TEST_RESULT_DIR = "test-output/extent-reports";
	public static final String SCREENSHOT_DIR = "test-output/extent-reports/screenshots";
	
	//Screenshot file
	public static String ScreenshotFile;
	
	//Chrome driver file
	public static final String CHROME_DRIVER_FILE = new File("src/test/resources/drivers").getAbsolutePath() + "\\chromedriver.exe";

}
