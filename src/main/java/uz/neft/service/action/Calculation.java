package uz.neft.service.action;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.repository.*;
import uz.neft.repository.action.CollectionPointActionRepository;
import uz.neft.repository.action.MiningSystemActionRepository;
import uz.neft.repository.action.UppgActionRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.repository.constants.ForecastCondensateRepository;
import uz.neft.repository.constants.ForecastGasRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;

@Service
public class Calculation {

    private final MiningSystemRepository miningSystemRepository;
    private final MiningSystemActionRepository miningSystemActionRepository;

    private final UppgRepository uppgRepository;
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
    private final Logger logger;


    public Calculation(MiningSystemRepository miningSystemRepository, MiningSystemActionRepository miningSystemActionRepository, UppgRepository uppgRepository, UppgActionRepository uppgActionRepository, CollectionPointRepository collectionPointRepository, CollectionPointActionRepository collectionPointActionRepository, WellRepository wellRepository, WellActionRepository wellActionRepository, GasCompositionRepository gasCompositionRepository, MiningSystemGasCompositionRepository miningSystemGasCompositionRepository, Converter converter, ForecastGasRepository forecastGasRepository, ForecastCondensateRepository forecastCondensateRepository, Logger logger) {
        this.miningSystemRepository = miningSystemRepository;
        this.miningSystemActionRepository = miningSystemActionRepository;
        this.uppgRepository = uppgRepository;
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
        this.logger = logger;
    }


    private void debitAllWells(Integer miningSystemId){

        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(miningSystemId);

            List<Uppg> uppgList = uppgRepository.findAllByMiningSystem(miningSystem.get());





        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString());
        }

    }

    private void  debitAllCollectionPoints(Integer miningSystemId){

    }







}
