import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;


public class Alaki {
	
	public static void main(String[] args)
	        throws Exception
	    {
		   // Create a basic jetty server object that will listen on port 8080.
		        // Note that if you set this to port 0 then a randomly available port
		          // will be assigned that you can either look in the logs for the port,
		          // or programmatically obtain it for use in test cases.
		          Server server = new Server(8086);
//		40  
		
		
		
		          ServletHandler handler = new ServletHandler();
		          server.setHandler(handler);
		
		          handler.addServletWithMapping(HelloServlet.class, "/*");
		  
		
		         server.start();
		
	              server.join();
	    }
	
	 @SuppressWarnings("serial")
	      public static class HelloServlet extends HttpServlet
	      {
	           @Override
	           protected void doGet( HttpServletRequest request,
	                                 HttpServletResponse response ) throws ServletException,
	                                                               IOException
	           {
	               response.setContentType("text/html");
	               response.setStatus(HttpServletResponse.SC_OK);
	               response.getWriter().println("<h1>Hello from HelloServlet</h1>");
	           }
	       }
}



