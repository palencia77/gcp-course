package com.palencia77;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.utils.SystemProperty;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Logger;

@WebServlet(name = "Clase2AppEngine", value = "/clase2")
public class Clase2AppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Logger logger = Logger.getAnonymousLogger();
    response.setContentType("text/plain");
    response.getWriter().println("Servlet clase 2 OK - App Engine - Standard using ");

    // Cache en AppEngine
    MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
    String ultimoAcceso = (String) cache.get("ultimoAcceso");

    // Segmentar el espacio cache para diferentes usuarios
    NamespaceManager.set("juan");
    cache.put("ultimoAcceso", Calendar.getInstance().getTime().toString());

    NamespaceManager.set("jesus");
    cache.put("ultimoAcceso", Calendar.getInstance().getTime().toString());

    response.getWriter().write("Ultimo acceso: " + ultimoAcceso);

    // Colas
    Queue cola = QueueFactory.getDefaultQueue();
    for (int i=0; i<50; i++) {
      cola.add(TaskOptions.Builder.withPayload(new DeferredTask() {
        @Override
        public void run() {
          Logger.getAnonymousLogger().severe("trabajo async!!! " + Calendar.getInstance().getTime().toString());
        }
      }));
    }


    logger.info("Saliendo del doGet");
  }

  public static String getInfo() {
    return "Version: " + System.getProperty("java.version")
          + " OS: " + System.getProperty("os.name")
          + " User: " + System.getProperty("user.name");
  }

}
