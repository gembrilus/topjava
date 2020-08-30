package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    // O(2*n)
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapOfCaloriesByDays = new HashMap<>();
        for (UserMeal userMeal : meals) {
            mapOfCaloriesByDays.merge(userMeal.getLocalDate(), userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> list = new ArrayList<>();
        Predicate<UserMeal> predicate = getMealFilter(startTime, endTime);
        for (UserMeal meal : meals) {
            if (predicate.test(meal)) {
                UserMealWithExcess userMealWithExcess = toMealWithExcess(meal, mapOfCaloriesByDays.get(meal.getLocalDate()) > caloriesPerDay);
                list.add(userMealWithExcess);
            }
        }
        return list;
    }

    // О(2*n)
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapOfCaloriesByDays = meals.stream() //(or parallelStream() for very big list)
                .collect(Collectors.groupingBy(
                        UserMeal::getLocalDate,
                        Collectors.summingInt(UserMeal::getCalories)
                        )
                );

        return meals.stream() //(or parallelStream() for very big list)
                .filter(getMealFilter(startTime, endTime))
                .map(meal -> toMealWithExcess(meal, mapOfCaloriesByDays.get(meal.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static Predicate<UserMeal> getMealFilter(LocalTime startTime, LocalTime endTime) {
        return meal -> TimeUtil.isBetweenHalfOpen(meal.getLocalTime(), startTime, endTime);
    }

    private static UserMealWithExcess toMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories(),
                excess
        );
    }
}
