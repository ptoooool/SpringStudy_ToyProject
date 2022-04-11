package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //  오더랑 멤버는 다대 1 관계 // ManyToOne은 반드시 LAZY로 해야된다.
    @JoinColumn(name = "member_id") //FK가 된다고 보면된다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //casacde를 해주면 persist(order)만 해주면 orderItem도  같이 persist해준다. delete 될 때도 같이 됨
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 오더 저장될때 딜리버리도 같이 저장된다(persist) 각자 해줘야되는데 한번에 되게하는 cascade
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태[ORDER, CANCEL]

    //==연관관계 메서드==//
    //아래 메서드 3개와 같이 이렇게 필요한 연관테이블의 경우 양방향으로 해두면 좋다.
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this); // member를 set 할때 order에도 들어가게 한번에 가능하게한다.
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);

    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem: orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        //return totalPrice += orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum(); dlfjgrpehehla
        return totalPrice;
    }


}
