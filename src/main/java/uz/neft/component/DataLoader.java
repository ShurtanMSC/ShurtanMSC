package uz.neft.component;
//lord

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.neft.dto.action.WellActionDto;
import uz.neft.entity.*;
import uz.neft.entity.action.MiningSystemAction;
import uz.neft.entity.action.UppgAction;
import uz.neft.entity.action.WellAction;
import uz.neft.entity.enums.OpcServerType;
import uz.neft.entity.enums.WellCategory;
import uz.neft.entity.enums.WellStatus;
import uz.neft.entity.variables.*;
import uz.neft.entity.variables.MiningSystemGasComposition;
import uz.neft.repository.*;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.action.UppgActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.repository.constants.ConstantRepository;
import uz.neft.repository.constants.MiningSystemConstantRepository;
import uz.neft.service.Calculator;
import uz.neft.service.action.WellActionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@NoArgsConstructor
public class DataLoader implements CommandLineRunner {
    //test
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    MiningSystemRepository miningSystemRepository;
    GasCompositionRepository gasCompositionRepository;
    MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository;
    ConstantRepository constantRepository;
    MiningSystemConstantRepository miningSystemConstantRepository;
    WellActionRepository wellActionRepository;

    @Autowired
    private MiningSystemActionRepository miningSystemActionRepository;
    @Autowired
    private UppgRepository uppgRepository;
    @Autowired
    private CollectionPointRepository collectionPointRepository;
    @Autowired
    private WellRepository wellRepository;
    @Autowired
    private OpcServerRepository opcServerRepository;
    @Autowired
    private WellActionService wellActionService;
    @Autowired
    private UppgActionRepository uppgActionRepository;
    @Autowired
    public DataLoader(UserRepository userRepository, MiningSystemConstantRepository miningSystemConstantRepository, RoleRepository roleRepository, ConstantRepository constantRepository
            , PasswordEncoder passwordEncoder, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository, WellActionRepository wellActionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.miningSystemRepository = miningSystemRepository;
        this.gasCompositionRepository = gasCompositionRepository;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.constantRepository = constantRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.wellActionRepository = wellActionRepository;
    }

//
//        @Value("${spring.datasource.initialization-mode}")
//    private String mode;

    @Value("${opc.simulation.service.address}")
    private String simulate;
    @Override
    public void run(String... args) throws Exception {
//        if (mode.equals("always")) {
        try {
            Role director = roleRepository.save(new Role(RoleName.SUPER_ADMIN));
            Role operator = roleRepository.save(new Role(RoleName.OPERATOR));
            Role employee = roleRepository.save(new Role(RoleName.EMPLOYEE));
            Role energetic = roleRepository.save(new Role(RoleName.ENERGETIC));
            Role metrologist = roleRepository.save(new Role(RoleName.METROLOGIST));
            Role geologist = roleRepository.save(new Role(RoleName.GEOLOGIST));
            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("admin")
                            .password(passwordEncoder.encode("admin"))
                            .fio("Амонулло Исмаили")
                            .phone("+998993793877")
                            .roles(Collections.singleton(director))
                            .username("admin")
                            .build()
            );
            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("operator")
                            .password(passwordEncoder.encode("operator"))
                            .fio("Азизжон Усманов")
                            .phone("+998993793877")
                            .roles(Collections.singleton(operator))
                            .username("operator")
                            .build()
            );

            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("employee")
                            .password(passwordEncoder.encode("employee"))
                            .fio("employee")
                            .phone("+998993793877")
                            .roles(Collections.singleton(employee))
                            .username("employee")
                            .build()
            );

            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("energetic")
                            .password(passwordEncoder.encode("energetic"))
                            .fio("Махмуд Саломов")
                            .phone("+998993793877")
                            .roles(Collections.singleton(energetic))
                            .username("energetic")
                            .build()
            );

            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("metrologist")
                            .password(passwordEncoder.encode("metrologist"))
                            .fio("Уткир Мусурманкулов")
                            .phone("+998993793877")
                            .roles(Collections.singleton(metrologist))
                            .username("metrologist")
                            .build()
            );

            userRepository.save(
                    User
                            .builder()
                            .active(true)
                            .email("geologist")
                            .password(passwordEncoder.encode("geologist"))
                            .fio("Джавлон Турсун-Ходжаев")
                            .phone("+998993793877")
                            .roles(Collections.singleton(metrologist))
                            .username("geologist")
                            .build()
            );


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String pythonServer="http://10.10.24.120:8000/opc/";
//            String localServer="http://10.10.24.120:8080/api/simulate/collection_point";
            String localServer=simulate+"/api/simulate/collection_point";
            String herokuServer= "https://shurtanback.herokuapp.com/api/simulate/collection_point";

            OpcServer serverReal = opcServerRepository.save(new OpcServer("Ecograph 1", "Haqiqiy server", "EH_Wetzer.OPC_DA_Server.4",localServer, OpcServerType.REAL));
