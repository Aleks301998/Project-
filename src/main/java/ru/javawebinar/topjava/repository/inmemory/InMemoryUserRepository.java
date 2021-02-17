package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<Integer, User> userMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public static final  int User_ID = 1;
    public static final int Admin_ID = 2;

    @Override
    public boolean delete(int id) {
      return userMap.remove(id) !=null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userMap.put(user.getId(), user);
            return user;
        }
      return userMap.computeIfPresent(user.getId(), ((id, oldUser) -> user));
    }

    @Override
    public User get(int id) {
     return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        return userMap.values().stream().sorted(Comparator.comparing(User::getName).
                thenComparing(User::getEmail)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
     return userMap.values().stream().
             filter(user -> email.equals(user.getEmail())).findFirst().orElse(null);
    }
}
