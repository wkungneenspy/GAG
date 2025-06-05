package com.monitor.client.utils;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class Log4jLoader
 * 
 * initialise la configuration de Log4j, au demarrage de l'application
 */

public class Log4JLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log4JLoader() {
        super();
    }

	/**
	 * Execute a l'initialisation de la servlet
	 */
	public void init(ServletConfig config) throws ServletException {
				
		System.out.println("Log4JLoader is initializing log4j");
		String log4jLocation = config.getInitParameter("log4jconfig");

		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File log4jFile = new File(log4jProp);
			
			if (log4jFile.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
		
		
		
	}
}
