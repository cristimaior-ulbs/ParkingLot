package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserBean userBean;

    @Inject
    PasswordBean passwordBean;

    public List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
            dtos.add(userDto);
        }
        return dtos;
    }

    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT c FROM User c", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void createUser(String username, String email, String password, List<String> user_groups) {
        LOG.info("createUser");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(user);

        assignGroupsToUser(username, user_groups);
    }

    public void assignGroupsToUser(String username, List<String> user_groups) {
        for(String user_group : user_groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(user_group);
            entityManager.persist(userGroup);
        }
    }
}