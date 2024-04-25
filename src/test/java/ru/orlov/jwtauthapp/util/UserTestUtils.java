package ru.orlov.jwtauthapp.util;

import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;
import ru.orlov.jwtauthapp.model.User;

@UtilityClass
public class UserTestUtils {

    private final EasyRandom easyRandom = new EasyRandom();

    public User createUser() {
        return easyRandom.nextObject(User.class);
    }
}
