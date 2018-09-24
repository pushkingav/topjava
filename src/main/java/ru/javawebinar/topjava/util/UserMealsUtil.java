package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    /*
    * Реализовать метод UserMealsUtil.getFilteredWithExceeded:
    -  должны возвращаться только записи между startTime и endTime
    -  поле UserMealWithExceed.exceed должно показывать,
                                     превышает ли сумма калорий за весь день параметра метода caloriesPerDay
    Т.е UserMealWithExceed - это запись одной еды, но поле exceeded будет одинаково для всех записей за этот день.
    - Проверьте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
    - Оцените Time complexity вашего алгоритма, если он O(N*N)- попробуйте сделать O(N).
    если суммарное количество каллорий за день превышает 2000(в нашем случае), то все usermeal с булином за этот день должны выдать тру
    * */

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //1. Группируем лист в мапу  Map<LocalDate, List<UserMeal>> - группировка трапез по дате
        //2. Выбираем из мапы только те листы, где сумма калорий больше caloriesPerDay

        List<Map.Entry<LocalDate, List<UserMeal>>> collect = mealList.stream().collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .entrySet().stream().filter(localDateListEntry -> {
                    int count = localDateListEntry.getValue().stream().mapToInt(UserMeal::getCalories).sum();
                    return count > caloriesPerDay;
                }).collect(Collectors.toList());
        List<UserMealWithExceed> collect1 =
                collect.get(0).getValue().stream().map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true))
                        .filter(userMealWithExceed -> {
                            LocalTime localTime = userMealWithExceed.getDateTime().toLocalTime();
                            return localTime.isAfter(startTime) && localTime.isBefore(endTime);
                        }).collect(Collectors.toList());
        return collect1;
    }
}
