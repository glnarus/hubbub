package edu.acc.j2ee.hubbub5;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String dbProtocol = sc.getInitParameter("db.protocol");
        String dbHost = sc.getInitParameter("db.host");
        String dbPort = sc.getInitParameter("db.port");
        String dbName = sc.getInitParameter("db.name");
        String dbUser = sc.getInitParameter("db.user");
        String dbPass = sc.getInitParameter("db.pass");
        String jdbcUrl = String.format(
            "%s://%s:%s/%s;user=%s;password=%s",
            dbProtocol, dbHost, dbPort, dbName, dbUser, dbPass
        );
        HubbubDAO db = new HubbubDAO(jdbcUrl);
        sce.getServletContext().setAttribute("db", db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HubbubDAO db = (HubbubDAO)sce.getServletContext().getAttribute("db");
        db.close();
    }
}
