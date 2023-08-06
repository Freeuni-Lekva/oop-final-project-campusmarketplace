package marketplace.servlets;

import marketplace.search.SearchEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        SearchEngine searchEngine = (SearchEngine) getServletContext().getAttribute("searchEngine");
        String query = httpServletRequest.getParameter("query");
        httpServletRequest.setAttribute("posts", searchEngine.execute(query, 15));

    }
}