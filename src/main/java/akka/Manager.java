package akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.command.manager.*;
import akka.command.worker.CmdTaskCalculate;
import akka.command.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import uz.neft.controller.opc.OpcService;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.service.Calculator;
import uz.neft.service.action.WellActionService;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Manager extends AbstractBehavior<Command> {

    @Value("${akka.worker.count}")
    private int workerCount=100;
    private final Map<ActorRef<Command>,ActorState> workers=new HashMap<>();
//    private final Set<ActorRef<Command>> workers=new HashSet<>();
    private final ApplicationContext springContext;

    public Manager(ActorContext<Command> context,ApplicationContext springContext) {
        super(context);
        this.springContext=springContext;
        for (int i = 0; i <workerCount; i++) {
            ActorRef<Command> worker = getContext().spawn(Worker.create(), "worker-"+String.valueOf(workers.size()));
            workers.put(worker,ActorState.IDLE);
        }
    }



    @Override
    public Receive<Command> createReceive() {

        return newReceiveBuilder()
//                .onMessage(CmdStart.class,command->{
//
//                    for (ActorRef<Command> worker:workers.keySet()) {
//                        if (workers.get(worker).equals(ActorState.IDLE)){
//                            return Behaviors.same();
//                        }
//                    }
//
//                    return Behaviors.same();
//                })

//                .onMessage(CmdUppg.class,command->{
//
//                    ActorRef<Command> worker = order();
//                    if (worker!=null){
//                        worker.tell(new CmdTaskUppg(getContext().getSelf(),springContext,command.getUppgs()));
//                    }else {
//                        return Behaviors.withTimers(timer->{
//                            timer.startSingleTimer(new CmdUppg(command.getUppgs()), Duration.ofMillis(1000));
//                            return Behaviors.same();
//                        });
//                    }
//                    return Behaviors.same();
//                })
//
//
//
//
//
//                .onMessage(CmdCpAll.class, command->{
//                    for (int i = 0; i <command.getCollectionPointList().size() ; i++) {
//                        ActorRef<Command> worker = order();
//                        if (worker!=null){
//                            CollectionPoint p = command.getCollectionPointList().get(i);
////                            worker.tell(new CmdTaskCp(getContext().getSelf(),springContext,command.getCollectionPointList().get(i)));
//                            CollectionPointAction action = getValues(springContext, command.getCollectionPointList().get(i));
//                            worker.tell(
//                                    new CmdTaskCp(getContext().getSelf(),
//                                    springContext,
//                                    command.getCollectionPointList().get(i),
//                                    action.getTemperature(),
//                                    action.getPressure()));
//                        }
//                        else {
//                            int finalI = i;
//                            return Behaviors.withTimers(timer->{
//                                timer.startSingleTimer(new CmdCpAll(command.getCollectionPointList().subList(finalI,command.getCollectionPointList().size())), Duration.ofMillis(1000));
//                                return Behaviors.same();
//                            });
//                        }
//                    }
//                    return Behaviors.same();
//                })
//

                .onMessage(CmdCalculate.class,command->{
                    ActorRef<Command> worker = order();
                    if (worker!=null){
                        workers.put(worker,ActorState.ACTIVE);
                        worker.tell(new CmdTaskCalculate(getContext().getSelf(),springContext,command.getCpAction()));
                    }
                    else {
                        return Behaviors.withTimers(timer->{
                            timer.startSingleTimer(new CmdCalculate(command.getCpAction()),Duration.ofMillis(1000));
                            return Behaviors.same();
                        });
                    }

                    return Behaviors.same();
                })

                .onMessage(CmdComplete.class,command->{
                        workers.put(command.getWorker(),ActorState.IDLE);
                        return Behaviors.same();
                })


                .build();
    }




    public ActorRef<Command> order(){
//        System.out.println("SIZE = "+workers.size());
        for (ActorRef<Command> worker:workers.keySet()) {
            if (workers.get(worker).equals(ActorState.IDLE)) return worker;
        }
        return null;
    }

}
