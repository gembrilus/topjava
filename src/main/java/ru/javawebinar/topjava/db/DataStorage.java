package ru.javawebinar.topjava.db;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface DataStorage {
    List<Meal> getAll();
    Meal get(long id);
    boolean create(Meal meal);
    boolean update(long id, Meal meal);
    boolean delete(long id);
}
