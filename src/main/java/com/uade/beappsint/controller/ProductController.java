package com.uade.beappsint.controller;

import com.uade.beappsint.dto.ImageDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Product", description = "Endpoints for product display and management")
public interface ProductController {
    @Operation(summary = "Return all products", description = "Returns all the database products and their info.")
    ResponseEntity<List<ProductDTO>> getAllProducts();

    @Operation(summary = "Return a product by the ID", description = "Returns a product by the ID.")
    ResponseEntity<ProductDTO> getProductById(Long id);

    @Operation(summary = "Create a product", description = "Create a product. Only admins can perform this action.")
    ResponseEntity<ProductDTO> createProduct(Product product);

    @Operation(summary = "update a product", description = "update a product. Only admins can perform this action.")
    ResponseEntity<ProductDTO> updateProduct(Long id, Product productDetails);

    @Operation(summary = "Delete a product", description = "Delete a product. Only admins can perform this action.")
    ResponseEntity<Void> deleteProduct(Long id);

    @Operation(summary = "View a product", description = "Receives the product id and associates the product with the logged user.")
    ResponseEntity<Void> viewProduct(Long id);

    @Operation(summary = "Return the featured products", description = "Return the actual featured products.")
    ResponseEntity<List<ProductDTO>> getFeaturedProducts();

    @Operation(summary = "Return the category products", description = "Return the category products.")
    ResponseEntity<List<ProductDTO>> getProductsByCategory(String category);

    @Operation(summary = "Return the user's recently viewed products", description = "Return the user's recently viewed products.")
    ResponseEntity<List<ProductDTO>> getRecentlyViewedProducts();

    @Operation(summary = "Search products by name", description = "Returns products that match the given partial name.")
    ResponseEntity<List<ProductDTO>> searchProductsByName(String partialName);

    @Operation(summary = "Get product recommendations", description = "Get recommendations for a product based on genre, decade, and director.")
    ResponseEntity<List<ProductDTO>> getRecommendations(Long id);

    @Operation(
            summary = "Get images associated with a product",
            description = "Retrieves all images that are associated with a specific product ID. This is useful for displaying multiple views of a product."
    )
    ResponseEntity<List<ImageDTO>> getImagesByProductId(Long productId);


    @Operation(
            summary = "Change the main image of a product",
            description = "Allows the user to add a new image to the specified product. This method accepts an ImageDTO object representing the image to be added."
    )
    ResponseEntity<Void> changeImageOfProduct(Long productId, ImageDTO imageDTO);

    @Operation(
            summary = "Add an image to a product",
            description = "Allows the user to add a new image to the specified product. This method accepts an ImageDTO object representing the image to be added."
    )
    ResponseEntity<Void> addImageToProduct(Long productId, ImageDTO imageDTO);
}
