package com.sct;

import java.util.regex.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class MainWindow {
public Server server;
protected static Shell shell;
private Text text;
static Button btnAnalyse;
static ScanWindow shell2;
Label progress;	
MainWindow mw = this;
Boolean verify=false;

public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
			
		} 
		catch (Exception e) {
//			e.printStackTrace();
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
	protected void createContents() {
		shell = new Shell();
		shell.setSize(762, 303);
		shell.setText("Scajax Beta");
		shell.addListener(SWT.Close, new Listener()
	    {
	        public void handleEvent(Event event)
	        {
	        	if(server!=null)
	        	{
	        			server.shutdown();
	        		
	        	}
	        }
	    });
		
		//UI
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 962, 243);
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(10, 39, 299, 21);
		Label lblPleaseEnterA = new Label(composite, SWT.NONE);
		lblPleaseEnterA.setBounds(10, 18, 180, 15);
		lblPleaseEnterA.setText("Please enter a URL to begin");
		final Button btnSpiderThisSite = new Button(composite, SWT.CHECK);
		btnSpiderThisSite.setBounds(323, 39, 93, 16);
		btnSpiderThisSite.setText("Spider this site (Experimental)");
		final Button btnVerify = new Button(composite, SWT.CHECK);
		btnVerify.setBounds(443, 39, 213, 16);
		btnVerify.setText("Verify each endpoint");
		final Button btnStaticAnalysis = new Button(composite, SWT.RADIO);
		btnStaticAnalysis.setBounds(10, 75, 96, 16);
		btnStaticAnalysis.setText("Static Analysis");
		final Button btnDynamicAnalysis = new Button(composite, SWT.RADIO);
		btnDynamicAnalysis.setBounds(10, 97, 128, 16);
		btnDynamicAnalysis.setText("Dynamic Analysis");
		Label lblStaticAnalysisScans = new Label(composite, SWT.NONE);
		lblStaticAnalysisScans.setBounds(402, 80, 299, 38);
		lblStaticAnalysisScans.setText("Static Analysis scans webpages for AJAX code patterns \r\nspecified in the plugins directory.");
		progress = new Label(composite, SWT.NONE);
		progress.setBounds(10, 216, 406, 17);
		progress.setText("Scanning, please wait...");
		progress.setVisible(false);
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(402, 133, 299, 67);
		lblNewLabel.setText("Dynamic Analysis uses browser extensions to detect \r\nAJAX endpoints and parameter functions. You must \r\ntrigger AJAX requests by interacting with the page \r\nvia the browser. ");
		btnAnalyse = new Button(composite, SWT.NONE);
		btnAnalyse.setBounds(651, 208, 75, 25);
		btnAnalyse.setText("Analyse");
		btnAnalyse.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent e) {
				try {
					//Input Validation
					Pattern urlp = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)",Pattern.DOTALL);
					Matcher urlm = urlp.matcher(text.getText());
					if(!urlm.matches()&&text.getText().toString().indexOf("localhost")==-1)
					{
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			        
			        messageBox.setText("Error");
			        messageBox.setMessage("Please Enter a valid URL");
			        messageBox.open();
					}
					else
					{
						if(!btnStaticAnalysis.getSelection()&&!btnDynamicAnalysis.getSelection())
						{
							MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
					        
					        messageBox.setText("Error");
					        messageBox.setMessage("Please select either static or dynamic analysis");
					        messageBox.open();
					
						}
						else if(btnStaticAnalysis.getSelection()&&btnDynamicAnalysis.getSelection())
						{
							MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
					        
					        messageBox.setText("Error");
					        messageBox.setMessage("The application does not support hybrid analysis at this time");
					        messageBox.open();
					
						}
						else
						{
							int mode;
							if(btnStaticAnalysis.getSelection())
							{
								mode=1;
							//	Splash loading = new Splash(shell, SWT.NONE);
								progress.setVisible(true);
								shell2 = new ScanWindow();
								shell2.open(text.getText(), btnSpiderThisSite.getSelection(), btnVerify.getSelection(), mode, mw );
								
							}
							else
							{
								mode=2;
							server = new Server(text.getText());
							new Thread(server).start();
							}
							}
							
					}
				} catch (Exception e1) {
//					e1.printStackTrace();
				}	
				
			}

			public void mouseUp(MouseEvent e) {
				
			}});
Menu bar = new Menu (shell, SWT.BAR);
			shell.setMenuBar (bar);
			MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
			fileItem.setText ("&File");
			Menu submenu = new Menu (shell, SWT.DROP_DOWN);
			fileItem.setMenu (submenu);
			MenuItem item = new MenuItem (submenu, SWT.PUSH);
				item.addListener (SWT.Selection, new Listener () {
				//@Override
				public void handleEvent (Event e) {
					System.out.println ("Select All");
				}
			});
		
	}
	public void frameLoaded()
	{
		progress.setVisible(false);
	}
}
