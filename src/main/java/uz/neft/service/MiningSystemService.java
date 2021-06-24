package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;

@Service
public class MiningSystemService {

    @Autowired
    MiningSystemRepository miningSystemRepository;


}
