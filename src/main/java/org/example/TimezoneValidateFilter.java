package org.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
import java.util.TimeZone;


@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getParameter("timezone") != null) {
            String timezone = req.getParameter("timezone");
            if (timezone.contains("UTC")) {
                final String result = timezone;
                timezone = timezone.replace("UTC", "Etc/GMT");
                if (isValid(timezone)) {
                    chain.doFilter(req, res);
                } else {
                    getResponse(res, "Invalid timezone " + result);
                }
            } else {
                getResponse(res, "Please enter correct UTC timezone");
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    private static void getResponse(HttpServletResponse res, String text) throws IOException {
        res.setStatus(400);
        res.setContentType("application/json");
        res.getWriter().write(text);
        res.getWriter().close();
    }

    private boolean isValid(String timezone) {
        return Set.of(TimeZone.getAvailableIDs()).contains(timezone);
    }
}