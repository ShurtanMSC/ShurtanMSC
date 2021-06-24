package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;

@Service
public class UppgService {

    @Autowired
    UppgRepository uppgRepository;


}
