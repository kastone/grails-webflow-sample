/*
    This is the Geb configuration file.

    See: http://www.gebish.org/manual/current/configuration.html
*/

import org.openqa.selenium.htmlunit.HtmlUnitDriver

//import org.openqa.selenium.firefox.FirefoxDriver

// Use htmlunit as the default
// See: http://code.google.com/p/selenium/wiki/HtmlUnitDriver
driver = {
    def driver = new HtmlUnitDriver()
    driver.javascriptEnabled = true
    return driver
}