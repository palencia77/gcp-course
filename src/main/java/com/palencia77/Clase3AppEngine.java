package com.palencia77;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@WebServlet(name = "Clase3AppEngine", value = "/clase3")
public class Clase3AppEngine extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Logger logger = Logger.getAnonymousLogger();
        response.setContentType("text/plain");
        response.getWriter().println("Servlet clase 3 OK - App Engine - Standard using ");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        if (request.getParameter("op") == null) {
            response.getWriter().println("Falta parametro op --> add, delete, list, listMale");
            return;
        }

        switch (request.getParameter("op")) {

            case "delete": {
                // datastore.delete();
                break;
            }
            case "list": {
                response.getWriter().println("Todos los Usuarios");
                Query query = new Query("Usuario");

                PreparedQuery preparedQuery = datastore.prepare(query);
                List<Entity> usuarios = preparedQuery.asList(FetchOptions.Builder.withLimit(100));

                printUsuarios(response, usuarios);
                break;
            }
            case "listMale": {
                response.getWriter().println("Usuarios Masculinos");
                Query query = new Query("Usuario")
                        .setFilter(new Query.FilterPredicate("sexo", Query.FilterOperator.EQUAL, true))
                        .addSort("nombre", Query.SortDirection.ASCENDING);

                PreparedQuery preparedQuery = datastore.prepare(query);
                List<Entity> usuarios = preparedQuery.asList(FetchOptions.Builder.withLimit(100));

                printUsuarios(response, usuarios);
                break;
            }
            case "listAdultMale": {
                response.getWriter().println("Usuarios Masculinos Mayores de Edad");

                List<Query.Filter> filters = new LinkedList<>();
                filters.add(new Query.FilterPredicate("sexo", Query.FilterOperator.EQUAL, true));
                filters.add(new Query.FilterPredicate("edad", Query.FilterOperator.GREATER_THAN, 18));

                Query query = new Query("Usuario").setFilter(new Query.CompositeFilter(Query.CompositeFilterOperator.AND, filters));

                PreparedQuery preparedQuery = datastore.prepare(query);
                List<Entity> usuarios = preparedQuery.asList(FetchOptions.Builder.withLimit(100));

                printUsuarios(response, usuarios);
                break;
            }
            case "add": {
                if (request.getParameter("nombre") != null) {
                    Entity entity = new Entity("Usuario");
                    entity.setProperty("creacion", Calendar.getInstance().getTime());
                    entity.setProperty("nombre", request.getParameter("nombre"));
                    entity.setProperty("sexo", new Random().nextBoolean());
                    entity.setProperty("edad", new Random().nextInt(100));
                    entity.setUnindexedProperty("hash", new Random().nextInt(1000));

                    datastore.put(entity);

                    response.getWriter().println("Usuario guardado!");
                } else {
                    response.getWriter().println("Falta el nombre!");
                }
            }
        }

        logger.info("Saliendo del doGet");
    }

    private void printUsuarios(HttpServletResponse response, List<Entity> usuarios) throws IOException {
        for (Entity usuario: usuarios) {
            response.getWriter().println("USUARIO: " + usuario.getProperty("nombre") + " // KEY: " + usuario.getKey());
            response.getWriter().println("PROPIEDADES: " + usuario.getProperties());
            response.getWriter().println("");
        }
    }

    public static String getInfo() {
        return "Version: " + System.getProperty("java.version")
                + " OS: " + System.getProperty("os.name")
                + " User: " + System.getProperty("user.name");
    }

}
