package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.repository.MiningSystemRepository;

import java.util.Collections;
import java.util.List;

@Service
public class Calculator {


//    public static double generalCalculate(double P_u, double T_u) {
//        double C;
//        double delta;
//        double p_otn;
//        double Z;
//        double RO = 0;
//        double T_pr;
//        double P_pr;
//
//
//
//    }

    // Средний расчетный дебит скважин месторождения Шуртан (D СКВ), 1×103 m3,
    // определяется по формуле

    // Sho'rtan konidagi (D SCW) quduqlarning o'rtacha taxminiy qazib olish darajasi, 1 × 103 m3,
    // formula bo'yicha aniqlanadi


    /**
     * Formulalar ketma-ketligi
     **/
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static double pseudoCriticalPressure(List<Double> molarFractions,
                                                List<Double> criticalPressure
    ) {
        if (molarFractions.size() != criticalPressure.size()) return 0;
//        if (molarFractions.stream().mapToDouble(n->n).sum()!=100) return 0;

        double s = 0;

        for (int i = 0; i < molarFractions.size(); i++) {
            s += molarFractions.get(i) * criticalPressure.get(i);
        }
        return s;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    public static double pseudoCriticalTempereture(List<Double> molarFractions,
                                                   List<Double> criticalTemperature
    ) {
        if (molarFractions.size() != criticalTemperature.size()) return 0;
//        if (molarFractions.stream().mapToDouble(n->n).sum()!=100) return 0;

        double s = 0;

        for (int i = 0; i < molarFractions.size(); i++) {
            s += molarFractions.get(i) * criticalTemperature.get(i);
        }
        return s;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double reducedPressure(double P_u, double P_pkr) {
        double P_pr = 0;

        P_pr = P_u / P_pkr;

        return P_pr;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double reducedTemperature(double T_u, double T_pkr) {
        double T_pr = 0;

        T_pr = T_u / T_pkr;

        return T_pr;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //  Коэффициент сверхсжимаемости газа определяется графически по зависимости коэффициента сверхсжимаемости природного газа от приведенных давления и температуры
    //  или по формуле:

    // Gazning superkompressivlik koeffitsienti tabiiy gaz superkompressivlik koeffitsientining pasaytirilgan bosim va haroratga bog'liqligi bilan grafik jihatdan aniqlanadi
    // yoki quyidagi formula bo'yicha:

    //Z = 1 – [(Ру – 6) · (0,345·10-2 · ∆в – 0,446·10-3) + 0,015] · [1,3 – 0,0144 · (Ту – 283,2)]
    public static double superCompressFactor(double P_u, double T_u, double p_otn) {
        double Z = 1 - ((P_u - 6) * (0.345 * 0.01 * p_otn - 0.446 * 0.001) + 0.015) * (1.3 - 0.0144 * (T_u - 283.2));
        return Z;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // ρ отн – относительная плотность газа (gazning nisbiy zichligi);
    public static double relativeDensity(double roGas, double roAir) {
        double RO = 0;
        RO = roGas / roAir;
        return RO;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //  Поправочный коэффициент, зависящий от приведенных давления и температуры (δ),
    //  определяется графически

    // Kamaytirilgan bosim va haroratga (δ) bog'liq holda tuzatish koeffitsienti,
    // funksiya grafigi jihatdan aniqlanadi

    //δ = -0,517 + 1,618ТПР – 0,448Т2ПР + РПР [1,204 - 1,231ТПР + 0,322Т2ПР]+
    //+ Р2ПР [-0,101 + 0,110ТПР - 0,02958Т2ПР]
    //РПР №154 = 1,9 / 4,7743 = 0,398
    //ТПР №154 = 320 / 202,7112 = 1,579
    public static double correctionFactor_P_T(double T_pr, double P_pr) {
        double delta = -0.517 + 1.618 * T_pr - 0.448 * T_pr * T_pr +
                P_pr * (1.204 - 1.231 * T_pr + 0.322 * T_pr * T_pr) +
                P_pr * P_pr * (-0.101 + 0.110 * T_pr - 0.02958 * T_pr * T_pr);
        return delta;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // D_СКВ – средний расчетный дебит скважин месторождения Шуртан

    public static double averageProductionRate(
            double C,
            double P_u,
            double p_otn,
            double Z,
            double T_u
    ) {

        double delta = 0;
        double D_well = 0;
        D_well = (C * P_u * delta) / (0.1013 * Math.sqrt(p_otn * T_u * Z));
        return D_well;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Поправочный коэффициент (КГ), определяется как отношение среднего расчетного дебита к суммарному среднему расчетному дебиту скважин месторождения
    // Шуртан по формуле: Tuzatish koeffitsienti (КГ) quyidagicha formula bo'yicha Sho'rtan konidagi quduqlarni qazib olishning o'rtacha taxminiy stavkasiga nisbati sifatida aniqlanadi:

    // D_well_all = D_СКВ_общ – общий средний расчетный дебит месторождения Шуртан
    // (Sho'rtan konining o'rtacha qazib olish darajasi ), 1×1000 m3.

    public static double correctionFactor_K(double D_well, double D_well_all) {
        double K = 0;

        K = D_well / D_well_all;

        return K;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Дебит скважин месторождения Шуртан определяется по средним дебитам скважин при определенном диаметре штуцера и устьевых параметрах скважин.
    // Дебит скважин месторождения Шуртан (Q скв), 1×103 m3, определяется по формуле

    // RASXOD

    public static double Q_flowRateOfWell(double D_well_given, double K) {
        double Q = 0;

//        Q = D_well_given * K;

        Q = 8000000 * K;

        return Q;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


}
