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
    private Categorie categorie = new Categorie(1L, "Electronics", "Gadgets and devices");

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setNom("Smartphone");
        productRequest.setDescription("High-end smartphone");
        productRequest.setPrix(999.99);
        productRequest.setStock(10L);
        productRequest.setCategorie(1L);

        produit = new Produit(1L, "Smartphone", "High-end smartphone", 999.99, 10L, null, null, 0);
        productResponse = new ProductResponse(1L, "Smartphone", "High-end smartphone", 55.2, 99999, new ProductResponseCategorie());
    }

    @Test
    void testCreateProduct() {
        when(productMapper.dtoToModel(productRequest)).thenReturn(produit);
        when(productRepo.save(produit)).thenReturn(produit);
        when(productMapper.modelToResponse(produit)).thenReturn(productResponse);
        when(categorieRepo.findById(1L)).thenReturn(categorie);

        ProductResponse response = productService.createProduct(productRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
        verify(productRepo, times(1)).save(produit);
        verify(categorieRepo, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct_CategorieNotFound() {
        when(categorieRepo.findById(1L)).thenReturn(null);
        assertThrows(InternalServerErrorException.class, () -> productService.createProduct(productRequest));
        verify(categorieRepo, times(1)).findById(1L);
        verify(productRepo, never()).save(any());
    }

    @Test
    void testGetProductById() {
        when(productRepo.findById(1L)).thenReturn(produit);
        when(productMapper.modelToResponse(produit)).thenReturn(productResponse);

        ProductResponse response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepo.findById(1L)).thenReturn(null);
        assertThrows(InternalServerErrorException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testUpdateProduct() {
        Produit existingProduit = new Produit(1L, "Old Name", "Old Description", 500.0, 5L, null, null, 0);
        when(productRepo.findById(1L)).thenReturn(existingProduit);
        when(categorieRepo.findById(1L)).thenReturn(categorie);

        Produit updatedProduit = new Produit(1L, "Smartphone", "High-end smartphone", 55.2, 99999L, null, categorie, 0);
        when(productRepo.update(updatedProduit)).thenReturn(updatedProduit);

        productResponse = new ProductResponse(1L, "Smartphone", "High-end smartphone", 55.2, 99999, new ProductResponseCategorie());
        when(productMapper.modelToResponse(updatedProduit)).thenReturn(productResponse);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setNom("Smartphone");
        productRequest.setDescription("High-end smartphone");
        productRequest.setPrix(55.2);
        productRequest.setStock(99999L);
        productRequest.setCategorie(1L);

        ProductResponse response = productService.updateProduct(1L, productRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Smartphone", response.getNom());
        assertEquals("High-end smartphone", response.getDescription());
        assertEquals(55.2, response.getPrix());
        assertEquals(99999, response.getStock());

        verify(productRepo, times(1)).update(updatedProduit);
    }

    @Test
    void testDeleteProduct() {
        when(productRepo.findById(1L)).thenReturn(produit);
        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepo, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllProducts() {
        when(productRepo.findAll()).thenReturn(List.of(produit));
        when(productMapper.modelToResponse(produit)).thenReturn(productResponse);

        List<ProductResponse> responses = productService.getAllProducts();

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Smartphone", responses.get(0).getNom());
    }

    @Test
    void testGetAllProducts_Empty() {
        when(productRepo.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ProductNotFound.class, () -> productService.getAllProducts());
    }

    @Test
    void testDeleteAllProducts() {
        when(productRepo.findAll()).thenReturn(List.of(produit));
        assertDoesNotThrow(() -> productService.deleteAllProducts());
        verify(productRepo, times(1)).deleteAll();
    }
}