package de.thi.cnd.review.adapter.outgoing.grpc;

import de.thi.cnd.review.domain.model.User;
import de.thi.cnd.review.ports.outgoing.UserProvider;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import user.UserServiceGrpc;
import user.Userservice;

@Service
public class UserGrpcProvider implements UserProvider {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @Override
    public User getUser(String username) {
        var response = userServiceStub.getUser(
                Userservice.GetUserRequest.newBuilder()
                        .setUsername(username)
                        .build()
        );

        return new User(
                response.getUser().getUsername(),
                response.getUser().getFirstname(),
                response.getUser().getLastname()
        );
    }
}
