package uz.neft.entity;

import lombok.*;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "uppg")
@Builder
public class Uppg extends AbsEntityInteger {

    @NotNull
    private String name;

    @ManyToOne
    private MiningSystem miningSystem;


}
