package utils;


import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;

import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import managers.ServerManager;




public class WebServerUtils {

	
	public static void fileHandler(){
	
// TODO : handling couple of files 
	
	    ResourceHandler resource_handler = new ResourceHandler();
	  
	    resource_handler.setResourceBase("files");
	    resource_handler.setDirectoriesListed(true);
	    ContextHandler contextHandler= new ContextHandler("/");

	    contextHandler.setHandler(resource_handler);
	    ServerManager.getInstance().setHandler(contextHandler);
		

	}
	
		// this method submit a new todo to the URL bellow + inserting a new  todo to the data base
	
	public static void submitingNewTodoHandler(){
		
		 ServletHandler handler = new ServletHandler();
//		 ServerManager.getInstance().setHandler(handler);
// 		handler.addServletWithMapping(SubmitNewTodo.class, "/submitingNewTodo");
// 		ServerManager.getInstance().setHandler(handler);

		
	}
	
	public static void MakingNewServer () {
		
		 ResourceHandler resource_handler = new ResourceHandler();
		  
		    resource_handler.setResourceBase("files");
		    resource_handler.setDirectoriesListed(true);
		    ContextHandler contextHandler= new ContextHandler("/");

		    contextHandler.setHandler(resource_handler);
//		    ServerManager.getInstance().setHandler(contextHandler);

		    
		    ServletHandler handler = new ServletHandler();
			 ServerManager.getInstance().setHandler(handler);
//	 		handler.addServletWithMapping(SubmitNewTodo.class, "/submitingNewTodo");
//	 		ServerManager.getInstance().setHandler(handler);
	 		
	 		ContextHandler contextSubmit = new ContextHandler("/submitingNewTodo");
	 		contextSubmit.setHandler(new SubmitNewTodo());
	 	    
	 	    ContextHandler contextGetTable = new ContextHandler("/table");
	 	    contextGetTable.setHandler(new getTable() );
	 	    
	 	    ContextHandler contextMarkAsDone = new ContextHandler("/markasdone");
	 	    contextMarkAsDone.setHandler(new MarkAsDone() );

	 	    ContextHandlerCollection contexts = new ContextHandlerCollection();
	 	    contexts.setHandlers(new Handler[] { contextHandler , contextSubmit , contextGetTable , contextMarkAsDone });
	 	    ServerManager.getInstance().setHandler(contexts);
	 	    
	 	    


		
	}
	
	
	
//	 @SuppressWarnings("serial")
//     public static class SubmitNewTodo extends HttpServlet
//     {
//          @Override
//          protected void doGet( HttpServletRequest request,
//                                HttpServletResponse response ) throws ServletException,
//                                                              IOException
//          {
////        	  response.setContentType("text/html");
////      		response.setStatus(HttpServletResponse.SC_OK);
////      		response.getWriter().println("ok");
//        	  
//        	  String id = request.getParameter("name");
//        	    String password = request.getParameter("lastname");
//        	    DatabaseUtils.insertToTableQuery(id +  "  " +  password);
//
//        	    System.out.println(id + password);
//        	    
//        	    
//          }
//      }
//	 
//	 
//	 
//	 
//	 
//
}

 class SubmitNewTodo extends AbstractHandler
{
	 @SuppressWarnings("unchecked")
    public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response) 
        throws IOException, ServletException
    {
    	
    	JSONObject jsonObj = new JSONObject();
    	String text ;
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        String id = request.getParameter("todoValue");    
  	    try {
			DatabaseUtils.insertToTableQuery(id);
			 jsonObj.put("Todo", id);
		  	    jsonObj.put("TODO_ID", "" + DatabaseUtils.getLastItemIdFromTableQuery("TodolistTable"));
		  	    jsonObj.put("IsDone", "0");
		  	    StringWriter out = new StringWriter();
		  	    jsonObj.writeJSONString(out);
		  	    text = out.toString();  	    
		    	response.getWriter().println(text);
		} catch (SQLException e) {
			System.out.println("log 0 ");
			
		}
  	    
  	    
 
//  	    System.out.println(DatabaseUtils.getLastItemIdFromTableQuery("TodolistTable"));

    }
}
 class getTable extends AbstractHandler 
 {
	 
	 @SuppressWarnings("unchecked")
	public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response) 
		        throws IOException, ServletException
		    {
		 		ArrayList<String []> rows;
		 		JSONArray jsonArray = new JSONArray();
		 		
//	        	JSONObject json = new JSONObject();
	        	
		        response.setContentType("text/html;charset=utf-8");
		        response.setStatus(HttpServletResponse.SC_OK);
		        baseRequest.setHandled(true);
		        
		        rows = DatabaseUtils.SelectAllQuery("TodolistTable");
		        if ( rows == null )
		        	System.out.println("shit");
		        else  
		        {
		        	
		        	for ( int i = 0 ; i < rows.size() ; i++){
		        	JSONObject json = new JSONObject();
		        	json.put("TODO_ID", rows.get(i)[0] );
		        	json.put("Todo", rows.get(i)[1] );
		        	json.put("IsDone", rows.get(i)[2] );
		        	jsonArray.add(json);
		        	
		        	}
		            StringWriter out = new StringWriter();
		        	jsonArray.writeJSONString(out);
		        	String text =  out.toString();
		        	response.getWriter().println(text); 

		        }
		        
//		        String id = request.getParameter("todoValue");    
//		  	    DatabaseUtils.insertToTableQuery(id);
//		  	    response.getWriter().println(DatabaseUtils.getLastItemIdFromTableQuery("TodolistTable")); 
//		  	    System.out.println(DatabaseUtils.getLastItemIdFromTableQuery("TodolistTable"));

		    }
	 
	 
 }
 
 class MarkAsDone  extends AbstractHandler {

		@Override
		public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response) 
				throws IOException, ServletException {
			System.out.println("success");
			
			response.setContentType("text/html;charset=utf-8");
	        response.setStatus(HttpServletResponse.SC_OK);
	        baseRequest.setHandled(true);
	        DatabaseUtils.UpdateQuerry("TODO_ID", request.getParameter("id"));
	        response.getWriter().println("ok"); 

			
			
		}
		 
		 
		  
	 }
	


       

