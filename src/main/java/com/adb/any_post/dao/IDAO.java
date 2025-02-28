package com.adb.any_post.dao;

import com.adb.any_post.exceptions.DAOException;

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
