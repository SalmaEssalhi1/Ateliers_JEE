package ma.fstt.atelier2.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    private static final String UTF_8 = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(UTF_8);
        }
        response.setCharacterEncoding(UTF_8);
        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).setHeader("Content-Type", "text/html; charset=" + UTF_8);
        }

        chain.doFilter(request, response);
    }
}


