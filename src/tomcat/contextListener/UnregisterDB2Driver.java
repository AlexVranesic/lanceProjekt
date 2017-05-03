package tomcat.contextListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UnregisterDB2Driver implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		Log logger = LogFactory.getLog("UnregisterDB2Driver");
	    // This fixes the JDBC driver not unloading corectly on a context reload  for DB2 JDBC 4.22.29
	    try {
	        logger.debug("Trying to stop the timer");
	        new com.ibm.db2.jcc.am.iq() {
	            {
	                if (a != null) {
	                    a.cancel();
	                } else {
	                    logger.debug("Timer is null, skipped");
	                }
	            }
	        };
	        logger.debug("Stopped the timer");
	    } catch (Exception e) {
	        logger.error("Could not stop the DB2 timer thread", e);
	    }
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}
}
