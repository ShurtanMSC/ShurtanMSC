package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.ConstantDto;
import uz.neft.dto.constantValue.ConstValueDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.ConstantValue;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.constants.ConstantRepository;
import uz.neft.repository.constants.ConstantValuesRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConstantService {

    private final ConstantRepository constantRepository;
    private final Converter converter;
    private final MiningSystemRepository miningSystemRepository;
    private final UppgRepository uppgRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final WellRepository wellRepository;
    private final ConstantValuesRepository constantValuesRepository;

    @Autowired
    public ConstantService(ConstantRepository constantRepository, Converter converter, MiningSystemRepository miningSystemRepository, UppgRepository uppgRepository, CollectionPointRepository collectionPointRepository, WellRepository wellRepository, ConstantValuesRepository constantValuesRepository) {
        this.constantRepository = constantRepository;
        this.converter = converter;
        this.miningSystemRepository = miningSystemRepository;
        this.uppgRepository = uppgRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.wellRepository = wellRepository;
        this.constantValuesRepository = constantValuesRepository;
    }

    public ResponseEntity<?> save(ConstantDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (dto.getName() == null) return converter.apiError400("name is null");

            Constant constant = new Constant();
            constant.setName(dto.getName());
            constant.setDescription(dto.getDescription());

            Constant save = constantRepository.save(constant);
            ConstantDto constantDto = converter.constantToConstantDto(save);

            return converter.apiSuccess201("Constant added", constantDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error Creating Constant");
        }
    }

    public ResponseEntity<?> edit(ConstantDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");

            Constant constant;
            Optional<Constant> byId = constantRepository.findById(dto.getId());
            if (byId.isPresent()) {
                constant = byId.get();
                constant.setName(dto.getName());
                constant.setDescription(dto.getDescription());
                constant = constantRepository.save(constant);

                ConstantDto gasCompositionDto = converter.constantToConstantDto(constant);
                return converter.apiSuccess202("Constant edited", gasCompositionDto);
            }
            return converter.apiError404("Constant not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error editing Constant");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<Constant> byId = constantRepository.findById(id);
                if (byId.isPresent()) {
                    constantRepository.deleteById(id);
                    return converter.apiSuccess200("Constant deleted ");
                }
                return converter.apiError404("Constant not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting Constant", e);
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            List<Constant> all = constantRepository.findAll();
            List<ConstantDto> collect = all.stream().map(converter::constantToConstantDto).collect(Collectors.toList());
            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching Constants", e);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id != null) {
                Optional<Constant> byId = constantRepository.findById(id);
                if (byId.isPresent()) {
                    ConstantDto constantDto = converter.constantToConstantDto(byId.get());
                    return converter.apiSuccess200(constantDto);
                } else {
                    return converter.apiError404("Constant not found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding Constant", e);
        }
    }


//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ConstantValues Service methods

    public HttpEntity<?> saveValue(ConstValueDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");

            if (dto.getConstantId() == null) return converter.apiError400("ConstantId is null");
            Optional<Constant> constant = constantRepository.findById(dto.getConstantId());
            if (!constant.isPresent()) return converter.apiError404("constant not found");


            if (dto.getMSystemId() == null && dto.getUppgId() == null && dto.getCpointId() == null && dto.getWellId() == null)
                return converter.apiError400("IDs are all null; at least one of IDs should not be null");

            ConstantValue constantValue = new ConstantValue();
            constantValue.setValue(dto.getValue());
            constantValue.setConstant(constant.get());
/**
 MSystemId
 **/
            if (dto.getMSystemId() == null) return converter.apiError404("MSystemId is null");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMSystemId());
            if (!miningSystem.isPresent()) return converter.apiError404("mining system not found");

            if (dto.getMSystemId() != null && dto.getUppgId() == null && dto.getCpointId() == null && dto.getWellId() == null) {
                constantValue.setMiningSystem(miningSystem.get());
            }
/**
 MSystemId, UppgId
 **/
            if (dto.getUppgId() == null) return converter.apiError404("UppgId is null");
            Optional<Uppg> uppg = uppgRepository.findById(dto.getUppgId());
            if (!uppg.isPresent()) return converter.apiError404("Uppg not found");

            if (dto.getMSystemId() != null && dto.getUppgId() != null && dto.getCpointId() == null && dto.getWellId() == null) {
                constantValue.setMiningSystem(miningSystem.get());
                constantValue.setUppg(uppg.get());
            }

/**
 MSystemId, UppgId, CpointId
 **/
            if (dto.getCpointId() == null) return converter.apiError404("CpointId is null");
            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getConstantId());
            if (!collectionPoint.isPresent()) return converter.apiError404("Collection Point not found");

            if (dto.getMSystemId() != null && dto.getUppgId() != null && dto.getCpointId() != null && dto.getWellId() == null) {
                constantValue.setMiningSystem(miningSystem.get());
                constantValue.setUppg(uppg.get());
                constantValue.setCollectionPoint(collectionPoint.get());
            }

