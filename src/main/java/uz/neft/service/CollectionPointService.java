package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.repository.CollectionPointRepository;

@Service
public class CollectionPointService {

    @Autowired
    CollectionPointRepository collectionPointRepository;


}
