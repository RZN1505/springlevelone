package pro.bolshakov.geekbrains.springlevelone.dz7.config;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Role;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.User;

import java.util.Objects;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasRole(Role role){
        if(user == null){
            throw new RuntimeException("User is not defined");
        }
        return Objects.equals(role, user.getRole());
    }
}
