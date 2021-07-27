package uz.neft.component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Role;
import uz.neft.entity.RoleName;
import uz.neft.entity.User;
import uz.neft.entity.variables.*;
import uz.neft.entity.variables.MiningSystemGasComposition;
import uz.neft.repository.*;


import java.util.Collections;

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

    @Autowired
    public DataLoader(UserRepository userRepository, MiningSystemConstantRepository miningSystemConstantRepository, RoleRepository roleRepository, ConstantRepository constantRepository
            , PasswordEncoder passwordEncoder, MiningSystemRepository miningSystemRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemMiningSystemGasCompositionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.miningSystemRepository = miningSystemRepository;
        this.gasCompositionRepository = gasCompositionRepository;
        this.miningSystemMiningSystemGasCompositionRepository = miningSystemMiningSystemGasCompositionRepository;
        this.constantRepository = constantRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
    }


    //    @Value("${spring.datasource.initialization-mode}")
//    private String mode;

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
                            .fio("admin")
                            .phone("+998993793877")
                            .roles(Collections.singleton(director))
                            .username("admin")
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }

        try {
            MiningSystem shurtan = miningSystemRepository.save(new MiningSystem("SHURTAN"));

            //----------------------------------------------------

            GasComposition CH4 = gasCompositionRepository.save(new GasComposition("CH4", 4.695, 190.55));

            GasComposition C2H6 = gasCompositionRepository.save(new GasComposition("C2H6", 4.976, 306.43));

            GasComposition C3H8 = gasCompositionRepository.save(new GasComposition("C3H8", 4.333, 369.82));

            GasComposition Izo_C4H10 = gasCompositionRepository.save(new GasComposition("Изо_С4Н10", 3.719, 408.13));

            GasComposition H_C4H10 = gasCompositionRepository.save(new GasComposition("H_C4H10", 3.871, 425.16));

            GasComposition Izo_C5H12 = gasCompositionRepository.save(new GasComposition("Изо_С5Н12", 3.4448, 460.39));

            GasComposition H_C5H12 = gasCompositionRepository.save(new GasComposition("Н_С5Н12", 3.435, 469.65));

            GasComposition C6H14 = gasCompositionRepository.save(new GasComposition("С6Н14", 3.072, 507.35));

            GasComposition C7H16 = gasCompositionRepository.save(new GasComposition("С7Н16", 2.790, 540.15));

            GasComposition C8H18v = gasCompositionRepository.save(new GasComposition("С8Н18в", 2.535, 568.76));

            GasComposition CO2 = gasCompositionRepository.save(new GasComposition("СО2", 7.527, 304.20));

            GasComposition N2 = gasCompositionRepository.save(new GasComposition("N2", 3.465, 126.26));

            GasComposition H2S = gasCompositionRepository.save(new GasComposition("H2S", 9.185, 373.60));

            GasComposition H20 = gasCompositionRepository.save(new GasComposition("Н2О", 22.555, 647.40));

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

            Constant roGas = new Constant(ConstantNameEnums.RO_GAS, "ρгаза – плотность газа при стандартных условиях (standart\n" +
                    "sharoitda gaz zichligi), kg/m3 ");
            Constant roAir = new Constant(ConstantNameEnums.RO_AIR, "ρвозд – плотность воздуха при стандартных условиях (standart\n" +
                    "sharoitda havo zichligi), kg/m3");

            Constant savedRoGas = constantRepository.save(roGas);
            Constant savedRoAir = constantRepository.save(roAir);

            MiningSystemConstant miningSystemConstantRoGasValue=new MiningSystemConstant(shurtan,roGas,0.772);
            MiningSystemConstant miningSystemConstantRoAirValue=new MiningSystemConstant(shurtan,roAir,1.205);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


