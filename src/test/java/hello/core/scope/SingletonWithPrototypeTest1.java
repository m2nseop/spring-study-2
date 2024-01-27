package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototyeBean.class);
        PrototyeBean prototypeBean1 = ac.getBean(PrototyeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototyeBean prototyeBean2 = ac.getBean(PrototyeBean.class);
        prototyeBean2.addCount();
        assertThat(prototyeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototyeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
        // 요청할때마 새롭게 생성되어 logic을 두번실행해도 count가 1이어야 하지만 그렇지 않다는 점이 문제점이다.
    }

    @Scope("singleton") // 싱글톤 스코프일 경우 생략 가능
    static class ClientBean {
        private final PrototyeBean prototyeBean;
        // 생성시점에 주입되어 의도와는 다르게 계속 같은 prototypeBean을 사용하게 된다.
        // 문제점: 요청을 받을때마다 생성되어야할 prototype 스코프 빈의 목적을 잃어버렸다.

        @Autowired // 생성자 1개일 경우 생략 가능 // 또는 @RequiredArgsConstructor를 통해 생성자 자체를 생략 가능
        public ClientBean(PrototyeBean prototyeBean) {
            this.prototyeBean = prototyeBean; //
        }

        public int logic() {
            prototyeBean.addCount();
            return prototyeBean.getCount();
        }
    }


    @Scope("prototype")
    static class PrototyeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototyeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototyeBean.destroy");
        }
    }
}
