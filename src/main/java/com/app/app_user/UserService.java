package com.app.app_user;

import com.app.enums.Role;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(MyUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином " + username + " не найден"));
    }

    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return repository.findByUsername(currentUserName).orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        }
        return null;
    }

    public List<AppUser> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<AppUser> findAllManagers() {
        return repository.findAllByRole(Role.MANAGER, Sort.by(Sort.Direction.DESC, "id"));
    }

    public AppUser find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с ИД: " + id));
    }

    public AppUser save(AppUser user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Пользователь с таким логином уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (findAll().isEmpty()) {
            user.setRole(Role.ADMIN);
        }
        return repository.save(user);
    }

    public AppUser update(AppUser user) {
        AppUser old = getCurrentUser();
        old.update(user);
        return repository.save(old);
    }

    public AppUser updateRole(String id, String role) {
        AppUser user = find(id);
        try {
            user.setRole(Role.valueOf(role));
        } catch (Exception e) {
            throw new BadRequestException("Некорректный выбор роли");
        }
        return repository.save(user);
    }

    public AppUser updateImg(MultipartFile img) {
        AppUser user = getCurrentUser();
        try {
            user.setImg(saveFile(img, "user"));
        } catch (Exception e) {
            throw new BadRequestException("Некорректное изображение");
        }
        return repository.save(user);
    }

    public AppUser updateLicense(MultipartFile license) {
        AppUser user = getCurrentUser();
        try {
            user.setLicense(saveFile(license, "user"));
        } catch (Exception e) {
            throw new BadRequestException("Некорректный файл");
        }
        return repository.save(user);
    }

    public AppUser updateManager(ManagerDto manager) {
        AppUser user = getCurrentUser();
        user.updateManager(manager);
        return repository.save(user);
    }

    public AppUser updateManagerApproved(String id) {
        AppUser user = find(id);
        user.setRole(Role.MANAGER);
        return repository.save(user);
    }

    public void delete(String userId) {
        AppUser user = find(userId);
        repository.deleteById(user.getId());
    }

}
