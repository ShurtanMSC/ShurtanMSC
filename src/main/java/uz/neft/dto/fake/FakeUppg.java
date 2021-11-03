package uz.neft.dto.fake;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FakeUppg {

    private List<FakeFlowMeter> flowMeters;

    private double nakoplenniy_obyom;
    private double nakoplenniy_obyom_s_nachalo_sutok;
    private double nakoplenniy_obyom_za_vchera;
    private double nakoplenniy_obyom_s_nachalo_mesyach;
    private double nakoplenniy_obyom_za_pered_mesyach;
    private double nakoplenniy_obyom_perepad_davleniya;
    private double davleniya;
    private double temperatura;
    private double rasxod;


//    public FakeUppg getUppg1(String url,String username,String password){
//        try {
//            Connection connection=null;
//            Statement statement=null;
//            ResultSet resultSet=null;
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            connection= DriverManager.getConnection(url,username,password);
//            statement=connection.createStatement();
//            resultSet=statement.executeQuery("SELECT [ID]" +
//                    "     ,[Val]" +
//                    "FROM dbo.Data");
//
//            if (resultSet.next()){
//                System.out.println(resultSet.getString(1));
//                System.out.println(resultSet.getString(2));
//                System.out.println(resultSet.getString(3));
//                System.out.println(resultSet.getString(4));
//                System.out.println(resultSet.getString(5));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return new FakeUppg();
//        }
//    }
//
//    public FakeUppg getUppg2(String url,String username,String password){
//        try {
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return new FakeUppg();
//        }
//    }


}
