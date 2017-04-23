 package controller;

import java.io.File;

import org.eclipse.jetty.server.Server;

import managers.ServerManager;
import utils.Constants;
import utils.DatabaseUtils;
import utils.WebServerUtils;

public class Controller {

	
	private Controller ( ){
		
		
		DatabaseUtils.createTablesQuery();
//		WebServerUtils.fileHandler("index.html");
//		WebServerUtils.submitingNewTodoHandler(); 
		
		WebServerUtils.MakingNewServer();
		
		try {
			ServerManager.getInstance().start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	public static void main(String[] args) {
		new Controller();
	}
}
