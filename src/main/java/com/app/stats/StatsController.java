package com.app.stats;

import com.app.app_user.AppUser;
import com.app.app_user.UserService;
import com.app.enums.OrderingStatus;
import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.vehicle.Vehicle;
import com.app.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.app.util.Global.ADMIN;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Secured({ADMIN})
public class StatsController {

    private final VehicleService vehicleService;
    private final UserService userService;

    @GetMapping("/vehicles/prices")
    public Result vehiclesPrices() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        List<Vehicle> vehicles = vehicleService.findAll();

        vehicles.sort(Comparator.comparing(Vehicle::getPrice));

        for (int i = 0; i < vehicles.size(); i++) {
            if (i == 5) break;
            names.add(vehicles.get(i).getName());
            values.add(vehicles.get(i).getPrice());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Vehicles Prices",
                Collections.unmodifiableMap(res)
        );
    }

    @GetMapping("/users/managers/orderings")
    public Result usersManagersOrderings() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        List<AppUser> users = userService.findAllManagers();

        users.sort(Comparator.comparing(AppUser::getOrderingsManagerCompleted));
        Collections.reverse(users);

        for (int i = 0; i < users.size(); i++) {
            if (i == 5) break;
            names.add(users.get(i).getFio());
            values.add(users.get(i).getOrderingsManagerCompleted());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Users Managers Orderings",
                Collections.unmodifiableMap(res)
        );
    }

    @GetMapping("/users/managers/ratings")
    public Result usersManagersRatings() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        List<AppUser> users = userService.findAllManagers();

        users.sort(Comparator.comparing(AppUser::getRating));
        Collections.reverse(users);

        for (int i = 0; i < users.size(); i++) {
            if (i == 5) break;
            names.add(users.get(i).getFio());
            values.add(users.get(i).getRating());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Users Managers Ratings",
                Collections.unmodifiableMap(res)
        );
    }

}
