package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.web.json.JsonUtil.writeIgnoreProps;

class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_MEALS_URL + '/';

    @Autowired
    MealService mealService;

    @Test
    void getAll() throws Exception {
        TestUtil.print(mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(writeIgnoreProps(MEALS))));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonUtil.writeValue(MEAL1)));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Meal> mealsWithoutMeal1 = new ArrayList<>();
        mealsWithoutMeal1.addAll(MEALS);
        mealsWithoutMeal1.remove(MEAL1);
        assertMatch(mealService.getAll(USER_ID), mealsWithoutMeal1);

    }

    @Test
    void testCreateWithUri() throws Exception {
        Meal expected = new Meal(null, LocalDateTime.now(), "New Meal", 505);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.get(expected.getId(), 100000), expected);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1_ID, MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updated.setDescription("Updated Meal Description");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void testGetBetween() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + "between"
         + "?startDate=" + MEAL1.getDate() + "&startTime=" + MEAL1.getTime()
         + "&endDate=" + MEAL3.getDate() + "&endTime=" + MEAL3.getTime())
        ).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value()));
        List<Meal> returnedFromJson = JsonUtil.readValuesIgnoringUnknownProperties(action.andReturn().getResponse().getContentAsString(),
                Meal.class);
        assertMatch(mealService.getBetweenDates(MEAL1.getDate(), MEAL3.getDate(), 100000), returnedFromJson);
    }
}