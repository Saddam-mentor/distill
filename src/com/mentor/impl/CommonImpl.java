package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.catalina.connector.RequestFacade;
import org.jboss.portal.portlet.impl.jsr168.api.ActionRequestImpl;
import org.jboss.portal.portlet.impl.jsr168.api.RenderRequestImpl;

import com.mentor.resource.ConnectionToDataBase;

public class CommonImpl {
	public static String serverIpAddressWithPort()
	{
		String url="";
		RenderRequestImpl requestRR=null;
		ActionRequestImpl requestAR=null;
	
		RequestFacade requestFacade=null;
		try{
			Object request1 =FacesContext.getCurrentInstance() .getExternalContext().getRequest();

			if(request1 instanceof RenderRequestImpl)
			{
				requestRR=(RenderRequestImpl)request1;

				url="//"+requestRR.getServerName()+":"+requestRR.getServerPort()+"/doc";
			}else if(request1 instanceof ActionRequestImpl)
			{
				requestAR=(ActionRequestImpl)request1;

				url="//"+requestAR.getServerName()+":"+requestAR.getServerPort()+"/doc";
			}
			else if(request1 instanceof RequestFacade)
			{
				requestFacade=(RequestFacade)request1;
				url="//"+requestFacade.getServerName()+":"+requestFacade.getServerPort()+"/doc";
			}
			else
			{
				url="/doc";
			}



		}catch(Exception ex)
		{
			System.out.println("URL Exception");
		}

		return url;
	}
	
	
	public static String getSendSmsUser()
	{
		String user="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try
		{
				String query ="SELECT D.VCH_SMS_USER FROM " +
						" COMMON_EMAIL_ACTION D  ";		

				conn = ConnectionToDataBase.getConnection();
				pstmt=conn.prepareStatement(query);
				
				rs=pstmt.executeQuery();

				while(rs.next())
				{			
					user= rs.getString(1);						
				}

		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{	
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();      		


			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return user;
	}
	
	
	public static String getSendSmsUserPassword()
	{
		String user="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try
		{
			
				String query ="SELECT D.VCH_SMS_PWD FROM " +
						" COMMON_EMAIL_ACTION D  ";		

				conn = ConnectionToDataBase.getConnection();
				pstmt=conn.prepareStatement(query);
				
				rs=pstmt.executeQuery();

				while(rs.next())
				{			
					user= rs.getString(1);						
				}

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{	
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();      		


			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return user;
	}

	
	// get email user 
	public static String getEmailUser()
	{
		String user="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try
		        {
				String query ="SELECT D.vch_email_id FROM " +
						      " COMMON_EMAIL_ACTION D  ";		

				conn = ConnectionToDataBase.getConnection();
				pstmt=conn.prepareStatement(query);
				
				rs=pstmt.executeQuery();
                while(rs.next())
				{			
					user= rs.getString(1);						
				}

		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{	
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();      		


			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return user;
	}
	
	
	public static String getEmailUserPassword()
	{
		String user="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try
		{
			
				String query ="SELECT D.vch_password FROM " +
						" COMMON_EMAIL_ACTION D  ";		

				conn = ConnectionToDataBase.getConnection();
				pstmt=conn.prepareStatement(query);
				
				rs=pstmt.executeQuery();

				while(rs.next())
				{			
					user= rs.getString(1);						
				}

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{	
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();      		


			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return user;
	}
	
	public static String getSendSmsFrom()
	{
		String user="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try
		{
			
				String query ="SELECT D.VCH_SMS_FROM FROM " +
						" COMMON_EMAIL_ACTION D  ";		

				conn = ConnectionToDataBase.getConnection();
				pstmt=conn.prepareStatement(query);
				
				rs=pstmt.executeQuery();

				while(rs.next())
				{			
					user= rs.getString(1);						
				}

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{	
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();      		


			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return user;
	}
	
	//======================================Up. Excise Getting the Year Value with the Names========================
		
		
		

		
// ============ Send Email ====================
	
	public static void sendEmail(String to,String sub,String msg, String txtfrom, String txtpassword){  
		   
		final String from= txtfrom;
		final String password= txtpassword;
	    Properties props = new Properties();    
	    props.put("mail.smtp.host", "smtp.gmail.com");    
	    props.put("mail.smtp.socketFactory.port", "465");    
	    props.put("mail.smtp.socketFactory.class",    
	              "javax.net.ssl.SSLSocketFactory");    
	    props.put("mail.smtp.auth", "true");    
	    props.put("mail.smtp.port", "465");    
	    //get Session 
	      
	    Session session = Session.getInstance(props,new javax.mail.Authenticator() {    
	    	
	     protected javax.mail.PasswordAuthentication getPasswordAuthentication()
	     {    
	    	 
	     return new javax.mail.PasswordAuthentication(from, password);  
	     
	     }    
	    });    
	    //compose message    
	     try {   
	    	 MimeMessage message = new MimeMessage(session);    
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
	    
	     message.setSubject(sub);
	     message.setContent(msg,"text/html" );
	     //send message  
	     Transport.send(message);    
	     System.out.println("message sent successfully");    
	    } catch (MessagingException e) {throw new RuntimeException(e);}    
	       
	}
	
	//======================================Up. Excise Getting the Year Value with the Names========================
	
	
	
		public static ArrayList getSeason() {
				
				ArrayList list=new ArrayList();
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs=null;
					
					SelectItem item=new SelectItem();
					item.setLabel("--select--");
					item.setValue("0");
					list.add(item);
					try
					{
					String query = "SELECT sesn_id,frm_yr,to_yr FROM mst_season order by (sesn_id ::int) ";


							   
					conn = ConnectionToDataBase.getConnection();
					pstmt=conn.prepareStatement(query);
					
				
					
					rs=pstmt.executeQuery();
					
					while(rs.next())
					{
						 item=new SelectItem();
						 
						item.setValue(rs.getString("sesn_id"));
						item.setLabel(rs.getString("frm_yr")+"-"+rs.getString("to_yr"));
						
						list.add(item);
										
					}
					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
			      		try
			      		{	
			      			
			          		if(conn!=null)conn.close();
			          		if(pstmt!=null)pstmt.close();
			          		if(rs!=null)rs.close();
			          		
			          		
			      		}
			      		catch(Exception e)
			      		{
			      			e.printStackTrace();
			      		}
			      	}
					return list;
				}
			
		//////////////////////////
	
	
	
}


