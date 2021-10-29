package uz.neft.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.neft.entity.enums.OpcServerType;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    public OpcServer(String name, String description, String address, String url) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.url = url;
    }
    @Enumerated(EnumType.STRING)
    private OpcServerType type;
}
