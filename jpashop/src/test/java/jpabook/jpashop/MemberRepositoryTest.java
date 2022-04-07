package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest
{
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional //EntityManager를 통한 데이터 변경은 반드시 Transaction이 필요함
    //Transactional은 TEST에 있으면 DB가 ROLLBACK 되버린다.
	@Rollback(false)	//테스트 종료 후 데이터를 롤백하지 않고 그대로 남겨두는 옵션
    public void testMember() throws Exception
    {
        //given
//        Member member = new Member();
//        member.setUsername("1hoon");
//
//        //when
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.find(savedId);
//
//        //then
//        assertEquals(findMember.getId(), member.getId());
//        assertEquals(findMember.getUsername(), member.getUsername());
//        assertEquals(findMember, member); //객체 자체에 대해서 같은지 아닌지 보는데
        //findMember == member / 같은 영속성 Content안에서는 같은 데이터로 인식한다.
    }
}
// jar파일 만드는 명령어 ./gradlew clean build
// build 완료되면 libs까지 들어가서 java -jar jpashop-0.0.1-SNAPSHOT.jar 치면됨