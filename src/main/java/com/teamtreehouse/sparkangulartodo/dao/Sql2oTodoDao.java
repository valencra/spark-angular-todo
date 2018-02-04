package com.teamtreehouse.sparkangulartodo.dao;

import com.teamtreehouse.sparkangulartodo.exception.DaoException;
import com.teamtreehouse.sparkangulartodo.model.Todo;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTodoDao implements TodoDao {
  private final Sql2o sql2o;

  public Sql2oTodoDao(Sql2o sql2o) {
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
  public List<Todo> findAll() throws DaoException {
    String queryText = "SELECT * FROM todos";
    try(Connection connection = sql2o.open()) {
      return connection.createQuery(queryText)
          .executeAndFetch(Todo.class);
    } catch (Sql2oException exception) {
      throw new DaoException(exception, "Encountered a problem while getting todos!");
    }
  }

  @Override
  public Todo findById(int id) throws DaoException {
    String queryText = "SELECT * FROM todos WHERE id = :id";
    try(Connection connection = sql2o.open()) {
      return connection.createQuery(queryText)
          .addParameter("id", id)
          .executeAndFetchFirst(Todo.class);
    } catch (Sql2oException exception) {
      throw new DaoException(exception, "Encountered a problem while getting todo by ID!");
    }
  }

  @Override
  public void update(Todo todo) throws DaoException {
    String sql = "UPDATE todos SET name = :name, completed = :completed WHERE id = :id";
    try (Connection connection = sql2o.open()) {
      connection.createQuery(sql)
          .addParameter("name", todo.getName())
          .addParameter("completed", todo.isCompleted())
          .addParameter("id", todo.getId())
          .executeUpdate();
    } catch (Sql2oException exception) {
      throw new DaoException(exception, "Encountered a problem while updating todo!");
    }
  }

  @Override
  public void delete(int id) throws DaoException {
    String sql = "DELETE FROM todos WHERE id = :id";
    try (Connection connection = sql2o.open()) {
      connection.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    } catch (Sql2oException exception) {
      throw new DaoException(exception, "Encountered a problem while deleting todo!");
    }
  }
}
