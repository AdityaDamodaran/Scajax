package com.sct;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class datastructure {
	int id;
	String page;
	String title;
	String URL;
	String method;
	String contenttype;
	ArrayList<String> parameters= new ArrayList<String>();
	int pcount;
	/**
	 * @param args
	 */
	public datastructure(int aid,String atitle,String aURL,String amethod,String aPage,String aParameter)
	{
		this.id=aid;
		this.title=atitle;
		this.URL=aURL;
		this.method=amethod;
		this.page=aPage;
		if(this.method.indexOf("GET")!=-1)
		{
			if(this.URL.indexOf("?")!=-1){
				try{
			String temp = this.URL.substring(this.URL.indexOf("?"));
			this.URL=this.URL.substring(0,this.URL.indexOf("?"));
			temp=temp.substring(1);
		//	temp=temp.substring(0,temp.length()-1);
			Pattern rJSON = Pattern.compile("/(?:\"(\\\\u[a-zA-Z0-9]{4}|\\\\[^u]|[^\\\\\"])*\"(\\s*:)?|\\b(true|false|null)\\b|-?\\d+(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?|([\\{\\}\\[\\]]))/"); 
			Matcher mJSON = rJSON.matcher(temp);
		//	Pattern rNVP = Pattern.compile("/(?:\\?|\\&)(?<key>[\\w]+)=(?<value>[\\w+,.-]+)(?:\\:?)(?<option>[\\w,]*)/");
			Pattern rNVP = Pattern.compile("(\\w*)=(\\w*)[\\&(\\w*)=(\\w*)]*");
			Matcher mNVP = rNVP.matcher(temp);
			if(mJSON.matches())
			{
				//JSON
				contenttype="JSON";
				parameters.add(temp);
			}
			
			else if(mNVP.matches())
			{
				//NAME-VALUE PAIRS
				contenttype="NVP";
				if(temp.indexOf("&")!=-1)
				{
				String temp2[] = temp.split("&");
				for(String val : temp2)
				{
					parameters.add(val);
					
				}
				}
			}
			else
			{
				contenttype="UNKNOWN";
				parameters.add(temp);
			}
				}
				catch(Exception e){
					contenttype="UNKNOWN";
					parameters.add("");
						
				}
			}
			else
			{
				contenttype="UNKNOWN";
				parameters.add("");
			}
		}
		else
		{
			//POST
			
			String temp =aParameter;// aParameter.substring(1);
		//	temp=temp.substring(0,temp.length()-1);
			temp=temp.trim();
			if(temp.startsWith("\'")||temp.startsWith("\""))
				temp=temp.substring(1);
			if(temp.endsWith("\'")||temp.endsWith("\""))
				temp=temp.substring(0,temp.length()-1);
			
			Pattern rJSON = Pattern.compile("/(?:\"(\\\\u[a-zA-Z0-9]{4}|\\\\[^u]|[^\\\\\"])*\"(\\s*:)?|\\b(true|false|null)\\b|-?\\d+(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?|([\\{\\}\\[\\]]))/"); 
			Matcher mJSON = rJSON.matcher(temp);
			Pattern rNVP = Pattern.compile("(\\w*)=(\\w*)[\\&(\\w*)=(\\w*)]*");
			Matcher mNVP = rNVP.matcher(temp);
			if(mJSON.matches())
			{
				//JSON
				contenttype="JSON";
				parameters.add(temp);
			}
			else if(mNVP.matches())
			{
				//NAME-VALUE PAIRS
				contenttype="NVP";
				if(temp.indexOf("&")!=-1)
				{
				String temp2[] = temp.split("&");
				for(String val : temp2)
				{
					parameters.add(val);
					
				}
				}
			}
			else
			{
				contenttype="UNKNOWN";
				parameters.add(temp);
			}
	
		}
	
	}
	public datastructure(int aid,String aURL,String amethod,String aPage,String aParameter)
	{
		this.id=aid;
		this.title=amethod;
		this.URL=aURL;
		this.method=amethod;
		this.page=aPage;
		if(this.method.substring(0, 3).equals("GET"))
		{
			try{
			String temp = this.URL.substring(this.URL.indexOf("?")+1);
			this.URL=this.URL.substring(0,this.URL.indexOf("?"));
			String originalParam=temp;
			try
			{
				contenttype="NVP";
				if(temp.indexOf("&")!=-1)
				{
					String temp2[] = temp.split("&");
					for(String val : temp2)
					{
						parameters.add(val);
					
					}
				}
				else
				{
					parameters.add(temp);
									
				}
				
			}
			catch(Exception e)
			{
				contenttype="UNKNOWN";
				parameters.add(originalParam);
				
			}
			}
			catch(Exception e)
			{
				
			}
			
		}
		else
		{
			//POST
			String temp = aParameter.substring(1);
			temp=temp.substring(0,temp.length()-1);
			Pattern rJSON = Pattern.compile("/(?:\"(\\\\u[a-zA-Z0-9]{4}|\\\\[^u]|[^\\\\\"])*\"(\\s*:)?|\\b(true|false|null)\\b|-?\\d+(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?|([\\{\\}\\[\\]]))/"); 
			Matcher mJSON = rJSON.matcher(temp);
			Pattern rNVP = Pattern.compile("/(?:\\?|\\&)(?<key>[\\w]+)=(?<value>[\\w+,.-]+)(?:\\:?)(?<option>[\\w,]*)/");
			Matcher mNVP = rNVP.matcher("?"+temp);
			if(mJSON.matches())
			{
				//JSON
				contenttype="JSON";
				parameters.add(temp);
			}
			else if(mNVP.matches())
			{
				//NAME-VALUE PAIRS
				contenttype="NVP";
				if(temp.indexOf("&")!=-1)
				{
				String temp2[] = temp.split("&");
				for(String val : temp2)
				{
					parameters.add(val);
					
				}
				}
			}
			else
			{
				contenttype="UNKNOWN";
				parameters.add(temp);
			}
	
		}
	
	}

}
