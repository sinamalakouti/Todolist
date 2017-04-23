package managers;

import org.eclipse.jetty.server.Server;

public class ServerManager extends Server {
 
	   private static ServerManager serverInstance = null;
	   
	   
	   	private ServerManager(){
	   		
		   	   // making a server listening on port 8080

	   		super(80);
	   		
	   		
	   		
	   	}
	
	   public static Server getInstance() {
		      if(serverInstance == null) {
		         serverInstance = new ServerManager();
		      }
		      return serverInstance;
		   }

	
}
