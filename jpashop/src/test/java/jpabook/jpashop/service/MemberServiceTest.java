package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //이거 덕분에 같은 트랜잭션 안에서 같은 entity pk값이 똑같으면 같은 영속성컨텍스트안에서 관리가 되서 하나로만 관리가된다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false) // 결과를 보고싶으면 이렇게 명식해서 롤백안되게해야함
    public void 회원가입()throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);


        //then
        //transactional 덕분에 가능한것
        em.flush(); //영속성 컨택스트에 있는 변경,등록 내용을 데이터베이스에 반영한다. Rollback false 없이 하도록하는것
        assertEquals(member, memberRepository.findOne(saveId)); //멤버를 찾아서 온 멤버랑 같아야된다.

        //flush를 하면 순간은 insert가 되지만 Test 자체에는 Transactional이기 떄문에 Rollback이된다.
        //@Rollback보다는 이걸 쓰는편인듯하다. Rollback 어노테이션은 아예 디비에 반영이 되기떄문에 좋지는않다.

    }

    @Test //(expected = IllegalStateException.class) Junit5에서는 안됨
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2); // 예외가 발생해야 한다!!!
        }catch (IllegalStateException e){
            return;
        }

        //Junit5에서는 excpected안되서 아래와 같이 쓴다.
        assertThrows(EmptyResultDataAccessException.class, ()->{
            memberService.join(member2); // 예외가 발생해야 한다!!!
        });

        //then
        fail("예외가 발생해야 한다."); //여기로 왔다는건 성공했따는 뜼이니 오면안된다.
    }
}