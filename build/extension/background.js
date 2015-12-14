
  //while(res==null);
console.log("bright as day.");
var content='text/plain';
chrome.webRequest.onBeforeRequest.addListener(
  function(info) {
if(info.url.substr(0,21)!="http://localhost:7722")
{
  
  var tabLocation=info.url;
  if(info.tabId>0)
chrome.tabs.get(info.tabId, function(loc){
  tabLocation=loc.url;
});
var xhr = new XMLHttpRequest();
var str='';
//for(var i=0;i<info.requestHeaders.length;i++)
//Must Log Errors
//{
//	str=str+i+":"+info.requestHeaders[i].name;		  
//} 
//str=str+"body:"+info.requestBody;
//if(info.requestBody.formData!=null) 
//console.log(info.requestBody.formData);
if(info.requestBody!=undefined){
  var bb = new Blob([new Uint8Array(info.requestBody.raw[0].bytes)],{type:"text/plain"});
  var reader = new FileReader();
  reader.addEventListener("loadend",function(){
    console.log(reader.result);
    
xhr.open("POST", "http://localhost:7722", true);
xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
xhr.send("url="+encodeURIComponent(info.url)+"$$$$$$$method="+encodeURIComponent(info.method)+"$$$$$$$pg="+encodeURIComponent(tabLocation)+"$$$$$$$body="+encodeURIComponent(reader.result));
  });
  reader.readAsText(bb);
}
else
{
xhr.open("POST", "http://localhost:7722", true);
xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
xhr.send("url="+encodeURIComponent(info.url)+"$$$$$$$method="+encodeURIComponent(info.method)+"$$$$$$$pg="+encodeURIComponent(tabLocation)+"$$$$$$$body=na");

}}

},{
    urls: [
      "<all_urls>"
    ], types: ["xmlhttprequest"]},
["requestBody"]);
