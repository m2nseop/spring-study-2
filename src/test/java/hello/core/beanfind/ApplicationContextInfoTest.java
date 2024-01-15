package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // 스프링 컨테이너에 빈은
    // 빈 이름(=키), 빈 객체(=값) 쌍으로 저장되어 있다



    @Test
    @DisplayName("모든 빈 출력하기")
    public void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 컨테이너에 등록된 빈들의 이름(=키)들을 가져온다
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName); // 이름(=키)값으로 빈 객체를 찾아온다
            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    // 위 테스트 코드는 내가 만든 빈만 나오는게 아니니까 가독성이 좀 딸린다.
    // 내가 만든 빈(어플리케이션 빈)만 출력되게 하고 싶다

    @Test
    @DisplayName("어플리케이션 빈 출력하기")
    public void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 컨테이너에 등록된 빈들의 이름(=키)들을 가져온다
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);// 해당 빈의 메타데이터 정보를 가져온다.

            // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
            // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }
}
