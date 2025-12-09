package com.app.vehicle;

import com.app.app_user.UserService;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final UserService userService;

    public List<Vehicle> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Vehicle> findAllManager(String id) {
        return repository.findAllByOwner_Id(userService.find(id).getId(), Sort.by(Sort.Direction.DESC, "id"));
    }

    public Vehicle find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Транспорт не найден по ИД: " + id));
    }

    public Vehicle save(Vehicle save) {
        save.setOwner(userService.getCurrentUser());
        return repository.save(save);
    }

    public Vehicle update(String id, Vehicle update) {
        Vehicle old = find(id);
        old.update(update);
        return repository.save(old);
    }

    public Vehicle updateImg(String id, MultipartFile img) {
        Vehicle vehicle = find(id);
        try {
            vehicle.setImg(saveFile(img, "vehicle"));
        } catch (IOException e) {
            throw new BadRequestException("Некорректное изображение");
        }
        return repository.save(vehicle);
    }

    public void delete(String id) {
        repository.deleteById(find(id).getId());
    }

}