//            OpcServer serverRealSim = opcServerRepository.save(new OpcServer("Ecograph 2", "Haqiqiy server", "EH_Wetzer.OPC_DA_Server.4",localServer));
            OpcServer serverSimulation = opcServerRepository.save(new OpcServer("Matrikon", "Simulyatsion server", "Matrikon.OPC.Simulation.1",localServer,OpcServerType.SIMULATE));

            String random="Random.Real";
            String temperature="Unit_10.TT_10.VT_R8";
            String pressure="Unit_10.PT_10.VT_R8";

            MiningSystem shurtan = miningSystemRepository.save(new MiningSystem(InitialNames.SHURTAN));
            MiningSystem south_tandyrcha = miningSystemRepository.save(new MiningSystem(InitialNames.SOUTH_TANDYRCHA));

            miningSystemRepository.save(new MiningSystem(InitialNames.BUZAKHUR));
            miningSystemRepository.save(new MiningSystem(InitialNames.EAST_BUZAKHUR));
            miningSystemRepository.save(new MiningSystem(InitialNames.FERUZA));
            miningSystemRepository.save(new MiningSystem(InitialNames.ZAFAR));
            miningSystemRepository.save(new MiningSystem(InitialNames.MEZON));
            miningSystemRepository.save(new MiningSystem(InitialNames.GARMISTON));
            miningSystemRepository.save(new MiningSystem(InitialNames.SOVLIGOR));
            miningSystemRepository.save(new MiningSystem(InitialNames.SHIRKENT));
            miningSystemRepository.save(new MiningSystem(InitialNames.KORATEPA));
            miningSystemRepository.save(new MiningSystem(InitialNames.NEW_KORATEPA));
            miningSystemRepository.save(new MiningSystem(InitialNames.NOMOZBOY));
            miningSystemRepository.save(new MiningSystem(InitialNames.NORTHERN_SHURTAN));
            miningSystemRepository.save(new MiningSystem(InitialNames.OYDIN));
            miningSystemRepository.save(new MiningSystem(InitialNames.RUBOYI));
            miningSystemRepository.save(new MiningSystem(InitialNames.TARNASOY));
            miningSystemRepository.save(new MiningSystem(InitialNames.TOSHLI));
            miningSystemRepository.save(new MiningSystem(InitialNames.TURTSARI));
            miningSystemRepository.save(new MiningSystem(InitialNames.YAKKASARAY));
            miningSystemRepository.save(new MiningSystem(InitialNames.YERMOK));

            MiningSystemAction miningSystemAction1=miningSystemActionRepository.save(MiningSystemAction
                    .builder()
                    .miningSystem(shurtan)
                    .expend(10000)
                    .build());

            MiningSystemAction miningSystemAction2=miningSystemActionRepository.save(MiningSystemAction
                    .builder()
                    .miningSystem(south_tandyrcha)
                    .expend(10000)
                    .build());

            Uppg uppg = uppgRepository.save(new Uppg("УППГ-1",shurtan));
            Uppg uppg2 = uppgRepository.save(new Uppg("УППГ-2",shurtan));

            UppgAction uppgAction1= UppgAction
                    .builder()
                    .uppg(uppg)
                    .expend(8000)
                    .incomePressure(15)
                    .exitPressure(15)
                    .condensate(15)
                    .incomePressure(50)
                    .exitTemperature(50)
                    .onWater(15)
                    .actualPerformance(15)
                    .designedPerformance(20)
                    .build();
            uppgActionRepository.save(uppgAction1);



            UppgAction uppgAction2= UppgAction
                    .builder()
                    .uppg(uppg2)
                    .expend(8000)
                    .incomePressure(15)
                    .exitPressure(15)
                    .condensate(15)
                    .incomePressure(50)
                    .exitTemperature(50)
                    .onWater(15)
                    .actualPerformance(15)
                    .designedPerformance(20)
                    .build();
            uppgActionRepository.save(uppgAction2);

            Uppg uppg3 = uppgRepository.save(new Uppg("uppgtandir",south_tandyrcha));
            Uppg uppg4 = uppgRepository.save(new Uppg("uppgtandir2",south_tandyrcha));



            /** UPPG1 **/
            CollectionPoint cp1=collectionPointRepository.save(new CollectionPoint("СП-1",uppg,temperature,pressure,serverReal));



            CollectionPoint cp2=collectionPointRepository.save(new CollectionPoint("СП-2",uppg,random,random,serverSimulation));
            CollectionPoint cp3=collectionPointRepository.save(new CollectionPoint("СП-3",uppg,random,random,serverSimulation));
            CollectionPoint cp4=collectionPointRepository.save(new CollectionPoint("СП-12",uppg,random,random,serverSimulation));
            CollectionPoint cp5=collectionPointRepository.save(new CollectionPoint("СП-14",uppg,random,random,serverSimulation));
            CollectionPoint cp6=collectionPointRepository.save(new CollectionPoint("СП-15",uppg,random,random,serverSimulation));
            CollectionPoint cp7=collectionPointRepository.save(new CollectionPoint("СП-22",uppg,random,random,serverSimulation));
            CollectionPoint cp8=collectionPointRepository.save(new CollectionPoint("СП-26",uppg,random,random,serverSimulation));




            /** UPPG2 **/
            CollectionPoint cp9=collectionPointRepository.save(new CollectionPoint("СП-4",uppg2,random,random,serverSimulation));
            CollectionPoint cp10=collectionPointRepository.save(new CollectionPoint("СП-6",uppg2,random,random,serverSimulation));
            CollectionPoint cp11=collectionPointRepository.save(new CollectionPoint("СП-9",uppg2,random,random,serverSimulation));
            CollectionPoint cp12=collectionPointRepository.save(new CollectionPoint("СП-10",uppg2,temperature,pressure,serverReal));
            CollectionPoint cp13=collectionPointRepository.save(new CollectionPoint("СП-20",uppg2,random,random,serverSimulation));
            CollectionPoint cp14=collectionPointRepository.save(new CollectionPoint("СП-24",uppg2,random,random,serverSimulation));
            CollectionPoint cp15=collectionPointRepository.save(new CollectionPoint("БТ-5",uppg2,random,random,serverSimulation));
            CollectionPoint cp16=collectionPointRepository.save(new CollectionPoint("БТ-16",uppg2,random,random,serverSimulation));
            CollectionPoint cp17=collectionPointRepository.save(new CollectionPoint("БТ-17",uppg2,random,random,serverSimulation));
            CollectionPoint cp18=collectionPointRepository.save(new CollectionPoint("БТ-19",uppg2,random,random,serverSimulation));
            CollectionPoint cp19=collectionPointRepository.save(new CollectionPoint("БТ-21",uppg2,random,random,serverSimulation));
            CollectionPoint cp20=collectionPointRepository.save(new CollectionPoint("БТ-30",uppg2,random,random,serverSimulation));
            CollectionPoint cp21=collectionPointRepository.save(new CollectionPoint("БТ-34",uppg2,random,random,serverSimulation));

            CollectionPoint pointtandircha=collectionPointRepository.save(new CollectionPoint("sptandircha",uppg3,random,random,serverSimulation));
            CollectionPoint pointtandircha2=collectionPointRepository.save(new CollectionPoint("sptandircha2",uppg4,random,random,serverSimulation));

            List<CollectionPoint> collectionPointList=new ArrayList<>();
            collectionPointList.add(cp1);
            collectionPointList.add(cp2);
            collectionPointList.add(cp3);
            collectionPointList.add(cp4);
            collectionPointList.add(cp5);
            collectionPointList.add(cp6);
            collectionPointList.add(cp7);
            collectionPointList.add(cp8);
            collectionPointList.add(cp9);
            collectionPointList.add(cp10);
            collectionPointList.add(cp11);
            collectionPointList.add(cp12);
            collectionPointList.add(cp13);
            collectionPointList.add(cp14);
            collectionPointList.add(cp15);
            collectionPointList.add(cp16);
            collectionPointList.add(cp17);
            collectionPointList.add(cp18);
            collectionPointList.add(cp19);
            collectionPointList.add(cp20);
            collectionPointList.add(cp21);

            Integer[] array_cp1 = {154,155,157,169,312,158,309};
            Integer[] array_cp2 = {4,170,171,172,173,185,313};
            Integer[] array_cp3 = {13,52,120,122,219,275,315};
            Integer[] array_cp4 = {125,128,129,130,132,136,278,305,314,320,323};
            Integer[] array_cp5 = {137,139,140,141,153,283};
            Integer[] array_cp6 = {3,160,151,282,284,296,311};
            Integer[] array_cp7 = {2,53,55,57,1,304,316};
            Integer[] array_cp8 = {167,174,175,182,183,184,103};

            Integer[] array_cp9 = {127,300,301,37,321};
            Integer[] array_cp10 = {14,197,199,208,211,218,252,253,254,257,288,289};
            Integer[] array_cp11 = {10,15,119,196,201,202,203,207,115,118,258,260,285,293,306,307};
            Integer[] array_cp12 = {61,198,200,51,56,292,256};
            Integer[] array_cp13 = {67,5,190,192,193,194,195,303,259,261,280,308,310,317,318};
            Integer[] array_cp14 = {33,161,164,165};
            Integer[] array_cp15 = {92,102,123,178,210,251,286,287};
            Integer[] array_cp16 = {83,86,131,225,240};
            Integer[] array_cp17 = {23,79};
            Integer[] array_cp18 = {72,163,21,71,144,145,179,244};
            Integer[] array_cp19 = {236,233,290,319};
            Integer[] array_cp20 = {96,101,108,110,7};
            Integer[] array_cp21 = {264,266,267,268};









