package com.mentor.resource;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;


public class ConnectionToDataBase {
	public static Connection getConnection19_20()
	{
		Connection con = null;
		InitialContext ctx = null;
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:bottlingdb_19_20");
			con = ds.getConnection();	

		}catch(NamingException ne)
		{
			System.out.println("Exception 1"+ne.getMessage());

		}catch (SQLException se){
			System.out.println("Exception 2"+se.getMessage());
		}
		catch (Exception se){
			System.out.println("Exception 3"+se.getMessage());
		}
		return con;		
	}
	public static Connection getConnection3()
	{
		Connection con = null;
		InitialContext ctx = null;
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:bottlingdb");
			con = ds.getConnection();	

		}catch(NamingException ne)
		{
			System.out.println("Exception 1"+ne.getMessage());

		}catch (SQLException se){
			System.out.println("Exception 2"+se.getMessage());
		}
		catch (Exception se){
			System.out.println("Exception 3"+se.getMessage());
		}
		return con;		
	}
	public static Connection getConnection()
	{
		Connection con = null;
		InitialContext ctx = null;
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:upexcisedb");
			con = ds.getConnection();	

		}catch(NamingException ne)
		{
			System.out.println("Exception 1"+ne.getMessage());

		}catch (SQLException se){
			System.out.println("Exception 2"+se.getMessage());
		}
		catch (Exception se){
			System.out.println("Exception 3"+se.getMessage());
		}
		return con;		
	}
	public static Connection getConnection_20_21()
	{
		Connection con = null;
		InitialContext ctx = null;
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:bottlingdb_20_21");
			con = ds.getConnection();	

		}catch(NamingException ne)
		{
			System.out.println("Exception 1"+ne.getMessage());

		}catch (SQLException se){
			System.out.println("Exception 2"+se.getMessage());
		}
		catch (Exception se){
			System.out.println("Exception 3"+se.getMessage());
		}
		return con;		
	}
	public static Connection getConnection2()
	{
		Connection con = null;
		InitialContext ctx = null;
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:PortalDS");
			con = ds.getConnection();	
		}catch(NamingException ne)
		{
			System.out.println("Exception 1"+ne.getMessage());
		}catch (SQLException se){
			System.out.println("Exception 2"+se.getMessage());						
		}catch (Exception se){
			System.out.println("Exception 3"+se.getMessage());			
		}
		return con;		
	}
	 
	
}
