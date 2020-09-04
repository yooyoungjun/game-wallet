package game.external;

import org.springframework.stereotype.Component;

@Component
public class GiftServiceFallback implements GiftService {

    @Override
    public void exchange(Gift gift) {
        System.out.println("Circuit breaker has been opened. Fallback returned instead.");
    }

}