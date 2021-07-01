package com.carero;

import com.carero.domain.cat.Category;
import com.carero.domain.cat.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitCat {

    private final InitCatService initCatService;

    @PostConstruct
    public void init(){
        initCatService.catInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitCatService {
        private final EntityManager em;

        public void catInit(){
            Category cat1 = new Category("아동");
            Category cat2 = new Category("노인 & 장애인");
            Category cat3 = new Category("반려동물");
            Category cat4 = new Category("가사도우미");

            em.persist(cat1);
            em.persist(cat2);
            em.persist(cat3);
            em.persist(cat4);

            SubCategory subcat1_1 = new SubCategory("베이비시터",cat1);
            SubCategory subcat1_2 = new SubCategory("등하원시터",cat1);
            SubCategory subcat1_3 = new SubCategory("학습시터",cat1);
            SubCategory subcat1_4 = new SubCategory("산후도우미",cat1);
            SubCategory subcat1_5 = new SubCategory("놀이시터",cat1);
            SubCategory subcat1_6 = new SubCategory("기타",cat1);

            em.persist(subcat1_1);
            em.persist(subcat1_2);
            em.persist(subcat1_3);
            em.persist(subcat1_4);
            em.persist(subcat1_5);
            em.persist(subcat1_6);

            SubCategory subcat2_1 = new SubCategory("활동보조인",cat2);
            SubCategory subcat2_2 = new SubCategory("간병인",cat2);
            SubCategory subcat2_3 = new SubCategory("요양보호사",cat2);
            SubCategory subcat2_4 = new SubCategory("간호사",cat2);
            SubCategory subcat2_5 = new SubCategory("물리치료사",cat2);
            SubCategory subcat2_6 = new SubCategory("기타",cat2);

            em.persist(subcat2_1);
            em.persist(subcat2_2);
            em.persist(subcat2_3);
            em.persist(subcat2_4);
            em.persist(subcat2_5);
            em.persist(subcat2_6);


            SubCategory subcat3_1 = new SubCategory("조련사",cat3);
            SubCategory subcat3_2 = new SubCategory("펫시터",cat3);
            SubCategory subcat3_3 = new SubCategory("도그워커",cat3);
            SubCategory subcat3_4 = new SubCategory("기타",cat3);

            em.persist(subcat3_1);
            em.persist(subcat3_2);
            em.persist(subcat3_3);
            em.persist(subcat3_4);

            SubCategory subcat4_1 = new SubCategory("음식/주방",cat4);
            SubCategory subcat4_2 = new SubCategory("청소도우미",cat4);
            SubCategory subcat4_3 = new SubCategory("기타",cat4);

            em.persist(subcat4_1);
            em.persist(subcat4_2);
            em.persist(subcat4_3);

        }
    }
}
