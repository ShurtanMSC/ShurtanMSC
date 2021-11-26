package akka;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.command.manager.CmdStart;
import akka.command.Command;

public class Main {
    public static void main(String[] args) {
        ActorSystem<Command> actorSystem=ActorSystem.create(Behaviors.setup(ctx -> new Manager(ctx,null)),"Manager");
//        long start=System.currentTimeMillis();
        actorSystem.tell(new CmdStart());
//        long end=System.currentTimeMillis();
//        System.out.println();
    }
}
