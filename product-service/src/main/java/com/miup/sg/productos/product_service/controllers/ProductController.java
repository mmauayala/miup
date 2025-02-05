package com.miup.sg.productos.product_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.miup.sg.productos.product_service.models.common.dto.response.CustomResponse;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.Stock;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductCreateRequest;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductUpdateRequest;
import com.miup.sg.productos.product_service.models.productos.dto.response.ProductResponse;
import com.miup.sg.productos.product_service.models.productos.entity.MovimientoStock;
import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;
import com.miup.sg.productos.product_service.models.productos.entity.VentaEntity;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductToProductResponseMapper;
import com.miup.sg.productos.product_service.services.ProductCreateService;
import com.miup.sg.productos.product_service.services.ProductDeleteService;
import com.miup.sg.productos.product_service.services.ProductMovimientoService;
import com.miup.sg.productos.product_service.services.ProductReadService;
import com.miup.sg.productos.product_service.services.ProductStockService;
import com.miup.sg.productos.product_service.services.ProductUpdateService;
import com.miup.sg.productos.product_service.services.ProductVentaService;

@RestController
@RequestMapping("/v1/productos")
@RequiredArgsConstructor
@Validated
public class ProductController {

    @Autowired
    private ProductCreateService productCreateService;
    
    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductReadService productReadService;
    
    @Autowired
    private ProductUpdateService productUpdateService;
    
    @Autowired
    private ProductDeleteService productDeleteService;

    @Autowired
    private ProductVentaService productVentaService;

    @Autowired
    private ProductMovimientoService productMovService;

    private final ProductToProductResponseMapper productToProductResponseMapper = ProductToProductResponseMapper.initialize();


    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<String> createProduct(@RequestBody @Valid final ProductCreateRequest productCreateRequest) {

        final Product createdProduct = productCreateService
                .createProduct(productCreateRequest);

        return CustomResponse.successOf(createdProduct.getId());
    }

    @GetMapping("/lista")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<Product> findAll(Pageable pageable){
        return productReadService.findAll(pageable);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<ProductResponse> getProductById(@PathVariable @UUID final String productId) {

        final Product product = productReadService.getProductById(productId);

        final ProductResponse productResponse = productToProductResponseMapper.map(product);

        return CustomResponse.successOf(productResponse);

    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<ProductResponse> updatedProductById(
            @RequestBody @Valid final ProductUpdateRequest productUpdateRequest,
            @PathVariable @UUID final String productId) {

        final Product updatedProduct = productUpdateService.updateProductById(productId, productUpdateRequest);

        final ProductResponse productResponse = productToProductResponseMapper.map(updatedProduct);

        return CustomResponse.successOf(productResponse);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> deleteProductById(@PathVariable @UUID final String productId) {

        productDeleteService.deleteProductById(productId);
        return CustomResponse.SUCCESS;
    }

    @PostMapping("/{productoId}/stock")
    public ResponseEntity<?> registerStock(@PathVariable String productoId, @RequestBody Stock stock) {
        StockEntity nuevoStock = productStockService.RegisterStock(productoId, stock.getCantidad(), stock.getPrecioIngreso());
        return ResponseEntity.ok(nuevoStock);
    }

    // Obtener stock por producto
    @GetMapping("/{productoId}/stock")
    public ResponseEntity<?> stockByProduct(@PathVariable String productoId) {
        StockEntity stock = productStockService.StockByProduct(productoId);
        return ResponseEntity.ok(stock);
    }

    // Registrar una venta
    @PostMapping("/venta")
    public ResponseEntity<VentaEntity> registrarVenta(@RequestParam String productoId,
                                                @RequestParam Double cantidadVendida,
                                                @RequestParam Double precioVenta) {
        VentaEntity venta = productVentaService.registrarVenta(productoId, cantidadVendida, precioVenta);
        return ResponseEntity.ok(venta);
    }

    // Obtener el historial de movimientos de un producto
    @GetMapping("/{productoId}/movimientos")
    public ResponseEntity<List<MovimientoStock>> obtenerHistorialMovimientos(@PathVariable String productoId) {
        List<MovimientoStock> movimientos = productMovService.obtenerHistorialMovimientos(productoId);
        return ResponseEntity.ok(movimientos);
    }
}

