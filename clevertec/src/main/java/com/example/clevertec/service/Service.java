package com.example.clevertec.service;

import java.util.List;

public interface Service <T>{

    List<T> findAll();

    T findById(Long id);

    T create(T t);

    void deleteById(Long id);

    T update (Long id, T t);
}
