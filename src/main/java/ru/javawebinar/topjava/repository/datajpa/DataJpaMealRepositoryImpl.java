package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        User user = crudUserRepository.getOne(userId);
        meal.setUser(user);
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<Meal> meal = crudMealRepository.findById(id);
        if (meal.isEmpty() || meal.get().getUser().getId() != userId) {
            return null;
        }
        return meal.get();
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = crudUserRepository.getOne(userId);
        return crudMealRepository.findAllByUserOrderByDateTimeDesc(user);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User user = crudUserRepository.getOne(userId);
        return null;
//       return crudMealRepository.findAllByDateBetweenAndUserOrderByDateTimeDesc(user, startDate.toLocalDate(), endDate.toLocalDate());
    }
}
