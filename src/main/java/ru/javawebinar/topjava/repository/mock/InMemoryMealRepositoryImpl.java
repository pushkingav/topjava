package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public void delete(int userId, int mealId) {
        log.info("delete {}", mealId);
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        Meal meal = currentUserMeals.computeIfAbsent(mealId, notFoundMealId -> {
            throw new NotFoundException(String.format("User %d has no meal with id %d", userId, mealId));
        });
        currentUserMeals.remove(meal.getId());
        repository.put(userId, currentUserMeals);
    }

    @Override
    public Meal save(Meal meal) {
        Map<Integer, Meal> currentUserMeals = repository.computeIfAbsent(meal.getUserId(), userId -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            currentUserMeals.put(meal.getId(), meal);
            repository.put(meal.getUserId(), currentUserMeals);
            log.info("save {}", meal);
            return meal;
        }

        log.info("edit {}", meal);
        currentUserMeals.put(meal.getId(), meal);
        repository.put(meal.getUserId(), currentUserMeals);
        return meal;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get user {}, meal {}", userId, mealId);
        Meal meal = repository.get(userId).computeIfAbsent(mealId, notFoundMealId -> {
            throw new NotFoundException(String.format("User %d has no meal with id %d", userId, mealId));
        });
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId) == null ? Collections.emptyList() : repository.get(userId).values();
    }
}

