package hello.core.beanfind;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회 ")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass() = " + memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass());
    }

    @Test
    @DisplayName("구체 타입으로 조회 ")
    void findBeanByName2() {
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    // interface에 의존하는 것이 바람직하기 때문에 위의 코드는 좋은 코드는 아니다. // 코드의 유연성이 떨어진다
    // 하지만 위처럼 구체 타입으로도 빈 객체를 조회할 수 있다는 점을 알아두면 쓰일때가 있을 수 있다

    @Test
    @DisplayName("빈 이름으로 조회 X")
    void findBeanByNameX() {
//        MemberService xxxx = ac.getBean("xxxx", MemberService.class);

        // junit의 assertThrows
        // 2번째 파라미터의 코드를 실행햇을 때, 1번째 파라미터의 예외가 터져야한다.
        // 터질경우 성공, 안터질경우 실패
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxx", MemberService.class));


        // assertj의 assertThatThrownBy는 메시지까지 검증 가능
        assertThatThrownBy(() ->  ac.getBean("xxxx", MemberService.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class)
                .hasMessage("No bean named 'xxxx' available");
    }
}