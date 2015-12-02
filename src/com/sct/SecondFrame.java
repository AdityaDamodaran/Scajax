package com.sct;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class SecondFrame {

	public Shell shell;
	private Text text;
	private Text text2;
	private Table table;
	public List list;
	public String url2;
	public boolean spider2;
	public boolean verify;
	public int mode2;
	public datastructure[] localds = new datastructure[1000];
	public int dscount=0;
	public int sel =0;
	public URL jarpath = this.getClass().getProtectionDomain().getCodeSource().getLocation();
	public static void main(String[] args) {
		try {
			SecondFrame window = new SecondFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public void open(String url, boolean spider, boolean verify,int mode, final MainFrame mf) {
		url2=url;
		spider2=spider;
		mode2=mode;
		this.verify=verify;
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	createContents();
		    	mf.frameLoaded();
		    	shell.open();
				shell.layout();
			
		    }
		});
	//	while (!shell.isDisposed()) {
		//	if (!display.readAndDispatch()) {
			//	display.sleep();
		//	}
	//	}
	}
	public void open(String url, boolean spider, int mode) {
		url2=url;
		spider2=spider;
		mode2=mode;
	
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	createContents();
		    	shell.open();
				shell.layout();
			
		    }
		});
	//	while (!shell.isDisposed()) {
		//	if (!display.readAndDispatch()) {
			//	display.sleep();
		//	}
	//	}
	}

	public void scanner(String html,String pg) throws URISyntaxException{
	/*	ClassLoader classloader = getClass().getClassLoader();
		final URL urlf = classloader.getResource("/plugins");
		 if (urlf != null) {
	            final File plgs = new File(urlf.toURI());
	            for (File plg : plgs.listFiles()) {
	                System.out.println(plg.getName());
	            }
	    }


	*/
	//Debug:
	//File plugindir = new File("C:\\Dev\\SCT\\plugins");
	File plugindir = new File(jarpath.getPath()+"plugins");
	Document plgxml;
	DocumentBuilderFactory docb = DocumentBuilderFactory.newInstance();
	
	for (File plg : plugindir.listFiles()) {
		DocumentBuilder dbuild;
		try {
			dbuild = docb.newDocumentBuilder();
			plgxml = dbuild.parse(plg);
			//Element doc = plgxml.getDocumentElement();
			//NodeList n = doc.getElementsByTagName("plugin");
			//if(n.item(0).getAttributes().getNamedItem("type").toString()=="2")
			//{
			XPath xPath = XPathFactory.newInstance().newXPath();
		    NodeList nodes = (NodeList) xPath.evaluate("//plugin/@type", plgxml, XPathConstants.NODESET);
		    Node node = nodes.item(0);
		        if(node.getTextContent().equals("1"))
		        {
		        	String method="",endpoint="",param="";
		        	System.out.println("Match 1 trial");
		        	nodes = (NodeList) xPath.evaluate("//plugin/title/@name", plgxml, XPathConstants.NODESET);
		        	node = nodes.item(0);
		        	String title = node.getTextContent();
		        	if (title.indexOf("GET")!=-1)
		        	{
		        		method="GET";
		        	}
		        	if (title.indexOf("POST")!=-1)
		        	{
		        		method="POST";
		        	}
		        	nodes = (NodeList) xPath.evaluate("//plugin/title/regex/@captgrps", plgxml, XPathConstants.NODESET);
		        	node = nodes.item(0);
		        	int cg = Integer.parseInt(node.getTextContent());
		        	NodeList nodesmain = (NodeList) xPath.evaluate("//plugin/title/regex/re/text()", plgxml, XPathConstants.NODESET);
		        	Node nodemain = nodesmain.item(0);
		        	Pattern re1 = Pattern.compile(nodemain.getTextContent());
		    					Matcher mat = re1.matcher(html);
		    					
		    					System.out.println(re1.pattern());
		    					while(mat.find())
		    					{
		    						for(int k=0;k<cg;k++)
		    						{
		    							if(k==0)
		    								nodes = (NodeList) xPath.evaluate("//plugin/title/regex/cg/@name", plgxml, XPathConstants.NODESET);
		    							else
		    								nodes = (NodeList) xPath.evaluate("//plugin/title/regex/cg["+(k+1)+"]/@name", plgxml, XPathConstants.NODESET);
		    							node = nodes.item(0);
		    				        	if(node.getTextContent().toLowerCase().equals("method"))
		    				        	{
		    				        		method=mat.group(k+1);
		    				        	}
		    				        	if(node.getTextContent().toLowerCase().equals("url"))
		    				        	{
		    				        		endpoint=mat.group(k+1);
		    				        	}
		    				        	if(node.getTextContent().toLowerCase().equals("param"))
		    				        	{
		    				        		param=mat.group(k+1);
		    				        	}
		    				        }
		    						endpoint=endpoint.replaceAll("\""," ").trim();
					        		int flag=1;
					        		if(verify==true)
					        		{
					        		try{
					    				final URL url = new URL(endpoint);
					    				final URLConnection urlConnection = url.openConnection();
					    				urlConnection.setDoOutput(true);
					    				urlConnection.connect();
					    				if(method.equals("POST"))
					    				{
					    				final OutputStream outputStream = urlConnection.getOutputStream();
					    				outputStream.write(param.getBytes("UTF-8"));
					    				outputStream.flush();
					    				}
					    					BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					    					String inputLine;
					    					String response ="";
					    					while ((inputLine = in.readLine()) != null) 
					    						response=response+inputLine;
					    					in.close();
					   
					        			} catch (FileNotFoundException e1) {
					    						new MessageBox(shell).setMessage("Page not found");
					    					flag=0;
					        			} catch (IOException e1) {
					    					// TODO Auto-generated catch block
					        				flag=0;
					        			}
					        			}
					    				if(flag==1){
		    			     		localds[dscount]= new datastructure(dscount,title, endpoint, method, pg, param);
					        		dscount++;
					    			}
		    					}
		        
		        }
		        else if(node.getTextContent().equals("2"))
		        {
		        	
		        	String method = "",endpoint = "",param="";
		        	System.out.println("Match 1 trial");
		        	nodes = (NodeList) xPath.evaluate("//plugin/title/@name", plgxml, XPathConstants.NODESET);
		        	node = nodes.item(0);
		        	String title = node.getTextContent();
		        	nodes = (NodeList) xPath.evaluate("//plugin/title/regex/@captgrps", plgxml, XPathConstants.NODESET);
		        	node = nodes.item(0);
		        	@SuppressWarnings("unused")
					int cg = Integer.parseInt(node.getTextContent());
		        	NodeList nodesmain = (NodeList) xPath.evaluate("//plugin/title/regex/re/text()", plgxml, XPathConstants.NODESET);
		        	Node nodemain = nodesmain.item(0);
		       /// 	nodes = (NodeList) xPath.evaluate("//plugin/title/regex/re/text()", plgxml, XPathConstants.NODESET);
		        	//node = nodes.item(0);
		        	
		        	Pattern re1 = Pattern.compile(nodemain.getTextContent());
					Matcher mat = re1.matcher(html);
					
					System.out.println(re1.pattern());
					while(mat.find())
					{
						System.out.println("Match 2 trial");	
						nodes = (NodeList) xPath.evaluate("//plugin/title/regex[2]/re/text()", plgxml, XPathConstants.NODESET);
			        	node = nodes.item(0);
			        	Pattern re2 = Pattern.compile(mat.group(1).replaceAll("\u00A0", "").trim()+node.getTextContent());
			    		Matcher mat2 = re2.matcher(html);
			    		System.out.println(re2.pattern());
			    		if(mat2.find())
						{
							System.out.println("Match 3 trial");
							NodeList nodescg = (NodeList) xPath.evaluate("//plugin/title/regex[2]/@captgrps", plgxml, XPathConstants.NODESET);
				        	Node nodecg = nodescg.item(0);
				        	int cg2 = Integer.parseInt(nodecg.getTextContent());
							//System.out.println(node.getTextContent()+mat2.group(1)+node2.getTextContent()+mat2.group(2));
				        	for(int k=0;k<cg2;k++)
    						{
    							if(k==0)
    								nodes = (NodeList) xPath.evaluate("//plugin/title/regex[2]/cg/@name", plgxml, XPathConstants.NODESET);
    							else
    								nodes = (NodeList) xPath.evaluate("//plugin/title/regex[2]/cg["+(k+1)+"]/@name", plgxml, XPathConstants.NODESET);
    							node = nodes.item(0);
    				        	if(node.getTextContent().toLowerCase().equals("method"))
    				        	{
    				        		method=mat2.group(k+1);
    				        	}
    				        	if(node.getTextContent().toLowerCase().equals("url"))
    				        	{
    				        		endpoint=mat2.group(k+1);
    				        	}
    				        	if(node.getTextContent().toLowerCase().equals("param"))
    				        	{
    				        		param=mat2.group(k+1);
    				        	}
    				        }
    			        	NodeList nodes2 = (NodeList) xPath.evaluate("//plugin/title/regex[3]/re/text()", plgxml, XPathConstants.NODESET);
				        	node = nodes2.item(0);
				        	Pattern re3 = Pattern.compile(mat.group(1).replaceAll("\u00A0", "").trim()+node.getTextContent());
							Matcher mat3 = re3.matcher(html);
							if(mat3.find())
				        	{
								nodescg = (NodeList) xPath.evaluate("//plugin/title/regex[3]/@captgrps", plgxml, XPathConstants.NODESET);
					        	nodecg = nodescg.item(0);
					        	cg2 = Integer.parseInt(nodecg.getTextContent());
					        	for(int k=0;k<cg2;k++)
	    						{
	    							if(k==0)
	    								nodes = (NodeList) xPath.evaluate("//plugin/title/regex[3]/cg/@name", plgxml, XPathConstants.NODESET);
	    							else
	    								nodes = (NodeList) xPath.evaluate("//plugin/title/regex[3]/cg["+(k+1)+"]/@name", plgxml, XPathConstants.NODESET);
	    							node = nodes.item(0);
	    				        	if(node.getTextContent().toLowerCase().equals("method"))
	    				        	{
	    				        		method=mat3.group(k+1);
	    				        	}
	    				        	if(node.getTextContent().toLowerCase().equals("url"))
	    				        	{
	    				        		endpoint=mat3.group(k+1);
	    				        	}
	    				        	if(node.getTextContent().toLowerCase().equals("param"))
	    				        	{
	    				        		param=mat3.group(k+1);
	    				        	}
	    				        }
					        	endpoint=endpoint.replaceAll("\""," ").trim();
				        		endpoint=endpoint.replaceAll("\'"," ").trim();
				        		if(endpoint.indexOf("http")==-1||endpoint.indexOf("ftp")==-1)
				        		{
				        			try{
				        			endpoint=pg.substring(0,pg.lastIndexOf("/"))+"/"+endpoint;
				        			
				        			}
				        			catch(Exception e)
				        			{
				        				System.out.println(pg);
				        			}
				        		}
				        		int flag=1;
				        		if(verify==true)
				        		{
				        		try{
				    				final URL url = new URL(endpoint);
				    				final URLConnection urlConnection = url.openConnection();
				    				urlConnection.setDoOutput(true);
				    				urlConnection.connect();
				    				if(method.equals("POST"))
				    				{
				    				final OutputStream outputStream = urlConnection.getOutputStream();
				    				outputStream.write(param.getBytes("UTF-8"));
				    				outputStream.flush();
				    				}
				    					BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				    					String inputLine;
				    					String response ="";
				    					while ((inputLine = in.readLine()) != null) 
				    						response=response+inputLine;
				    					in.close();
				   
				        			} catch (FileNotFoundException e1) {
				    						new MessageBox(shell).setMessage("Page not found");
				    					flag=0;
				        			} catch (IOException e1) {
				    					// TODO Auto-generated catch block
				        				flag=0;
				        			}
				        			}
				    				if(flag==1){
				        		localds[dscount]= new datastructure(dscount,title, endpoint, method, pg, param);
				        		dscount++;
				    			}
				        	}	
						}
					}
		        }
		        
			//}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	}
		
	public void fromServer(String sURL, String sMethod,final String sPage, String sParam)
	{
	//#debug	
	//	if(sPage.indexOf(url2)!=-1)
	//	{
		localds[dscount]= new datastructure(dscount, sURL, sMethod, sPage, sParam);
		dscount++;
		Display.getDefault().asyncExec(new Runnable() {
            public void run() {
            	System.out.println("Here!");
            	int flag=0;
            	for(String listitem : list.getItems())
        		{
        			if(listitem.toString().equals(sPage))
        			{
        				flag=1;
        			}
        		}	
if(flag==0){
            	list.add(sPage);
            	System.out.println("Update!");
}		
            	}
		});
	//	}
	}
	public void crawl(String rawhtml)
	{
		rawhtml.replaceAll("\\s", " ");
		
	}
	
	public void createContents() {
		shell = new Shell();
		shell.setSize(876, 520);
		shell.setText("Scanner");
		final List list2;
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 10, 850, 461);
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(471, 77, 369, 170);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(table, SWT.LEFT);
		tc1.setText("Name");
		tc1.setWidth(table.getBounds().width/2);
		  TableColumn tc2 = new TableColumn(table,SWT.LEFT);
		  tc2.setText("Value");
		  tc2.setWidth(table.getBounds().width/2);	
		Label lblWebpagesDetected = new Label(composite, SWT.NONE);
		lblWebpagesDetected.setBounds(10, 10, 114, 15);
		lblWebpagesDetected.setText("Webpages detected:");
		list2 = new List(composite, SWT.BORDER);
		list2.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
	
			table.clearAll();
			int id = Integer.parseInt(list2.getSelection()[0].substring(list2.getSelection()[0].lastIndexOf("\\")+1));
			sel=id;
			datastructure dsl = localds[id];
			text.setText(dsl.URL);
			if(dsl.contenttype.equals("JSON"))
			{
			TableItem tbitem = new TableItem(table, SWT.NONE,0);
			tbitem.setText(0, "JSON");
			tbitem.setText(1, dsl.parameters.get(0));
			
			}
			else if(dsl.contenttype.equals("NVP"))
			{
				int j = 0;
				for(String tbdata:dsl.parameters)
				{
					TableItem tbitem = new TableItem(table, SWT.NONE,j);
					tbitem.setText(0, tbdata.split("=")[0]);
					tbitem.setText(1, tbdata.split("=")[1]);
					j++;
							
				}
			}
			else
			{
				TableItem tbitem = new TableItem(table, SWT.NONE,0);
				tbitem.setText(0, "UNKNOWN");
				tbitem.setText(1, dsl.parameters.get(0));
					
			}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		 
		 table.addSelectionListener(new SelectionAdapter() {
		         @Override
		         public void widgetSelected(SelectionEvent e) {
		             final TableEditor editor = new TableEditor(table);               
		             editor.horizontalAlignment = SWT.LEFT;
		             editor.grabHorizontal = true;
		             editor.minimumWidth = 50;
		             Control oldEditor = editor.getEditor();
		             if (oldEditor != null)
		                 oldEditor.dispose();                
		             TableItem item = (TableItem) e.item;
		             if (item == null)
		                 return;
		             Text newEditor = new Text(table, SWT.NONE);
		             newEditor.setText(item.getText(1));

		             newEditor.addModifyListener(new ModifyListener() {
		                 public void modifyText(ModifyEvent me) {
		                     Text text = (Text) editor.getEditor();
		                     editor.getItem()
		                             .setText(1, text.getText());
		                 }
		             });         
		             newEditor.selectAll();
		             newEditor.setFocus();           
		             editor.setEditor(newEditor, item, 1);      

		         }
		     });     
		list = new List(composite, SWT.BORDER | SWT.V_SCROLL);
		list.setBounds(10, 28, 243, 219);
		list.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			String pgload=list.getSelection()[0];
			System.out.println(list.getSelection()[0]);
			datastructure dsl;
			list2.removeAll();
			for(int i=0;i<dscount;i++)
			{
				dsl=localds[i];
				if(dsl.page.equals(pgload))
				{
				list2.add(dsl.title+"\\"+dsl.id);
				}
			}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String pgload=list.getSelection()[0];
				for(datastructure dsl : localds)
				{
					if(dsl.page.equals(pgload))
					{
					list2.add(dsl.title+"\\"+dsl.id);
					}
				}
					
			}
		});
		
		Label lblAjaxRequests = new Label(composite, SWT.NONE);
		lblAjaxRequests.setBounds(259, 10, 101, 15);
		lblAjaxRequests.setText("AJAX requests:");
		
		list2.setBounds(259, 28, 206, 219);
		
		text2 = new Text(composite, SWT.WRAP);
		text2.setBounds(10,287,830,164);
		
		Label lblEndpoint = new Label(composite, SWT.NONE);
		lblEndpoint.setBounds(471, 10, 55, 15);
		lblEndpoint.setText("Endpoint:");
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(471, 29, 369, 21);
		
		Label lblParameters = new Label(composite, SWT.NONE);
		lblParameters.setBounds(471, 56, 71, 15);
		lblParameters.setText("Parameters:");
		
		Label lblResponse = new Label(composite, SWT.NONE);
		lblResponse.setBounds(10, 266, 55, 15);
		lblResponse.setText("Response:");
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(765, 253, 75, 25);
		btnNewButton.setText("Fire Request");
		btnNewButton.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
				try{
				final URL url = new URL(text.getText());
				System.out.println(url.toString());
				final URLConnection urlConnection = url.openConnection();
				urlConnection.setDoOutput(true);
				urlConnection.connect();
				if(localds[sel].method.equals("POST"))
				{
				final OutputStream outputStream = urlConnection.getOutputStream();
				String fuzzp="";
				int flag=0;
				for(TableItem row : table.getItems())
				{
					if(row.getText(0)!=null)
					{
						if(row.getText(0).equals("JSON"))
						{
							fuzzp=fuzzp+row.getText(1);
						}
						else if(row.getText(0).equals("UNKNOWN"))
						{
							fuzzp=fuzzp+row.getText(1);
						}
						else
						{
							//NVP
							if(flag==0)
							{
							fuzzp=fuzzp+row.getText(0)+"="+row.getText(1);
							flag=1;
							}
							else
							{
							fuzzp=fuzzp+"&"+row.getText(0)+"="+row.getText(1);
							}
						}
					}
				}
					outputStream.write(fuzzp.getBytes("UTF-8"));
					outputStream.flush();
				}
					BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					String inputLine;
					String response ="";
					while ((inputLine = in.readLine()) != null) 
						response=response+inputLine;
					in.close();
				//	Report resp = new Report(shell, SWT.NONE, response);
				//	resp.open();
					text2.setText(response);
				} catch (FileNotFoundException e1) {
						new MessageBox(shell).setMessage("Page not found");
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
					
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		if(mode2==1)
		{
			list.add(url2);
			final WebClient webclient = new WebClient(BrowserVersion.CHROME);
			WebClientOptions webopt = webclient.getOptions();
			webopt.setGeolocationEnabled(false);
			webopt.setDoNotTrackEnabled(true);
			webopt.setJavaScriptEnabled(true);
			webopt.setUseInsecureSSL(true);
			webopt.setThrowExceptionOnScriptError(false);
	//		webclient.waitForBackgroundJavaScript(1000);
			try {
				final HtmlPage htm = (HtmlPage) webclient.getPage(url2);
			//	text.setText(htm.asXml());
			//	String //str = new String("<script src=\"/_apps/new/js/megamenu.js\"></script><script src=\"/_apps/new/js/simple-accordion.js\"></script><script type=\"text/javascript\"//<![CDAT[$(document).foundation();/*var parentAccordion1=new TINY.accordion.slider(\"parentAccordion1\");parentAccordion1.init(\"acc\",\"h3\",false,\"selected\");*/var parentAccordion2=new TINY.accordion.slider(\"parentAccordion2\");parentAccordion2.init(\"acc2\",\"h3\",true,0,\"open");
				try {
					scanner(htm.asXml(),url2);
					list.select(0);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				webclient.close();
			//	Pattern re1 = Pattern.compile("var\\s*(.*)\\s*=\\s*new\\s*XMLHttpRequest\\(\\);");
			//	Matcher mat = re1.matcher(htm.asXml());
				
				
			//	while(mat.find())
		//		{
				//	System.out.println(mat.group(1).replaceAll("\u00A0", "").trim()+"s");
				//	Pattern re2 = Pattern.compile(mat.group(1).replaceAll("\u00A0", "").trim()+"\\.open\\((.*),(.*)\\);");
			//		Matcher mat2 = re2.matcher(htm.asXml());
				//	while(mat2.find())
					//{
						//System.out.println("Method:"+mat2.group(1)+"Endpoint:"+mat2.group(2));
			//			Pattern re3 = Pattern.compile(mat.group(1).replaceAll("\u00A0", "").trim()+"\\.send\\((.*)\\);");
			//			Matcher mat3 = re3.matcher(htm.asXml());
			//			if(mat3.find())
			//			System.out.println("Params:"+mat3.group(1));
							
		//			}
					
		//		}
				
					
			} catch (FailingHttpStatusCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else
		{
			
		}
	}
}
