/*
 * Created on Sep 04, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mentor.utility;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.jboss.portal.portlet.impl.jsr168.api.ActionRequestImpl;
import org.jboss.portal.portlet.impl.jsr168.api.RenderRequestImpl;

import com.mentor.resource.ConnectionToDataBase;
//import javax.servlet.http.HttpServletRequest;



/**
 * @author abhishek gautam
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResourceUtil {
	
	public static void addErrorMessageRow(int rowNumber,String id,String key,Object[] params)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
	String message = MessageFormat.format(ResourceUtil.getMessage(key),params);
	Locale locale = ctx.getViewRoot().getLocale();
	
	ResourceBundle bundle = ResourceBundle.getBundle(ctx
			.getApplication().getMessageBundle(), locale);
	
	String resource = bundle.getString(Constants.ROW_NUM);
	
	message=message + " " + resource + "-" + rowNumber;
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR ,message,message );
	
	ctx.addMessage(id,msg);
	}
    public static final PortletContext getContext()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return ((PortletContext)ctx.getExternalContext().getContext());    
    }
    
    public static String getRealPath(String path)
    {
        return getContext().getRealPath(path);
    }

	public static String getMessage(String key)
	{
	FacesContext ctx 		= FacesContext.getCurrentInstance();
	Locale locale 			= ctx.getViewRoot().getLocale();
	ResourceBundle bundle 	= ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(),locale);
	return bundle.getString(key);
	}

	public static String getMessage(String id, String messageKey) {
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			Locale locale = ctx.getViewRoot().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle(ctx
					.getApplication().getMessageBundle(), locale);
			String resource = bundle.getString(messageKey);
			if (id != null) {
				String argument = bundle.getString(id);

				MessageFormat formatter = new MessageFormat(resource);

				return formatter.format(new String[] { argument });
			}
			return resource;
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static String getMessageTwoID(String id,String id1, String messageKey) {
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			Locale locale = ctx.getViewRoot().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle(ctx
					.getApplication().getMessageBundle(), locale);
			String resource = bundle.getString(messageKey);
			if (id != null) {
				String argument = bundle.getString(id);
				String argument1 = bundle.getString(id1);
				String argument3 = bundle.getString(Constants.COMMA);
				argument1 =argument1 + argument3 +argument;
				MessageFormat formatter = new MessageFormat(resource);

				return formatter.format(new String[] { argument1 });
			}
			return resource;
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static String getMessageSec(int strLength,String id, String messageKey) {
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			Locale locale = ctx.getViewRoot().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle(ctx
					.getApplication().getMessageBundle(), locale);
			String resource = bundle.getString(messageKey);
			if (id != null) {
				String argument =bundle.getString(id);
				argument =   argument + " " + strLength;
				MessageFormat formatter = new MessageFormat(resource);

				return formatter.format(new String[] { argument });
			}
			return resource;
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static void addErrorMessage(String id, String messageKey) 
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String message = ResourceUtil.getMessage(id, messageKey);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message);
		ctx.addMessage(id, msg);
	}
	public static void addErrorMessageTwoID(String id,String id1, String messageKey) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String message = ResourceUtil.getMessageTwoID(id,id1, messageKey);
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message);
		ctx.addMessage(id1, msg);
	}
	public static void addRowErrorMessage(int rowNumber,String id, String messageKey) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		Locale locale = ctx.getViewRoot().getLocale();
		String message = ResourceUtil.getMessage(id, messageKey);
		ResourceBundle bundle = ResourceBundle.getBundle(ctx
				.getApplication().getMessageBundle(), locale);
		
		String resource = bundle.getString(Constants.ROW_NUM);
		
		message=message + " " + resource + "-" + rowNumber;
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message);
		ctx.addMessage(id, msg);
	}
	public static void addErrorMessageSec(int strLength,String id, String messageKey) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String message = ResourceUtil.getMessageSec(strLength, id, messageKey);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message);
		ctx.addMessage(id, msg);
	}
	public static void addRowErrorMessageSec(int rowNumber,int strLength,String id, String messageKey) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Locale locale = ctx.getViewRoot().getLocale();
		String message = ResourceUtil.getMessageSec(strLength, id, messageKey);
		ResourceBundle bundle = ResourceBundle.getBundle(ctx
				.getApplication().getMessageBundle(), locale);
		
		String resource = bundle.getString(Constants.ROW_NUM);
		
		message=message + " " + resource + "-" + rowNumber;
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message);
		ctx.addMessage(id, msg);
	}
	
	public static void addMessage(String id, String messageKey) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String message = ResourceUtil.getMessage(null, messageKey);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				message, message);
		
		ctx.addMessage(id, msg);
	}
	public static void addErrorMessage(String id,String key,Object[] params)
	{
	String message = MessageFormat.format(ResourceUtil.getMessage(key),params);
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR ,message,message );
	FacesContext ctx = FacesContext.getCurrentInstance();
	ctx.addMessage(id,msg);
	}
	/**
	 * 
	 * @return user
	 */
