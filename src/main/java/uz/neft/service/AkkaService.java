package uz.neft.service;

import akka.actor.typed.ActorSystem;
import akka.command.Command;
import akka.command.manager.CmdCalculate;
import akka.command.manager.CmdStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;

@Service
public class AkkaService {
    @Autowired
    private ActorSystem<Command> actorSystem;
    @Autowired
    private MiningSystemRepository miningSystemRepository;
    @Autowired
    private UppgRepository uppgRepository;
    @Autowired
    private CollectionPointRepository collectionPointRepository;

    public void test(){
        actorSystem.tell(new CmdStart());
    }

    public void calculate(CollectionPointAction cpAction){
        actorSystem.tell(new CmdCalculate(cpAction));
    }


}
