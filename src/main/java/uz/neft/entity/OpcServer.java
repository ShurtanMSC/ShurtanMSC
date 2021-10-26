package uz.neft.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OpcServer extends AbsEntityInteger {

    @NotBlank(message = "Opc Server name cannot be blank")
    private String name;
    @NotBlank(message = "Opc Server description cannot be blank")
    private String description;
    @NotBlank(message = "Opc Server address cannot be blank")
    private String address;
    @NotBlank(message = "Opc Server url cannot be blank")
    private String url;
}
