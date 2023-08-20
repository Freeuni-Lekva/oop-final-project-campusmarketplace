package marketplace.servlets;

import marketplace.constants.FilterConstants;
import marketplace.dao.FilterDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.search.SearchEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.setCharacterEncoding("UTF-8");
        SearchEngine searchEngine = (SearchEngine) getServletContext().getAttribute("searchEngine");
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        FilterDAO filterDAO = (FilterDAO) getServletContext().getAttribute("filterDAO");

        String query = httpServletRequest.getParameter("query");
        String filter_ids = httpServletRequest.getParameter("filters");
        System.out.println(filter_ids);

        if(query != null && query.equals("")) query = null;
        if(filter_ids != null && filter_ids.equals("")) filter_ids = null;

        if(query==null && filter_ids==null){
            httpServletResponse.sendRedirect("/feedposts");
            return;
        }

        String num_str = httpServletRequest.getParameter("num");
        int num = 0;
        if(num_str==null) num = Integer.MAX_VALUE;
        else num = Integer.parseInt(num_str);


        List<FeedPost> filter_posts = new ArrayList<>();
        if(filter_ids != null) filter_posts = filterDAO.filterPosts(Arrays.stream(filter_ids.split("\\s+")).map(i-> FilterConstants.FILTERS_MAP.get(Integer.parseInt(i))).collect(Collectors.toList()));
        Set<Integer> filter_post_ids = filter_posts.stream().map(FeedPost::getPost_id).collect(Collectors.toSet());

        if(query==null){
            httpServletRequest.getSession().setAttribute("feedPosts", filter_posts);
            httpServletRequest.getRequestDispatcher("/homepage/homepage.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }

        List<FeedPost> outputs = searchEngine.execute(query, num);
        if(filter_ids != null) {
            outputs = outputs.stream().filter(i->filter_post_ids.contains(i.getPost_id())).collect(Collectors.toList());
        }
        httpServletRequest.getSession().setAttribute("feedPosts", outputs);
        httpServletRequest.getRequestDispatcher("/homepage/homepage.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
