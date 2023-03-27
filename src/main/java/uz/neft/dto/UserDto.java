package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Dto{
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String fio;
    private Short roleId;
    private String roleName;
    private boolean active=true;
}
