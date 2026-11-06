package example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import build.buf.gen.kh.edu.istad.product.grpc.ListProductsRequest;
import build.buf.gen.kh.edu.istad.product.grpc.ProductGrpcServiceGrpc;

public class ProductClient {
    public static void main(String[] args) throws InterruptedException {
        String target = args.length > 0 ? args[0] : "localhost:3333";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        try {
            var stub = ProductGrpcServiceGrpc.newBlockingStub(channel)
                    .withDeadlineAfter(30, TimeUnit.SECONDS);
            var products = stub.listProducts(ListProductsRequest.getDefaultInstance());
            int count = 0;
            while (products.hasNext()) {
                var product = products.next();
                System.out.printf("%s | %s | %.2f | quantity=%d%n",
                        product.getProductId(), product.getName(),
                        product.getPrice(), product.getQuantity());
                count++;
            }
            System.out.printf("Stream completed: %d products%n", count);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
