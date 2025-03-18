package com.miup.sg.productos.product_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.miup.sg.productos.product_service.models.common.dto.response.CustomResponse;
import com.miup.sg.productos.product_service.models.productos.Desperdicio;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.Promocion;
import com.miup.sg.productos.product_service.models.productos.Stock;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductCreateRequest;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductUpdateRequest;
import com.miup.sg.productos.product_service.models.productos.dto.response.ProductResponse;
import com.miup.sg.productos.product_service.models.productos.dto.response.ProductoIdResponse;
import com.miup.sg.productos.product_service.models.productos.entity.DesperdicioEntity;
import com.miup.sg.productos.product_service.models.productos.entity.MovimientoStock;
import com.miup.sg.productos.product_service.models.productos.entity.PromocionEntity;
import com.miup.sg.productos.product_service.models.productos.entity.StockEntity;
import com.miup.sg.productos.product_service.models.productos.entity.VentaEntity;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductToProductResponseMapper;
import com.miup.sg.productos.product_service.services.ProductCreateService;
import com.miup.sg.productos.product_service.services.ProductDeleteService;
import com.miup.sg.productos.product_service.services.ProductDesperdicioService;
import com.miup.sg.productos.product_service.services.ProductMovimientoService;
import com.miup.sg.productos.product_service.services.ProductReadService;
import com.miup.sg.productos.product_service.services.ProductStockService;
import com.miup.sg.productos.product_service.services.ProductUpdateService;
import com.miup.sg.productos.product_service.services.ProductVentaService;
import com.miup.sg.productos.product_service.services.ProductPromocionService;

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

    @Autowired
    private ProductDesperdicioService productDesperdicioService;

    @Autowired
    private ProductPromocionService productPromocionService;

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

    @GetMapping("/obtener-id/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ProductoIdResponse> getProductIdByName(@PathVariable String name) {
        ProductoIdResponse response = productReadService.getProductIdByName(name);
        return ResponseEntity.ok(response);
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> registerStock(@PathVariable String productoId, @RequestBody Stock stock) {
        StockEntity nuevoStock = productStockService.RegisterStock(productoId, stock.getCantidad(), stock.getPrecioIngreso(), stock.getPrecioVenta());
        return ResponseEntity.ok(nuevoStock);
    }

    // Obtener stock por producto
    @GetMapping("/{productoId}/stock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> stockByProduct(@PathVariable String productoId) {
        StockEntity stock = productStockService.StockByProduct(productoId);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/lista/stock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<StockEntity>> obtenerTodoElStock() {
        List<StockEntity> stock = productStockService.obtenerStock(); 
        return ResponseEntity.ok(stock); 
    }


    // Registrar una venta
    @PostMapping("/venta")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<VentaEntity>> registrarVenta(@RequestBody Map<String, Double> productosYCantidades) {
        List<VentaEntity> ventas = productVentaService.registrarVenta(productosYCantidades);
        return ResponseEntity.ok(ventas);
    }

    // Obtener el historial de movimientos de un producto
    @GetMapping("/{productoId}/movimientos")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<MovimientoStock>> obtenerHistorialMovimientos(@PathVariable String productoId) {
        List<MovimientoStock> movimientos = productMovService.obtenerHistorialMovimientos(productoId);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/movimientos")
    public List<MovimientoStock> obtenerTodosLosMovimientos() {
        return productMovService.obtenerTodosLosMovimientos();
    }

    @PostMapping("/{productoId}/desperdicios")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<DesperdicioEntity> registrarDesperdicio(
            @PathVariable String productoId,
            @RequestBody Desperdicio desperdicio) {
        DesperdicioEntity nuevoDesperdicio = productDesperdicioService.registrarDesperdicio(productoId, desperdicio.getCantidad());
        return ResponseEntity.ok(nuevoDesperdicio);
    }

    @GetMapping("/{productoId}/desperdicios")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<DesperdicioEntity>> obtenerHistorialDesperdicios(@PathVariable String productoId) {
        List<DesperdicioEntity> desperdicios = productDesperdicioService.obtenerHistorialDesperdicios(productoId);
        return ResponseEntity.ok(desperdicios);
    }

    // Obtener todas las promociones
    @GetMapping("/promociones/lista")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<PromocionEntity>> obtenerTodasLasPromociones() {
        List<PromocionEntity> promociones = productPromocionService.obtenerTodasLasPromociones();
        return ResponseEntity.ok(promociones);
    }

    // Crear una nueva promoción
    @PostMapping("/promociones/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<PromocionEntity> crearPromocion(@RequestBody Promocion promocion) {
        PromocionEntity nuevaPromocion = productPromocionService.crearPromocion(promocion.getNombre(),
                                                                                promocion.getTipo(),
                                                                                promocion.getValor(),
                                                                                promocion.getCantidadBonificada(),
                                                                                promocion.getCantidadRequerida(),
                                                                                promocion.getFechaFin(),
                                                                                promocion.getProducto());
        return ResponseEntity.ok(nuevaPromocion);
    }

    // Activar/Desactivar una promoción
    @PatchMapping("/promociones/{id}/estado")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<PromocionEntity> cambiarEstadoPromocion(@PathVariable Long id, @RequestParam Boolean activa) {
        PromocionEntity promocion = productPromocionService.cambiarEstadoPromocion(id, activa);
        return ResponseEntity.ok(promocion);
    }

}

