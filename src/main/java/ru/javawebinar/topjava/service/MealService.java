package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.getStart;
import static ru.javawebinar.topjava.util.DateTimeUtil.localDateTimeExclusive;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Meal> getBetweenHalfOmen(@Nullable LocalDate startDate, @Nullable LocalDate endData, int userId) throws NotFoundException {
        return repository.getBetweenHalfOpen(getStart(startDate), localDateTimeExclusive(endData), userId);
    }

    public List<Meal> getAll(int userId) throws NotFoundException {
        return (List<Meal>) repository.getAll(userId);
    }

    public void update(Meal meal, int userId) throws NotFoundException{
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

}