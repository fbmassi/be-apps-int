package com.uade.beappsint.service;

import com.uade.beappsint.dto.ImageDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Image;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.exception.ResourceNotFoundException;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.repository.ImageRepository;
import com.uade.beappsint.repository.ProductRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        imageRepository = Mockito.mock(ImageRepository.class);
        authService = Mockito.mock(AuthService.class);
        productService = new ProductServiceImpl(productRepository, customerRepository, imageRepository, authService);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product product = new Product();
        product.setId(1L);

        Customer creator = new Customer();
        creator.setEmail("creator@example.com");
        product.setCreatedBy(creator);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        // Act
        List<ProductDTO> result = productService.getAllProducts();

        // Assert
        assertEquals(1, result.size());
        assertNotNull(result.get(0));
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        Product product = new Product();
        product.setId(1L);

        Customer creator = new Customer();
        creator.setEmail("test@example.com");
        product.setCreatedBy(creator);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        ProductDTO result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testCreateProduct_Success() {
        // Arrange
        Customer mockAdmin = new Customer();
        mockAdmin.setAdmin(true);
        Product product = new Product();
        product.setName("Product A");

        when(authService.getAuthenticatedCustomer()).thenReturn(mockAdmin);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        ProductDTO result = productService.createProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals("Product A", result.getName());
    }

    @Test
    void testCreateProduct_NotAdmin() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setAdmin(false);
        Product product = new Product();

        when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.createProduct(product));
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Customer mockAdmin = new Customer();
        mockAdmin.setId(1);
        mockAdmin.setAdmin(true);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setCreatedBy(mockAdmin);

        when(authService.getAuthenticatedCustomer()).thenReturn(mockAdmin);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // Act
        ProductDTO updatedProduct = productService.updateProduct(1L, existingProduct);

        // Assert
        assertNotNull(updatedProduct);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        Customer mockAdmin = new Customer();
        mockAdmin.setAdmin(true);

        when(authService.getAuthenticatedCustomer()).thenReturn(mockAdmin);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.updateProduct(1L, new Product()));
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        Customer mockAdmin = new Customer();
        mockAdmin.setId(1);
        mockAdmin.setAdmin(true);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setCreatedBy(mockAdmin);

        when(authService.getAuthenticatedCustomer()).thenReturn(mockAdmin);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        Customer mockAdmin = new Customer();
        mockAdmin.setAdmin(true);

        when(authService.getAuthenticatedCustomer()).thenReturn(mockAdmin);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.deleteProduct(1L));
    }

    @Test
    void testViewProduct_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setRecentlyViewedProducts(new ArrayList<>());

        Product product = new Product();
        product.setId(1L);
        product.setViews(0);

        when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        productService.viewProduct(1L);

        // Assert
        assertEquals(1, product.getViews());
        assertEquals(1, mockCustomer.getRecentlyViewedProducts().size());
        verify(customerRepository, times(1)).save(mockCustomer);
    }

    @Test
    void testViewProduct_NotFound() {
        // Arrange
        Customer mockCustomer = new Customer();
        when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.viewProduct(1L));
    }

    @Test
    void testSearchProductsByName_Success() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        // Initialize createdBy with a mock Customer
        Customer createdByCustomer = new Customer();
        createdByCustomer.setEmail("test@example.com"); // Set other required fields if necessary
        product.setCreatedBy(createdByCustomer);

        when(productRepository.findByNameContainingIgnoreCase("Test")).thenReturn(Collections.singletonList(product));

        // Act
        List<ProductDTO> result = productService.searchProductsByName("Test");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
    }

    @Test
    void testGetRecommendations_Success() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setCategory("Electronics");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.findRecommendationsByCategory("Electronics", 1L)).thenReturn(Collections.emptyList());
        when(productRepository.findRecommendationsByDecade(anyInt(), anyInt(), anyLong())).thenReturn(Collections.emptyList());
        when(productRepository.findRecommendationsByDirector(anyString(), anyLong())).thenReturn(Collections.emptyList());

        // Act
        List<ProductDTO> result = productService.getRecommendations(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetImagesByProductId_Success() {
        // Arrange
        Image image = new Image();
        image.setUrl("http://example.com/image.jpg");

        Product product = new Product();
        product.setId(1L);

        image.setProduct(product);
        when(productRepository.findImagesByProductId(1L)).thenReturn(Collections.singletonList(image));

        // Act
        List<ImageDTO> result = productService.getImagesByProductId(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals("http://example.com/image.jpg", result.get(0).getUrl());
    }

    @Test
    void testAddImageToProduct_Success() {
        // Arrange
        Customer mockAdmin = new Customer();
        mockAdmin.setId(1);
        mockAdmin.setAdmin(true);
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl("http://example.com/image.jpg");

        Product product = new Product();
        product.setId(1L);
        product.setCreatedBy(mockAdmin);

        when(authService.getAuthenticatedCustomer()).thenReturn(mockAdmin);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        productService.addImageToProduct(1L, imageDTO);

        // Assert
        verify(imageRepository, times(1)).save(any(Image.class));
    }

    @Test
    void testAddImageToProduct_NotAdmin() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setAdmin(false);
        ImageDTO imageDTO = new ImageDTO();

        when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.addImageToProduct(1L, imageDTO));
    }

    @Test
    void testAddImageToProduct_ProductNotFound() {
        // Arrange
        Customer mockAdmin = new Customer();
        mockAdmin.setAdmin(true);
        ImageDTO imageDTO = new ImageDTO();

        when(authService.getAuthenticatedCustomer()).thenReturn(mockAdmin);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.addImageToProduct(1L, imageDTO));
    }

}
