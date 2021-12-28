package uz.neft.service;

import org.springframework.stereotype.Service;
import uz.neft.dto.ForecastDto;
import uz.neft.entity.ForecastGas;
import uz.neft.entity.MiningSystem;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.constants.ForecastGasRepository;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TESTForecastGasService {

    private ForecastGasRepository forecastGasRepository;
    private MiningSystemRepository miningSystemRepository;
    private final ForecastGasService forecastGasService;

    public TESTForecastGasService(ForecastGasRepository forecastGasRepository, MiningSystemRepository miningSystemRepository, ForecastGasService forecastGasService) {
        this.forecastGasRepository = forecastGasRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.forecastGasService = forecastGasService;
    }

    //Ру(i) – среднее устьевое давление в конце текущего месяца.
    // Ru (i) - oyning oxirida quduqning o'rtacha bosimi
    double P_u = 0.3;

    //Коэффициент фильтрационного сопротивления А
    //Filtrlash qarshilik koeffitsienti A
    double a = 0.1;

    //Коэффициент фильтрационного сопротивления В
    //Filtrlash qarshilik koeffitsienti B
    double b = 0.5;

    //Сопротивление жидких компонентов по НКТ
    //Quvurlar bo'ylab suyuqlik tarkibiy qismlarining qarshiligi
    double S = 0.8;

    //Сопротивление НКТ
    //Quvurlarga qarshilik
    double teta = 0.7;

    // Начальная пластовая температура
    // Rezervuarning dastlabki harorati
    double T_pl = 4;

    // Критическая температура газа
    // Kritik gaz harorati
    double T_kr = 10;

    // Критическое давление газа
    // Kritik gaz bosimi
    double P_kr = 10;

    // Начальное пластовое давление
    // Rezervuarning dastlabki bosimi
    double P_n_pl = 10;

    // Коэффициент сверсжимаемости газа в начале разработки месторождения
    // Konni o'zlashtirishning boshida gazning siqilish koeffitsienti
    double Z_n = 0.7;

    // Начальные запасы газа
    // Dastlabki gaz zahiralari
    double Q_n_z = 77;

    /**
     * Forecast - prognoz
     * Yangi oy uchun hisoblash va saqlash
     * metodi
     **/
    public List<ForecastGas> addNewForecast(Integer miningId) {

        //N – количество действующих скважин за i-ый месяц;
        // N - i-oy uchun ishlaydigan quduqlar soni;
        int N = 40;

        // ? ? ?
        double Q_sut = 5;

        // 1.	Из левой таблицы берется средное значение устьевого давления в конце месяца
        // и по формуле (2) считается пластовое давление в конце текущего месяца
        // (текущий месяц – это месяц когда есть фактические замерные данные)
        // Chapdagi jadvaldan oyning oxirida quduqning o'rtacha bosimi olinadi va (2) formula
        // bo'yicha, rezervuar bosimi joriy oyning oxirida hisoblanadi
        // (joriy oy haqiqiy o'lchangan oy hisoblanadi) ma'lumotlar)
        double P_pl = PrognozCalculatorTest.reservoirPressure(P_u, S, a, Q_sut, b, teta, N);

        // ? ? ?
        double delta_P = 2;

        // 2. Определяется средный дебит одной скважины для (i+1) прогнозного месяца:
        // Bitta quduqning o'rtacha ishlab chiqarish darajasi (i + 1) prognoz oy uchun aniqlanadi:
        double Q_sr_well = PrognozCalculatorTest.averageForecastGasForOneWellNextMonth(a, b, P_pl, delta_P);

        // t_((i+1)) – Количество дней в месяце (i+1) периода.
        // t _ ((i + 1)) - (i + 1) davr oyidagi kunlar soni.
        int t = 30;

        // 3.	Определяется прогнозный отбор газа за (i+1)-ый месяц (полученное значение отражается на диаграмме за прогнозируемого месяца):
        // (i + 1) oy uchun bashorat qilingan gaz ishlab chiqarish aniqlanadi (natijadagi qiymat prognoz oy uchun diagrammada aks ettirilgan):
        double Q_otb_gaz_next = PrognozCalculatorTest.forecastGasForNextMonth(Q_sr_well, N, t);

        //где П_((i+1)) – потенциальное содержание конденсата при Рпл(i+1) определяется по
        //функции изотерма конденсации (функция для каждого месторождения своя).
        //Bu erda P _ ((i + 1)) - Rpl (i + 1) da potentsial kondensat tarkibi
        // kondensatsiya izotermasi funktsiyasi bilan aniqlanadi
        // (funktsiya har bir maydon uchun har xil).
        double P = 0.5;

        // 4.	Определяется прогнозный отбор конденсата за (i+1)-ый месяц (полученное значение отражается на диаграмме за прогнозируемого месяца):
        //(i + 1) oy uchun prognoz kondensat olinishi aniqlanadi (natijadagi qiymat prognoz oy uchun diagrammada aks ettirilgan):
        double Q_otb_condensate_next = PrognozCalculatorTest.forecastCondensateForNextMonth(Q_otb_gaz_next, P);

        // ?  ? ?
        double Q_otb = 5;

        //5.	Определяется отбор газа с начала разработки, включая прогнозного отбора за (i+1)-ый месяц:
        //Ishlanish boshidan gaz qazib olish, shu jumladan (i + 1) - oy uchun prognoz qilingan gaz ishlab chiqarish aniqlanadi:
        double Q_otb_next = PrognozCalculatorTest.forecastForNextMonth(Q_otb, Q_otb_gaz_next);

        // 6.	Определяется коэффициент сверсжимаемости газа для (i+1) периода:
        // Gazning siqilish koeffitsienti (i + 1) davr uchun aniqlanadi:
        double Z_next = PrognozCalculatorTest.coefficientOfGasCompressibilityForNextMonth(T_pl, T_kr, P_pl, P_kr);

        double P_pl_next = PrognozCalculatorTest.reservoirPressureForNextMonth(P_n_pl, Z_n, Q_otb_next, Q_n_z, Z_next);

        //----------------------------------------------
        // SAVE process
        //----------------------------------------------
//        HashMap<Integer,Month> monthName = new HashMap<>();
//        monthName.put(1,Month.JANUARY);
//        monthName.put(2,Month.FEBRUARY);
//        monthName.put(3,Month.MARCH);
//        monthName.put(4,Month.APRIL);
//        monthName.put(5,Month.MAY);
//        monthName.put(6,Month.JUNE);
//        monthName.put(7,Month.JULY);
//        monthName.put(8,Month.AUGUST);
//        monthName.put(9,Month.SEPTEMBER);
//        monthName.put(10,Month.OCTOBER);
//        monthName.put(11,Month.NOVEMBER);
//        monthName.put(12,Month.DECEMBER);

        ForecastGas forecastGas;

        Date currentDate = new Date(System.currentTimeMillis());
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonth()+1;

        Optional<MiningSystem> miningSystem = miningSystemRepository.findById(miningId);

        for (int i = currentMonth; i <= 12; i++) {

            Optional<ForecastGas> byMiningSystemAndYearAndMonth = forecastGasRepository.findByMiningSystemAndYearAndMonth(miningSystem.get(), currentYear, Month.of(i));

            ForecastDto forecastDto= ForecastDto
                    .builder()
                    .year(currentYear + 1900)
                    .month(Month.of(i))
                    .mining_system_id(miningId)
                    .expected(Q_otb_gaz_next)
                    .build();

            ApiResponse apiResponse = forecastGasService.add(forecastDto);
            ApiResponseObject apiResponseObject= (ApiResponseObject) apiResponse;
            forecastGas = (ForecastGas) apiResponseObject.getObject();

//            if (byMiningSystemAndYearAndMonth.isPresent()){
//                forecastGas=byMiningSystemAndYearAndMonth.get();
//                forecastGas.setExpected(Q_otb_gaz_next);
//            }else {
//                forecastGas= ForecastGas
//                        .builder()
//                        .year(currentYear + 1900)
//                        .month(Month.of(i))
//                        .miningSystem(miningSystem.get())
//                        .expected(Q_otb_gaz_next)
//                        .build();
//            }
//            forecastGas = byMiningSystemAndYearAndMonth.orElseGet(ForecastGas::new);
//
//            forecastGas.setYear(currentYear + 1900);
//            forecastGas.setMonth(Month.of(i));
//            forecastGas.setMiningSystem(miningSystem.get());
//            forecastGas.setExpected(Q_otb_gaz_next);
//            System.out.println("forecastGas");
//            System.out.println(forecastGas);

            forecastGasRepository.save(forecastGas);
        }

        List<ForecastGas> allByYearAndMonth = forecastGasRepository.findAllByYearAndMonth(currentYear, Month.of(currentMonth));
        return allByYearAndMonth;
    }
}
