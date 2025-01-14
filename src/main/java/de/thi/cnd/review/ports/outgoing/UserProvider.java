package de.thi.cnd.review.ports.outgoing;

import de.thi.cnd.review.domain.model.User;

public interface UserProvider {

    User getUser(String username);

}
