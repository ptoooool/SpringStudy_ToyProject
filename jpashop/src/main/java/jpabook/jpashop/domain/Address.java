package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable // @Embeddable 애노테이션을 지정한 클래스를 밸류 클래스라고 합니다. 밸류 클래스란 int, double 처럼 하나의 값을 나타내는 클래스를 말합니다.
//밸류 클래스는 여러개의 값(address1, address2, zipCode)을 가지지만 개념적으로 하나의 값(주소)을 표현합니다. 또한 다른 밸류 객체와 식별하기 위한 식별자를 가지지 않는 것이 특징입니다.
@Getter @Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
