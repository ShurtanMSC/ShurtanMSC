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
    private double perepad_davleniya;
    private double davleniya;
    private double temperatura;
    private double rasxod;


    public void sum(boolean a){
        int t=0;
        int p=0;
        int dp=0;
        int amountTemperature=0;
        int amountPressure=0;
        int amountPerepadPressure=0;
        if (a){

            for (FakeFlowMeter flowMeter : flowMeters) {

                this.nakoplenniy_obyom +=flowMeter.fakeFlowMeterElements.get(0).getV();
                this.nakoplenniy_obyom_s_nachalo_sutok +=flowMeter.fakeFlowMeterElements.get(1).getV();
                this.nakoplenniy_obyom_za_vchera +=flowMeter.fakeFlowMeterElements.get(2).getV();
                this.nakoplenniy_obyom_s_nachalo_mesyach +=flowMeter.fakeFlowMeterElements.get(3).getV();
                this.nakoplenniy_obyom_za_pered_mesyach +=flowMeter.fakeFlowMeterElements.get(4).getV();

                if (!(flowMeter.fakeFlowMeterElements.get(5).getV()<=0)){
                    amountPerepadPressure+=flowMeter.fakeFlowMeterElements.get(5).getV();
                    dp++;
                }
                if (!(flowMeter.fakeFlowMeterElements.get(7).getV()<=0)){
                    amountTemperature+=flowMeter.fakeFlowMeterElements.get(7).getV();
                    t++;
                }
                if (!(flowMeter.fakeFlowMeterElements.get(6).getV()<=0)){
                    amountPressure+=flowMeter.fakeFlowMeterElements.get(6).getV();
                    p++;
                }
            }


        }else {
            for (FakeFlowMeter flowMeter : flowMeters) {

                this.nakoplenniy_obyom +=flowMeter.fakeFlowMeterElements.get(0).getV();
                this.nakoplenniy_obyom_s_nachalo_sutok +=flowMeter.fakeFlowMeterElements.get(6).getV();
                this.nakoplenniy_obyom_za_vchera +=flowMeter.fakeFlowMeterElements.get(7).getV();
                this.nakoplenniy_obyom_s_nachalo_mesyach +=flowMeter.fakeFlowMeterElements.get(8).getV();
                this.nakoplenniy_obyom_za_pered_mesyach +=flowMeter.fakeFlowMeterElements.get(9).getV();


                if (!(flowMeter.fakeFlowMeterElements.get(1).getV()<=0)){
                    amountPerepadPressure+=flowMeter.fakeFlowMeterElements.get(1).getV();
                    dp++;
                }
                if (!(flowMeter.fakeFlowMeterElements.get(3).getV()<=0)){
                    amountTemperature+=flowMeter.fakeFlowMeterElements.get(3).getV();
                    t++;
                }
                if (!(flowMeter.fakeFlowMeterElements.get(2).getV()<=0)){
                    amountPressure+=flowMeter.fakeFlowMeterElements.get(2).getV();
                    p++;
                }


            }

        }
        if (dp!=0){
            this.perepad_davleniya=(double) amountPerepadPressure/dp;
        }
        if (t!=0){
            this.temperatura=(double) amountTemperature/t;
        }
        if (p!=0){
            this.davleniya=(double) amountPressure/p;
        }

    }

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
