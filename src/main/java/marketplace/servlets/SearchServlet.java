package marketplace.servlets;

import marketplace.search.SearchEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        SearchEngine searchEngine = (SearchEngine) getServletContext().getAttribute("searchEngine");
        String query = httpServletRequest.getParameter("query");
        String filter_ids = httpServletRequest.getParameter("filters");

        List<Integer> filters = new ArrayList<>();
        if(filter_ids != null){
            for(String ids : filter_ids.split("\\s+")){
                filters.add(Integer.parseInt(ids));
            }
        }

        String num_str = httpServletRequest.getParameter("num");
        int num = 0;
        if(num_str==null) num = Integer.MAX_VALUE;
        else num = Integer.parseInt(num_str);

        httpServletRequest.setAttribute("posts", searchEngine.execute(query, num));
        httpServletRequest.getRequestDispatcher("jsp/index.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
