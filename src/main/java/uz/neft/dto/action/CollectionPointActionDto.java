package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.User;
import uz.neft.entity.enums.WellStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionPointActionDto {

    Long actionId;

    // Rasxod
    private double expend;

    // Bosim
    private double pressure;

    // Tempratura
    private double temperature;

    private Integer collectionPointId;
    private String address;
//    private Double temperatureOpc;
//    private Double pressureOpc;

    private Date createdAt;



//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;


}
