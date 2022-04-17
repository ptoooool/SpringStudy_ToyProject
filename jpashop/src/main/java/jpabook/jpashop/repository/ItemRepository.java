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
            // merge는 비어있는값이 객체에 있으면 속성자체가 모든 값을 update 하기 때문에
            // null이 들어가게 될 수있다. 그렇기 때문에 편하긴하지만 실무에서는 쓰이지않는다.
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
