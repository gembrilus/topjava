package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create meal {} for userId = {}", meal, SecurityUtil.authUserId());
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete meal with id {} for userId = {}", id, SecurityUtil.authUserId());
        service.delete(id,SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get meal with id {} for userId = {}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.authUserId());
    }

    public Collection<Meal> getAll() {
        log.info("get all meals for userId {}", SecurityUtil.authUserId());
        return service.getAll(SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        log.info("update meal {} for userId = {}", meal, SecurityUtil.authUserId());
        service.update(meal, SecurityUtil.authUserId());
    }

}