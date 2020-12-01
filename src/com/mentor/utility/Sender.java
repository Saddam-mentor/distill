package com.mentor.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Sender {

String message;

String type;String sendername;String mobile;

String dlr;

String server;
int port;
String destination;String username;String password;String source;

 public Sender(String server,String username, String password,String mobile,String sendername,
String message) {
this.username = username;
this.password = password;
this.message = message;
this.mobile = mobile;
this.sendername = sendername;

}
public String submitMessage() {
	String response=null;
	
	try {
	
// Url that will be called to submit the message
URL sendUrl = new URL("http://mentorsms.in/sms_api/sendsms.php");
HttpURLConnection httpConnection = (HttpURLConnection) sendUrl.openConnection();
// This method sets the method type to POST so that // will be send as a POST request 
httpConnection.setRequestMethod( "POST");
// This method is set as true wince we intend to send // input to the server 
httpConnection.setDoInput(true);
// This method implies that we intend to receive data from server. 
httpConnection.setDoOutput(true);
// Implies do not use cached data
httpConnection.setUseCaches(false);
// Data that will be sent over the stream to the server. 
DataOutputStream dataStreamToServer = new DataOutputStream( httpConnection.getOutputStream());
dataStreamToServer.writeBytes("username=" + URLEncoder.encode(this.username, "UTF-8") + "&password=" + 

URLEncoder.encode(this.password, "UTF-8") + "&mobile=" + URLEncoder.encode(this.mobile, "UTF-8") + 

"&sendername=" + URLEncoder.encode(this.sendername, "UTF-8") + "&message="+ URLEncoder.encode

(this.message, "UTF-8"));
dataStreamToServer.flush();
dataStreamToServer.close();
// Here take the output value of the server.
BufferedReader dataStreamFromUrl = new BufferedReader(
new InputStreamReader(httpConnection.getInputStream()));
String dataFromUrl ="", dataBuffer="" ;
// Writing information from the stream to the buffer

while((dataBuffer = dataStreamFromUrl.readLine())!=null) {
dataFromUrl += dataBuffer;
}

dataStreamFromUrl.close();
response="success";

System.out.println("Response:" + dataFromUrl);
} catch (Exception ex) {
ex.printStackTrace();
return "failed";
}
return response;
}




private static StringBuffer convertToUnicode(String regText) {
char[] chars = regText.toCharArray();
StringBuffer hexString = new StringBuffer();
for(int i=0;i<chars.length;i++) {
String iniHexString = Integer.toHexString((int) chars[i]); 
if (iniHexString.length() == 1){
iniHexString = "000" + iniHexString;}
else if(iniHexString.length() == 2)
{iniHexString = "00" + iniHexString;}
else if(iniHexString.length() == 3)
{iniHexString = "0" + iniHexString; hexString.append(iniHexString);}
}
System.out.println(hexString);
return hexString;
}




/*public static void main(String[] args) {
try {

// Configure Setting

Sender s = new Sender("String url", "username", 

"password","mobile","senderid", "message");
	//Sender s = new Sender("http://mentorsms.in/sms_api/sendsms.php", "excise", 

	//"Excise@123","9415027176","EXCISE", "Hi atul how r u ur job is done");
s.submitMessage();


} catch (Exception ex) {}
}*/
}


