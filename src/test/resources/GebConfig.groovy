import org.openqa.selenium.chrome.ChromeDriver

reportsDir = 'target/geb-reports'

driver = {
    System.setProperty("geb.driver", "org.openqa.selenium.chrome.ChromeDriver")
    def webDriverExecutable = System.getProperty("geb.chrome.driver")

    System.setProperty("webdriver.chrome.driver", webDriverExecutable)

    def webDriver = new ChromeDriver()
    webDriver.manage().window().maximize()

    webDriver
}
