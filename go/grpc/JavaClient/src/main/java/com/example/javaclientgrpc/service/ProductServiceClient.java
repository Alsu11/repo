package com.example.javaclientgrpc.service;

import com.example.javaclientgrpc.pb.Product;
import com.example.javaclientgrpc.pb.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceClient {
    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub service;

    public Product.ProductResponse getProductById(String id) {
        return service.getProduct(Product.ProductRequest.newBuilder()
                        .setId(id)
                .build());
    }
}
