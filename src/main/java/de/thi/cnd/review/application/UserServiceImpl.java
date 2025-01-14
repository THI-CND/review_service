package de.thi.cnd.review.application;

import de.thi.cnd.review.domain.UserService;
import de.thi.cnd.review.domain.model.User;
import de.thi.cnd.review.ports.outgoing.UserProvider;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserProvider userProvider;

    public UserServiceImpl(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public User getUser(String username) {
        return userProvider.getUser(username);
    }

}
