package com.palencia77;

import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloAppEngine", value = "/hello")
public class HelloAppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Logger logger = Logger.getAnonymousLogger();

    Properties properties = System.getProperties();

    if (properties.get("miPropiedad") == null) {
      logger.warning("La propiedad 'miPropiedad' no existe");
    }

    response.setContentType("text/plain");
    response.getWriter().println("Hello App Engine - Standard using "
        + SystemProperty.version.get() + " Java " + properties.get("java.specification.version"));
    logger.info("Saliendo del doGet");
  }

  public static String getInfo() {
    return "Version: " + System.getProperty("java.version")
          + " OS: " + System.getProperty("os.name")
          + " User: " + System.getProperty("user.name");
  }

}
