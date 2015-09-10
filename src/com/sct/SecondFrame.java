package com.sct;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SecondFrame {

	public Shell shell;
	private Text text;
	private Table table;
	public List list;
	public String url2;
	public boolean spider2;
	public int mode2;
	public datastructure[] localds = new datastructure[1000];
	public int dscount=0;
	public int sel =0;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SecondFrame window = new SecondFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
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
	public void open(String url, boolean spider, int mode) {
		url2=url;
		spider2=spider;
		mode2=mode;
		//Display display = Display.getDefault();
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
		
	File plugindir = new File("C:\\Dev\\SCT\\plugins");
	Document plgxml;
	DocumentBuilderFactory docb = DocumentBuilderFactory.newInstance();
	
	for (File plg : plugindir.listFiles()) {
		DocumentBuilder dbuild;
		try {
			dbuild = docb.newDocumentBuilder();
			plgxml = dbuild.parse(plg);
			Element doc = plgxml.getDocumentElement();
			NodeList n = doc.getElementsByTagName("plugin");
			//if(n.item(0).getAttributes().getNamedItem("type").toString()=="2")
			//{
			XPath xPath = XPathFactory.newInstance().newXPath();
		    NodeList nodes = (NodeList) xPath.evaluate("//plugin/@type", plgxml, XPathConstants.NODESET);
		    Node node = nodes.item(0);
		        if(node.getTextContent().equals("1"))
		        {
		        	
		        }
		        if(node.getTextContent().equals("2"))
		        {
		        	String method,endpoint,param;
		        	System.out.println("Match 1 trial");
		        	nodes = (NodeList) xPath.evaluate("//plugin/title/@name", plgxml, XPathConstants.NODESET);
		        	node = nodes.item(0);
		        	String title = node.getTextContent();
		        	nodes = (NodeList) xPath.evaluate("//plugin/title/regex/@captgrps", plgxml, XPathConstants.NODESET);
		        	node = nodes.item(0);
		        	int cg = Integer.parseInt(node.getTextContent());
		        	nodes = (NodeList) xPath.evaluate("//plugin/title/regex/re/text()", plgxml, XPathConstants.NODESET);
		        	node = nodes.item(0);
		        	
		        	Pattern re1 = Pattern.compile(node.getTextContent());
					Matcher mat = re1.matcher(html);
					System.out.println(re1.pattern());
					if(mat.find())
					{
						System.out.println("Match 2 trial");	
						nodes = (NodeList) xPath.evaluate("//plugin/title/regex[2]/re/text()", plgxml, XPathConstants.NODESET);
			        	node = nodes.item(0);
			        	Pattern re2 = Pattern.compile(mat.group(1).replaceAll("\u00A0", "").trim()+node.getTextContent());
			    		Matcher mat2 = re2.matcher(html);
						if(mat2.find())
						{
							System.out.println("Match 3 trial");
						//	nodes = (NodeList) xPath.evaluate("//plugin/title/regex[2]/cg/@name", plgxml, XPathConstants.NODESET);
				        //	node = nodes.item(0);
				        //	nodes = (NodeList) xPath.evaluate("//plugin/title/regex[2]/cg[2]/@name", plgxml, XPathConstants.NODESET);
				        //	Node node2 = nodes.item(0);
				        	//System.out.println(node.getTextContent()+mat2.group(1)+node2.getTextContent()+mat2.group(2));
				        	method=mat2.group(0);
				        	endpoint=mat2.group(1);
				        //	NodeList nodes2 = (NodeList) xPath.evaluate("//plugin/title/regex[3]/re/text()", plgxml, XPathConstants.NODESET);
				        //	node = nodes2.item(0);
				        	
				        	Pattern re3 = Pattern.compile(mat.group(1).replaceAll("\u00A0", "").trim()+"\\.send\\((.*)\\);");
							Matcher mat3 = re3.matcher(html);
						//	nodes = (NodeList) xPath.evaluate("//plugin/title/regex[3]/cg/@name", plgxml, XPathConstants.NODESET);
				        //	node2 = nodes.item(0);
							if(mat3.find())
				        	{
				        		param=mat3.group(0);
				        		localds[dscount]= new datastructure(dscount,title, endpoint, method, pg, param);
				        		dscount++;
				        		//System.out.println(node2.getTextContent()+mat3.group(1));
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
	//#debug	if(sPage.contains(url2))
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
		
	/**
	 * Create contents of the window.
	 */
	public void createContents() {
		shell = new Shell();
		shell.setSize(575, 300);
		shell.setText("SWT Application");
		final List list_1;
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 10, 549, 241);
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(320, 71, 219, 123);
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
		list_1 = new List(composite, SWT.BORDER);
		list_1.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			//	System.out.println(list_1.getSelection()[0].substring(list_1.getSelection()[0].lastIndexOf("\\")+1));
			table.clearAll();
			int id = Integer.parseInt(list_1.getSelection()[0].substring(list_1.getSelection()[0].lastIndexOf("\\")+1));
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
		list.setBounds(10, 28, 132, 166);
		list.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			String pgload=list.getSelection()[0];
			System.out.println(list.getSelection()[0]);
			datastructure dsl;
			for(int i=0;i<dscount;i++)
			{
				dsl=localds[i];
				list_1.removeAll();
				if(dsl.page.equals(pgload))
				{
				list_1.add(dsl.title+"\\"+dsl.id);
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
					list_1.add(dsl.title+"\\"+dsl.id);
					}
				}
					
			}
		});
		
		Label lblAjaxRequests = new Label(composite, SWT.NONE);
		lblAjaxRequests.setBounds(160, 10, 101, 15);
		lblAjaxRequests.setText("AJAX requests:");
		
		list_1.setBounds(156, 28, 145, 166);
		
		Label lblEndpoint = new Label(composite, SWT.NONE);
		lblEndpoint.setBounds(320, 10, 55, 15);
		lblEndpoint.setText("Endpoint:");
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(320, 29, 219, 21);
		
		Label lblParameters = new Label(composite, SWT.NONE);
		lblParameters.setBounds(320, 56, 71, 15);
		lblParameters.setText("Parameters:");
		
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(464, 206, 75, 25);
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
					Report resp = new Report(shell, SWT.NONE, response);
					resp.open();
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
