package com.ratan.controller;

/**
 * 
 * @author Sanjay.Ratan
 * 
 */
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Vector;

import com.ratan.common.*;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doPost(req, resp);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		DBHelper dbh = new DBHelper();		
		
		System.out.println("Welcome to Global Automation ("+new Date()+")!!");
		Vector vOut = dbh.getResult(request.getParameter("txtEmpName"));
		
		PrintWriter pw = response.getWriter();
		
		response.setContentType("text/html");
		
		try
		{	
			String strVal = "";
			
			pw.print("<h5>Date: "+new Date()+"</h5>");
			pw.print("<table style='border=1px solid black;color:blue;background-color:lightblue;'>");						
			pw.print("<tr style='border=1px solid black;'><th colspan='2' align='center'><b>Employee's Detail</b></th></tr>");	
			for(int nCount = 0;nCount < vOut.size();nCount++){		
				if(nCount == 0)strVal="Emp. ID";else if(nCount == 1)strVal="Emp. Name";else if(nCount == 2)strVal="DOB";else if(nCount == 3)strVal="Address";else strVal="";
				pw.print("<tr><td style='border=1px solid black;'><b>"+strVal+"&nbsp;&nbsp;</b></td><td style='border=1px solid black;'>"+vOut.get(nCount)+"</td></tr>");	
			}				
			pw.print("</table>");
			pw.print("<input type='button' value='Home Page' onclick='callMe();'/>");			
			pw.print("<script>function callMe(){history.go(-1);}</script>");
		}		
		catch(Exception e)
		{			
			System.out.println("Exception Occurred in Controller! - "+e.getMessage());
			e.printStackTrace();
		}		
	}	
}
