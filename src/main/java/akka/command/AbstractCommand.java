package akka.command;

import akka.actor.typed.ActorRef;
import akka.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationContext;

@AllArgsConstructor
@Getter
public abstract class AbstractCommand implements Command {
    private ActorRef<Command> sender;
    private ApplicationContext springContext;
}
