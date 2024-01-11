package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        // 위 코드에서 discountPrice는 discountPolicy가 무엇을 하든 할인된 가격만 받으면 되기 때문에
        // 나중에 수정이 필요하더라도 service딴에서는 코드를 수정할 필요가 없다
        // 그점에서 위 코드는 단일체계 원칙을 잘 지킨 코드라고 할 수 있다.

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
