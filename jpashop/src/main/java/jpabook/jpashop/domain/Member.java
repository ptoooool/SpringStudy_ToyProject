package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {

        @Id @GeneratedValue
        @Column(name = "member_id")
        private Long id;

        private String name;

        @Embedded // Embeddable이랑 둘중하나만 있어도 되지만 명시하는 용
        private Address address;

        @OneToMany(mappedBy = "member") //order필드에 있는 member에 의해서 매핑된것이라는 뜻(매핑된 읽기전용이 된다는것) 여기에 값을 넣는다고 저쪽의 FK값이 변경되지않음
        private List<Order> orders = new ArrayList<>();


}
