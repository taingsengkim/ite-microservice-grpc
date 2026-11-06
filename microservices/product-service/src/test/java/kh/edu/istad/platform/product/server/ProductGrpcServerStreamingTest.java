package kh.edu.istad.platform.product.server;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import kh.edu.istad.platform.product.entity.Product;
import kh.edu.istad.platform.product.service.ProductService;
import kh.edu.istad.product.grpc.ListProductsRequest;
import kh.edu.istad.product.grpc.ProductResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductGrpcServerStreamingTest {
    private final ProductService service = mock(ProductService.class);
    private final ProductGrpcServerImpl server = new ProductGrpcServerImpl(service);

    @Test
    @SuppressWarnings("unchecked")
    void emitsEachProductThenCompletes() {
        when(service.findAll()).thenReturn(List.of(
                Product.builder().id("p1").name("Laptop").price(500).quantity(2).build(),
                Product.builder().id("p2").name("Mouse").price(10).quantity(5).build()));
        StreamObserver<ProductResponse> observer = mock(StreamObserver.class);

        server.listProducts(ListProductsRequest.getDefaultInstance(), observer);

        var messages = ArgumentCaptor.forClass(ProductResponse.class);
        var order = inOrder(observer);
        order.verify(observer, times(2)).onNext(messages.capture());
        order.verify(observer).onCompleted();
        assertEquals(List.of("p1", "p2"), messages.getAllValues().stream()
                .map(ProductResponse::getProductId).toList());
        assertEquals("Laptop", messages.getAllValues().getFirst().getName());
        assertEquals(500, messages.getAllValues().getFirst().getPrice());
        assertEquals(2, messages.getAllValues().getFirst().getQuantity());
        verify(observer, never()).onError(any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void emptyDatabaseCompletesWithoutMessages() {
        when(service.findAll()).thenReturn(List.of());
        StreamObserver<ProductResponse> observer = mock(StreamObserver.class);

        server.listProducts(ListProductsRequest.getDefaultInstance(), observer);

        verify(observer).onCompleted();
        verify(observer, never()).onNext(any());
        verify(observer, never()).onError(any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void cancelledCallStopsSending() {
        when(service.findAll()).thenReturn(List.of(Product.builder().id("p1").build()));
        ServerCallStreamObserver<ProductResponse> observer = mock(ServerCallStreamObserver.class);
        when(observer.isCancelled()).thenReturn(true);

        server.listProducts(ListProductsRequest.getDefaultInstance(), observer);

        verify(observer, never()).onNext(any());
        verify(observer, never()).onCompleted();
    }
}
