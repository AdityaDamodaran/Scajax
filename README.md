# Scajax
Scajax is an AJAX web application vulnerability scanner. The present release of Scajax only supports the detection of AJAX endpoints in websites, although future enhancements will allow users to scan these endpoints for vulnerabilities directly through the tool. The scanner uses two separate methods for detecting vulnerabilities, namely an experimental static analysis engine, and a dynamic analysis engine.

Static Analysis
The static analysis engine uses 'plugins', which are XML files that specify HTML/JavaScript patterns usiñg regular expressions, to detect and extract AJAX endpoints from code.

Dynamic Analysis
The dynamic analysis engine uses a Google chrome extension to interact with websites as they are rendered in a browser and extract endpoints on the fly.
