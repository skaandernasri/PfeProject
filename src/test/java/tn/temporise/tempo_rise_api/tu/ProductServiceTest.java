package tn.temporise.tempo_rise_api.tu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.temporise.application.exception.ProductNotFound;
import tn.temporise.application.mapper.ProductMapper;
import tn.temporise.application.service.ProductService;
import tn.temporise.domain.model.*;
import tn.temporise.domain.port.ProductRepo;
import tn.temporise.infrastructure.persistence.entity.ProduitEntity;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest  {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    protected ProductRequest productRequest;
    protected ProduitEntity produitEntity;
    protected ProductResponse productResponse;
    protected Produit produit;
    protected Set<Promotion> promotions;
    @BeforeEach
    void setUp() {
        // Initialisation des dates
        Date dateDebut = new Date();
        Date dateFin = new Date(System.currentTimeMillis() + 86400000); // 1 jour après

        // Création d'une promotion
        Promotion promotion = new Promotion(1L, "Summer Sale", "Discount for summer", 10.0, dateDebut, dateFin, null);
        promotions = Set.of(promotion);

        // Initialisation de ProductRequest
        productRequest = new ProductRequest();
        productRequest.setNom("Smartphone");
        productRequest.setDescription("High-end smartphone");
        productRequest.setPrix(999.99);
        productRequest.setStock(10L);

        // Initialisation de ProduitEntity
        produitEntity = new ProduitEntity();
        produitEntity.setId(1L);
        produitEntity.setNom("Smartphone");
        produitEntity.setDescription("High-end smartphone");
        produitEntity.setPrix(999.99);

        // Initialisation de ProductResponse
        productResponse = new ProductResponse();
        productResponse.setId(1L);
        productResponse.setNom("Smartphone");
        productResponse.setDescription("High-end smartphone");
        productResponse.setPrix(999.99);
        productResponse.setStock(10);

        // Initialisation de Produit (record)
        produit = new Produit(1L, "Smartphone", "High-end smartphone", 999.99, 10, promotions, null);
    }
    @Test
    void testCreateProduct() {
        // Mock des dépendances
        when(productMapper.dtoToModel(productRequest)).thenReturn(produit);
        when(productMapper.modelToEntity(produit)).thenReturn(produitEntity);
        when(productRepo.save(produitEntity)).thenReturn(produitEntity);
        when(productMapper.entityToResponse(produitEntity)).thenReturn(productResponse);

        // Appel de la méthode à tester
        ProductResponse response = productService.createProduct(productRequest);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
        verify(productRepo, times(1)).save(produitEntity);
    }

    @Test
    void testGetProductById() {
        // Mock des dépendances
        when(productRepo.findById(1L)).thenReturn(Optional.of(produitEntity));
        when(productMapper.entityToResponse(produitEntity)).thenReturn(productResponse);

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
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        // Vérification de l'exception
        assertThrows(ProductNotFound.class, () -> productService.getProductById(1L));
    }

    @Test
    void testUpdateProduct() {
        // Mock des dépendances
        when(productRepo.findById(1L)).thenReturn(Optional.of(produitEntity));
        when(productMapper.dtoToModel(productRequest)).thenReturn(produit);
        when(productMapper.modelToEntity(produit)).thenReturn(produitEntity);
        when(productRepo.save(produitEntity)).thenReturn(produitEntity);
        when(productMapper.entityToResponse(produitEntity)).thenReturn(productResponse);

        // Appel de la méthode à tester
        ProductResponse response = productService.updateProduct(1L, productRequest);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
    }

    @Test
    void testDeleteProduct() {
        // Mock des dépendances
        when(productRepo.findById(1L)).thenReturn(Optional.of(produitEntity));

        // Appel de la méthode à tester
        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        // Vérifications
        verify(productRepo, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllProducts() {
        // Mock des dépendances
        when(productRepo.findAll()).thenReturn(Collections.singletonList(produitEntity));
        when(productMapper.entityToResponse(produitEntity)).thenReturn(productResponse);

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
        when(productRepo.findAll()).thenReturn(Collections.singletonList(produitEntity));

        // Appel de la méthode à tester
        assertDoesNotThrow(() -> productService.deleteAllProducts());

        // Vérifications
        verify(productRepo, times(1)).deleteAll();
    }
}