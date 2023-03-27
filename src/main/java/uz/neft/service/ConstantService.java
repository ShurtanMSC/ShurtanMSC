package uz.neft.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.neft.dto.ConstantDto;
import uz.neft.dto.ConstantDto2;
import uz.neft.dto.constantValue.ConstantValueDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.variables.*;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.constants.*;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConstantService {

    private final ConstantRepository constantRepository;
    private final Converter converter;
    private final MiningSystemRepository miningSystemRepository;
    private final MiningSystemConstantRepository miningSystemConstantRepository;
    private final UppgConstantRepository uppgConstantRepository;
    private final CollectionPointConstantRepository collectionPointConstantRepository;
    private final WellConstantRepository wellConstantRepository;
    private final UppgRepository uppgRepository;
    private final CollectionPointRepository collectionPointRepository;
    private final WellRepository wellRepository;
    private final ConstantValuesRepository constantValuesRepository;
    private final Logger logger;
    @Autowired
    public ConstantService(ConstantRepository constantRepository, Converter converter, MiningSystemRepository miningSystemRepository, MiningSystemConstantRepository miningSystemConstantRepository, UppgConstantRepository uppgConstantRepository, CollectionPointConstantRepository collectionPointConstantRepository, WellConstantRepository wellConstantRepository, UppgRepository uppgRepository, CollectionPointRepository collectionPointRepository, WellRepository wellRepository, ConstantValuesRepository constantValuesRepository, Logger logger) {
        this.constantRepository = constantRepository;
        this.converter = converter;
        this.miningSystemRepository = miningSystemRepository;
        this.miningSystemConstantRepository = miningSystemConstantRepository;
        this.uppgConstantRepository = uppgConstantRepository;
        this.collectionPointConstantRepository = collectionPointConstantRepository;
        this.wellConstantRepository = wellConstantRepository;
        this.uppgRepository = uppgRepository;
        this.collectionPointRepository = collectionPointRepository;
        this.wellRepository = wellRepository;
        this.constantValuesRepository = constantValuesRepository;
        this.logger = logger;
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
            logger.error(e.toString());
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
            logger.error(e.toString());
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
            logger.error(e.toString());
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
            logger.error(e.toString());
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
            logger.error(e.toString());
            return converter.apiError409("Error in finding Constant", e);
        }
    }


