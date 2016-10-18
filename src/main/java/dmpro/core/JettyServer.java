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
import org.eclipse.jetty.webapp.WebAppContext;


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
		server = new Server(JETTY_PORT);
		webapp = new WebAppContext();
        webapp.setContextPath("/app");
        webapp.setDescriptor(webappDir + "/WEB-INF/web.xml");
        webapp.setResourceBase(webappDir);

		resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
		resourceHandler.setResourceBase(".");
		handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { webapp, resourceHandler, new DefaultHandler() });
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
