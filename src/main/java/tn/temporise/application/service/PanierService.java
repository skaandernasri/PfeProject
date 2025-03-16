package tn.temporise.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.NotFoundException;
import tn.temporise.application.mapper.PanierMapper;
import tn.temporise.domain.model.Panier;
import tn.temporise.domain.model.PanierRequest;
import tn.temporise.domain.model.PanierResponse;
import tn.temporise.domain.port.PanierRepo;
import tn.temporise.domain.port.ProductRepo;
import tn.temporise.infrastructure.persistence.entity.PanierEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PanierService {
    private final PanierRepo panierRepo;
    private final PanierMapper panierMapper;
    private final ProductRepo productRepo;
    // Create: Save a new Panier
//    public PanierResponse createPanier(PanierRequest panierRequest) {
//        try {
//            // Convert request DTO to model
//            Panier panier = panierMapper.dtoToModel(panierRequest);
//            log.info("panier model : "+ panier);
//            log.info("panier request : "+panierRequest);
//            PanierEntity panierEntity = panierMapper.modelToEntity(panier);
//            Set<ProduitEntity> produits = panierEntity.getProduits().stream()
//                    .map(produit -> productRepo.findById(produit.getId()).orElseThrow(()->new NotFoundException("Product not found")))
//                    .collect(Collectors.toSet());
//            for(ProduitEntity produit:produits){
//                log.info("liste produits dans le panier: "+ produit.getId()+"  "+produit.getNom() );
//            }
//
//            PanierEntity savedEntity = panierRepo.save(panierEntity); // Save entity to DB
//            Panier panierModel=panierMapper.entityToModel(savedEntity);
//            return panierMapper.modelToResponse(panierModel); // Convert the saved entity back to response DTO
//        }catch (NotFoundException e){
//            throw e;
//        }
//        catch (Exception e) {
//            throw new InternalServerErrorException("Failed to create Panier: " + e.getMessage());
//        }
//    }

    // Read: Retrieve a Panier by its ID
    public PanierResponse getPanierById(Long id) {
        try {
            PanierEntity panierEntity = panierRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Panier not found with id: " + id)); // Find Panier by ID
            log.info("panier by id"+panierEntity.getId());
            return panierMapper.entityToResponse(panierEntity);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve Panier: " + e.getMessage());
        }
    }

    // Read: Retrieve all Paniers
    public List<PanierResponse> getAllPaniers() {
        try {
            List<PanierResponse> panierList = panierRepo.findAll() // Fetch all Paniers from DB
                    .stream()
                    .map(panierMapper::entityToResponse)
                    .collect(Collectors.toList());
            if (panierList.isEmpty()) {
                throw new NotFoundException("There are no Paniers");
            }
            return panierList;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve Paniers: " + e.getMessage());
        }
    }
    // Read: Retrieve all Paniers for a specific user
    public List<PanierResponse> getAllPaniersUser(Long id) {
        try {
            List<PanierResponse> panierList = panierRepo.findByUtilisateurId(id) // Fetch all Paniers from DB
                    .stream()
                    .map(panierMapper::entityToResponse) // Map entity to response DTO
                    .collect(Collectors.toList());
            if (panierList.isEmpty()) {
                throw new NotFoundException("There are no Paniers");
            }
            return panierList;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve Paniers: " + e.getMessage());
        }
    }

    // Update: Update an existing Panier
    public PanierResponse updatePanier(Long id, PanierRequest panierRequest) {
        try {
            Optional<PanierEntity> optionalPanier = panierRepo.findById(id)
                    .map(existingEntity -> {
                        Panier panier = panierMapper.dtoToModel(panierRequest); // Convert DTO to model
                        PanierEntity updatedEntity = panierMapper.modelToEntity(panier); // Convert model to entity
                        updatedEntity.setId(id); // Ensure the ID remains the same
                        return panierRepo.save(updatedEntity); // Save updated entity
                    });
            if (optionalPanier.isPresent()) {
                return panierMapper.entityToResponse(optionalPanier.get()); // Convert to response DTO and return
            }
            throw new NotFoundException("Panier not found");
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update Panier: " + e.getMessage());
        }
    }

    // Delete: Delete a Panier by its ID
    public void deletePanier(Long id) {
        try {
            PanierEntity panierEntity = panierRepo.findById(id)
                    .orElseThrow(()->new NotFoundException("Panier not found")); // Find Panier by ID
            panierRepo.deleteById(id); // Delete Panier by ID
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete Panier: " + e.getMessage());
        }
    }

    // Delete: Delete all Paniers
    public void deleteAllPaniers() {
        try {
            List<PanierEntity> panierList = panierRepo.findAll(); // Fetch all Paniers
            if (panierList.isEmpty()) {
                throw new NotFoundException("There are no Paniers to delete");
            }
            panierRepo.deleteAll(); // Delete all Paniers
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete all Paniers: " + e.getMessage());
        }
    }
}
