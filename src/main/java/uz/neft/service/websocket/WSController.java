package uz.neft.service.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WSController {

    @Autowired
    private WSService wsService;

//    @PostMapping("/send-message")
//    public void sendMessage(@RequestBody final Message message) {
//        wsService.notifyFrontend(message.getContent());
//    }
//
//    @PostMapping("/send-private-message/{id}")
//    public void sendPrivateMessage(@PathVariable final String id,
//                                   @RequestBody final Message message) {
//        wsService.notifyUser(id,message.getContent());
//    }

}
