package uz.neft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.template.AbsEntityInteger;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "uppg")
public class Uppg extends AbsEntityInteger {

    @NotNull
    private String name;

    @ManyToOne
    private MiningSystem miningSystem;


}
