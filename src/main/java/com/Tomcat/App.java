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
	private Context appContext;
	private File f;
	private static Tomcat tomcat = new Tomcat();

	public static void initializeserver(int port, File f) {
		tomcat.setPort(port);
		f = f;
	}

	public void run() {
		tomcat.setBaseDir(mWorkingDir);
		tomcat.getHost().setAppBase(mWorkingDir);
		tomcat.getHost().setAutoDeploy(true);
		tomcat.getHost().setDeployOnStartup(true);

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			LOGGER.severe("Tomcat could not be started.");
			e.printStackTrace();
		}
		LOGGER.info("Tomcat started on " + tomcat.getHost());
	
		// Alternatively, you can specify a WAR file as last parameter in the
		// following call e.g. "C:\\Users\\admin\\Desktop\\app.war"
		appContext = App.getTomcat().addWebapp(App.getTomcat().getHost(), "/SpringAccount", f.getAbsolutePath());
		LOGGER.info("Deployed " + appContext.getBaseName() + " as " + appContext.getBaseName());
		LOGGER.info("mWorkingDir " + mWorkingDir);
		tomcat.getServer().await();
	}

	public Context getAppContext() {
		return appContext;
	}

	public void setAppContext(Context appContext) {
		this.appContext = appContext;
	}

	public static Tomcat getTomcat() {
		return tomcat;
	}

	public static void setTomcat(Tomcat tomcat) {
		App.tomcat = tomcat;
	}

}