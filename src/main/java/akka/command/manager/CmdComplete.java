package akka.command.manager;

import akka.actor.typed.ActorRef;
import akka.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class CmdComplete implements Command {
    private ActorRef<Command> worker;
    private Object object;
}
