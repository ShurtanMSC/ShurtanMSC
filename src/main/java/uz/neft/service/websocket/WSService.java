package uz.neft.service.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
//    private final NotificationService notificationService;
    public WSService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
//        this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message) {
        System.out.println("JALAB2");
        //        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages", message);
    }


//    public void notifyUser(final String id,final String message) {
//        ResponseMessage response = new ResponseMessage(message);
//        notificationService.sendPrivateNotification(id);
//        messagingTemplate.convertAndSendToUser(id,"/topic/private-messages", response);
//    }

}
