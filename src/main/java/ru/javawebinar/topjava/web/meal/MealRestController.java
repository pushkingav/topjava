package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;
    //TODO - Тут берем id user'а из SecurityUtil!
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getAllFiltered(String type) {
        //Отдать свою еду, отфильтрованную по startDate, startTime, endDate, endTime
        return null;
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(SecurityUtil.authUserId(), mealId);
    }

    public boolean delete(int id) {
        //удалить свою еду по id, параметр запроса - id еды.
        // Если еда с этим id чужая или отсутствует - NotFoundException
        log.info("delete {}", id);
        return service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal) {
        log.info("update {}", meal);
        service.update(meal);
    }
}
