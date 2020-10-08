package ru.javawebinar.topjava.db;

import ru.javawebinar.topjava.model.Meal;

import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorage implements DataStorage {
    private final Map<Long, Meal> meals = new ConcurrentHashMap<>();

    private InMemoryStorage(){}

    public static InMemoryStorage newInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal get(long id) {
        return meals.get(id);
    }

    @Override
    public boolean create(Meal meal) {
        if (meals.containsValue(meal)) return false;
        return meals.put(meal.getDateTime().getLong(ChronoField.MILLI_OF_SECOND), meal) != null;
    }

    @Override
    public boolean update(long id, Meal meal) {
        if (meals.containsValue(meal)){
            meals.replace(id, meal);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (meals.containsKey(id)){
            meals.remove(id);
            return true;
        } else {
            return false;
        }
    }

    private static class InstanceHolder{
        private static final InMemoryStorage INSTANCE = new InMemoryStorage();
    }
}
