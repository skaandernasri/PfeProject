package tn.temporise.tempo_rise_api.tu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.temporise.application.exception.InternalServerErrorException;
import tn.temporise.application.exception.ProductNotFound;
import tn.temporise.application.mapper.ProductMapper;
import tn.temporise.application.service.ProductService;
import tn.temporise.domain.model.*;
import tn.temporise.domain.port.CategorieRepo;
import tn.temporise.domain.port.ProductRepo;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CategorieRepo categorieRepo;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductRequest productRequest;
    private Produit produit;
    private ProductResponse productResponse;
    Categorie categorie = new Categorie(1L, "Electronics", "Gadgets and devices");

    @BeforeEach
    void setUp() {
        // Initialisation des données de test
        productRequest = new ProductRequest();
        productRequest.setNom("Smartphone");
        productRequest.setDescription("High-end smartphone");
        productRequest.setPrix(999.99);
        productRequest.setStock(10L);
        productRequest.setCategorie(1L); // Assuming category ID 1

        produit = new Produit(1L, "Smartphone", "High-end smartphone", 999.99, 10L, null, null, 0);
        productResponse = new ProductResponse(1L, "Smartphone","High-end smartphone", 55.2, 99999, new ProductResponseCategorie());
    }

    @Test
    void testCreateProduct() {
        // Mock des dépendances
        when(productMapper.dtoToModel(productRequest)).thenReturn(produit);
        when(productRepo.save(produit)).thenReturn(produit);
        when(productMapper.modelToResponse(produit)).thenReturn(productResponse);
        // Mock the Categorie to simulate it exists
        when(categorieRepo.findById(1L)).thenReturn(categorie);

        // Appel de la méthode à tester
        ProductResponse response = productService.createProduct(productRequest);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
        verify(productRepo, times(1)).save(produit);
        verify(categorieRepo, times(1)).findById(1L); // Verify that the Categorie was checked
    }

    @Test
    void testCreateProduct_CategorieNotFound() {
        // Mock des dépendances
        when(categorieRepo.findById(1L)).thenReturn(null); // Simulate Categorie not found

        // Appel de la méthode à tester et vérification de l'exception
        assertThrows(InternalServerErrorException.class, () -> productService.createProduct(productRequest));

        // Vérifications
        verify(categorieRepo, times(1)).findById(1L); // Verify that the Categorie was checked
        verify(productRepo, never()).save(any()); // Ensure the product was not saved
    }

    @Test
    void testGetProductById() {
        // Mock des dépendances
        when(productRepo.findById(1L)).thenReturn(produit);
        when(productMapper.modelToResponse(produit)).thenReturn(productResponse);

        // Appel de la méthode à tester
        ProductResponse response = productService.getProductById(1L);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
    }

    @Test
    void testGetProductById_NotFound() {
        // Mock des dépendances
        when(productRepo.findById(1L)).thenReturn(null);

        // Vérification de l'exception
        assertThrows(InternalServerErrorException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testUpdateProduct() {
        // Mock des dépendances
        Produit existingProduit = new Produit(1L, "Old Name", "Old Description", 500.0, 5L, null, null, 0);
        when(productRepo.findById(1L)).thenReturn(existingProduit); // Simulate existing product

        // Create a Categorie object for the updated product
        Categorie categorie = new Categorie(1L, "Electronics", "Gadgets and devices");
        when(categorieRepo.findById(1L)).thenReturn(categorie); // Simulate fetching the category

        // Create the updated Produit object with the correct values
        Produit updatedProduit = new Produit(1L, "Smartphone", "High-end smartphone", 55.2, 99999L, null, categorie, 0);
        when(productRepo.update(updatedProduit)).thenReturn(updatedProduit); // Simulate successful update

        // Create the expected ProductResponse
        productResponse = new ProductResponse(1L, "Smartphone", "High-end smartphone", 55.2, 99999, new ProductResponseCategorie());
        when(productMapper.modelToResponse(updatedProduit)).thenReturn(productResponse); // Map model to response

        // Create a ProductRequest with updated values
        ProductRequest productRequest = new ProductRequest();
        productRequest.setNom("Smartphone");
        productRequest.setDescription("High-end smartphone");
        productRequest.setPrix(55.2);
        productRequest.setStock(99999L);
        productRequest.setCategorie(1L); // Assuming category ID 1

        // Appel de la méthode à tester
        ProductResponse response = productService.updateProduct(1L, productRequest);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
        assertEquals("High-end smartphone", response.getDescription());
        assertEquals(55.2, response.getPrix());
        assertEquals(99999, response.getStock());

        // Verify that the existing product was updated with the correct Produit object
        verify(productRepo, times(1)).update(updatedProduit);
    }

    @Test
    void testDeleteProduct() {
        // Mock des dépendances
        when(productRepo.findById(1L)).thenReturn(produit);

        // Appel de la méthode à tester
        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        // Vérifications
        verify(productRepo, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllProducts() {
        // Mock des dépendances
        when(productRepo.findAll()).thenReturn(List.of(produit));
        when(productMapper.modelToResponse(produit)).thenReturn(productResponse);

        // Appel de la méthode à tester
        List<ProductResponse> responses = productService.getAllProducts();

        // Vérifications
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Smartphone", responses.get(0).getNom());
    }

    @Test
    void testGetAllProducts_Empty() {
        // Mock des dépendances
        when(productRepo.findAll()).thenReturn(Collections.emptyList());

        // Vérification de l'exception
        assertThrows(ProductNotFound.class, () -> productService.getAllProducts());
    }

    @Test
    void testDeleteAllProducts() {
        // Mock des dépendances
        when(productRepo.findAll()).thenReturn(List.of(produit));

        // Appel de la méthode à tester
        assertDoesNotThrow(() -> productService.deleteAllProducts());

        // Vérifications
        verify(productRepo, times(1)).deleteAll();
    }
}