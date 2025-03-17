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
import tn.temporise.application.exception.NotFoundException;
import tn.temporise.application.mapper.CategorieMapper;
import tn.temporise.application.service.CategorieService;
import tn.temporise.domain.model.*;
import tn.temporise.domain.port.CategorieRepo;
import tn.temporise.infrastructure.persistence.entity.CategorieEntity;

@ExtendWith(MockitoExtension.class)
public class CategorieServiceTest {

    @Mock
    private CategorieRepo categorieRepo;

    @Mock
    private CategorieMapper categorieMapper;

    @InjectMocks
    private CategorieService categorieService;

    private CategorieRequest categorieRequest;
    private CategorieResponse categorieResponse;
    private Categorie categorie;
    private CategorieEntity categorieEntity;

    @BeforeEach
    void setUp() {
        Date dateDebut = new Date();
        Date dateFin = new Date(System.currentTimeMillis() + 86400000);

        Promotion promotion = new Promotion(1L, "Summer Sale", "Discount for summer", 10.0, dateDebut, dateFin, null);
        Set<Promotion> promotions = Set.of(promotion);

        categorie = new Categorie(1L, "Electronics", "Gadgets and devices");
        categorieRequest = new CategorieRequest();
        categorieRequest.setNom("Electronics");
        categorieRequest.setDescription("Gadgets and devices");

        categorieEntity = new CategorieEntity();
        categorieEntity.setId(1L);
        categorieEntity.setNom("Electronics");
        categorieEntity.setDescription("Gadgets and devices");

        categorieResponse = new CategorieResponse();
        categorieResponse.setId(1L);
        categorieResponse.setNom("Electronics");
        categorieResponse.setDescription("Gadgets and devices");
    }

    @Test
    void testCreateCategorie() {
        when(categorieMapper.dtoToModel(categorieRequest)).thenReturn(categorie);
        when(categorieRepo.save(categorie)).thenReturn(categorie);
        when(categorieMapper.modelToResponse(categorie)).thenReturn(categorieResponse);

        CategorieResponse response = categorieService.createCategorie(categorieRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getNom());
        verify(categorieRepo, times(1)).save(categorie);
    }

    @Test
    void testGetCategorieById() {
        when(categorieRepo.findById(1L)).thenReturn(categorie);
        when(categorieMapper.modelToResponse(categorie)).thenReturn(categorieResponse);

        CategorieResponse response = categorieService.getCategorieById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getNom());
    }

    @Test
    void testGetCategorieById_NotFound() {
        when(categorieRepo.findById(1L)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> categorieService.getCategorieById(1L));
    }

    @Test
    void testGetAllCategories() {
        when(categorieRepo.findAll()).thenReturn(Collections.singletonList(categorie));
        when(categorieMapper.modelToResponse(categorie)).thenReturn(categorieResponse);

        List<CategorieResponse> responses = categorieService.getAllCategories();

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Electronics", responses.getFirst().getNom());
    }

    @Test
    void testGetAllCategories_Empty() {
        when(categorieRepo.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> categorieService.getAllCategories());
    }

    @Test
    void testUpdateCategorie() {
        when(categorieRepo.findById(1L)).thenReturn(categorie);
        when(categorieRepo.update(categorie)).thenReturn(categorie);
        when(categorieMapper.modelToResponse(categorie)).thenReturn(categorieResponse);

        CategorieResponse response = categorieService.updateCategorie(1L, categorieRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getNom());
    }

    @Test
    void testDeleteCategorie() {
        when(categorieRepo.findById(1L)).thenReturn(categorie);
        assertDoesNotThrow(() -> categorieService.deleteCategorie(1L));
        verify(categorieRepo, times(1)).deleteById(1L);
    }
}