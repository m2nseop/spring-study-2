package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 스프링 컨테이너의 빈 저장소에 저장될 이름은 기본적으로 클래스이름에서 맨 앞글자를 소문자로 바꿔서 저장된다.
// MemberServiceImpl -> memberServiceImpl
// 원하는대로 지정하고 싶으면 @Component("원하는 이름") 이런식으로 작성하면 된다.
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    // @Autowired -> 스프링이 생성자 파라미터에 타입에 맞는 객체를 주입해준다.
    // 자동 의존관계 주입
    @Autowired // ac.getBean(MemberRepository.class) 와 같다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
