package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class JspMealController {
    @Autowired
    private MealService mealService;

   @GetMapping("/meals")
    public List<MealTo> getUsers(HttpServletRequest request) {
       String action = request.getParameter("action");

       /*switch (action == null ? "all" : action) {
           case "delete":
               int id = getId(request);
               mealController.delete(id);
               response.sendRedirect("meals");
               break;
           case "create":
           case "update":
               final Meal meal = "create".equals(action) ?
                       new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                       mealController.get(getId(request));
               request.setAttribute("meal", meal);
               request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
               break;
           case "all":
           default:
               request.setAttribute("meals", mealController.getAll());
               request.getRequestDispatcher("/meals.jsp").forward(request, response);
               break;
       }*/
       return null;
   }
}
