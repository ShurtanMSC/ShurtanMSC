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
@Entity(name = "collection_point")
@Builder
public class CollectionPoint extends AbsEntityInteger {

    @NotNull
    private String name;

    private String temperatureUnit;
    private String pressureUnit;

    @ManyToOne
    private Uppg uppg;

    @ManyToOne
    private OpcServer opcServer;

    public String jsonRequestBodyTemperature(){
        if (opcServer!=null){
            return "{\n" +
                    "    \"server\":\""+opcServer.getAddress()+"\",\n" +
                    "    \"unit\":\""+temperatureUnit+"\"\n" +
                    "}";
        }
        return "";
    }

    public String jsonRequestBodyPressure(){
        if (opcServer!=null){
            return "{\r\n    \"server\":\""+opcServer.getAddress()+"\",\r\n    \"unit\":\""+pressureUnit+"\"\r\n}";
        }
        return "";
    }


}
