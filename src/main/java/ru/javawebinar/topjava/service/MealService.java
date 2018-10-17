package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

public interface MealService {
    List<MealWithExceed> getAll(int userId, int caloriesPerDay);

    List<MealWithExceed> getAllFiltered(String type);

    MealWithExceed get(int userId, int mealId, int caloriesPerDay);

    boolean delete(int userId, int mealId);

    void update(Meal meal, int id);
}