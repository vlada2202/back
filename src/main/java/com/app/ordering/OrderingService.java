package com.app.ordering;

import com.app.app_user.AppUser;
import com.app.app_user.UserService;
import com.app.enums.OrderingStatus;
import com.app.system.exception.ObjectNotFoundException;
import com.app.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderingService {

    private final OrderingRepository repository;
    private final UserService userService;
    private final VehicleService vehicleService;

    public List<Ordering> findAll() {
        AppUser user = userService.getCurrentUser();
        List<Ordering> orderings = new ArrayList<>();

        switch (user.getRole()) {
            case USER -> orderings = user.getOrderings();
            case MANAGER -> orderings = user.getOrderingsManager();
        }

        orderings.sort(Comparator.comparing(Ordering::getId));
        Collections.reverse(orderings);

        return orderings;
    }

    public Ordering find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найдена заявка по ИД: " + id));
    }

    public Ordering save(Ordering save, String vehicleId) {
        save.setVehicle(vehicleService.find(vehicleId));
        save.setOwner(userService.getCurrentUser());
        return repository.save(save);
    }

    public Ordering approved(String id) {
        Ordering ordering = find(id);
        ordering.setStatus(OrderingStatus.APPROVED);
        return repository.save(ordering);
    }

    public Ordering rejected(String id) {
        Ordering ordering = find(id);
        ordering.setStatus(OrderingStatus.REJECTED);
        return repository.save(ordering);
    }

    public Ordering done(String id) {
        Ordering ordering = find(id);
        ordering.setStatus(OrderingStatus.DONE);
        return repository.save(ordering);
    }

}
