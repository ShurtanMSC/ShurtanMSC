package uz.neft.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.service.action.CollectionPointActionService;


@Component
@Service
public class SchulzTasks {
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


    @Scheduled(fixedRate = 5000)
    public void transform() throws InterruptedException {
        System.out.println("TASK");
        System.out.println();
        collectionPointActionService.setAll(1);
    }



}
