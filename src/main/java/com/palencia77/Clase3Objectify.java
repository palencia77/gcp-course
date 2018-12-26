package com.palencia77;

import com.google.appengine.api.datastore.*;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.palencia77.models.Producto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@WebServlet(name = "Clase3Objectify", value = "/clase3Objectify")
public class Clase3Objectify extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Logger logger = Logger.getAnonymousLogger();
        response.setContentType("text/plain");
        response.getWriter().println("Servlet clase 3 objectify OK - App Engine - Standard using ");


        ObjectifyService.register(Producto.class);
        Objectify ofy = ObjectifyService.ofy();

        Producto producto = new Producto("Leche", 30d, Calendar.getInstance().getTime());
        ofy.save().entity(producto).now();

        // ofy.load().kind("Usuario").id(12121).now();

        logger.info("Saliendo del doGet");
    }

    public static String getInfo() {
        return "Version: " + System.getProperty("java.version")
                + " OS: " + System.getProperty("os.name")
                + " User: " + System.getProperty("user.name");
    }

}
