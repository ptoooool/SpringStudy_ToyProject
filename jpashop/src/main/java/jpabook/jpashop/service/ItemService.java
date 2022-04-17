package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional //저번에 말한거처럼 위에 readonly를 했으니 save는 따로 다시 transactional하는것
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional //여기서 따로 저장없이 트랜잭션에 의해서 자동으로 커밋이된다.
    public void updateItem(Long itemId, Book bookParam){
        Item findItem = itemRepository.findOne(itemId);
        // findItem으로 불러오고 아래에서 값이 바뀌기 때문에 JPA가 바뀐걸 감지하고
        // 바뀐걸 update 쿼리를 DB에 날린다. 이게 변경감지를 이용한 update방법
        findItem.setPrice(bookParam.getPrice());
        findItem.setName(bookParam.getName());
        findItem.setStockQuantity(bookParam.getStockQuantity());
    }

    public List<Item> findItems(){
        return itemRepository.findaAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
