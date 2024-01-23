package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final이 붙은 필드를 파라미터로 하는 생성자의 역할과 같다.
public class OrderServiceImpl implements OrderService{

//    @Autowired
    private final MemberRepository memberRepository;
//    @Autowired
    private final DiscountPolicy discountPolicy;

//    @RequiredArgsConstructor와 역할이 같다.
    @Autowired // 생성자가 하나일때는 @Autowired 생략해도 의존관계 주입됨
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // setter 주입 // 필드는 final 선언을 할 수 없음
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        // 위 코드에서 discountPrice는 discountPolicy가 무엇을 하든 할인된 가격만 받으면 되기 때문에
        // 나중에 수정이 필요하더라도 service딴에서는 코드를 수정할 필요가 없다
        // 그점에서 위 코드는 단일체계 원칙을 잘 지킨 코드라고 할 수 있다.

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
