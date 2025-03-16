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

import java.util.List;


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
            Categorie categorie = categorieMapper.dtoToModel(categorieRequest); // Convert DTO to model
            Categorie savedCategorie = categorieRepo.save(categorie); // Save model to DB
            return categorieMapper.modelToResponse(savedCategorie); // Convert model to response
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create category: " + e.getMessage());
        }
    }

    // Read: Retrieve a category by its ID
    public CategorieResponse getCategorieById(Long id) {
        try {
            Categorie categorie = categorieRepo.findById(id);
            if (categorie == null) {
                throw new NotFoundException("Categorie not found with id: " + id);
            }
            return categorieMapper.modelToResponse(categorie);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve category: " + e.getMessage());
        }
    }

    // Read: Retrieve all categories
    public List<CategorieResponse> getAllCategories() {
        try {
            List<CategorieResponse> categorieList = categorieRepo.findAll()
                    .stream()
                    .map(categorieMapper::modelToResponse)
                    .toList();
            if (categorieList.isEmpty()) {
                throw new NotFoundException("There are no categories");
            }
            return categorieList;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve categories: " + e.getMessage());
        }
    }

    // Update: Update an existing category
    public CategorieResponse updateCategorie(Long id, CategorieRequest categorieRequest) {
        try {
            Categorie existingCategorie = categorieRepo.findById(id);
            if (existingCategorie == null) {
                throw new NotFoundException("Category not found with id: " + id);
            }

            Categorie updatedCategorie = new Categorie(
                    id, // Assignation de l'ID
                    categorieRequest.getNom(),
                    categorieRequest.getDescription()
            );

            updatedCategorie = categorieRepo.update(updatedCategorie);
            if (updatedCategorie == null) {
                throw new InternalServerErrorException("Failed to update category: Update returned null");
            }
            CategorieResponse response = categorieMapper.modelToResponse(updatedCategorie);
            if (response == null) {
                throw new InternalServerErrorException("Failed to map Categorie to CategorieResponse");
            }
            return response;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update category: " + e.getMessage());
        }
    }

    // Delete: Delete a category by its ID
    public void deleteCategorie(Long id) {
        try {
            Categorie categorie = categorieRepo.findById(id);
            if (categorie == null) {
                throw new NotFoundException("Category not found with id: " + id);
            }
            categorieRepo.deleteById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete category: " + e.getMessage());
        }
    }

    // Delete: Delete all categories
    public void deleteAllCategories() {
        try {
            List<Categorie> categorieList = categorieRepo.findAll();
            if (categorieList.isEmpty()) {
                throw new NotFoundException("There are no categories to delete");
            }
            categorieRepo.deleteAll();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete all categories: " + e.getMessage());
        }
    }
}
