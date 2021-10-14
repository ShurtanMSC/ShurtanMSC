package uz.neft.entity.action;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.User;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

//import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CollectionPointAction extends AbsEntityLong {

    // Bosim
    private double pressure;

    // Tempratura
    private double temperature;

    // Rasxod
    private double expend;

    @ManyToOne
    private User user;

    @ManyToOne
    private CollectionPoint collectionPoint;
    @Value("${opc.service.address}")
    protected String address;


    public Double getTemperatureOpc(){
        try {
            Gson gson=new Gson();
            HttpResponse<JsonNode> response = Unirest.post(collectionPoint.getOpcServer().getUrl()+"/temperature")
                    .header("Content-Type", "application/json")
                    .body(collectionPoint.jsonRequestBodyTemperature())
                    .asJson();
//            System.out.println(response);
//            System.out.println(response.getStatus());
            if (response.getBody()!=null){
                if (response.getBody().isArray()){
                    String[] a= gson.fromJson(String.valueOf(response.getBody()), (Type) String[].class);
                    return Double.valueOf(a[0]);
                }
                return 0.0;
            }
            return 0.0;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }



    public Double getPressureOpc(){
        try {

            Gson gson=new Gson();
            HttpResponse<JsonNode> response = Unirest.post(collectionPoint.getOpcServer().getUrl()+"/pressure")
                    .header("Content-Type", "application/json")
                    .body(collectionPoint.jsonRequestBodyPressure())
                    .asJson();
//            System.out.println(response);
//            System.out.println(response.getStatus());
            if (response.getBody()!=null){
                if (response.getBody().isArray()){
                    String[] a= gson.fromJson(String.valueOf(response.getBody()), (Type) String[].class);
                    return Double.valueOf(a[0]);
                }
                return 0.0;
            }
            return 0.0;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }



//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;


    @Override
    public String toString() {
        return "CollectionPointAction{" +
                "pressure=" + pressure +
                ", temperature=" + temperature +
                ", expend=" + expend +
                ", user=" + user +
                ", collectionPoint=" + collectionPoint +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CollectionPointAction that = (CollectionPointAction) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
