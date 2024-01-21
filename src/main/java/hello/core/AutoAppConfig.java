package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core.member", // = {"" ,""} 이런식으로 여러개도 지정가능
        // 탐색할 패키지의 시작 위치 지정 -> 모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 오래걸리기 때문
        basePackageClasses = AutoAppConfig.class, // 이런식으로 클래스를 지정하여, 해당 클래스가 존재하는 패키지를 탐색할 수도 있음

        // 아무것도 지정을 안한다면? 디폴트 스캔 시작 위치는 어디인가?
        // @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 스프링 빈에 등록하지 않을 클래스 지정, @Configuration 제외 -> AppConfig파일을 제외하기 위함 // @Configuration 어노테이션에는 @Component 어노테이션이 포함되어 있기 때문
        // 평소에 쓸때는 excludeFileters를 통해 Configuration을 제외 안해줘도 된다. 그냥 AppConifg 파일 살릴려고 한거다. 그냥 알아만 두자
)
// @Component 어노테이션이 붙은 class를 전부 찾아서 스프링 빈으로 등록해준다.
public class AutoAppConfig {
    // 아무것도 없어도 된다.
}