//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ConstantValues Service methods

    public HttpEntity<?> saveValue(ConstantValueDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            ApiResponseObject helper = (ApiResponseObject) helper(dto);

            if (helper.isSuccess()) {
                ConstantValue constantValue = (ConstantValue) helper.getObject();
                return converter.apiSuccess201(helper.getMessage(), converter.constantValueToConstValueDto(constantValuesRepository.save(constantValue)));
            } else return converter.apiError409(helper.getMessage(), null);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Creating ConstantValue");
        }
    }

    public HttpEntity<?> editValue(ConstantValueDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");
            if (!constantValuesRepository.existsById(dto.getId()))
                return converter.apiError404("Constant value not found");
            ApiResponseObject helper = (ApiResponseObject) helper(dto);
            if (helper.isSuccess()) {
                ConstantValue constantValue = (ConstantValue) helper.getObject();
                return converter.apiSuccess200("Successfully edited", converter.constantValueToConstValueDto(constantValuesRepository.save(constantValue)));
            } else return converter.apiError409(helper.getMessage(), null);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Creating ConstantValue");
        }
    }

    public HttpEntity<?> deleteValue(Integer id) {

        try {
            if (id == null) return converter.apiError400("Id null");
            Optional<ConstantValue> byId = constantValuesRepository.findById(id);

            if (byId.isEmpty()) return converter.apiError404("ConstantValue not found");

            constantValuesRepository.deleteById(id);
            return converter.apiSuccess200("ConstantValue deleted ");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in deleting ConstantValue");
        }
    }

    public HttpEntity<?> allValues() {
        try {
            List<ConstantValue> all = constantValuesRepository.findAll();
            List<ConstantValueDto> collect = all.stream().map(converter::constantValueToConstValueDto).collect(Collectors.toList());
            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching ConstantValues", e);
        }
    }

    public HttpEntity<?> byIdValue(Integer id) {
        try {
            if (id == null) return converter.apiError400("Id null");
            Optional<ConstantValue> byId = constantValuesRepository.findById(id);
            if (byId.isEmpty()) return converter.apiError404("Constant not found");
            ConstantValueDto constantValueDto = converter.constantValueToConstValueDto(byId.get());
            return converter.apiSuccess200(constantValueDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in finding Constant", e);
        }
    }

    public ApiResponse helper(ConstantValueDto dto) {
        if (dto.getConstantId() == null) return converter.apiError("Constant id is null", null);
        Optional<Constant> constant = constantRepository.findById(dto.getConstantId());
        if (constant.isEmpty()) return converter.apiError("Constant not found", null);
        if (dto.getValue() == null) return converter.apiError("Value id is null", null);
        ConstantValue constantValue = new ConstantValue();
        constantValue.setConstant(constant.get());
        constantValue.setValue(dto.getValue());
        if (dto.getId() != null) constantValue.setId(dto.getId());
        if (dto.getMiningSystemId() != null) {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
            if (miningSystem.isPresent()) {
                constantValue.setMiningSystem(miningSystem.get());
                if (dto.getUppgId() != null) {
                    Optional<Uppg> uppg = uppgRepository.findById(dto.getUppgId());
                    if (uppg.isPresent()) {
                        constantValue.setUppg(uppg.get());
                        if (dto.getCollectionPointId() != null) {
                            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
                            if (collectionPoint.isPresent()) {
                                constantValue.setCollectionPoint(collectionPoint.get());
                                if (dto.getWellId() != null) {
                                    Optional<Well> well = wellRepository.findById(dto.getWellId());
                                    if (well.isPresent()) {
                                        constantValue.setWell(well.get());
                                        return converter.apiSuccess("Well constant value added", constantValue);
                                    }
                                    return converter.apiError("Well not found", null);
                                }
                                return converter.apiSuccess("Collection point constant value added", constantValue);
                            }
                            return converter.apiError("Collection point not found", null);
                        }
                        return converter.apiSuccess("Uppg constant value added", constantValue);
                    }
                    return converter.apiError("Uppg not found", null);
                }
                return converter.apiSuccess("Mining system constant value added", constantValue);
            }
            return converter.apiError("Mining system not found", null);
        }
        return converter.apiError("Mining system id is null", null);

    }

    // NEW ********************************************************************************************************************

    // MINING CONSTANTS
    public HttpEntity<?> findAllMiningSysConst(Integer miningId) {
        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(miningId);
            List<MiningSystemConstant> allByMiningSystem = miningSystemConstantRepository.findAllByMiningSystem(miningSystem.get());
//            List<ConstantDto> collect = all.stream().map(converter::constantToConstantDto).collect(Collectors.toList());
            return converter.apiSuccess200(allByMiningSystem);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching Constants", e);
        }
    }

    @Transactional
    public ResponseEntity<?> saveMiningConstant(ConstantDto2 dto, Integer miningId) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (miningId == null) return converter.apiError400("miningId is null");

            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(miningId);

            Constant constant = new Constant();
            constant.setName(dto.getName());
            constant.setDescription(dto.getDescription());
            Constant constant1 = constantRepository.save(constant);


            MiningSystemConstant miningSystemConstant = new MiningSystemConstant();
            miningSystemConstant.setConstant(constant1);
            miningSystemConstant.setMiningSystem(miningSystem.get());
            miningSystemConstant.setValue(dto.getValue());
            MiningSystemConstant save1 = miningSystemConstantRepository.save(miningSystemConstant);

            return converter.apiSuccess201("MiningSystemConstant added");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Creating MiningSystemConstant");
        }
    }

    public ResponseEntity<?> deleteConstant(Integer id,String constantOf) {
        try {
            if (id != null) {
                if (constantOf.equals("MINING_SYSTEM")) {
                    Optional<MiningSystemConstant> byId = miningSystemConstantRepository.findById(id);
                    if (byId.isPresent()) {
                        miningSystemConstantRepository.deleteById(id);
                        return converter.apiSuccess200("Constant deleted");
                    }
                    return converter.apiError404("Constant not found");
                }
                if (constantOf.equals("UPPG")) {
                    Optional<UppgConstant> byId = uppgConstantRepository.findById(id);
                    if (byId.isPresent()) {
                        uppgConstantRepository.deleteById(id);
                        return converter.apiSuccess200("Constant deleted");
                    }
                    return converter.apiError404("Constant not found");
                }
                if (constantOf.equals("COLLECTION_POINT")) {
                    Optional<CollectionPointConstant> byId = collectionPointConstantRepository.findById(id);
                    if (byId.isPresent()) {
                        collectionPointConstantRepository.deleteById(id);
                        return converter.apiSuccess200("Constant deleted");
                    }
                    return converter.apiError404("Constant not found");
                }
                if (constantOf.equals("WELL")) {
                    Optional<WellConstant> byId = wellConstantRepository.findById(id);
                    if (byId.isPresent()) {
                        wellConstantRepository.deleteById(id);
                        return converter.apiSuccess200("Constant deleted");
                    }
                    return converter.apiError404("Constant not found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in deleting Constant", e);
        }
    }

    public HttpEntity<?> editMiningConstant(ConstantDto2 dto, Integer miningId) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");
            if (!miningSystemConstantRepository.existsById(dto.getId()))
                return converter.apiError404("Mining Constant not found");

//            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(miningId);
            Optional<MiningSystemConstant> byId = miningSystemConstantRepository.findById(dto.getId());

            MiningSystemConstant miningSystemConstant = new MiningSystemConstant();
            miningSystemConstant = byId.get();
            miningSystemConstant.getConstant().setName(dto.getName());
            miningSystemConstant.getConstant().setDescription(dto.getDescription());
            miningSystemConstant.setValue(dto.getValue());

            MiningSystemConstant save1 = miningSystemConstantRepository.save(miningSystemConstant);

            return converter.apiSuccess201("MiningSystemConstant edited");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Editing ConstantValue");
        }

    }


    // UPPG CONSTANT
    public HttpEntity<?> findAllUppgConst(Integer uppgId) {
        try {
            Optional<Uppg> uppg = uppgRepository.findById(uppgId);
            List<UppgConstant> uppgConstants = uppgConstantRepository.findAllByUppg(uppg.get());
            return converter.apiSuccess200(uppgConstants);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching Constants", e);
        }
    }

    public HttpEntity<?> saveUppgConstant(ConstantDto2 dto, Integer uppgId) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (uppgId == null) return converter.apiError400("uppgId is null");

            Optional<Uppg> uppg = uppgRepository.findById(uppgId);

            Constant constant = new Constant();
            constant.setName(dto.getName());
            constant.setDescription(dto.getDescription());
            Constant constant1 = constantRepository.save(constant);

            UppgConstant uppgConstant = new UppgConstant();
            uppgConstant.setConstant(constant1);
            uppgConstant.setUppg(uppg.get());
            uppgConstant.setValue(dto.getValue());
            uppgConstantRepository.save(uppgConstant);

            return converter.apiSuccess201("Uppg Constant added");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Creating Uppg Constant");
        }
    }

    public HttpEntity<?> editUppgConstant(ConstantDto2 dto, Integer uppgId) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");
            if (!uppgConstantRepository.existsById(dto.getId()))
                return converter.apiError404("Uppg Constant not found");

            UppgConstant uppgConstant = uppgConstantRepository.findById(dto.getId()).get();

            uppgConstant.getConstant().setName(dto.getName());
            uppgConstant.getConstant().setDescription(dto.getDescription());
            uppgConstant.setValue(dto.getValue());

            uppgConstantRepository.save(uppgConstant);

            return converter.apiSuccess201("Uppg Constant edited");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Editing ConstantValue");
        }
    }

    // CollectionPoint CONSTANT
    public HttpEntity<?> findAllCollectionPointConst(Integer collectionId) {
        try {
            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(collectionId);
            List<CollectionPointConstant> collectionPointConstants = collectionPointConstantRepository.findAllByCollectionPoint(collectionPoint.get());
            return converter.apiSuccess200(collectionPointConstants);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching Constants", e);
        }
    }

    public HttpEntity<?> saveCollectionPointConstant(ConstantDto2 dto, Integer collectionPointId) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (collectionPointId == null) return converter.apiError400("collectionPointId is null");

            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(collectionPointId);

            Constant constant = new Constant();
            constant.setName(dto.getName());
            constant.setDescription(dto.getDescription());
            Constant constant1 = constantRepository.save(constant);

            CollectionPointConstant collectionPointConstant = new CollectionPointConstant();
            collectionPointConstant.setConstant(constant1);
            collectionPointConstant.setCollectionPoint(collectionPoint.get());
            collectionPointConstant.setValue(dto.getValue());
            collectionPointConstantRepository.save(collectionPointConstant);

            return converter.apiSuccess201("CollectionPoint Constant added");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Creating CollectionPoint Constant");
        }
    }

    public HttpEntity<?> editCollectionPointConstant(ConstantDto2 dto, Integer collectionPointId) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");
            if (!collectionPointConstantRepository.existsById(dto.getId()))
                return converter.apiError404("CollectionPoint Constant not found");

            CollectionPointConstant collectionPointConstant = collectionPointConstantRepository.findById(dto.getId()).get();

            collectionPointConstant.getConstant().setName(dto.getName());
            collectionPointConstant.getConstant().setDescription(dto.getDescription());
            collectionPointConstant.setValue(dto.getValue());

            collectionPointConstantRepository.save(collectionPointConstant);

            return converter.apiSuccess201("CollectionPoint Constant edited");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Editing ConstantValue");
        }
    }

    // WELL CONSTANT
    public HttpEntity<?> findAllWellConst(Integer wellId) {
        try {
            Optional<Well> well = wellRepository.findById(wellId);
            List<WellConstant> wellConstants = wellConstantRepository.findAllByWell(well.get());
            return converter.apiSuccess200(wellConstants);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error in fetching Constants", e);
        }
    }

    public HttpEntity<?> saveWellConstant(ConstantDto2 dto, Integer wellId) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (wellId == null) return converter.apiError400("wellId is null");

            Optional<Well> well = wellRepository.findById(wellId);

            Constant constant = new Constant();
            constant.setName(dto.getName());
            constant.setDescription(dto.getDescription());
            Constant constant1 = constantRepository.save(constant);

            WellConstant wellConstant = new WellConstant();
            wellConstant.setConstant(constant1);
            wellConstant.setWell(well.get());
            wellConstant.setValue(dto.getValue());
            wellConstantRepository.save(wellConstant);

            return converter.apiSuccess201("Well Constant added");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Creating well Constant");
        }
    }

    public HttpEntity<?> editWellConstant(ConstantDto2 dto, Integer wellId) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");
            if (!wellConstantRepository.existsById(dto.getId()))
                return converter.apiError404("Well Constant not found");

            WellConstant wellConstant = wellConstantRepository.findById(dto.getId()).get();

            wellConstant.getConstant().setName(dto.getName());
            wellConstant.getConstant().setDescription(dto.getDescription());
            wellConstant.setValue(dto.getValue());

            wellConstantRepository.save(wellConstant);

            return converter.apiSuccess201("Well Constant edited");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return converter.apiError409("Error Editing ConstantValue");
        }
    }
}


