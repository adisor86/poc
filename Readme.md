#A. Framework description
This framework should be seen as a proof of concept and not as a production ready framework; I tried to respect standards
and practices but its main goal was to provide a viable solution to automate scenarios at both UI & API levels and to provide an example in how it can be implemented a Spike/Stress performance test.

The proposed solution is a Spring Boot application; as it can be seen from the **pom.xml** it has a few other dependencies, but the most relevant ones are the following:
* Selenium 4.1.2 - for UI scenarios
* Rest Assured 4.5.1 - for API scenarios
* JUnit 5.8.2 - the actual testing framework
* Lombok plugin installed in your IDEA (though this is not mandatory, all the tests will get executed successfully even without it; not having this plugin installed it will cause a compile error in ApiTests.java due to the fact that `builder()` method will not be recognized)

Also, for performance test there are presented two options:
 * option 1 - implementation in [JMeter](https://jmeter.apache.org/)
 * option 2 - implementation in [Artillery](https://www.artillery.io/).

#B. Framework structure 
##B1. UI automation
As stated above, for the UI tests I chose the Selenium 4 to interact with the browser elements. The current POC provides a solution for the multi-browser execution (Chrome & Firefox) on both local machine and browser stack platform. Everything can be easily configured while using properties defined at `application.yaml` file (more details under execution chapter).

Main packages used for the UI tests are the following:
* **selenium.driver package** - I used Factory design pattern for driver instantiation in order to provide an easy ability to run it for multiple browsers and multiple platforms
* **selenium.pages** - Page Object Model classes
* **SeleniumActions.java** - wrapper over Selenium actions in trying to adapt them in the given context, but also to have a decoupling between tests and the Selenium library
* **tests.ui** - Junit tests for the implemented UI scenarios - for the given POC I included 3 suites - Login, Search & Smoke which includes two end-to-end scenarios 

When it comes the application under test, I used [http://automationpractice.com](http://automationpractice.com/index.php). 

##B2. API automation
For the API scenarios I used Rest Assured 4.5. Main packages used for the API tests are the following:
* **api.dto** - it contains the API object models - e.g. User model
* **RestApiClient.java** - wrapper over RestAssured where I implemented the HTTP methods while adding all the needed customization;
* **tests.api** - test class containing all API scenarios in where I validate basic CRUD operations on User resource

When it comes the application under test, I used [https://gorest.co.in/](https://gorest.co.in/).

##B3. Performance testing
For the performance testing, since the requirement was a rather simple scenario in which I had to navigate to the home page of a web application, I chose to implement it while using two different frameworks:
* **tests.performance.jmeter** - JMeter implementation; here it is a simple `BitPandaJmeterPerformance.jmx` that can be executed either from command line or from JMeter GUI (more details under Test Execution)
* **tests.performance.artillery** - Artillery.io implementation; I decided to include this option, too, mainly due to fact that from what I found so far, within Bitpand company, there is a flavor for JS / Node.js, therefore artillery seemed to me something closer to what you are used to work with  

When it comes to the application under test, I choose the [https://artilery.io](https://artilery.io) for which I considered the below two scenarios:
* scenario 1: open home page
* scenario 2: navigate to the blog section

#C. Test Execution
##C1. Pre-requisites for test execution
In order to may execute all covered tests, the following pre-requisites have to be in place:
* UI & API test execution:
  * **Java 8 SDK** installed on the local machine and all its needed configuration done (JAVA_HOME set, compatible JRE installed)
  * **Java IDEAs** like IntelliJ, Eclipse installed and configured accordingly (framework has been developed while using IntelliJ IDEA)
* Performance test execution:
  * **JMeter 5.4.1** installed and configured accordingly on the local machine - for JMeter script execution
  * **Node.js v14 (or later) & Artillery** (latest stable version) installed and configured on the local machine - for Artillery script execution

##C2.1 Steps to run UI scenarios
Once the above pre-requisites are handled, in order to may run UI scenarios, all the below steps have to be followed:
* open current solution in IDEA, wait for its dependencies to be resolved and then compile the project
* from project explorer, go to **src/java/com/adi/poc/test/tests/ui**; within this package there are three classes with tests/scenarios:
  * **UiLoginTests.java** - some basic scenarios for Login feature
  * **UiSearchProductsTests.java** - some basic scenarios for Product Search functionality
  * **UISmokeTest.java** - 2 more like end-to-end scenarios - login, search for a product & add item to cart; login, search for a product, add it to the cart & then remove item from cart
* before starting the UI execution, make sure the right settings are added within **application.yaml** configuration file, settings like:
  * browser.localExecution - choose `true` for a local execution and `false` for an execution in browser stack cloud
  * browser.type - for the moment only `chrome` & `firefox` browsers are supported for local execution; still extending the supported browser list should be a very small effort
  * browser_stack.* - all necessary configuration for execution in the cloud; please note these will only be considered when `browser.localExecution = false`; also provided user (adipirlea1) is having a free trial license and it is connected to my personal email address , **so please don't abuse the remaining automation minutes -~ 90 mins** 
  * user.username, user.password - credentials used for the login operation
  * url.uiBaseurl - url for UI application under test
* finally, to execute tests, open class having the desired scenarios and run tests as JUnit test (e.g. right click on class -> Run UILoginTests)

##C2.2 Steps to run API scenarios
* open current solution in IDEA, wait for its dependencies to be resolved and then compile project (if not done already)
* from project explorer, go to **src/java/com/adi/poc/test/tests/api**; within this package there a single class which contains all implemented scenarios:
  * **ApiTests.java** - it contains some basic scenarios to validate CRUD operations on `user` resource 
* before starting the UI execution, make sure the right settings are added within **application.yaml** configuration file, settings like:
  * user.token - current token was generated for my test account and it should never get expired (per their documentation)
  * url.apiUrl - base API url used for all http requests
* finally, to execute tests, open test class and run tests as JUnit test (e.g. right click on class -> Run UILoginTests)

**Notes:** 
* I also created a generic TestSuite class - **TestSuiteGrouping.java** - from where both UI & API tests can be triggered - as default there are enabled the SmokeUiSuite & APiTests.
* For API tests, I added the dependency between tests on purpose, in the sense of just re-using the userId created via the post for the rest of the methods; please make sure you'll **execute all these tests in at the same time** and not one by one in isolation, otherwise you'll might have failures 
* I know that in the assessment it was requested to have also the CI execution resolved, but I did not consider it for the moment, mainly due to time constraint; up until now I used Jenkins (both on premise and hyeprloop) in order to trigger the test execution; we may discuss about the approach I had in a follow-up session (if any); also, I know that there are plenty of other options to choose from, and most probably on a quick search, I would have found something to quickly integrate, but for the moment my time did not allow that (I already spent quite some time on developing this framework froms scratch :))

##C2.3 Steps to run Performance scenarios

#### C2.3.1 Run performance test while using JMeter script:
Considering this is a POC, the execution will be done from the local machine; make sure the following steps are considered: 
* have JMeter installed on your local machine - see details [here](https://jmeter.apache.org/download_jmeter.cgi)
* GUI mode execution - you'll have to open JMeter GUI by opening `jmeter.bat/sh` file & import the `BitPandaJmeterPerformance.jmx` file; once this is done, just press on Run/Play test option
  * settings like duration, max load, etc. can be changed from **User Defined Variables** config element;
* Non GUI mode execution - open a terminal, navigate to the folder where JMeter is installed (bin folder) and type the following command:
``jmeter -n -t "absolute path to .jmx file" -l "absolute path to results.jtl"``
  * settings like duration, max load, etc. can be overriden by using  -D argument - e.g. `-DmaxLoad=100`
  
Other alternatives to run the script can be found below:
  * using Taurus - install Taurus on your local machine - see details [here](https://gettaurus.org/install/Installation/)
  * using cloud applications like [Blazemeter](https://www.blazemeter.com/), [OctoPerf](https://octoperf.com/), etc.

#### C2.3.2 Run performance test while using Artillery script:
Considering this is a POC, the execution will be done from the local machine; below steps have to be followed: 
* install (if not already have) [Node.js v14](https://nodejs.org/en/download/) or later
* install artillery via npm command `npm install -g artillery@latest`
  * after installation completes, you may use the following command to check everything is ok `artillery dino` - if successful installation, then an ASCI dinosaur should be displayed
  * for any issues please consult the [official artillery](https://www.artillery.io/docs/guides/getting-started/installing-artillery) documentation 
* open the terminal and navigate to the location where the artillery `BitpandaArtilleryPerformance.yml` script is saved (..\src\test\java\com\adi\poc\tests\performance\artillery)
* in order to start execution use the following command: `artillery run --output results.json BitpandaArtilleryPerformance.yml` -> this will run test and store results in 'results.json' file in the current folder
* to generate the html results report use the following command: `artillery report results.json` -> it will generate a HTML report to the current folder
* firstName & LastName fields are duplicated  - part fo Personal Information section & Your Address section
* empty spaces are allowed for input fields, even for password

#D. Test Results
## D1. UI automation results
All automated tests **should pass**, though while implementing them I saw quite some issues within the application; I will list below a few of them but please be aware that I did not spend time to actually test the application, but rather I scanned it for 15-20 mins:
* Create account issues:
  * email validation is not done correctly - invalid email address can be used - e.g. adi.def@mail.c
  * inconsistent validation messages displayed for invalid email addresses - e.g. bc.def@mail..com - it will show the validation message but also the green (ok) validation inline the email input field
  * UX possible issue in the way the address & phone number warning message is displayed (red message displayed below the input field while for the rest of the mandatory fields are having just * sign)
  * country list is populated just with US
  * `Assign an address alias for future reference.` is marked as mandatory input field but it isn't 
* Filtering issues:
  * filtering is not working all the time, especially when multiple filter criterias are used; also invalid results when color filtering is used by itself  - e.g. clicking on pink it will return products for which the selected color does not exist
  * issues with Price range scroll filtering -> once you'll drag and drop it to the highest value, you'll not be able to reduce it down again
* Cart issues:
  * added products to the cart are not saved for user session -> e.g. while being logged in with account 1, add 1-2 items to cart and then login with the same account in another browser => no items are available in the cart within the 2nd browser
  * Order complete message when `Bank wire` option is selected for payment, it contains hard-codded data that is not linked to the logged in account (info displayed after)
* General issues:
  * after using the application for a while I received lot of `508 - Resource Limit Is Reached errors`
  * Add new wishlist -> user is able to click on Save wishlist without adding any name for it or any product; still, nothing happens but this is confusing for the actual user (save button should be disabled)

Please note that while working on the framework, I saw some changes done on the DOM, which caused a failure for one of the scenarios; I fixed it right away, but though I tried to define locators as good as I could (without actually knowing the behavior of the application) - used with high priority IDs, then classname and lastly the xpath/css selectors to identify web elements

Test results for the execution in browser stack can be seen while performing a login on [browser-stack](https://www.browserstack.com/) with user & password from `application.yaml` file.

## D2. API automation results
Out of the total 10 covered scenarios, 2 of them are failing due to the wrong http status returned for the given scenario - createUserWhileUsingWrongEndpointWhichDoesNotSupportPostMethod & createUserWithInvalidBody

## D3. Performance test results
In order to clearly identify the SPIKE impact, I adopted the following test strategy:
* run a constant load of 5 VUs for the first 30s
* have the 1000 VUs spike done for 15s 
* continued with a load of 5 VUs for the next 30s
Monitored all this time the client metrics only.

In this way, I could clearly determine the SPIKE impact by looking on response time for the 3 simulated time intervals.

As a final result, **my conclusions can be found below**:
* it is a clear impact in terms of response time for the duration of SPIKE
  * ~100ms request response time on constant 5 VUs load
  * during the SPIKE the response time from server has gradually increased up to 16s
* the positive aspects:
  * site didn't go down, therefore all requests were successful
  * site recovered quite well after the SPIKE and the response time returned to normal
* an important aspect to be noted is that I only had access to the client metrics, so I don't know what happened on the server side with the actual resources and how the handling was done; this is an important area to be closely monitored when executing performance tests.

In terms of optimal application response, though there is not a hard defined & accepted max threshold (there are a lot of factors to look at when defining what is acceptable and what not), based on my knowledge I'd mentioned the following:
* most of the requests should be handled under 1s
* delays greater than 5-10 seconds might jeopardize the UX and users will typically leave the web-site


**Note:** results from both JMeter & Artillery execution can be found in **../performance/results* folder.

