package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        String format = f.format(new Date());
        if (req.getParameter("timezone") != null) {
            int timezone = Integer.parseInt(req.getParameter("timezone").replace("UTC", "").trim());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, timezone);
            format = f.format(calendar.getTime());
            String replace = req.getParameter("timezone");
            format = format.replace("UTC", replace);
        }
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write(format);
        resp.getWriter().close();
    }
}