//	public static String getUser()
//	{
//		FacesContext ctx = FacesContext.getCurrentInstance();
//		//HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
//	
//	//return ((HttpServletRequest)ctx.getExternalContext().getRequest()).getRemoteUser();
//
//	}
	/**
	 * 
	 * @return current PortletSession
	 */
	public static PortletSession getSession()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		PortletRequest request = (PortletRequest) ctx.getExternalContext().getRequest();
		
    	return (PortletSession)request.getPortletSession();
	}

	public static String getMessagee(String[] id, String messageKey) {
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			Locale locale = ctx.getViewRoot().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle(ctx
					.getApplication().getMessageBundle(), locale);
			String resource = bundle.getString(messageKey);
			if (id != null) {
				String []argument =new String[id.length];
				for(int i=0; i<id.length; i++){
				argument[i] = bundle.getString(id[i]);
				}	
				MessageFormat formatter = new MessageFormat(resource);
				return formatter.format( argument);
			}		
			return resource;
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void addRowErrorMessage1(int rowNumber,String id, String messageKey) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Locale locale = ctx.getViewRoot().getLocale();
		String message = ResourceUtil.getMessage(id, messageKey);
		ResourceBundle bundle = ResourceBundle.getBundle(ctx
				.getApplication().getMessageBundle(), locale);
		
		String resource = bundle.getString(Constants.ROW_NUM);
		
		message=message + " " + resource + "-" + rowNumber;
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message);
		ctx.addMessage(id, msg);
		
	}
	
	
public  static String getUserNameAllReq(){
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
		Object request =FacesContext.getCurrentInstance() .getExternalContext().getRequest();
		RenderRequestImpl reqRRI=null;
		 ActionRequestImpl reqARI=null;
		 String userName=null;
		 String userNameNew=null;
		if(request instanceof RenderRequestImpl)
		{
			reqRRI=(RenderRequestImpl)request;
			userName=reqRRI.getRemoteUser();
			
						
		}else if(request instanceof ActionRequestImpl)
		{
			reqARI=(ActionRequestImpl)request;
			userName=reqARI.getRemoteUser();
			
		}
		else 
		{
			userName=FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
			
		}
		try {	
		String queryList="SELECT coalesce(vch_wrk_phon,'0') as logusr  from  public.loginmap where virtuallogin='"+userName+"'";
		con=ConnectionToDataBase.getConnection() ;
		
			pstmt=con.prepareStatement(queryList);
		
		rs= pstmt.executeQuery();

		if(rs.next())
		{ if(rs.getString(1)!=null && rs.getString(1).length()>0){
			userNameNew=rs.getString(1);
		}else{
			userNameNew=userName;
		} 
		} else{
			userNameNew=userName;
		}
		con.close();pstmt.close();rs.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return userNameNew;
	}
	

public static String encryptMD5(String pass) {
	
	 String passwd="";
     try {
	         MessageDigest md = MessageDigest.getInstance("MD5");
/*	         System.out.println("Message digest object info: ");
	         System.out.println("   Algorithm = "+md.getAlgorithm());
	         System.out.println("   Provider = "+md.getProvider());
	         System.out.println("   toString = "+md.toString());*/
			 
	         md.update(pass.getBytes()); 
	      	 byte[] encodedPassword = md.digest();
	        passwd = bytesToHex(encodedPassword);
	       // System.out.println("####### passwd $$$$$$$ : " + passwd);
	      } catch(NoSuchAlgorithmException nsae){
	      	//System.out.println("NoSuchAlgorithmException : "+ nsae);
	      }
	      	catch (Exception e) {
	        // System.out.println("Exception : "+e);
	      }
	
     passwd=encryptAgainMD5(passwd);
     
	return passwd;
}

public static String encryptAgainMD5(String pass) {
	
	 String passwd="";
     try {
	         MessageDigest md = MessageDigest.getInstance("MD5");
/*	         System.out.println("Message digest object info: ");
	         System.out.println("   Algorithm = "+md.getAlgorithm());
	         System.out.println("   Provider = "+md.getProvider());
	         System.out.println("   toString = "+md.toString());*/
			 
	         md.update(pass.getBytes()); 
	      	 byte[] encodedPassword = md.digest();
	        passwd = bytesToHex(encodedPassword);
	        System.out.println("####### passwd $$$$$$$ : " + passwd);
	      } catch(NoSuchAlgorithmException nsae){
	      	System.out.println("NoSuchAlgorithmException : "+ nsae);
	      }
	      	catch (Exception e) {
	         System.out.println("Exception : "+e);
	      }
	
	return passwd;
}

public static String bytesToHex(byte[] b) {
     char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
     StringBuffer buf = new StringBuffer();
     for (int j=0; j<b.length; j++) {
        buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
        buf.append(hexDigit[b[j] & 0x0f]);
     }
     return buf.toString();
  }

	
	

}
