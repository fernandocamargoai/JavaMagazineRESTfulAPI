package br.com.devmedia.javamagazine.restfulapi.service;

import br.com.devmedia.javamagazine.restfulapi.model.bean.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getCurrentUser(){
        User defaultUser = User.findUser("default");
        if(defaultUser == null){
            User user = new User();
            user.setId("default");
            user.setLogin("default");
            user.setName("Default");
            user.setPassword("default");
            user.persist();
        }
        return defaultUser;
    }

}
