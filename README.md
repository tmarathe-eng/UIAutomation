# UIAutomation

This repo implements automation framework for following scenario using Selenium WebDriver, TestNG and Maven.

## Scenario

Given the following site: https://www.saucedemo.com/ write the proper automation tests for the following scenario: 

Login as standard_user and purchase ALL the T-shirts. 

To create the tests above, write an automation framework using Selenium WebDriver with Java OR Javascript, Maven, and TestNG.

## Framework Details

Framework is built using Page Object Model and contains following type of classes arranged in various packages.

1. Page package contains classes corresponding to each of the following pages for given website.
   • Login Page
   • Products Page
   • Cart Page
   • Checkout Information Page
   • Order Review Page
   
   In addition to these classes, there is a BasePage class which is super class for all of these page classes, which contains common methods such as findElement, click, clickAndVerify etc. These methods are inherited by all child classes and reduces code repitition.

2. Test package contains buyAllTshirtClass - which implements given scenario. It also contains BaseTest class which contains methods to setup test environment before starting test process.

3. Helper package contains two sub-packages - constants and util. 
In constants package, there is FilePath class which stores path for all files and directories used in the project. 
In util package, there are utility classes to implement methods for logging, reporting and excel data extraction.

4. Apart from these packages, there are following configuration files in the framework:
 - /src/main/resources/log4j.properties - Provides logging framework configuration using log4j
 - testng.xml - Contains configuration for running the project as TestNG Suite
 - pom.xml - Contains maven configuration to manage dependencies, and maintain maven runtime profiles    
