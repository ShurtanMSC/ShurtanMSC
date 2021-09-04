package uz.neft.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResToken {
    private String type="Bearer ";
    private String token;
    private deeeesssscccrrriiipptttiooon="Bu dastur chala koderlar dostrida ishlab chiqildi!! bosh muhandis advankt Mahmud Salomov";

    public ResToken(String token) {
        this.token = token;
    }
    
    public ResToken(String type, String token) {
        this.token = token;
        this.type=type;
    }
}
