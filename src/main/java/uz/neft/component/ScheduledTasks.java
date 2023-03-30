package uz.neft.component;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.neft.backup.Test;
import uz.neft.dto.fake.FakeService;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.service.AkkaService;
import uz.neft.service.action.MiningSystemActionService;
import uz.neft.service.action.WellActionService;
import uz.neft.service.websocket.MessageController;
import uz.neft.service.websocket.WSService;
import uz.neft.service.action.CollectionPointActionService;



@Component
@Service
public class ScheduledTasks {
    @Autowired
    private CollectionPointRepository collectionPointRepository;
    @Autowired
    private CollectionPointActionRepository collectionActionRepository;
    @Autowired
    private CollectionPointActionService collectionPointActionService;
    @Autowired
    private MiningSystemActionService miningSystemActionService;
    @Autowired
    private WellActionService wellActionService;
    @Autowired
    private WellRepository wellRepository;
    @Autowired
    private AkkaService akkaService;
    @Autowired
    private Test test;
    @Autowired
    Logger logger;
    @Autowired
    private WSService wsService;
    @Autowired
    private FakeService fakeService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedDelay = 500)
    public void transform() throws InterruptedException {
        //System.out.println("TASK");
//        messageController.getMessage();
        logger.info("TASK");
        System.out.println();
        collectionPointActionService.setAll(1);
        messagingTemplate.convertAndSend("/topic/collection-point-action", collectionPointActionService.getAllWithActionsByMiningSystemWs(1));
        messagingTemplate.convertAndSend("/topic/well-action", wellActionService.getAllWithActionByMiningSystemWs(1));
        messagingTemplate.convertAndSend("/topic/uppg-action", fakeService.all());
        messagingTemplate.convertAndSend("/topic/mining-system-action", miningSystemActionService.allWithActionsWs());
    }

//    @Scheduled(fixedDelay = 86400000)
    @Scheduled(cron = "0 0 12 * * ?")
    public void backup() {
        System.out.println("Backup started");
        logger.info("Backup started");
        test.backup();
    }



}
