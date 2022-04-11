package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 이렇게하면 생성자 막는것 동일
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격
    private int count; //주문수량

//    protected OrderItem (){} //이렇게 해두면 생성자이용을 막는것이라 보면된다.
    //protected이기 때문에 Service쪽에서 이용을 아래의 createOrderItem뺴고는 쓰지못하게 하는 용도

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); //넘어온 제품갯수막큼 재고에서 빼주기
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회로직==//

    /**
     * 주문상품 전체 가격로직
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