//            Well well=wellRepository.save(wellRepository.save(new Well(11,point)));
//            Well well2=wellRepository.save(wellRepository.save(new Well(12,point)));
//            Well well3=wellRepository.save(wellRepository.save(new Well(3,pointtandircha2)));
//            WellAction wellAction=wellActionRepository.save(WellAction
//                    .builder()
//                    .user(userRepository.findById(1).get())
//                    .pressure(1)
//                    .temperature(1)
//                    .rpl(1)
//                    .well(well)
//                    .expend(1)
//                    .status(WellStatus.IN_WORK)
//                    .build());


            //----------------------------------------------------

            GasComposition CH4 = gasCompositionRepository.save(new GasComposition("CH4", Calculator.mega_pascal_to_kgf_sm2(4.695), 190.55));

            GasComposition C2H6 = gasCompositionRepository.save(new GasComposition("C2H6", Calculator.mega_pascal_to_kgf_sm2(4.976), 306.43));

            GasComposition C3H8 = gasCompositionRepository.save(new GasComposition("C3H8", Calculator.mega_pascal_to_kgf_sm2(4.333), 369.82));

            GasComposition Izo_C4H10 = gasCompositionRepository.save(new GasComposition("Изо_С4Н10", Calculator.mega_pascal_to_kgf_sm2(3.719), 408.13));

            GasComposition H_C4H10 = gasCompositionRepository.save(new GasComposition("H_C4H10", Calculator.mega_pascal_to_kgf_sm2(3.871), 425.16));

            GasComposition Izo_C5H12 = gasCompositionRepository.save(new GasComposition("Изо_С5Н12", Calculator.mega_pascal_to_kgf_sm2(3.4448), 460.39));

            GasComposition H_C5H12 = gasCompositionRepository.save(new GasComposition("Н_С5Н12", Calculator.mega_pascal_to_kgf_sm2(3.435), 469.65));

            GasComposition C6H14 = gasCompositionRepository.save(new GasComposition("С6Н14", Calculator.mega_pascal_to_kgf_sm2(3.072), 507.35));

            GasComposition C7H16 = gasCompositionRepository.save(new GasComposition("С7Н16", Calculator.mega_pascal_to_kgf_sm2(2.790), 540.15));

            GasComposition C8H18v = gasCompositionRepository.save(new GasComposition("С8Н18в", Calculator.mega_pascal_to_kgf_sm2(2.535), 568.76));

            GasComposition CO2 = gasCompositionRepository.save(new GasComposition("СО2", Calculator.mega_pascal_to_kgf_sm2(7.527), 304.20));

            GasComposition N2 = gasCompositionRepository.save(new GasComposition("N2", Calculator.mega_pascal_to_kgf_sm2(3.465), 126.26));

            GasComposition H2S = gasCompositionRepository.save(new GasComposition("H2S", Calculator.mega_pascal_to_kgf_sm2(9.185), 373.60));

            GasComposition H20 = gasCompositionRepository.save(new GasComposition("Н2О", Calculator.mega_pascal_to_kgf_sm2(22.555), 647.40));

            //----------------------------------------------------

            MiningSystemGasComposition CH4_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, CH4, 90.0960));

            MiningSystemGasComposition C2H6_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, C2H6, 3.7600));

            MiningSystemGasComposition C3H8_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, C3H8, 0.9140));

            MiningSystemGasComposition Izo_C4H10_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, Izo_C4H10, 0.1820));

            MiningSystemGasComposition H_C4H10_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, H_C4H10, 0.2160));

            MiningSystemGasComposition Izo_C5H12_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, Izo_C5H12, 0.1050));

            MiningSystemGasComposition H_C5H12_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, H_C5H12, 0.0720));

            MiningSystemGasComposition C6H14_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, C6H14, 0.4660));

            MiningSystemGasComposition C7H16_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, C7H16, 0.0000));

            MiningSystemGasComposition C8H18v_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, C8H18v, 0.0000));

            MiningSystemGasComposition CO2_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, CO2, 3.2720));

            MiningSystemGasComposition N2_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, N2, 0.8470));

            MiningSystemGasComposition H2S_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, H2S, 0.0700));

            MiningSystemGasComposition H20_MOLAR = miningSystemMiningSystemGasCompositionRepository.save(new MiningSystemGasComposition(shurtan, H20, 0.0000));

            Constant roGas = new Constant(ConstantNameEnums.RO_GAS, "ρгаза – плотность газа при стандартных условиях (standart" +
                    "sharoitda gaz zichligi), kg/m3 ");
            Constant roAir = new Constant(ConstantNameEnums.RO_AIR, "ρвозд – плотность воздуха при стандартных условиях (standart" +
                    "sharoitda havo zichligi), kg/m3");

            Constant frcA=new Constant(ConstantNameEnums.A,"Коэффициент фильтрационного сопротивления А (Filtrlanishga qarshilik koeffitsienti A)");
            Constant frcB=new Constant(ConstantNameEnums.B,"Коэффициент фильтрационного сопротивления B (Filtrlanishga qarshilik koeffitsienti B)");
            Constant rlcS=new Constant(ConstantNameEnums.S,"Сопротивление жидких компонентов по НКТ (Quvurlar bo'ylab suyuq komponentlarning qarshiligi S)");

            Constant trTeta=new Constant(ConstantNameEnums.TETA,"Сопротивление НКТ (Quvurlarga qarshilik θ)");

            roGas = constantRepository.save(roGas);
            roAir = constantRepository.save(roAir);
            frcA = constantRepository.save(frcA);
            frcB = constantRepository.save(frcB);

            rlcS = constantRepository.save(rlcS);

            trTeta = constantRepository.save(trTeta);



            MiningSystemConstant miningSystemConstantRoGasValue=miningSystemConstantRepository.save(new MiningSystemConstant(shurtan,roGas,0.772));
            MiningSystemConstant miningSystemConstantRoAirValue=miningSystemConstantRepository.save(new MiningSystemConstant(shurtan,roAir,1.205));
            MiningSystemConstant miningSystemConstantFrcAValue=miningSystemConstantRepository.save(new MiningSystemConstant(shurtan,frcA,2.9200));
            MiningSystemConstant miningSystemConstantFrcBValue=miningSystemConstantRepository.save(new MiningSystemConstant(shurtan,frcB,0.0053));
            MiningSystemConstant miningSystemConstantRlcSValue=miningSystemConstantRepository.save(new MiningSystemConstant(shurtan,rlcS,0.78));

            MiningSystemConstant miningSystemConstantTrTetaValue=miningSystemConstantRepository.save(new MiningSystemConstant(shurtan,trTeta,0.0306));





            saverPerCp(array_cp1,cp1);
            saverPerCp(array_cp2,cp2);
            saverPerCp(array_cp3,cp3);
            saverPerCp(array_cp4,cp4);
            saverPerCp(array_cp5,cp5);
            saverPerCp(array_cp6,cp6);
            saverPerCp(array_cp7,cp7);
            saverPerCp(array_cp8,cp8);
            saverPerCp(array_cp9,cp9);
            saverPerCp(array_cp10,cp10);
            saverPerCp(array_cp11,cp11);
            saverPerCp(array_cp12,cp12);
            saverPerCp(array_cp13,cp13);
            saverPerCp(array_cp14,cp14);
            saverPerCp(array_cp15,cp15);
            saverPerCp(array_cp16,cp16);
            saverPerCp(array_cp17,cp17);
            saverPerCp(array_cp18,cp18);
            saverPerCp(array_cp19,cp19);
            saverPerCp(array_cp20,cp20);
            saverPerCp(array_cp21,cp21);



            double[] arrayC={88,88,124,32.6,78.1,29.9,62,83.1,88.8,135,112,155,
                    83.1,88.8,93.3,27.4,83.1,93.3,150,73.6,73.6,78.1,93.3,73.6,78.1,78.1,88.8,44.7,50.8,
                    62,50.8,73.6,100,100,100,93.3,100,65.7,78.1,124,27.4,78.1,78.1,58.3,73.6,83.1,83.1,100,69.6,
                    88.8,50.8,83.1,124,104,112,154,104,135,78.1,135,124,112,112,58.3,124,124,135,
                    142,124,104,20.5,88.8,94.3,62,104,124,65.7,142,112,148,158,138,140,100,50.8,112,
                    94.3,94.3,104,88.8,73.6,150,135,140,41.4,50.8,41.4,83.1,130,118,135,62,124,120,124,112,62,
                    65.7,58.3,78.1,69.6,83.1,78.1,8.57,124,112,116,104,112,104,112,112,112,88.8,78.1,110,119,128,
                    124,124,41.4,24.8,104,112,130,118,124,112,118,93.3,78.1,41.4,88.8,54.8,124,124,130,124,135,135,116,124,135, 88.7};

            List<Well> wellList=wellRepository.findAllByMiningSystemId(1);

            for (int i = 0; i <wellList.size() ; i++) {
                Well well=wellList.get(i);
                well.setC(arrayC[i]);
                well=wellRepository.save(well);
                wellActionSaver(well);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void saverPerCp(Integer[] array,CollectionPoint cp){
        for (Integer integer : array) {
            saver(integer, cp);
        }
    }

    public void saver(Integer number,CollectionPoint cp){
        Well well154=wellRepository.save(
                Well.builder()
                        .number(number)
                        .category(WellCategory.MINING)
                        .altitude(10)
                        .collectionPoint(cp)
                        .depth(10)
                        .x(10)
                        .y(10)
                        .horizon("h")
                        .commissioningDate(new Date())
                        .drillingStartDate(new Date())
                        .build());

//        WellActionDto wellActionDto= WellActionDto
//                .builder()
//                .pressure(1.9)
//                .rpl(37.22)
//                .wellId(well154.getId())
//                .perforation_min(500)
//                .perforation_max(1000)
//                .temperature(47)
//                .status(WellStatus.IN_WORK)
//                .build();
//        wellActionService.addManually(new User(),wellActionDto);

//        WellAction wellAction154=wellActionRepository.save(WellAction
//                .builder()
//                .well(well154)
//                .pressure(40)
//                .temperature(46.85)
//                .expend(141)
//                .rpl(37.22)
//                .status(WellStatus.IN_WORK)
//                .perforation_max(1000)
//                .perforation_min(500)
//                .build());
    }

    public void wellActionSaver(Well well){
        try {
            WellActionDto wellActionDto= WellActionDto
                    .builder()
                    .pressure(Calculator.mega_pascal_to_kgf_sm2(1.9))
                    .rpl(37.22)
                    .wellId(well.getId())
                    .perforation_min(500)
                    .perforation_max(1000)
                    .temperature(47)
                    .status(WellStatus.IN_WORK)
                    .build();
            wellActionService.addManually(new User(),wellActionDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


