package tn.temporise.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.NotFoundException;
import tn.temporise.application.exception.ProductNotFound;
import tn.temporise.application.mapper.ProductMapper;
import tn.temporise.domain.model.Categorie;
import tn.temporise.domain.model.ProductRequest;
import tn.temporise.domain.model.ProductResponse;
import tn.temporise.domain.model.Produit;
import tn.temporise.domain.port.CategorieRepo;
import tn.temporise.domain.port.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private  ProductRepo productRepo;
    @Autowired
    private CategorieRepo categorieRepo;
    @Autowired
    private  ProductMapper productMapper;
    // Create: Save a new product
    public ProductResponse createProduct(ProductRequest productRequest) {
        try {
            // Vérification de la catégorie
            Categorie categorie = categorieRepo.findById(productRequest.getCategorie());
            if(categorie==null)
                throw new NotFoundException("Categorie not found");

            Produit produit = productMapper.dtoToModel(productRequest);

            productRepo.save(produit);
            return productMapper.modelToResponse(produit);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create product: " + e.getMessage());
        }
    }

    // Read: Retrieve a product by its ID
    public ProductResponse getProductById(Long id) {
        try {
            Produit produit = productRepo.findById(id);
            if (produit == null) {
                throw new ProductNotFound("Product not found");
            }
            log.info("Product found: {}", produit);
            return productMapper.modelToResponse(produit);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve product: " + e.getMessage());
        }
    }

    // Read: Retrieve all products
    public List<ProductResponse> getAllProducts() {
        try {
            List<ProductResponse> produitList = productRepo.findAll()
                    .stream()
                    .map(productMapper::modelToResponse)
                    .collect(Collectors.toList());
            if (produitList.isEmpty()) {
                throw new ProductNotFound("There are no products available");
            }
            return produitList;
        }catch (ProductNotFound p){
            throw p;
        }
        catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve products: " + e.getMessage());
        }
    }

    // Update: Update an existing product
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        try {
            // Retrieve the existing product
            Produit existingProduit = productRepo.findById(id);
            if (existingProduit == null) {
                throw new ProductNotFound("Product with ID " + id + " not found");
            }
            Categorie categorie = categorieRepo.findById(productRequest.getCategorie());
            if(categorie==null)
                throw new NotFoundException("Categorie not found");


            // Update the existing product with new values from the request
            Produit updatedProduit = new Produit(
                    id, // Use the ID from the path variable
                    productRequest.getNom(), // Update name
                    productRequest.getDescription(), // Update description
                    productRequest.getPrix(), // Update price
                    productRequest.getStock(), // Update stock
                    existingProduit.promotions(), // Keep existing promotions (if applicable)
                    categorie, // Update category (if applicable)
                    existingProduit.quantite() // Keep existing quantity (if applicable)
            );

            // Save the updated product
            Produit savedProduit = productRepo.update(updatedProduit);

            // Map the updated product to a response
            return productMapper.modelToResponse(savedProduit);
        } catch (ProductNotFound e) {
            throw e; // Re-throw ProductNotFound
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update product: " + e.getMessage());
        }
    }

    // Delete: Delete a product by its ID
    public void deleteProduct(Long id) {
        try {
            Produit produit = productRepo.findById(id);
            if (produit == null) { // Correction ici
                throw new ProductNotFound("Produit non trouvé");
            }
            productRepo.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete product: " + e.getMessage());
        }
    }

    // Delete: Delete all products
    public void deleteAllProducts() {
        try {
            List<Produit> produitList = productRepo.findAll();
            if (produitList.isEmpty()) {
                throw new ProductNotFound("There is no product to delete");
            }
            productRepo.deleteAll();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete all products: " + e.getMessage());
        }
    }

}
