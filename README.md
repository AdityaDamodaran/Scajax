# Scajax
Scajax is an AJAX web application vulnerability scanner. The present release of Scajax only supports the detection of AJAX endpoints in websites, although future enhancements will allow users to scan these endpoints for vulnerabilities directly through the tool. The scanner uses two separate methods for detecting vulnerabilities, namely an experimental static analysis engine, and a dynamic analysis engine.

##Static Analysis
The static analysis engine uses 'plugins', which are XML files that can be used to specify regular expressions, which can in turn be used to extract AJAX endpoints from HTML/JavaScript code.

##Dynamic Analysis
The dynamic analysis engine uses a Google chrome extension to interact with websites while they are rendered in a browser and extract endpoints on the fly.

##Installation Instructions
##Installing the Chrome Extension (required for dynamic analysis)
1. Navigate to [chrome://extensions/](chrome://extensions/) in Google Chrome
2. Click on the "Load unpacked extension" button
3. Select the folder named "extension" listed in this repository, in the 'build' folder

##Using the Scanner
1. Enable the browser extension in Google Chrome from the [chrome://extensions/](Chrome Extensions) page
2. Run the AJAXScanner.jar file
3. Enter the URL of the site to be scanned in the URL text field
4. Select either the Static or the Dynamic analysis radio button
5. Check the 'Spider this site' checkbox, if spidering is required.
6. Click on the 'Analyse' button

###Static Analysis
1. The scanner parses either a single specified web page, or every single page in the site if the spider has been enabled, and lists all identified endpoints in the 'AJAX requests' listbox. 
2. The endpoints are categorised according to the webpages they were found on.
3. 
###Dynamic Analysis


