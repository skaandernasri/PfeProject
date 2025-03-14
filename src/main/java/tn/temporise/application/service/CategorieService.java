package tn.temporise.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.NotFoundException;
import tn.temporise.application.mapper.CategorieMapper;
import tn.temporise.domain.model.Categorie;
import tn.temporise.domain.model.CategorieRequest;
import tn.temporise.domain.model.CategorieResponse;
import tn.temporise.domain.port.CategorieRepo;
import tn.temporise.infrastructure.persistence.entity.CategorieEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategorieService {
    @Autowired
    private CategorieRepo categorieRepo;
    @Autowired
    private CategorieMapper categorieMapper;
    // Create: Save a new category
    public CategorieResponse createCategorie(CategorieRequest categorieRequest) {
        try {
            Categorie categorie = categorieMapper.dtoToModel(categorieRequest); // Convert request DTO to model
            CategorieEntity categorieEntity = categorieMapper.modelToEntity(categorie); // Convert model to entity
            CategorieEntity savedEntity = categorieRepo.save(categorieEntity); // Save entity to DB
            return categorieMapper.entityToResponse(savedEntity); // Convert the saved entity back to model
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create category: " + e.getMessage());
        }
    }

    // Read: Retrieve a category by its ID
    public CategorieResponse getCategorieById(Long id) {
        try {
            Optional<CategorieEntity> categorie = categorieRepo.findById(id); // Find category by ID
            if (categorie.isPresent()) {
                return categorieMapper.entityToResponse(categorie.get()); // Convert entity to model
            }
            throw new NotFoundException("Category not found");
        }
        catch (NotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new InternalServerErrorException("Failed to retrieve Categorie : "+e.getMessage());
        }
    }

    // Read: Retrieve all categories
    public List<CategorieResponse> getAllCategories() {
        try {
            List<CategorieResponse> categorieList = categorieRepo.findAll() // Fetch all categories from DB
                    .stream()
                    .map(categorieMapper::entityToResponse) // Map entity to model
                    .collect(Collectors.toList());
            if (categorieList.isEmpty()) {
                throw new NotFoundException("There is no categories");
            }
            return categorieList;
        }catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve categories: " + e.getMessage());
        }
    }

    // Update: Update an existing category
    public CategorieResponse updateCategorie(Long id, CategorieRequest categorieRequest) {
        try {
            Optional<CategorieEntity> optionalCategorie = categorieRepo.findById(id)
                    .map(existingEntity -> {
                        Categorie categorie = categorieMapper.dtoToModel(categorieRequest); // Convert DTO to model
                        CategorieEntity updatedEntity = categorieMapper.modelToEntity(categorie); // Convert model to entity
                        updatedEntity.setId(id); // Ensure the ID remains the same
                        return categorieRepo.save(updatedEntity); // Save updated entity
                    });
            if (optionalCategorie.isPresent()) {
                return categorieMapper.entityToResponse(optionalCategorie.get()); // Convert to model and return
            }
            throw new NotFoundException("Category not found");
        }catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update category: " + e.getMessage());
        }
    }

    // Delete: Delete a category by its ID
    public void deleteCategorie(Long id) {
        try {
            Optional<CategorieEntity> categorie = categorieRepo.findById(id); // Find category by ID
            if (categorie.isEmpty()) {
                throw new NotFoundException("Category not found");
            }
            categorieRepo.deleteById(id); // Delete category by ID
        }catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete category: " + e.getMessage());
        }
    }

    // Delete: Delete all categories
    public void deleteAllCategories() {
        try {
            List<Categorie> categorieList = categorieRepo.findAll() // Fetch all categories
                    .stream()
                    .map(categorieMapper::entityToModel) // Map entity to model
                    .toList();
            if (categorieList.isEmpty()) {
                throw new NotFoundException("There are no categories to delete");
            }
            categorieRepo.deleteAll(); // Delete all categories
        }catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete all categories: " + e.getMessage());
        }
    }
}
