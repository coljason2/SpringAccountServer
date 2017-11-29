package com.Tomcat;

import java.io.File;
import java.util.logging.Logger;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Hello world!
 *
 */
public class App implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(App.class.getName());
	private final static String mWorkingDir = System.getProperty("java.io.tmpdir");
	private static String ENCODING = "UTF-8";
	private static File warfile;
	private static Tomcat tomcat = new Tomcat();

	public static void initializeserver(int port, File f) {
		tomcat.setPort(port);
		warfile = f;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		tomcat.setBaseDir(mWorkingDir);
		tomcat.getHost().setAppBase(mWorkingDir);
		tomcat.getHost().setAutoDeploy(true);
		tomcat.getHost().setDeployOnStartup(true);
		tomcat.getConnector().setURIEncoding(ENCODING);
		try {
			tomcat.start();
		} catch (LifecycleException e) {
			LOGGER.severe("Tomcat could not be started.");
			e.printStackTrace();
		}
		LOGGER.info("Tomcat started on ");

		// Alternatively, you can specify a WAR file as last parameter in the
		// following call e.g. "C:\\Users\\admin\\Desktop\\app.war"
		Context appContext = App.getTomcat().addWebapp(App.getTomcat().getHost(), "/SpringAccount",
				warfile.getAbsolutePath());
		LOGGER.info("Deployed " + appContext.getBaseName() + " as " + appContext.getBaseName());
		LOGGER.info("mWorkingDir " + mWorkingDir);
		tomcat.getServer().await();
	}

	public static Tomcat getTomcat() {
		return tomcat;
	}

	public static void setTomcat(Tomcat tomcat) {
		App.tomcat = tomcat;
	}

}
