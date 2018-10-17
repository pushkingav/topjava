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
    public MealWithExceed get(int userId, int mealId, int caloriesPerDay) {
        List<MealWithExceed> all = getAll(userId, caloriesPerDay);
        return all.stream().filter(mealWithExceed -> mealWithExceed.getId().equals(mealId)).findAny().get();
    }

    @Override
    public boolean delete(int userId, int mealId) {
        repository.delete(userId, mealId);
        return true;
    }

    @Override
    public void update(Meal meal, int id) {
        repository.save(meal);
    }
}