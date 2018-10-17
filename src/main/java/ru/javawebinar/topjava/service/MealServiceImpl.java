package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public List<MealWithExceed> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(repository.getAll(userId), caloriesPerDay);
    }

    @Override
    public List<MealWithExceed> getAllFiltered(String type) {
        return null;
    }

    @Override
    public Meal get (int userId, int mealId) {
        return repository.get(userId, mealId);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        repository.delete(userId, mealId);
        return true;
    }

    @Override
    public void update(Meal meal) {
        repository.save(meal);
    }
}