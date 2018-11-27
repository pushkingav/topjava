package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequestMapping(MealRestController.REST_MEALS_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_MEALS_URL = "/rest/meals";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithUri(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewMealResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_MEALS_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewMealResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @GetMapping(value = "/between")
    public List<MealTo> getBetween(@RequestParam(value = "startDate")
                                   @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
                                   @RequestParam(value = "startTime", required = false)
                                   @DateTimeFormat(iso = ISO.TIME) LocalTime startTime,
                                   @RequestParam(value = "endDate")
                                   @DateTimeFormat(iso = ISO.DATE) LocalDate endDate,
                                   @RequestParam(value = "endTime")
                                   @DateTimeFormat(iso = ISO.TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}