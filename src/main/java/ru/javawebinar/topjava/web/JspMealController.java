package ru.javawebinar.topjava.web;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class JspMealController extends AbstractMealController {
    static {
        log = LoggerFactory.getLogger(JspMealController.class);
    }

    @GetMapping("/meals/delete/{id}")
    public String delete(@PathVariable Integer id) {
        super.delete(id);
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
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
