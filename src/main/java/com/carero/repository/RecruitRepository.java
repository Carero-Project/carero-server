package com.carero.repository;

import com.carero.domain.recruit.Recruit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecruitRepository {

    private final EntityManager em;

    public void save(Recruit recruit){
        em.persist(recruit);
    }

    public Recruit findOne(Long id){
        return em.find(Recruit.class, id);
    }

    public List<Recruit> findAll(){
        return em.createQuery("select r from Recruit r",Recruit.class).getResultList();
    }


    public void deleteAll() {
        List<Recruit> recruits = findAll();
        for (Recruit recruit : recruits) {
            em.remove(recruit);
        }
    }

    public void deleteById(Long id) {
        Recruit recruit = findOne(id);
        em.remove(recruit);
    }

    public List<Recruit> findByPage(int offset, int limit) {

        return em.createQuery(
                "select r from Recruit r" +
                        " join fetch r.user u" +
                        " join fetch r.workInfo wi" +
                        " order by r.id desc", Recruit.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }
}
