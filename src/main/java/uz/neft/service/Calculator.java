package uz.neft.service;

import org.springframework.stereotype.Service;

@Service
public class Calculator {

    // The average estimated production rate of wells in the Shurtan field (D SCW), 1 × 103 m3,
    // is determined by the formula

    // Средний расчетный дебит скважин месторождения Шуртан (D СКВ), 1×103 m3,
    // определяется по формуле

    // Sho'rtan konidagi (D SCW) quduqlarning o'rtacha taxminiy qazib olish darajasi, 1 × 103 m3,
    // formula bo'yicha aniqlanadi

    public static double averageProductionRate(
                                        double C,
                                        double  P_u,
                                        double b,
                                        double p_otn,
                                        double Z,
                                        double T_abs
    ){
        double D=0;
        D=(C*P_u*b)/(0.1013*Math.sqrt(p_otn*T_abs*Z));
        return D;
    }





    // The correction factor depending on the reduced pressure and temperature (δ)
    // is determined graphically

    //  Поправочный коэффициент, зависящий от приведенных давления и температуры (δ),
    //  определяется графически

    // Kamaytirilgan bosim va haroratga (δ) bog'liq holda tuzatish koeffitsienti,
    // funksiya grafigi jihatdan aniqlanadi

    //δ = -0,517 + 1,618ТПР – 0,448Т2ПР + РПР [1,204 - 1,231ТПР + 0,322Т2ПР]+
    //+ Р2ПР [-0,101 + 0,110ТПР - 0,02958Т2ПР]
    //РПР №154 = 1,9 / 4,7743 = 0,398
    //ТПР №154 = 320 / 202,7112 = 1,579
    public static double correctionFactor(double T_pr, double P_pr){
        double b=-0.517+1.618*T_pr-0.448*T_pr*T_pr+
                P_pr*(1.204-1.231*T_pr+0.322*T_pr*T_pr)+
                P_pr*P_pr*(-0.101+0.110*T_pr-0.02958*T_pr*T_pr);
        return b;
    }







    // The gas supercompressibility factor is determined graphically by the dependence of the natural gas supercompressibility factor on the reduced pressure and temperature
    // or by the formula:

    //  Коэффициент сверхсжимаемости газа определяется графически по зависимости коэффициента сверхсжимаемости природного газа от приведенных давления и температуры
    //  или по формуле:

    // Gazning superkompressivlik koeffitsienti tabiiy gaz superkompressivlik koeffitsientining pasaytirilgan bosim va haroratga bog'liqligi bilan grafik jihatdan aniqlanadi
    // yoki quyidagi formula bo'yicha:


    //Z = 1 – [(Ру – 6) · (0,345·10-2 · ∆в – 0,446·10-3) + 0,015] · [1,3 – 0,0144 · (Ту – 283,2)]
    public static double superCompressFactor(double P, double T, double p_otn){
        double Z=1-((P-6)*(0.345*0.01*p_otn - 0.446*0.001)+0.015)*(1.3-0.0144*(T - 283.2));
        return Z;
    }





}
