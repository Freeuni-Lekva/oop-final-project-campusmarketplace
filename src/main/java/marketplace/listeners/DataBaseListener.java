package marketplace.listeners;

import marketplace.config.ChatConfig;
import marketplace.constants.DatabaseConstants;
import marketplace.dao.ChatDAO;
import marketplace.dao.PostDAO;
import marketplace.dao.UserDAO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DataBaseListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/" + DatabaseConstants.DATABASE_NAME);
            dataSource.setUsername(DatabaseConstants.DATABASE_USERNAME);
            dataSource.setPassword(DatabaseConstants.DATABASE_PASSWORD);

            servletContextEvent.getServletContext().setAttribute("userDAO", new UserDAO(dataSource));
            servletContextEvent.getServletContext().setAttribute("postDAO",new PostDAO(dataSource));
            servletContextEvent.getServletContext().setAttribute("chatDAO", new ChatDAO(dataSource));

        } catch (Exception e){
            throw new RuntimeException("Database connection error");
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
