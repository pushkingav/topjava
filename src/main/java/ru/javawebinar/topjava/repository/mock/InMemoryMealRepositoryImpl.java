package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public void delete(int userId, int mealId) {
        log.info("delete {}", userId, mealId);
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        currentUserMeals.remove(mealId);
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
        // treat case: update, but absent in storage
        log.info("edit {}", meal);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}

