package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        // appConfig에서 service 구현체를 받아오기만 하면 된다.
//        // 설사 구현체가 바뀌더라도 위 코드에 손댈 필요도 없다.
//        // 이게 의존관계 주입=DI이며 DI의 장점이다.

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // ApplicationContext는 스프링의 시작점이며, 모든걸 관리해주고 스프링 컨테이너라고 한다.
        // 여기서 Bean을 관리하는거고 의존성을 주입해주는거다

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        // name은 AppConfig에 정의된 "메서드 이름"이다.

        Member member = new Member(1L, "memberA", Grade.VIP);
        // Long 타입이라 L을 붙여줘야 함
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
