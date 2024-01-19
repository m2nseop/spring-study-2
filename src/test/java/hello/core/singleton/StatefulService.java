package hello.core.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
//        this.price = price; // 여기가 문제!
        return price; // 이렇게 지역변수를 사용하도록 변경하면 문제점이 해결된다.
    }

    public int getPrice() {
        return price;
    }
}
