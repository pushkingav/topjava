package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(getRef(userId));
        if (meal.isNew()) {
            entityManager.persist(meal);
            return meal;
        } else {
            return entityManager.merge(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        User ref = getRef(userId);
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user", ref)
                .executeUpdate() > 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User ref = getRef(userId);
        List<Meal> meals = entityManager.createNamedQuery(Meal.GET_BY_ID, Meal.class)
                .setParameter("id", id)
                .setParameter("user", ref)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User ref = getRef(userId);
        return entityManager.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("user", ref)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User ref = getRef(userId);
        return entityManager.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("startdate", startDate)
                .setParameter("enddate", endDate)
                .setParameter("user", ref)
                .getResultList();
    }

    private User getRef(int userId) {
        return entityManager.getReference(User.class, userId);
    }
}