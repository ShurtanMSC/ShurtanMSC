package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.ConstantDto;
import uz.neft.dto.constantValue.ConstantValueDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.entity.Well;
import uz.neft.entity.variables.Constant;
import uz.neft.entity.variables.ConstantValue;
import uz.neft.payload.ApiResponse;
import uz.neft.payload.ApiResponseObject;
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

    public HttpEntity<?> saveValue(ConstantValueDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            ApiResponseObject helper = (ApiResponseObject) helper(dto);

            if (helper.isSuccess()){
                ConstantValue constantValue= (ConstantValue) helper.getObject();
                return converter.apiSuccess201(helper.getMessage(),converter.constantValueToConstValueDto(constantValuesRepository.save(constantValue)));
            } else return converter.apiError409(helper.getMessage(),null);

        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error Creating ConstantValue");
        }
    }

    public HttpEntity<?> editValue(ConstantValueDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("id is null");
            if (!constantValuesRepository.existsById(dto.getId())) return converter.apiError404("Constant value not found");
            ApiResponseObject helper = (ApiResponseObject) helper(dto);
            if (helper.isSuccess()){
                ConstantValue constantValue= (ConstantValue) helper.getObject();
                return converter.apiSuccess200("Successfully edited",converter.constantValueToConstValueDto(constantValuesRepository.save(constantValue)));
            } else return converter.apiError409(helper.getMessage(),null);


        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error Creating ConstantValue");
        }
    }

    public HttpEntity<?> deleteValue(Integer id) {

        try {
            if (id == null) return converter.apiError400("Id null");
            Optional<ConstantValue> byId = constantValuesRepository.findById(id);

            if (!byId.isPresent()) return converter.apiError404("ConstantValue not found");

            constantValuesRepository.deleteById(id);
            return converter.apiSuccess200("ConstantValue deleted ");
        } catch (Exception e) {
            e.printStackTrace();
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
            return converter.apiError409("Error in fetching ConstantValues", e);
        }
    }

    public HttpEntity<?> byIdValue(Integer id) {
        try {
            if (id == null) return converter.apiError400("Id null");
            Optional<ConstantValue> byId = constantValuesRepository.findById(id);
            if (!byId.isPresent()) return converter.apiError404("Constant not found");
            ConstantValueDto constantValueDto = converter.constantValueToConstValueDto(byId.get());
            return converter.apiSuccess200(constantValueDto);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding Constant", e);
        }
    }

    public ApiResponse helper(ConstantValueDto dto) {
        if(dto.getConstantId()==null) return converter.apiError("Constant id is null",null);
        Optional<Constant> constant = constantRepository.findById(dto.getConstantId());
        if (!constant.isPresent()) return converter.apiError("Constant not found",null);
        if(dto.getValue()==null) return converter.apiError("Value id is null",null);
        ConstantValue constantValue=new ConstantValue();
        constantValue.setConstant(constant.get());
        constantValue.setValue(dto.getValue());
        if (dto.getId()!=null) constantValue.setId(dto.getId());
        if (dto.getMiningSystemId()!=null){
            Optional<MiningSystem> miningSystem=miningSystemRepository.findById(dto.getMiningSystemId());
            if (miningSystem.isPresent()){
                constantValue.setMiningSystem(miningSystem.get());
                if (dto.getUppgId()!=null){
                    Optional<Uppg> uppg = uppgRepository.findById(dto.getUppgId());
                    if (uppg.isPresent()){
                        constantValue.setUppg(uppg.get());
                        if (dto.getCollectionPointId()!=null){
                            Optional<CollectionPoint> collectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
                            if (collectionPoint.isPresent()){
                                constantValue.setCollectionPoint(collectionPoint.get());
                                if (dto.getWellId()!=null){
                                    Optional<Well> well = wellRepository.findById(dto.getWellId());
                                    if (well.isPresent()){
                                        constantValue.setWell(well.get());
                                        return converter.apiSuccess("Well constant value added",constantValue);
                                    } return converter.apiError("Well not found",null);
                                } return converter.apiSuccess("Collection point constant value added",constantValue);
                            } return converter.apiError("Collection point not found",null);
                        } return converter.apiSuccess("Uppg constant value added",constantValue);
                    } return converter.apiError("Uppg not found",null);
                } return converter.apiSuccess("Mining system constant value added",constantValue);
            } return converter.apiError("Mining system not found",null);
        } return converter.apiError("Mining system id is null",null);

    }
}


