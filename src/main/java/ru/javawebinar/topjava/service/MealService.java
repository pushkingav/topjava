package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

public interface MealService {
    List<MealWithExceed> getAll(int userId, int caloriesPerDay);

    List<MealWithExceed> getAllFiltered(String type);

    Meal get(int userId, int mealId);

    boolean delete(int userId, int mealId);

    void update(Meal meal);
}