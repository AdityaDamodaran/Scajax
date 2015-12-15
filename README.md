# Scajax
Scajax is an AJAX web application vulnerability scanner. The present release of Scajax only supports the detection of AJAX endpoints in websites, although future enhancements will allow users to scan these endpoints for vulnerabilities directly through the tool. The scanner uses two separate methods for detecting vulnerabilities, namely an experimental static analysis engine, and a dynamic analysis engine.

##Static Analysis
The static analysis engine uses 'plugins', which are XML files that specify HTML/JavaScript patterns usiñg regular expressions, to detect and extract AJAX endpoints from code.

##Dynamic Analysis
The dynamic analysis engine uses a Google chrome extension to interact with websites as they are rendered in a browser and extract endpoints on the fly.

##Installation Instructions
The Scanner uses a Google Chrome browser extension to detect 
##Installing the Chrome Extension
1. Navigate to [chrome://extensions/](chrome://extensions/) in Google Chrome
2. Click on the "Load unpacked extension" button
3. Select the folder named "extension" listed in this repository, in the 'build' folder

##Using the Scanner
1. Enable the browser extension in Google Chrome from the [chrome://extensions/](Chrome Extensions) page
2. Run the AJAXScanner.jar file
3. Enter the URL of the site to be scanned in the URL text field
4. Select either the Static or the Dynamic analysis radio button
5. Click on the 'Analyse' button
###Static Analysis
- The scanner scans the site using the plugins loaded in the plugins folder
- 
###Dynamic Analysis


