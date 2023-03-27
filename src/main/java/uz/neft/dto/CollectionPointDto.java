package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionPointDto implements Dto{

    private Integer id;
    private String name;
    private Integer uppgId;
    private boolean active;
}
