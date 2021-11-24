package uz.neft.config;

import akka.Manager;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AkkaConfig {

    @Autowired
    private ApplicationContext context;

//    @Bean
//    public Behavior<Command> setup() {
//        return Behaviors.setup(ctx -> new Manager(ctx,context));
//    }


    @Bean
    public ActorSystem<Command> setupSystem(){
        return ActorSystem.create(Behaviors.setup(ctx -> new Manager(ctx,context)),"Manager");
    }

}
