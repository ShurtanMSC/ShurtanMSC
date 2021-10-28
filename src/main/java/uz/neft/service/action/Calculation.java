package uz.neft.service.action;

import org.springframework.stereotype.Service;
import uz.neft.repository.*;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.action.UppgActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.repository.constants.ForecastCondensateRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.utils.Converter;

@Service
public class Calculation {

    private final MiningSystemRepository miningSystemRepository;
    private final MiningSystemActionRepository miningSystemActionRepository;

    private final UppgRepository uppgRepositoryu;
    private final UppgActionRepository uppgActionRepository;

    private final CollectionPointRepository collectionPointRepository;
    private final CollectionPointActionRepository collectionPointActionRepository;

    private final WellRepository wellRepository;
    private final WellActionRepository wellActionRepository;

    private final GasCompositionRepository gasCompositionRepository;
    private final MiningSystemGasCompositionRepository miningSystemGasCompositionRepository;

    private final Converter converter;

    private final ForecastGasRepository forecastGasRepository;
    private final ForecastCondensateRepository forecastCondensateRepository;


    public Calculation(MiningSystemRepository miningSystemRepository, MiningSystemActionRepository miningSystemActionRepository, UppgRepository uppgRepositoryu, UppgActionRepository uppgActionRepository, CollectionPointRepository collectionPointRepository, CollectionPointActionRepository collectionPointActionRepository, WellRepository wellRepository, WellActionRepository wellActionRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemGasCompositionRepository, Converter converter, ForecastGasRepository forecastGasRepository, ForecastCondensateRepository forecastCondensateRepository) {
        this.miningSystemRepository = miningSystemRepository;
        this.miningSystemActionRepository = miningSystemActionRepository;
        this.uppgRepositoryu = uppgRepositoryu;
        this.uppgActionRepository = uppgActionRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.collectionPointActionRepository = collectionPointActionRepository;
        this.wellRepository = wellRepository;
        this.wellActionRepository = wellActionRepository;
        this.gasCompositionRepository = gasCompositionRepository;
        this.miningSystemGasCompositionRepository = miningSystemGasCompositionRepository;
        this.converter = converter;
        this.forecastGasRepository = forecastGasRepository;
        this.forecastCondensateRepository = forecastCondensateRepository;
    }

//
//    private void debitAllWells(){
//
//    }
//
//
//
//    private double P_pkr(){
//
//    }



}
