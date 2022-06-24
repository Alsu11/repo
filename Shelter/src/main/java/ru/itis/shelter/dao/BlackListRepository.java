package ru.itis.shelter.dao;

public interface BlackListRepository {
    void save(String token);

    boolean exists(String token);
}

