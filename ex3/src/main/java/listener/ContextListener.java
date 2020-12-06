package listener;

import model.Post;
import servlet.OtherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Post post = Post.builder().build();
        post.setTitle("New Title");
        ServletContext context = sce.getServletContext();
        context.setAttribute("postObject", post);

        OtherServlet otherServlet = new OtherServlet();
        ServletRegistration servlet = context.addServlet("OtherServlet", otherServlet);
        servlet.addMapping("/other");
    }
}
