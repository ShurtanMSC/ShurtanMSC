package uz.neft.component;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.neft.backup.Test;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.service.AkkaService;
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
    private WellActionRepository wellActionRepository;
    @Autowired
    private WellRepository wellRepository;
    @Autowired
    private AkkaService akkaService;
    @Autowired
    private Test test;
    @Autowired
    Logger logger;


    @Scheduled(fixedDelay = 500)
    public void transform() throws InterruptedException {
        //System.out.println("TASK");
        logger.info("TASK");
        System.out.println();
        collectionPointActionService.setAll(1);
    }

    @Scheduled(fixedDelay = 86400000)
    public void backup() throws InterruptedException {
        System.out.println("Backup started");
        logger.info("Backup started");
        test.backup();
    }



}
