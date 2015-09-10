package com.sct;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class Report extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	public String textparam;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Report(Shell parent, int style, String resp) {
		super(parent, style);
		setText("Server response");
		textparam=resp;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		text.setText(textparam);
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText(getText());
		
		Label lblResponse = new Label(shell, SWT.NONE);
		lblResponse.setBounds(10, 10, 55, 15);
		lblResponse.setText("Response:");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(10, 32, 424, 197);
		
		Button btnClose = new Button(shell, SWT.NONE);
		btnClose.setBounds(359, 236, 75, 25);
		btnClose.setText("Close");
		btnClose.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			shell.dispose();	
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
