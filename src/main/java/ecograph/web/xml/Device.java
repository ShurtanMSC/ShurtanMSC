package ecograph.web.xml;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    public int vstslvl;
    public Param param;
    public String vtime;
    public String id;
    public List<String> tag;
    public double v1;
    public int vstslvl1;
    public String man;
    public String type;
    public String u1;
    public String hlsts1;
}
