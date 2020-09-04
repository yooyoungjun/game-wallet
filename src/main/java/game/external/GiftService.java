
package game.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

// @FeignClient(name="gift", url="http://gift:8080")
@FeignClient(name="gift", url="${api.url.gift}", fallback = GiftServiceFallback.class)
public interface GiftService {

    @RequestMapping(method= RequestMethod.POST, path="/gifts")
    public void exchange(@RequestBody Gift gift);

}