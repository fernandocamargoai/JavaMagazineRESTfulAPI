package br.com.devmedia.javamagazine.restfulapi.service;

import br.com.devmedia.javamagazine.restfulapi.model.bean.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Service
public class UserService {

    @Transactional
    public User getCurrentUser(){
        try {
            return User.findUsersByLoginEquals("default").getSingleResult();
        }
        catch (EmptyResultDataAccessException e){
            User defaultUser = new User();
            defaultUser.setLogin("default");
            defaultUser.setEmail("default@default.com");
            defaultUser.setName("Default");
            defaultUser.setPassword("default");
            defaultUser.persist();
            return defaultUser;
        }
    }

}
