package com.adb.chat_app.dao;

import com.adb.chat_app.exceptions.DAOException;

import java.util.List;
import java.util.Optional;

// An interface for all DAO object
public interface IDAO<T> {
    Optional<T> get(long ID) throws DAOException;
    List<T> getAll() throws DAOException;
    int save(T t) throws DAOException;
    int update(Object ... params) throws DAOException;
    int delete(long id) throws DAOException;
}
