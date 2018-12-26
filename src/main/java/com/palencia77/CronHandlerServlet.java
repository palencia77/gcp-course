package com.palencia77;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

public class CronHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.getAnonymousLogger().warning("Cron ejecutado" + Calendar.getInstance().getTime().toString());
        resp.getWriter().write("Servlet ok");
    }
}
