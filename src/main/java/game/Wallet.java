package game;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Wallet_table")
public class Wallet {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long rewardId;
    private Long customerId;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Issued issued = new Issued();
        BeanUtils.copyProperties(this, issued);
        issued.publishAfterCommit();


    }

    @PreUpdate
    public void onPreUpdate(){
        Exchanged exchanged = new Exchanged();
        BeanUtils.copyProperties(this, exchanged);
        exchanged.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        game.external.Gift gift = new game.external.Gift();
        gift.setWalletId(this.getId());
        gift.setStatus("Exchanged.");
        // mappings goes here
        WalletApplication.applicationContext.getBean(game.external.GiftService.class)
            .exchange(gift);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
