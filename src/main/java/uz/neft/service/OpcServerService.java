package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.OpcServer;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.OpcServerRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpcServerService {

    private final OpcServerRepository opcServerRepository;
    private final Converter converter;

    @Autowired
    public OpcServerService(OpcServerRepository opcServerRepository, Converter converter, CollectionPointRepository collectionPointRepository) {
        this.opcServerRepository = opcServerRepository;
        this.converter = converter;
    }

    public ResponseEntity<?> save(OpcServer opcServer) {
        try {
            if (opcServer.getId() == null) {
                OpcServer save = opcServerRepository.save(opcServer);
                return converter.apiSuccess200("ops server saved", save);
            }
            return converter.apiError400("id shouldn't be sent");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error creating opcServer");
        }
    }

    public ResponseEntity<?> edit(OpcServer opcServer) {
        try {
            if (opcServer.getId() == null) return converter.apiError400("Id null");

            OpcServer editOpcServer;
            Optional<OpcServer> byId = opcServerRepository.findById(opcServer.getId());
            if (byId.isPresent()) {
                editOpcServer = byId.get();
                editOpcServer.setName(opcServer.getName());
                editOpcServer.setDescription(opcServer.getDescription());
                editOpcServer.setAddress(opcServer.getAddress());
                editOpcServer.setUrl(opcServer.getUrl());
                editOpcServer.setType(opcServer.getType());

                editOpcServer = opcServerRepository.save(editOpcServer);

                return converter.apiSuccess200("opcServer edited", editOpcServer);
            }
            return converter.apiError404("OpcServer not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error editing opcServer");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<OpcServer> byId = opcServerRepository.findById(id);
                if (byId.isPresent()) {
                    opcServerRepository.delete(byId.get());
                    return converter.apiSuccess200("OpcServer deleted");
                }
                return converter.apiError404("OpcServer not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting opcServer", e);
        }

    }

    public ResponseEntity<?> findAll() {
        try {
            List<OpcServer> all = opcServerRepository.findAllByOrderByCreatedAtDesc();
            return converter.apiSuccess200(all);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all opcServers", e);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id != null) {
                Optional<OpcServer> byId = opcServerRepository.findById(id);
                if (byId.isPresent()) {
                    return converter.apiSuccess200(byId.get());
                }
                return converter.apiError404("OpcServer not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding opcServer", e);
        }

    }
}
