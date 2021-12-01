package uz.neft.service;

import org.springframework.stereotype.Service;

@Service
public class PrognozCalculatorTest {

    /**
     * Prognoz uchun Formulalar ketma-ketligi
     **/

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 1.	Из левой таблицы берется средное значение устьевого давления в конце месяца и по формуле (2)
    // считается пластовое давление в конце текущего месяца (текущий месяц – это месяц когда есть фактические замерные данные)
    // Chapdagi jadvaldan oyning oxirida quduqning o'rtacha bosimi olinadi va (2) formula bo'yicha,
    // rezervuar bosimi joriy oyning oxirida hisoblanadi (joriy oy haqiqiy o'lchangan oy hisoblanadi) ma'lumotlar)
    public static double reservoirPressure(double P_u, double S,
                                           double a, double Q_sut, double b, double teta, int N) {

        //N – количество действующих скважин за i-ый месяц; N - i-oy uchun ishlaydigan quduqlar soni;

        //Q_(ср.сут)
        double Q_sr_sut = 0;
        //Q_(ср.сут) – средний фактический суточный отбор одной скважины в конце месяца;
        //bu erda    Q_ (o'rtacha kun) - oy oxirida bitta quduqning o'rtacha kunlik haqiqiy ishlab chiqarishi;

        double sum = 0;

        for (int i = 0; i < N - 1; i++) {
            sum = sum + Q_sut;
        }

        Q_sr_sut = sum / N;

        //P_(пл)(i)
        double P_pl = 0;

        P_pl = Math.sqrt(P_u * P_u * Math.exp(2 * S) + a * Q_sr_sut + (b + teta) * Q_sr_sut * Q_sr_sut);

        //Ру(i) – среднее устьевое давление в конце текущего месяца.
        // Ru (i) - joriy oyning oxirida quduqning o'rtacha bosimi.

        // S - Сопротивление жидких компонентов по НКТ
        //Quvurlar bo'ylab suyuqlik tarkibiy qismlarining qarshiligi

        // a - Коэффициент фильтрационного сопротивления А
        //Filtrlash qarshilik koeffitsienti A

        // b - Коэффициент фильтрационного сопротивления В
        //Filtrlash qarshilik koeffitsienti B

        return P_pl;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 2.	Определяется средный дебит одной скважины для (i+1) прогнозного месяца:
    // Bitta quduqning o'rtacha ishlab chiqarish darajasi (i + 1) prognoz oy uchun aniqlanadi:

    public static double averageForecastGasForOneWellNextMonth(double a, double b, double P_pl, double delta_P) {

        // Q_(ср.скв(i+1))=(-a^2+√(a^2+4b(2Р_пл(i)∆Р-∆Р^2)))/2b

        double Q_sr_well = 0;

        Q_sr_well = (-a * a + Math.sqrt(a * a + 4 * b * (2 * P_pl * delta_P - delta_P * delta_P))) / (2 * b);

        return Q_sr_well;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 3.	Определяется прогнозный отбор газа за (i+1)-ый месяц (полученное значение отражается на диаграмме за прогнозируемого месяца):
    // (i + 1) oy uchun bashorat qilingan gaz ishlab chiqarish aniqlanadi (natijadagi qiymat prognoz oy uchun diagrammada aks ettirilgan):

    public static double forecastGasForNextMonth(double Q_sr_well, int N, int t) {

        //Q_(отб.газа месяц(i+1))=Q_(ср.скв(i+1))*N_((i+1))*t_((i+1))

        // где N_((i+1)) – количество действующих скважин за (i+1) периода;
        // bu erda N _ (i + 1) - (i + 1) davr uchun ishlaydigan quduqlar soni;

        // t_((i+1)) – Количество дней в месяце (i+1) периода.
        // t _ ((i + 1)) - (i + 1) davr oyidagi kunlar soni.

        // Q_(отб.газа месяц(i+1))
        double Q_otb_gaz_next = 0;

        Q_otb_gaz_next = Q_sr_well * N * t;

        return Q_otb_gaz_next;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 4.	Определяется прогнозный отбор конденсата за (i+1)-ый месяц (полученное значение отражается на диаграмме за прогнозируемого месяца):
    // (i + 1) oy uchun prognoz kondensat olinishi aniqlanadi (natijadagi qiymat prognoz oy uchun diagrammada aks ettirilgan):

    public static double forecastCondensateForNextMonth(double Q_otb_gaz_next, double P) {

        // Q_(отб.конденсата месяц(i+1))=Q_(отб.газа месяц(i+1))*П_((i+1))

        // где П_((i+1)) – потенциальное содержание конденсата при Рпл(i+1) определяется по
        // функции изотерма конденсации (функция для каждого месторождения своя).
        // Bu erda P _ ((i + 1)) - Rpl (i + 1) da potentsial kondensat tarkibi
        // kondensatsiya izotermasi funktsiyasi bilan aniqlanadi (funktsiya har bir maydon uchun har xil).

        // Q_(отб.конденсата месяц(i+1))
        double Q_otb_condensate_next = 0;

        Q_otb_condensate_next = Q_otb_gaz_next * P;

        return Q_otb_condensate_next;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 5.	Определяется отбор газа с начала разработки, включая прогнозного отбора за (i+1)-ый месяц:
    // Ishlanish boshidan gaz qazib olish, shu jumladan (i + 1) - oy uchun prognoz qilingan gaz ishlab chiqarish aniqlanadi:

    public static double forecastForNextMonth(double Q_otb, double Q_otb_gaz_next) {

        // Q_(отб(i+1))=Q_(отб(i))+Q_(отб.газа месяц(i+1))

        double Q_otb_next = 0;

        Q_otb_next = Q_otb + Q_otb_gaz_next;

        return Q_otb_next;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 6.	Определяется коэффициент сверсжимаемости газа для (i+1) периода:
    // Gazning siqilish koeffitsienti (i + 1) davr uchun aniqlanadi:

    public static double coefficientOfGasCompressibilityForNextMonth(double T_pl, double T_kr, double P_pl_next, double P_kr) {

        // Z_((i+1))=〖(0.4 log_10⁡〖Т_пл/Т_кр 〗+0,1)〗^(Р_(пл(i+1))/Р_кр )+0,73 Р_(пл(i+1))/Р_кр

        //где Тпл, Ткр, Ркр – берется из таблицы (с правой верхней таблицы).
        //bu yerda Tpl, Tkr, Rkr - jadvaldan olinadi (yuqori o'ng jadvaldan).

        // Z_((i+1))
        double Z_next = 0;

        Z_next = Math.pow((0.4 * Math.log10(T_pl / T_kr) + 0.1), (P_pl_next / P_kr)) + 0.73 * P_pl_next / P_kr;

        return Z_next;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //  7.	С помощю метода итерации определяется пластовое давление в конце (i+1) периода:
    //  Iteratsiya usulidan foydalanib, rezervuar bosimi (i + 1) davr oxirida aniqlanadi:

    public static double reservoirPressureForNextMonth(double P_n_pl, double Z_n, double Q_otb_next, double Q_n_z, double Z_next) {

        // P_(пл(i+1))=Р_(н.пл)/z_н  (1-Q_(отб(i+1))/Q_(н.з) ) z_((i+1))

        // где Рпл.н, Zн, Qн.з – берется из таблицы (с правой верхней таблицы).
        // bu yerda Rpl.n, Zn, Qn.z - jadvaldan olinadi (yuqori o'ngdagi jadvaldan).

        // P_(пл(i+1))
        double P_pl_next = 0;

        P_pl_next = P_n_pl / Z_n * (1 - Q_otb_next / Q_n_z) * Z_next;

        return P_pl_next;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


}