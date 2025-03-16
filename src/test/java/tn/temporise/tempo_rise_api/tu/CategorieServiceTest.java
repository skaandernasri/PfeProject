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
import tn.temporise.domain.model.Categorie;
import tn.temporise.domain.model.CategorieRequest;
import tn.temporise.domain.model.CategorieResponse;
import tn.temporise.domain.model.Promotion;
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

    protected Categorie op;
    protected CategorieRequest categorieRequest;
    protected CategorieResponse categorieResponse;
    protected CategorieEntity categorieEntity;
    protected Categorie categorie;

    @BeforeEach
    void setUp() {
        // Initialisation des dates
        Date dateDebut = new Date();
        Date dateFin = new Date(System.currentTimeMillis() + 86400000); // 1 jour après

        // Création d'une promotion
        Promotion promotion = new Promotion(1L, "Summer Sale", "Discount for summer", 10.0, dateDebut, dateFin, null);
        Set<Promotion> promotions = Set.of(promotion);

        // Création d'une catégorie
        categorie = new Categorie(1L, "Electronics", "Gadgets and devices");
        categorieRequest = new CategorieRequest();
        categorieRequest.setNom("Electronics");
        categorieRequest.setDescription("Gadgets and devices");

        // Initialisation de CategorieEntity
        categorieEntity = new CategorieEntity();
        categorieEntity.setId(1L);
        categorieEntity.setNom("Electronics");
        categorieEntity.setDescription("Gadgets and devices");

        // Initialisation de CategorieResponse
        categorieResponse = new CategorieResponse();
        categorieResponse.setId(1L);
        categorieResponse.setNom("Electronics");
        categorieResponse.setDescription("Gadgets and devices");
    }
    @Test
    void testCreateCategorie() {
        // Mock des dépendances
        when(categorieMapper.dtoToModel(categorieRequest)).thenReturn(categorie);
        when(categorieMapper.modelToEntity(any(Categorie.class))).thenReturn(categorieEntity);
        when(categorieRepo.save(categorieEntity)).thenReturn(categorieEntity);
        when(categorieMapper.entityToResponse(categorieEntity)).thenReturn(categorieResponse);

        // Appel de la méthode à tester
        CategorieResponse response = categorieService.createCategorie(categorieRequest);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getNom());
        verify(categorieRepo, times(1)).save(categorieEntity);
    }

    @Test
    void testGetCategorieById() {
        // Mock des dépendances
        when(categorieRepo.findById(1L)).thenReturn(Optional.of(categorieEntity));
        when(categorieMapper.entityToResponse(categorieEntity)).thenReturn(categorieResponse);

        // Appel de la méthode à tester
        CategorieResponse response = categorieService.getCategorieById(1L);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getNom());
    }

    @Test
    void testGetCategorieById_NotFound() {
        // Mock des dépendances
        when(categorieRepo.findById(1L)).thenReturn(Optional.empty());

        // Vérification de l'exception
        assertThrows(NotFoundException.class, () -> categorieService.getCategorieById(1L));
    }

    @Test
    void testGetAllCategories() {
        // Mock des dépendances
        when(categorieRepo.findAll()).thenReturn(Collections.singletonList(categorieEntity));
        when(categorieMapper.entityToResponse(categorieEntity)).thenReturn(categorieResponse);

        // Appel de la méthode à tester
        List<CategorieResponse> responses = categorieService.getAllCategories();

        // Vérifications
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Electronics", responses.get(0).getNom());
    }

    @Test
    void testGetAllCategories_Empty() {
        // Mock des dépendances
        when(categorieRepo.findAll()).thenReturn(Collections.emptyList());

        // Vérification de l'exception
        assertThrows(NotFoundException.class, () -> categorieService.getAllCategories());
    }

    @Test
    void testUpdateCategorie() {
        // Mock des dépendances
        when(categorieRepo.findById(1L)).thenReturn(Optional.of(categorieEntity));
        when(categorieMapper.dtoToModel(categorieRequest)).thenReturn(categorie);
        when(categorieMapper.modelToEntity(any(Categorie.class))).thenReturn(categorieEntity);
        when(categorieRepo.save(categorieEntity)).thenReturn(categorieEntity);
        when(categorieMapper.entityToResponse(categorieEntity)).thenReturn(categorieResponse);

        // Appel de la méthode à tester
        CategorieResponse response = categorieService.updateCategorie(1L, categorieRequest);

        // Vérifications
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getNom());
    }

    @Test
    void testDeleteCategorie() {
        // Mock des dépendances
        when(categorieRepo.findById(1L)).thenReturn(Optional.of(categorieEntity));

        // Appel de la méthode à tester
        assertDoesNotThrow(() -> categorieService.deleteCategorie(1L));

        // Vérifications
        verify(categorieRepo, times(1)).deleteById(1L);
    }
}