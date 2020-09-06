package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcess {

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final ExcessHolder holder;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, ExcessHolder holder) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.holder = holder;
    }

    public static class ExcessHolder{
        private final int CPD;
        private int caloriesPerDay;
        private boolean excess;

        public ExcessHolder(int CPD) {
            this.CPD = CPD;
        }

        public void setCalories(final int calories){
            caloriesPerDay += calories;
            excess = caloriesPerDay > CPD;
        }
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + holder.excess +
                '}';
    }
}
