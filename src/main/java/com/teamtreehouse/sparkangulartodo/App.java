package com.teamtreehouse.sparkangulartodo;


import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import com.google.gson.Gson;

import com.teamtreehouse.sparkangulartodo.dao.Sql2oTodoDao;
import com.teamtreehouse.sparkangulartodo.dao.TodoDao;
import com.teamtreehouse.sparkangulartodo.exception.ApiError;
import com.teamtreehouse.sparkangulartodo.model.Todo;
import org.sql2o.Sql2o;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        String dataSource = "jdbc:h2:~/todos.db";
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("java Api <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
        }
        Sql2o sql2o = new Sql2o(
            String.format("%s,;INIT=RUNSCRIPT from 'classpath:db/init.sql'", dataSource), "", "");
        TodoDao todoDao = new Sql2oTodoDao(sql2o);
        Gson gson = new Gson();

        // get all todos
        get("/todos", "application/json", (req, res) -> todoDao.findAll(), gson::toJson);


        // get todo by ID
        get("/todos/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = todoDao.findById(id);
            if (todo == null) {
                throw new ApiError(404, "Unable to find course with ID " + id);
            }
            return todo;
        }, gson::toJson);

        
    }

}
