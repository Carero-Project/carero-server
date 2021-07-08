package com.carero.repository;

import com.carero.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public Long save(User user){
        em.persist(user);

        return user.getId();
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        List<User> users = em.createQuery("select u from User u", User.class)
                .getResultList();

        return users;
    }

    public List<User> findByName(String name){
        return em.createQuery("select u from User u where u.username = :name",User.class)
                .setParameter("name",name)
                .getResultList();
    }

    public void deleteById(Long id){
        User one = findOne(id);
        em.remove(one);
    }
}
