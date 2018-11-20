package ru.javawebinar.topjava.web;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class JspMealController extends AbstractMealController {
    static {
        log = LoggerFactory.getLogger(JspMealController.class);
    }

    @GetMapping("/meals/delete/{id}")
    public String delete(@PathVariable Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/meals")
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/meals/create")
    public String createMeal(Model model) {
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping("/meals/update/{id}")
    public String updateMeal(@PathVariable Integer id, Model model) {
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/meals/create")
    public String addMeal(HttpServletRequest request, HttpServletResponse response) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        super.create(meal);
        return "redirect:/meals";
    }


    /*@GetMapping("/meals")
    public String getMeals(Model model, HttpServletRequest request) {
       String action = request.getParameter("action");
       String result = "";
       switch (action == null ? "all" : action) {
           case "delete":
               int id = getId(request);
               delete(id);
               model.addAttribute("meals", getAll());
               result = "redirect: meals";
               break;
           case "create":
           case "update":
               final Meal meal = "create".equals(action) ?
                       new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                       get(getId(request));
               super.update(meal);
               request.setAttribute("meal", meal);
               request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
               break;
           case "all":
           default:
               request.setAttribute("meals", getAll());
               request.getRequestDispatcher("/meals.jsp").forward(request, response);
               break;
       }
       return result;
   }*/

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
