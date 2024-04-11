package bank;
import dbC.dbConnection;
import java.util.regex.*;
import java.io.*; 
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class TCPServer {
	
    String clientSentence; 
    String retrunSentence; 
    String situation="Other";
    String id="";
    String pass="";
    int flag=0;
    int money=0;
    

    Pattern HELOPattern= Pattern.compile("^(HELO )([0-9]+)$");
    Pattern PASSPattern= Pattern.compile("^(PASS )([a-zA-Z0-9_]+)$");
    Pattern WDRAPattern= Pattern.compile("^(WDRA )([0-9]+)$");
     
    DataOutputStream  outToClient;
    ServerSocket welcomeSocket;
    Socket connectionSocket;
    BufferedReader inFromClient;
	
	public TCPServer(){
		try {
			ServerSocket welcomeSocket = new ServerSocket(2525);
        // 监视器,while(true)使之一直监视
        while (true) {
        	if(flag==1) {
            Monitor();
        	}
        	if(flag==0) {
        		connectionSocket = welcomeSocket.accept();
        		flag=1;
        	}

        }
    } catch (Exception e) { // 输出错误信息
        e.printStackTrace();
    }
	}
	 void Checkmes(String sentence) {
		Matcher matcher=HELOPattern.matcher(sentence);
		if(matcher.find()) {//HELO
			situation="HELO ";
			id=(matcher.group()).replace(situation, "");
			return;
		}
		matcher=PASSPattern.matcher(sentence);
		if(matcher.find()) {//PASS
			situation="PASS ";
			pass=(matcher.group()).replace(situation, "");
			return;
		}
		if(sentence.equals("BALA")) {
			situation="BALA";
			return;
		}
		matcher=WDRAPattern.matcher(sentence);
		if(matcher.find()) {
			situation="WDRA ";
			money=Integer.valueOf((matcher.group()).replace(situation, ""));
			return;
		}
		if(sentence.equals("BYE")) {
			situation="BYE";
			return;
		}
		situation="Other";
		return;
	}
	
	  boolean checkid(String username) {
		Connection con;
		Statement sql;
		ResultSet rs;

		con=dbConnection.dbC();
		
		String sqlStr=
				"select * from bankuser where username="+"'"+username+"'";
		try {
			sql=con.createStatement();
			rs=sql.executeQuery(sqlStr);
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	
	}
	
	  boolean login(String username, String password){
		Connection con;
		Statement sql;
		ResultSet rs;

		con=dbConnection.dbC();
		
		String sqlStr=
				"select * from bankuser where username='"+username+"' and password='"+password+"'";
		try {
			sql=con.createStatement();
			rs=sql.executeQuery(sqlStr);
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	 int Bala(String username)  {
		int mo=-1;
		Connection con;
		Statement sql;
		ResultSet rs;

		con=dbConnection.dbC();
		
		String sqlStr=
				"select bala from bankuser where username="+username;
		try {
			sql=con.createStatement();
			rs=sql.executeQuery(sqlStr);
			if(rs.next()) {
				mo=rs.getInt(1);
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return mo;
	}
	
	void withdraw(String username,int wm) {
		Connection con;
		Statement sql;
		int rs;

		con=dbConnection.dbC();
		
		String sqlStr=
				"update bankuser SET bala=bala-"+wm+" where username="+username;
		try {
			sql=con.createStatement();
			rs=sql.executeUpdate(sqlStr);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return;
	}
	
	public void Monitor(){
		try {
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            clientSentence = inFromClient.readLine();
			try{
            			Checkmes(clientSentence);
			}
			catch(Exception e){
				situation = "BYE";
				e.printStackTrace();
			}
            switch(situation){
                case"HELO ":
                	outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
                	System.out.println(id+"Want to login");
                	if(checkid(id)) {
                		System.out.println(id+"存在");
                		retrunSentence="500 AUTH REQUIRE";
                	}
                	else {
                		System.out.println(id+"不存在");
                		id="";
                		retrunSentence="401 ERROR!";
                	}
                	outToClient.writeBytes(retrunSentence+'\n');
                    break;
                case"PASS ":
                	outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
                	if(id==null||id=="") {
                		retrunSentence="401 ERROR!";
                		outToClient.writeBytes(retrunSentence+'\n');
                		break;
                	}
                	System.out.println(id+"Try to login");
                	if(login(id,pass)) {
                		System.out.println(id+"login successful");
                		retrunSentence="525 OK!";
                	}
                	else {
                		System.out.println(id+"login failed");
                		retrunSentence="401 ERROR!";
                		pass="";
                	}
                	outToClient.writeBytes(retrunSentence+'\n');
                    break;
                case"BALA":
                	outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
                	if(id==null||id==""||pass==null||pass=="") {
                		retrunSentence="401 ERROR!";
                		outToClient.writeBytes(retrunSentence+'\n');
                		break;
                	}
                	retrunSentence="AMMT:<"+Bala(id)+">";
                	System.out.println(id+"check the bala");
                	outToClient.writeBytes(retrunSentence+'\n');
                    break;
                case"WDRA ":
                	outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
                	if(id==null||id==""||pass==null||pass=="") {
                		retrunSentence="401 ERROR!";
                		outToClient.writeBytes(retrunSentence+'\n');
                		break;
                	}
                	if(Bala(id)<money) {
                		System.out.println(id+"do not have enough money");
                		retrunSentence="401 ERROR!";
                		money=0;
                	}
                	else {
                		withdraw(id, money);
                		System.out.println(id+"withdraw"+money+" $");
                		retrunSentence="525 OK!";
                		money=0;
                	}
                	outToClient.writeBytes(retrunSentence+'\n');
                    break;
                case"BYE":
                	outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
                	retrunSentence="BYE";
                	outToClient.writeBytes(retrunSentence+'\n');
                    connectionSocket.close();
                    flag=0;
                    break;
                default:
                	outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
                    System.out.println(clientSentence);
                    retrunSentence="401 ERROR!";
                    outToClient.writeBytes(retrunSentence+'\n');
                    break;
            }
		}
            catch (Exception e) {
            	e.printStackTrace();
			}
	}
}

