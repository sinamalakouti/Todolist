package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseUtils {

	// this will make a connection to a database if exists otherwise it will
	// build a new one  
		public static Connection buildConnectionToDatabase(String url) {
	
			// return connection to the url if faild it will return null
	
			Connection connection = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url ,"root","lafusmale");
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	
			return connection;
		}

	public static void createTablesQuery() {

		Connection connection = DatabaseUtils.buildConnectionToDatabase(Constants.url);
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(DatabaseUtils.buildCreatTableStatment("TodolistTable", creatTodolistTableParams()));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static final ArrayList<String> creatTodolistTableParams() {
		ArrayList<String> params = new ArrayList<>();
		params.add("TODO_ID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT");
		params.add(" TodoName  Varchar(100)  NOT NULL ");
		params.add("IsDone INTEGER NOT NULL");
		return params;
	}

	public static String buildCreatTableStatment(String tableName, ArrayList<String> records) {
		
		String result = "create table " + tableName + " ( ";
		
		for ( int i =0 ; i < records.size() ; i ++){
			
			if( i != records.size() - 1)
			result += records.get(i) + ",\n";
			
		}
		result += records.get(records.size()-1) + "  ) ;\n";
		return result; 
		
	}

	public static ArrayList<String>[] creatInsertToTodolistTableParams(String todoName) {

        ArrayList<String>[] params = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            params[i] = new ArrayList<>();
        }

        // for params :
        
        params[0].add("TodoName");
        params[0].add("IsDone");
        

        // for valus
        
        params[1].add("" + todoName + "");
        
        params[1].add(""+ 0 +"" );

        return params;

    }
	
	public static void insertToTableQuery(String todoName) throws SQLException{
		
		ArrayList<String>[] params = DatabaseUtils.creatInsertToTodolistTableParams(todoName);
		PreparedStatement preparedStatement = null;
//		 try {
	             preparedStatement = DatabaseUtils.buildInsertStatement(
		                    "TodolistTable", params[0], params[1]);
//	            		 Constants.connection.prepareStatement();
	             System.out.println("pre pare is \t " + preparedStatement);
	            preparedStatement.execute();

//	        } catch (SQLException e1) {
//	        	System.out.println("log 0");
//
//	        }  
	            
	            preparedStatement.close();

		
		
	}
	
    public static PreparedStatement buildInsertStatement(String tableName, ArrayList<String> params, ArrayList<String> values) {
    	
    	PreparedStatement preparedStatement = null;
    	PreparedStatement preparedStatement2 = null;
    	Connection connection = null;
//    	String query = "INSERT INTO TodolistTable 
        String SQLStatment = "INSERT INTO " + tableName + " (";

        for (int i = 0; i < params.size(); i++) {

            if (i != params.size() - 1)
                SQLStatment += params.get(i) + ", ";
            else SQLStatment += params.get(i) + " ) ";
        }
        SQLStatment += " VALUES ( ";
        for ( int i =0 ; i < values.size() ; i ++)
        {
        	if  ( i != values.size() - 1)
        	SQLStatment+= "? , ";
        	else 
        		SQLStatment += " ? );";
        	
        }
        try {
        	
        	 connection = buildConnectionToDatabase(Constants.url);
			preparedStatement = connection.prepareStatement(SQLStatment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("sql statement is " + SQLStatment);
        System.out.println("sisze is " + values.size());
        
        for (int i = 0; i < values.size(); i++) {
        	System.out.println(values.get(i));
        	System.out.println("is is " + i);
        	if  ( i == 0)
				try {
					
					preparedStatement.setString(i + 1, values.get(i));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					preparedStatement.setString(i + 1, values.get(i));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	        	try {
				preparedStatement2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	

         

        }
        try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return preparedStatement;
    }
    
    public static String buildSelectStatment(String tableName) {

        String SQLStatment = "SELECT * FROM  " + tableName + " ;";


        return SQLStatment;
    }
    // Updating Using Id
    public static String buildUpdateStatement (String tableName ,  ArrayList<String> params , ArrayList <String> values,
    		 String id_Name , String id_Value){
    	
    	String SQLStatment = "UPDATE " + tableName + " SET " ;
    	for ( int i =0  ; i < params.size() ; i++){
    		if( i != params.size() -1){
    			SQLStatment = SQLStatment + params.get(i) + " = "  +   values.get(i) + ",";
    			
    		}else {
    			 
    			SQLStatment = SQLStatment + params.get(i) + " = " +  values.get(i) + " WHERE " + id_Name + " = "
    					+ id_Value + " ;"; 
    		}
    		
    		
    	}
    	
    	System.out.println("statement is \t" + SQLStatment);
    	
    	return SQLStatment;
    }
    public static ArrayList<String>[] createUpdateTodoListParams( ){
    	
    	  @SuppressWarnings("unchecked")
		ArrayList<String>[] params = new ArrayList[2];
          for (int i = 0; i < 2; i++) {
              params[i] = new ArrayList<>();
          }

          // for params :

          params[0].add("IsDone");
        
          

          // for valus
          params[1].add("'" + 1 + "'");
         


          return params;
 
    	
    }
    public static void UpdateQuerry (String id_Name , String id_Value)
    {
    	Connection conneciton = null;
    	try {
    		conneciton.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Statement stmt;
		try {
			stmt = conneciton.createStatement();
			ArrayList<String>[] params = createUpdateTodoListParams();
	        stmt.executeUpdate(buildUpdateStatement("TodolistTable", params[0], params[1]  , id_Name, id_Value));
	        conneciton.commit();
	        // TODO : check it
	        conneciton.setAutoCommit(true);
	        stmt.close();
	        conneciton.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public static int getLastItemIdFromTableQuery(String tableName){

    	
//    	String SQLStatement = "SELECT * FROM " + tableName  + "WHERE " + "TODO_ID = ( SELECT MAX ( TODO_ID ) FROM  " +tableName  ;
    	String SQLStatement = "SELECT  MAX(" + "TODO_ID ) FROM " + tableName;
    	Connection connection = null;
    	System.out.println("connection is \t" + connection);
    	PreparedStatement preparedStatement = null;
    	int result = -1;
    	ResultSet  resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(SQLStatement);
			
			 resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			System.out.println("cannot get the last item's id due to failure !");

			e.printStackTrace();
		}
		
		try {
			while (resultSet.next()){
			ResultSetMetaData rsmd;
			try {
				rsmd = resultSet.getMetaData();
				String name = rsmd.getColumnName(1);
				result = resultSet.getInt(name);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
    	
    }
    
    public static ArrayList<String []> SelectAllQuery(String tableName) {
    	Statement  statement = null;
    	ResultSet rs = null;
    	ArrayList<String[]> rows = new ArrayList<>();
    	
    	
    	
    	try {
    		Connection connection = null;
			statement = connection.createStatement();
			rs = statement.executeQuery(DatabaseUtils.buildSelectStatment(tableName));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	 try {
			while ( rs.next() ) {
				String [] tmpArr = new String [3];
			     tmpArr[0] = "" + rs.getInt("TODO_ID");
			     tmpArr[1]= "" + rs.getString("TodoName");
			     tmpArr[2]= "" + rs.getInt("IsDone");
			     rows.add(tmpArr);

			     
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          try {
			rs.close();
			statement.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
    	if ( rows.size() == 0 )
    		return null;
    	else return rows;
    	
    	
    	
    }
    
    
    
     
    
    

}



