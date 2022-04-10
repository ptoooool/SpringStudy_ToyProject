package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item); // 신규등록
        }else{
            em.merge(item); // 이미 존재하기 때문에 update라고보면됨 (진짜update는아님)
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);

    }

    public List<Item> findaAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
