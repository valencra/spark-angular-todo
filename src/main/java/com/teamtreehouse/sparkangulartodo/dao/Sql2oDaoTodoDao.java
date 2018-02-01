package com.teamtreehouse.sparkangulartodo.dao;

import com.teamtreehouse.sparkangulartodo.exception.DaoException;
import com.teamtreehouse.sparkangulartodo.model.Todo;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDaoTodoDao implements TodoDao {
  private final Sql2o sql2o;

  public Sql2oDaoTodoDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public void add(Todo todo) throws DaoException {
    String sql = "INSERT INTO todos(name, completed) VALUES (:name, :completed)";
    try (Connection connection = sql2o.open()) {
      int id = (int) connection.createQuery(sql)
          .bind(todo)
          .executeUpdate()
          .getKey();
      todo.setId(id);
    } catch (Sql2oException exception) {
      throw new DaoException(exception, "Encountered a problem while adding todo!");
    }
  }

  @Override
  public List<Todo> findAll() {
    try(Connection connection = sql2o.open()) {
      return connection.createQuery("SELECT * FROM todos")
          .executeAndFetch(Todo.class);
    }
  }

  @Override
  public Todo findById(int id) {
    try(Connection connection = sql2o.open()) {
      return connection.createQuery("SELECT * FROM todos WHERE id = :id")
          .addParameter("id", id)
          .executeAndFetchFirst(Todo.class);
    }
  }

  @Override
  public void update(Todo todo) throws DaoException {

  }

  @Override
  public void delete(int id) throws DaoException {

  }
}
