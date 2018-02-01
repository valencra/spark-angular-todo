package com.teamtreehouse.sparkangulartodo.dao;

import com.teamtreehouse.sparkangulartodo.exception.DaoException;
import com.teamtreehouse.sparkangulartodo.model.Todo;

import java.util.List;

public interface TodoDao {
  void add(Todo todo) throws DaoException;

  List<Todo> findAll();

  Todo findById(int id);

  void update(Todo todo) throws DaoException;

  void delete(int id) throws DaoException;
}
