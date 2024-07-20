package com.korobkin.test.dao.interfacesGenericDAO;

import java.util.List;

public interface GenericDAO<T, ID> {
    List<T> index();
    T show(ID id);
    void save(T entity);
    void update(ID id, T entity);
    void delete(ID id);
}