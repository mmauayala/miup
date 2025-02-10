package com.miup.sg.productos.product_service.services;

import java.util.List;

import com.miup.sg.productos.product_service.models.productos.entity.DesperdicioEntity;

public interface ProductDesperdicioService {

    DesperdicioEntity registrarDesperdicio(String productoId, Double cantidad);

    List<DesperdicioEntity> obtenerHistorialDesperdicios(String productoId);

}
