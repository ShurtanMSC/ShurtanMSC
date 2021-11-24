package uz.neft.service;

import akka.actor.typed.ActorSystem;
import akka.command.manager.CmdCalculate;
import akka.command.manager.CmdStart;
import akka.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.fake.FakeUppg;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.ForecastGas;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.entity.action.UppgAction;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;

import java.sql.Timestamp;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
