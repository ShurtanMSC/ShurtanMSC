package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.CollectionPointDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final Converter converter;
    private final UppgRepository uppgRepository;

    @Autowired
    public CollectionPointService(CollectionPointRepository collectionPointRepository, Converter converter, UppgRepository uppgRepository) {
        this.collectionPointRepository = collectionPointRepository;
        this.converter = converter;
        this.uppgRepository = uppgRepository;
    }

    public ResponseEntity<?> save(CollectionPointDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            if (dto.getUppgId() == null) return converter.apiError400("Uppg id is null");
            CollectionPoint collectionPoint = new CollectionPoint();

            Optional<Uppg> byIdUppg = uppgRepository.findById(dto.getUppgId());
            if (byIdUppg.isPresent()) {
                collectionPoint.setName(dto.getName());
                collectionPoint.setUppg(byIdUppg.get());
                CollectionPoint save = collectionPointRepository.save(collectionPoint);
                CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(save);
                return converter.apiSuccess201("Collection point saved", collectionPointDto);
            }
            return converter.apiError404("Uppg not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error creating collection point");
        }
    }

    public ResponseEntity<?> edit(CollectionPointDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("Id is null");
            CollectionPoint editCollectionPoint;
            Optional<CollectionPoint> byId = collectionPointRepository.findById(dto.getId());
            if (byId.isPresent()) {
                Optional<Uppg> byIdUppg = uppgRepository.findById(dto.getUppgId());
                if (byIdUppg.isPresent()) {
                    editCollectionPoint = byId.get();
                    editCollectionPoint.setName(dto.getName());
                    editCollectionPoint.setUppg(byIdUppg.get());
                    CollectionPoint save = collectionPointRepository.save(editCollectionPoint);
                    CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(save);
                    return converter.apiSuccess200("Edited Mining System", collectionPointDto);
                }
                return converter.apiError404("Uppg not found");
            }
            return converter.apiError404("Collection not found!");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error edit collection point");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
                if (byId.isPresent()) {
                    collectionPointRepository.deleteById(id);
                    return converter.apiSuccess200("Collection point deleted");
                }
                return converter.apiError404("Collection point not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting collection point", e);
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            List<CollectionPoint> all = collectionPointRepository.findAll();
            List<CollectionPointDto> collect = all.stream().map(converter::collectionPointToCollectionPointDto).collect(Collectors.toList());

            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all collection points", e);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id != null) {
                Optional<CollectionPoint> byId = collectionPointRepository.findById(id);
                if (byId.isPresent()) {
                    CollectionPointDto collectionPointDto = converter.collectionPointToCollectionPointDto(byId.get());
                    return converter.apiSuccess200(collectionPointDto);
                }
                return converter.apiError404("CollectionPoint not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding collectionPoint", e);
        }

    }


}
