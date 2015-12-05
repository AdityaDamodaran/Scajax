package com.sct;
import java.net.*;
import java.io.*;

public class Server implements Runnable {
Thread mainthread;
int running=1;
String scope;
ServerSocket ssock;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
              
	}
	public void shutdown()
	{
		try {
			ssock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public Server(String scope)
{
	this.scope=scope;
}
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
			this.mainthread = Thread.currentThread();
			try {
				ScanWindow sw = new ScanWindow();
				sw.open(scope, false, 2);
				ssock = new ServerSocket(7722);
				while(running==1)
				{
					Socket client=ssock.accept();
					
					InputStream cli = client.getInputStream();
					//OutputStream output = client.getOutputStream();
				     //  output.write(("1").getBytes());
						BufferedReader in = new BufferedReader(new InputStreamReader(cli));
						String line;
						String request="";
						int length=0;
						String body = "";
						while ((line = in.readLine()) != null) {
			                if (line.equals("")) { // last line of request message
			                                        // header is a
			                                        // blank line (\r\n\r\n)
			                    break; // quit while loop when last line of header is
			                            // reached
			                }

			                // checking line if it has information about Content-Length
			                // weather it has message body or not
			                if (line.startsWith("Content-Length: ")) { // get the
			                                                            // content-length
			                    int index = line.indexOf(':') + 1;
			                    String len = line.substring(index).trim();
			                    length = Integer.parseInt(len);
			                }

			                request= request + line + "\n"; // append the request
			            } // end of while to read headers

			            // if there is Message body, go in to this loop
			            if (length > 0) {
			                int read;
			                while ((read = in.read()) != -1) {
			                    body+=Character.toString((char) read);
			                    if (body.length() == length)
			                        break;
			                }
			            }

			            request+=body;
			            String[] bd = body.split("\\$\\$\\$\\$\\$\\$\\$");
			            
			            System.out.println("URL:"+URLDecoder.decode(bd[0].split("=")[1], "UTF-8"));
			             System.out.println("Method:"+URLDecoder.decode(bd[1].split("=")[1], "UTF-8"));
			             System.out.println("Page:"+URLDecoder.decode(bd[2].split("=")[1], "UTF-8"));
				         System.out.println("Body:"+URLDecoder.decode(bd[3].split("=")[1], "UTF-8"));
			            sw.fromServer(URLDecoder.decode(bd[0].split("=")[1], "UTF-8"), URLDecoder.decode(bd[1].split("=")[1], "UTF-8"), URLDecoder.decode(bd[2].split("=")[1], "UTF-8"), URLDecoder.decode(bd[3].split("=")[1], "UTF-8"));
				    				        		
				//       output.flush();
				       client.close();
				  //     output.close();
				       cli.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
