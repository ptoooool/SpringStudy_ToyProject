package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


//@AllArgsConstructor //필드 모든것에 대해서 생성자를 만들어줌
@Service
@Transactional(readOnly = true) // JPA는 반드시 Transaction내에서 실행되어야한다. 이거 import할 때 spring의 것을 import해야된다.
@RequiredArgsConstructor // 얘는 final을 가진애만 생성자를 만들어준다.
public class MemberService { // 서비스에 커서 올리고 shift + ctrl + T .. 테스트 생성

//    @Autowired
      private final MemberRepository memberRepository;
      //final을 해줘야 생성자에서 값 set을 안해주면 에러가 뜨기때문에 확인이쉽다.

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional // 아래의 조회와는 다르게 위의 전체에 readOnly가 true면 전체에 먹히기 때문에 write인 이녀석은 따로 @Transactional을 해주면된다.
    public Long join(Member member){
       validateDuplicateMember(member); // 중복 회원 검증
       memberRepository.save(member);
       return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }
    //회원 전체조회
    @Transactional(readOnly = true) // 조회하는 부분에 있어서 성능향상을 준다.
    //Dirty체크나 DB한테 읽기전용이니 resource 많이 쓰지않고 읽어라고 해줘서 서능향상
    //조회에는 readOnly를 넣어주면 좋다.
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
    //
}
