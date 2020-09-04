package game;

import game.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    WalletRepository walletRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverAllocated_Issue(@Payload Allocated allocated){

        if(allocated.isMe()){
            Wallet wallet = new Wallet();
            wallet.setRewardId(allocated.getId());
            wallet.setCustomerId(allocated.getCustomerId());
            wallet.setStatus("Issued");

            walletRepository.save(wallet);

            System.out.println("##### listener Issue : " + allocated.toJson());
        }
    }

}
