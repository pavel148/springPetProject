package com.korobkin.test.dao.interfacesGenericDAO;

import java.util.Optional;

public interface NameExistenceCheckerDAO <T> {
    Optional<T> getByField(String fieldName);
}
