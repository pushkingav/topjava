package ru.javawebinar.topjava.web;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    static {
        log = LoggerFactory.getLogger(JspMealController.class);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String createMeal(Model model) {
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @PostMapping("/create")
    public String addMeal(HttpServletRequest request, HttpServletResponse response) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        super.create(meal);
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String getMealForUpdate(@PathVariable Integer id, Model model) {
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/update/{id}")
    public String updateMeal(@PathVariable Integer id, HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        meal.setId(id);
        super.update(meal, id);
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filterMeals(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
