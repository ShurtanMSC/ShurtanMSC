package akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.command.manager.CmdComplete;
import akka.command.worker.CmdTaskCalculate;
import akka.command.Command;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import uz.neft.controller.opc.OpcService;
import uz.neft.dto.fake.FakeService;
import uz.neft.dto.fake.FakeUppg;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.ForecastGas;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.enums.WellStatus;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.service.action.WellActionService;
import java.util.List;
import java.util.Optional;


public class Worker extends AbstractBehavior<Command> {


    public Worker(ActorContext<Command> context) {
        super(context);
    }


    public static Behavior<Command> create(){
        return Behaviors.setup(Worker::new);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(CmdTaskCalculate.class,command->{
                    System.out.println(getContext().getSelf());
                    checkWells(command.getSpringContext(),command.getCpAction(),command.getSender());
                    command.getSender().tell(new CmdComplete(getContext().getSelf(),null));
                    return Behaviors.same();
                })
                .build();

    }



    public static void checkWells(ApplicationContext springContext, CollectionPointAction cpAction,ActorRef<Command> sender){
        double expendCp = 0;
        try {
            List<Well> wellList = springContext.getBean(WellRepository.class).findAllByCollectionPointOrderByIdAsc(cpAction.getCollectionPoint());
            for (Well well : wellList) {
                Optional<WellAction> action = springContext.getBean(WellActionRepository.class).findFirstByWellOrderByCreatedAtDesc(well);
                if (action.isPresent()) {
                    if (action.get().getPressure() < cpAction.getPressure()) {
                        action.get().setExpend(0);
                        if (action.get().getStatus() == WellStatus.IN_WORK)
                            action.get().setStatus(WellStatus.IN_IDLE);
                    } else {
                        if (action.get().getStatus() == WellStatus.IN_IDLE)
                            action.get().setStatus(WellStatus.IN_WORK);
                        double expend = springContext.getBean(WellActionService.class).expend(action.get().getTemperature(), action.get().getPressure(), cpAction.getCollectionPoint().getUppg().getMiningSystem());
                        action.get().setAverage_expend(expend);
                        expendCp += action.get().getExpend();
                    }
                    springContext.getBean(WellActionRepository.class).save(action.get());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        springContext.getBean(Logger.class).info("Expend "+cpAction.getCollectionPoint().getName()+" = "+expendCp);
        System.out.println("Expend "+cpAction.getCollectionPoint().getName()+" = "+expendCp);
        cpAction.setExpend(expendCp);
        springContext.getBean(CollectionPointActionRepository.class).save(cpAction);
    }
}
