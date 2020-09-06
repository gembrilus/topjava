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

    // O(n)
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        Map<LocalDate, UserMealWithExcess.ExcessHolder> holders = new HashMap<>();

        for (UserMeal meal : meals) {
            UserMealWithExcess userMealWithExcess = remap(meal, holders, caloriesPerDay);
            if (getMealFilter(startTime, endTime).test(meal)) {
                list.add(userMealWithExcess);
            }
        }
        return list;
    }

    // О(n)
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, UserMealWithExcess.ExcessHolder> holders = new HashMap<>();

        return meals.parallelStream()
                .collect(Collectors.groupingBy(
                        getMealFilter(startTime, endTime)::test,
                        HashMap::new,
                        Collectors.mapping(meal -> remap(meal, holders, caloriesPerDay), Collectors.toList())
                )).get(true);
    }

    private static Predicate<UserMeal> getMealFilter(LocalTime startTime, LocalTime endTime) {
        return meal -> TimeUtil.isBetweenHalfOpen(meal.getLocalTime(), startTime, endTime);
    }

    private static UserMealWithExcess toMealWithExcess(UserMeal userMeal, UserMealWithExcess.ExcessHolder holder) {
        return new UserMealWithExcess(
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories(),
                holder
        );
    }

    private static UserMealWithExcess remap(UserMeal meal, Map<LocalDate, UserMealWithExcess.ExcessHolder> holders, final int caloriesPerDay){
        LocalDate date = meal.getLocalDate();
        int calories = meal.getCalories();

        UserMealWithExcess.ExcessHolder holder = new UserMealWithExcess.ExcessHolder(caloriesPerDay);
        holder.setCalories(calories);
        holders.merge(date, holder, (h1, h2) -> {
            h1.setCalories(calories);
            return h1;
        });
        return toMealWithExcess(meal,holders.get(date));
    }
}
