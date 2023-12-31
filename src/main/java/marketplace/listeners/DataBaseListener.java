package marketplace.listeners;

import marketplace.config.ChatConfig;
import marketplace.constants.DatabaseConstants;

import marketplace.search.SearchEngine;
import marketplace.dao.*;
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

            PostDAO postDAO = new PostDAO(dataSource);
            servletContextEvent.getServletContext().setAttribute("userDAO", new UserDAO(dataSource));

            servletContextEvent.getServletContext().setAttribute("postDAO", postDAO);
            servletContextEvent.getServletContext().setAttribute("searchEngine",new SearchEngine(postDAO));
            servletContextEvent.getServletContext().setAttribute("chatDAO", new ChatDAO(dataSource,(UserDAO)servletContextEvent.getServletContext().getAttribute("userDAO")));
            servletContextEvent.getServletContext().setAttribute("filterDAO",new FilterDAO(dataSource));
            servletContextEvent.getServletContext().setAttribute("photoDAO",new PhotoDAO(dataSource));
            servletContextEvent.getServletContext().setAttribute("favouritesDAO", new FavouritesDAO(dataSource));
            ChatConfig.setServletContext(servletContextEvent.getServletContext());

        } catch (Exception e){
            throw new RuntimeException("Database connection error");
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
