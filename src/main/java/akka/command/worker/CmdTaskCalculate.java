package akka.command.worker;

import akka.actor.typed.ActorRef;
import akka.command.AbstractCommand;
import akka.command.Command;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import uz.neft.entity.action.CollectionPointAction;

@Getter
public class CmdTaskCalculate extends AbstractCommand {
    private final CollectionPointAction cpAction;

    public CmdTaskCalculate(ActorRef<Command> sender, ApplicationContext springContext, CollectionPointAction cpAction) {
        super(sender, springContext);
        this.cpAction = cpAction;
    }
}
