package uz.neft.service.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import uz.neft.dto.CollectionPointDto;
import uz.neft.dto.action.CollectionPointActionDto;
import uz.neft.dto.action.ObjectWithActionsDto;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private NotificationService notificationService;




    @MessageMapping("/message")
    @SendTo("/topic/messages")
//    public ResponseMessage getMessage(final Message message) throws InterruptedException {
    public List<ObjectWithActionsDto> getMessage() throws InterruptedException {
        System.out.println("JALAB");
//        Thread.sleep(1000); // simulated delay
//        return new ResponseMessage(message.getContent());
        notificationService.sendGlobalNotification();
//        return new ResponseMessage(HtmlUtils.htmlEscape(message.getContent()));
        return temp();
    }

//    @MessageMapping("/private-message")
//    @SendToUser("/topic/private-messages")
//    public ResponseMessage getPrivateMessage(final Message message,
//                                             final Principal principal) throws InterruptedException {
//        System.out.println(principal);
//        System.out.println(principal.getName());
////        Thread.sleep(1000); // simulated delay
////        return new ResponseMessage(message.getContent());
//        notificationService.sendPrivateNotification(principal.getName());
//        return new ResponseMessage(HtmlUtils.htmlEscape("Private message to user "+principal.getName()+" : "+message.getContent()));
//    }

    public List<ObjectWithActionsDto> temp(){
        List<ObjectWithActionsDto> all=new ArrayList<>();
        for (int i = 0; i <21 ; i++) {
            CollectionPointDto collectionPointDto = CollectionPointDto
                    .builder()
                    .id(i)
                    .active(true)
                    .name("SP - " + i)
                    .uppgId(1)
                    .build();

            CollectionPointActionDto actionDto=CollectionPointActionDto
                    .builder()
                    .door(true)
                    .actionId((long) i)
                    .collectionPointId(i)
                    .expend(i*100)
                    .pressure(i)
                    .temperature(i)
                    .build();
            all.add(new ObjectWithActionsDto(collectionPointDto,actionDto));

        }
        return all;
    }

}
