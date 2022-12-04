package uz.neft.service.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification() {
//        ResponseMessage message = new ResponseMessage("Global notification");
        String message = "Test";
        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

//    public void sendPrivateNotification(final String id) {
//        ResponseMessage message = new ResponseMessage("Private notification");
//        messagingTemplate.convertAndSendToUser(id,"/topic/private-notifications", message);
//    }



}
