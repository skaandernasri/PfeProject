package tn.temporise.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.ProductNotFound;
import tn.temporise.application.mapper.ProductMapper;
import tn.temporise.domain.model.ProductRequest;
import tn.temporise.domain.model.ProductResponse;
import tn.temporise.domain.model.Produit;
import tn.temporise.domain.port.ProductRepo;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private  ProductRepo productRepo;
    @Autowired
    private  ProductMapper productMapper;
    // Create: Save a new product
    public ProductResponse createProduct(ProductRequest productRequest) {
        try {
            Produit produit = productMapper.dtoToModel(productRequest);
            ProduitEntity produitEntity = productMapper.modelToEntity(produit);
            ProduitEntity savedEntity = productRepo.save(produitEntity);
            return productMapper.entityToResponse(savedEntity);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create product: " + e.getMessage());
        }
    }

    // Read: Retrieve a product by its ID
    public ProductResponse getProductById(Long id) {
        try {
            ProduitEntity produit = productRepo.findById(id)
                    .orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));
            log.info("---------Product before getProductById Service------ " + produit + "---id: " + id);
            return productMapper.entityToResponse(produit);
        }catch (ProductNotFound e){
            throw e;
        }catch (Exception e){
            throw new InternalServerErrorException("Failed to retrieve product: "+e.getMessage());
        }

    }

    // Read: Retrieve all products
    public List<ProductResponse> getAllProducts() {
        try {
            List<ProductResponse> produitList=productRepo.findAll()
                    .stream()
                    .map(productMapper::entityToResponse)
                    .collect(Collectors.toList());
            if(produitList.isEmpty())
                throw new ProductNotFound("There is no product");
            return produitList;
        }catch (ProductNotFound e){
            throw  e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve products: " + e.getMessage());
        }
    }

    // Update: Update an existing product
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        try {
            Optional<ProduitEntity> optionalProduit = productRepo.findById(id)
                    .map(existingEntity -> {
                        Produit produit = productMapper.dtoToModel(productRequest);
                        ProduitEntity updatedEntity = productMapper.modelToEntity(produit);
                        updatedEntity.setId(id); // Ensure the ID remains the same
                        return productRepo.save(updatedEntity);
                    });
            if (optionalProduit.isPresent())
                return productMapper.entityToResponse(optionalProduit.get());
            throw new ProductNotFound("produit non trouvé");
        }catch (ProductNotFound e){
            throw  e;}

        catch (Exception e){
            throw new InternalServerErrorException("Failed to delete product: "+ e.getMessage());
        }
    }

    // Delete: Delete a product by its ID
    public void deleteProduct(Long id) {
        try {
            log.info("product before delete : "+productRepo.findById(id));
            Optional<ProduitEntity> produit=productRepo.findById(id);
            if (produit.isEmpty()){
                throw new ProductNotFound("produit non trouvé");
            }
            productRepo.deleteById(id);
        }catch (ProductNotFound e){
            throw  e;
        }
        catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete product: " + e.getMessage());
        }
    }

    // Delete: Delete all products
    public void deleteAllProducts() {
        try {
            List<Produit> produitList=productRepo.findAll()
                    .stream()
                    .map(productMapper::entityToModel)
                    .toList();
            if(produitList.isEmpty()){
                throw new ProductNotFound("there is no product to delete");
            }
            productRepo.deleteAll();
        }catch (ProductNotFound e){
            throw  e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete all products: " + e.getMessage());
        }
    }

}
