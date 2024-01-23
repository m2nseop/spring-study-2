package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @Configuration 어노테이션은
// CGLIB기술을 통해 bean 객체를 하나씩만 만들게 하여 싱글톤을 보장한다.
public class AppConfig {

    // 생기는 궁금증
    // 1. @Bean memberService() -> new MemoryMemberRepository();
    // 2. @Bean orderService() -> new MemoryMemberRepository();
    // 스프링 컨테이너는 싱글톤으로 bean 객체를 관리한다면서 1,2에 따르면 객체를 두 번 생성하는거 아닌가?
    // 하지만 스프링 컨테이너는 이를 해결해준다.

    // 예상 결과
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository

    // 실제 결과
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // 스프링에 의해 결국 하나의 객체만 생성되었다. // 객체가 생성되는 순서는 보장이 없기 때문에 바뀔 수 있음

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }
}