/**
 MSystemId, UppgId, CpointId, WellId
 **/
            if (dto.getWellId() == null) return converter.apiError404("WellId is null");
            Optional<Well> well = wellRepository.findById(dto.getWellId());
            if (!well.isPresent()) return converter.apiError404("well not found");

            if (dto.getMSystemId() != null && dto.getUppgId() != null && dto.getCpointId() != null && dto.getWellId() != null) {
                constantValue.setMiningSystem(miningSystem.get());
                constantValue.setUppg(uppg.get());
                constantValue.setCollectionPoint(collectionPoint.get());
                constantValue.setWell(well.get());
            }

            ConstantValue save = constantValuesRepository.save(constantValue);
            ConstValueDto constValueDto = converter.constantValueToConstValueDto(save);
            return converter.apiSuccess201("Constant Value saved", constValueDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error Creating ConstantValue");
        }
    }

    public HttpEntity<?> editValue(ConstValueDto dto) {

        try {
            ConstantValue constantValue;

            if (dto.getId() == null) return converter.apiError400("id is null");
            Optional<ConstantValue> constantValueById = constantValuesRepository.findById(dto.getId());
            if (!constantValueById.isPresent()) return converter.apiError404("constantValue not found");
            constantValue = constantValueById.get();

            if (dto.getConstantId() == null) return converter.apiError400("ConstantId is null");
            Optional<Constant> constant = constantRepository.findById(dto.getConstantId());
            if (!constant.isPresent()) return converter.apiError404("constant not found");

            if (dto.getMSystemId() == null && dto.getUppgId() == null && dto.getCpointId() == null && dto.getWellId() == null)
                return converter.apiError400("IDs are all null; at least one of IDs should not be null");

            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMSystemId());
            if (!miningSystem.isPresent()) return converter.apiError404("mining system not found");

            Optional<Uppg> uppg = uppgRepository.findById(dto.getUppgId());
            if (!uppg.isPresent()) return converter.apiError404("mining system not found");

            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getConstantId());
            if (!collectionPoint.isPresent()) return converter.apiError404("mining system not found");

            Optional<Well> well = wellRepository.findById(dto.getWellId());
            if (!well.isPresent()) return converter.apiError404("mining system not found");


            constantValue.setValue(dto.getValue());
            constantValue.setConstant(constant.get());

            if (dto.getMSystemId() != null && dto.getUppgId() == null && dto.getCpointId() == null && dto.getWellId() == null) {
                constantValue.setMiningSystem(miningSystem.get());
            }

            if (dto.getMSystemId() != null && dto.getUppgId() != null && dto.getCpointId() == null && dto.getWellId() == null) {
                constantValue.setMiningSystem(miningSystem.get());
                constantValue.setUppg(uppg.get());
            }

            if (dto.getMSystemId() != null && dto.getUppgId() != null && dto.getCpointId() != null && dto.getWellId() == null) {
                constantValue.setMiningSystem(miningSystem.get());
                constantValue.setUppg(uppg.get());
                constantValue.setCollectionPoint(collectionPoint.get());
            }

            if (dto.getMSystemId() != null && dto.getUppgId() != null && dto.getCpointId() != null && dto.getWellId() != null) {
                constantValue.setMiningSystem(miningSystem.get());
                constantValue.setUppg(uppg.get());
                constantValue.setCollectionPoint(collectionPoint.get());
                constantValue.setWell(well.get());
            }

            ConstantValue save = constantValuesRepository.save(constantValue);
            ConstValueDto constValueDto = converter.constantValueToConstValueDto(save);
            return converter.apiSuccess201("Constant Value edited", constValueDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error editing ConstantValue");
        }
    }

    public HttpEntity<?> deleteValue(Integer id) {

        try {
            if (id == null) return converter.apiError400("Id null");
            Optional<ConstantValue> byId = constantValuesRepository.findById(id);

            if (!byId.isPresent()) return converter.apiError404("ConstantValue not found");

            constantRepository.deleteById(id);
            return converter.apiSuccess200("ConstantValue deleted ");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting ConstantValue", e);
        }
    }

    public HttpEntity<?> allValues() {
        try {
            List<ConstantValue> all = constantValuesRepository.findAll();
            List<ConstValueDto> collect = all.stream().map(converter::constantValueToConstValueDto).collect(Collectors.toList());
            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching ConstantValues", e);
        }
    }

    public HttpEntity<?> byIdValue(Integer id) {
        try {
            if (id == null) return converter.apiError400("Id null");

            Optional<ConstantValue> byId = constantValuesRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("Constant not found");

            ConstValueDto constValueDto = converter.constantValueToConstValueDto(byId.get());
            return converter.apiSuccess200(constValueDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding Constant", e);
        }
    }
}
//