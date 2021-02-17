package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    @Autowired

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal{} for user", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} foe user", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealTo> getBetweenHalfOmen(@Nullable LocalDate startDate, @Nullable LocalTime startTime, @Nullable LocalDate endData, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info(" getBetweenHalfOmen dates({} - {} time {}) for user {}", startDate, endData, startTime, endTime, userId);
        List<Meal> mealListFilter = service.getBetweenHalfOmen(startDate, endData, userId);
        return MealsUtil.getFilteredTos(mealListFilter, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}