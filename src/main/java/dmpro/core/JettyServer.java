/**
 * 
 */
package dmpro.core;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import dmpro.api.RestAPI;
//import dmpro.api.RestApp;


/**
 * @author Joshua Landman, <joshua.s.landman@gmail.com>
 * created on Oct 16, 2016
 */


/**
 * Simple Jetty FileServer.
 * This is a simple example of Jetty configured as a FileServer.
 */
public class JettyServer implements Runnable {
	private final Logger log = Logger.getLogger(this.getClass().getName());

	dmpro.core.Server application;
	final int JETTY_PORT=8086;
	Server server;
	WebAppContext webapp;
	ResourceHandler resourceHandler;
	HandlerList handlers;
	String webappDir = "src/main/webapp";


	public JettyServer(dmpro.core.Server application) {
		this.application = application;
	}

	private void init() {
		/* jetty web server /app */
		server = new Server(JETTY_PORT);
		//System.setProperty("org.eclipse.jetty.LEVEL","INFO");
		
		//org.apache.log4j.LogManager.getLogger("org.eclipse.jetty").setLevel(org.apache.log4j.Level.WARN);
		//java.util.logging.LoggerFactory.getLogger("org.eclipse.jetty").setLevel(Level.WARNING);
		final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("org.eclipse.jetty");
		if (!(logger instanceof ch.qos.logback.classic.Logger)) {
		    return;
		}
		ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) logger;
		logbackLogger.setLevel(ch.qos.logback.classic.Level.WARN);
		
		
		webapp = new WebAppContext();
		webapp.setContextPath("/app");
		webapp.setDescriptor(webappDir + "/WEB-INF/web.xml");
		webapp.setResourceBase(webappDir);

		/* simple jersey config /api */
		ServletContextHandler restapp = new ServletContextHandler(ServletContextHandler.SESSIONS);
		restapp.setContextPath("/api");
		ServletHolder jerseyServlet = restapp.addServlet(
				org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);
		// Tells the Jersey Servlet which REST service/class to load.
		jerseyServlet.setInitParameter(
				"jersey.config.server.provider.classnames",
				RestAPI.class.getCanonicalName());
		restapp.setAttribute("dmpro",  application);

		/* General Web Serving  / (for dev) */
		resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
		resourceHandler.setResourceBase(".");
		handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { webapp, restapp, resourceHandler, new DefaultHandler() });
		server.setHandler(handlers);
	}


	public void stop() throws Exception {
		this.server.stop();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.init();
		try {
			this.server.start();
			this.server.join();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Jetty start failed", e);
			e.printStackTrace();
		}
	}

}
