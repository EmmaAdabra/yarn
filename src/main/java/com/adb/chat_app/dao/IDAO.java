package com.adb.chat_app.dao;

import java.util.List;
import java.util.Optional;

// An interface for all DAO object
public interface IDAO<T> {
    Optional<T> get(long ID);
    List<T> getAll();
    int save(T t);
    int update(Object ... params);
    int delete(long id);
}
