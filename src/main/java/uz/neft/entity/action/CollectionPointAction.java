package uz.neft.entity.action;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.User;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.lang.reflect.Type;
import java.sql.Timestamp;

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

    public Double getTemperatureOpc() {
        try {
            Gson gson = new Gson();
            HttpResponse<JsonNode> response;
            if (collectionPoint.getId() != 1) {
                response = Unirest.post(collectionPoint.getOpcServer().getUrl() + "/temperature")
                        .header("Content-Type", "application/json")
                        .body(collectionPoint.jsonRequestBodyTemperature())
                        .asJson();
            } else {
                response = Unirest.post(collectionPoint.getOpcServer().getUrl())
                        .header("Content-Type", "application/json")
                        .body(collectionPoint.jsonRequestBodyTemperature())
                        .asJson();
            }
//            System.out.println(response);
//            System.out.println(response.getStatus());
            if (response.getBody() != null) {
                if (response.getBody().isArray()) {
                    String[] a = gson.fromJson(String.valueOf(response.getBody()), (Type) String[].class);
                    return Double.valueOf(a[0]);
                }
                return 0.0;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public Double getPressureOpc() {
        try {

            Gson gson = new Gson();
            HttpResponse<JsonNode> response;
            if (collectionPoint.getId() != 1) {
                response = Unirest.post(collectionPoint.getOpcServer().getUrl() + "/pressure")
                        .header("Content-Type", "application/json")
                        .body(collectionPoint.jsonRequestBodyPressure())
                        .asJson();
            } else {
                response = Unirest.post(collectionPoint.getOpcServer().getUrl())
                        .header("Content-Type", "application/json")
                        .body(collectionPoint.jsonRequestBodyPressure())
                        .asJson();
            }

//            System.out.println(response);
//            System.out.println(response.getStatus());
            if (response.getBody() != null) {
                if (response.getBody().isArray()) {
                    String[] a = gson.fromJson(String.valueOf(response.getBody()), (Type) String[].class);
                    return Double.valueOf(a[0]);
                }
                return 0.0;
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }


//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;

    //    @LastModifiedDate
//    @Column(nullable = false)
    @CreationTimestamp
    @Column(nullable = false, updatable = true)
    private Timestamp modified;

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

}
