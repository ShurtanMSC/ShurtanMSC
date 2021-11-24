package akka.command.manager;

import akka.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.neft.entity.action.CollectionPointAction;

@Getter
@AllArgsConstructor
public class CmdCalculate implements Command {
    private CollectionPointAction cpAction;
}
