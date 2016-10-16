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
	
	final int JETTY_PORT=8086;
	Server server;
	ResourceHandler resourceHandler;
	HandlerList handlers;


	private void init() {
		server = new Server(JETTY_PORT);
		resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
		resourceHandler.setResourceBase(".");
		handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceHandler, new DefaultHandler() });
		server.setHandler(handlers);


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
