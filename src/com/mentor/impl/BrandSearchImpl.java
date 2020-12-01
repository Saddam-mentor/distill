package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mentor.datatable.BrandSearchDataTable;
import com.mentor.action.BrandSearchAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.ResourceUtil;

public class BrandSearchImpl 
{
	
	public ArrayList getData(BrandSearchAction action)
	{
		System.out.println(action.getRadio());
		System.out.println(action.getEnterData());
		
		int len=action.getEnterData().length();
		String s=action.getEnterData().substring(0,len);
		System.out.println(len);
		System.out.println(s);
		
		int i=1;
		ArrayList list=new ArrayList();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		String queryList="";
		try{
			if(action.getRadio().equalsIgnoreCase("BEER"))
			{
			 queryList= "   SELECT distinct a.brand_name, c.box_size,b.package_name, b.code_generate_through "+
					  " FROM distillery.brand_registration a ,  "+
					  "	distillery.packaging_details b,distillery.box_size_details c "+
					  " where a.brand_id=b.brand_id_fk and a.brand_name like '%"+action.getEnterData()+"%' and "+
					  "    a.license_category in ('BEER','IMPORTEDBEER')  "+
					  " and b.box_id=c.box_id  order by a.brand_name,b.package_name ";
			}
			else if(action.getRadio().equalsIgnoreCase("IMFL"))
			{
			 queryList= "   SELECT distinct a.brand_name, c.box_size,b.package_name, b.code_generate_through "+
					  " FROM distillery.brand_registration a ,  "+
					  "	distillery.packaging_details b,distillery.box_size_details c "+
					  " where a.brand_id=b.brand_id_fk and a.license_category in ('IMFL','IMPORTEDFL')  " +
					  " and a.brand_name like '%"+action.getEnterData()+"%'  "+
					  " and b.box_id=c.box_id  order by a.brand_name,b.package_name ";
			}
			else {
			
			 queryList= "   SELECT distinct a.brand_name, c.box_size,b.package_name, b.code_generate_through "+
							  " FROM distillery.brand_registration a ,  "+
							  "	distillery.packaging_details b,distillery.box_size_details c "+
							  " where a.brand_id=b.brand_id_fk and  a.brand_name like '%"+action.getEnterData()+"%' and  " +
							  " a.license_category='"+action.getRadio()+"'  "+
							  " and b.box_id=c.box_id  order by a.brand_name,b.package_name ";
			}
			System.out.println(queryList);
								 
			con=ConnectionToDataBase.getConnection() ;
			pstmt=con.prepareStatement(queryList);

			rs= pstmt.executeQuery();
			System.out.println(queryList);
			while(rs.next())
			{ 
				
			
					BrandSearchDataTable dt=new BrandSearchDataTable();
					
					dt.setSno(i);
					dt.setBrandName(rs.getString("brand_name"));
					dt.setBox_size(rs.getInt("box_size"));
					dt.setPackage_name(rs.getString("package_name"));
					dt.setCode_generate_through(rs.getString("code_generate_through"));
					
					list.add(dt);
					i++;
			
				
				
			}
		}
		catch(Exception se)
		{
			se.printStackTrace();
		}
		finally{
			try{
			if(pstmt!=null)pstmt.close();
			if(con!=null) con.close();	

		}catch(SQLException se){
			se.printStackTrace();
		}
		}
		
return list;
	
	}
		
	/*public ArrayList getData(BrandSearchAction action)
	{
		//System.out.println(action.getRadio());
		//System.out.println(action.getEnterData());
		System.out.println("hello machine");
		String s=action.getEnterData().trim();
		int len=s.length();
	//	String s=action.getEnterData().substring(0,len);
		System.out.println(len);
		System.out.println(s);
		
		int i=1;
		ArrayList list=new ArrayList();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		String queryList="";
		try{
			if(action.getRadio().equalsIgnoreCase("BEER"))
			{
			 queryList= "   SELECT distinct a.brand_name, c.box_size,b.package_name, b.code_generate_through "+
					  " FROM distillery.brand_registration a ,  "+
					  "	distillery.packaging_details b,distillery.box_size_details c "+
					  " where a.brand_id=b.brand_id_fk and "+
					  "    a.license_category in ('BEER','IMPORTEDBEER')  "+
					  " and b.box_id=c.box_id  order by a.brand_name,b.package_name ";
			}
			else if(action.getRadio().equalsIgnoreCase("IMFL"))
			{
			 queryList= "   SELECT distinct a.brand_name, c.box_size,b.package_name, b.code_generate_through "+
					  " FROM distillery.brand_registration a ,  "+
					  "	distillery.packaging_details b,distillery.box_size_details c "+
					  " where a.brand_id=b.brand_id_fk and a.license_category in ('IMFL','IMPORTEDFL')  " +
					  " "+
					  " and b.box_id=c.box_id  order by a.brand_name,b.package_name ";
			}
			else {
			
			 queryList= "   SELECT distinct a.brand_name, c.box_size,b.package_name, b.code_generate_through "+
							  " FROM distillery.brand_registration a ,  "+
							  "	distillery.packaging_details b,distillery.box_size_details c "+
							  " where a.brand_id=b.brand_id_fk and   " +
							  " a.license_category='"+action.getRadio()+"'  "+
							  " and b.box_id=c.box_id  order by a.brand_name,b.package_name ";
			}
								 
			con=ConnectionToDataBase.getConnection() ;
			pstmt=con.prepareStatement(queryList);

			rs= pstmt.executeQuery();
			System.out.println(queryList);
			while(rs.next())
			{ 
				
				//String brand=rs.getString("brand_name").substring(0,len);
				String brand=rs.getString("brand_name");
				
				for(int ii=0;ii<brand.length();ii++)
				{
					String w="";
					for(int j=ii;j<len;j++)
					{
						char c=brand.charAt(j);
						w=w+c;
					}
					
				
					if(w.equalsIgnoreCase(s))
					{
						
						System.out.println("original word"+brand);
						
					}
					else 
					{
						System.out.println("else wala chala"+w);
						w="";
					
					}
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				if(brand.trim().equalsIgnoreCase(s.trim()))
				{
					BrandSearchDataTable dt=new BrandSearchDataTable();
					
					dt.setSno(i);
					dt.setBrandName(rs.getString("brand_name"));
					dt.setBox_size(rs.getInt("box_size"));
					dt.setPackage_name(rs.getString("package_name"));
					dt.setCode_generate_through(rs.getString("code_generate_through"));
					
					list.add(dt);
					i++;
				}
				
				
			}
		}
		catch(Exception se)
		{
			se.printStackTrace();
		}
		finally{
			try{
			if(pstmt!=null)pstmt.close();
			if(con!=null) con.close();	

		}catch(SQLException se){
			se.printStackTrace();
		}
		}
		
return list;
	
	}*/
		
		
		
	}


