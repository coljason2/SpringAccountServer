package com.Tomcat;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import lombok.extern.java.Log;

/**
 * Hello world!
 *
 */
@Log
public class App implements Runnable {
	// private final static Logger LOGGER =
	// Logger.getLogger(App.class.getName());
	private final static String mWorkingDir = System.getProperty("java.io.tmpdir");
	private static String ENCODING = "UTF-8";
	private static File warfile;
	private static Tomcat tomcat = new Tomcat();
	private static File war = new File("SpringAccount.war");
	private Context appContext;

	public static void initializeserver(int port, File f) {
		tomcat.setPort(port);
		log.info(f.getAbsolutePath() + " " + f.getName().replaceAll(".war", ""));
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
			log.info("Tomcat could not be started.");
		}
		log.info("Tomcat started on ");

		// Alternatively, you can specify a WAR file as last parameter in the
		// following call e.g. "C:\\Users\\admin\\Desktop\\app.war"
		appContext = App.getTomcat().addWebapp(App.getTomcat().getHost(),
				"/" + warfile.getName().replaceAll(".war", ""), warfile.getAbsolutePath());
		log.info("Deployed " + appContext.getBaseName() + " as " + appContext.getBaseName());
		log.info("mWorkingDir " + mWorkingDir);
		tomcat.getServer().await();
	}

	public static Tomcat getTomcat() {
		return tomcat;
	}

	public static void setTomcat(Tomcat tomcat) {
		App.tomcat = tomcat;
	}

}
