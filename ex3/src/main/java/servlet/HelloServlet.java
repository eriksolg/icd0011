package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;
import util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/orders")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.getWriter().print("Hello!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String input = Util.readStream(request.getInputStream());
        System.out.println(input);
        Post post = new ObjectMapper().readValue(input, Post.class);
        String newJson = new ObjectMapper().writeValueAsString(post);

        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.append(newJson);

        Post postObject = (Post) getServletContext().getAttribute("postObject");
    }
}
