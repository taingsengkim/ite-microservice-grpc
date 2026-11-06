# Product server streaming demo

The existing `GetProductById` and `CreateProduct` RPCs remain unary.
`ListProducts` is server streaming: one empty request produces zero or more
`ProductResponse` messages, followed by completion.

```proto
rpc ListProducts (ListProductsRequest) returns (stream ProductResponse);
message ListProductsRequest {}
```

## Shared contract

The canonical schema lives in
`istad-common/istad-common-grpc/src/main/proto/product.proto`.
The `istad-common-grpc` module generates the Java messages and gRPC stubs once;
Product Service and the Order client consume `kh.edu.istad:istad-common-grpc`.
Run builds from the repository root so Gradle resolves the shared composite build:

```sh
./gradlew :istad-common:istad-common-grpc:build
```

## Flow

1. Send `GET /api/products/stream` to Order Service.
2. `ProductStreamController` subscribes to `ProductStreamClient.listProducts()`.
3. `ProductStreamingGrpcClientAdapter` uses the generated async stub to call Product Service on port 3333.
4. `ProductGrpcServerImpl` loads products through `ProductService` and its JPA repository.
5. The server calls `onNext()` once per product, then `onCompleted()` once.
6. The client maps each response to `QueryProductResult`. WebFlux writes one JSON object per line using NDJSON.

No products means a successful empty stream. Disconnecting the HTTP subscriber cancels
the gRPC call. The client applies a 30-second deadline. Errors before the HTTP response
is committed can become HTTP 503/504/500; errors after streaming starts terminate the
response because its HTTP status can no longer change.

## Try it

Start the existing database and the dependencies required by your local service
configuration, then Product Service (HTTP 2222, gRPC 3333) and Order Service (HTTP 3000).
Create at least two products:

```sh
curl -X POST http://localhost:2222/api/v1/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"Laptop","price":500,"quantity":2}'
curl -X POST http://localhost:2222/api/v1/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"Mouse","price":10,"quantity":5}'
curl -N -H 'Accept: application/x-ndjson' http://localhost:3000/api/products/stream
```

To call gRPC directly with grpcurl and the enabled server reflection:

```sh
grpcurl -plaintext -d '{}' localhost:3333 kh.edu.istad.product.grpc.ProductGrpcService/ListProducts
```

Database order is unspecified. There is no artificial delay: small result sets may
arrive almost immediately, but each product is a separate gRPC response message.
This classroom example loads all products into memory and buffers client responses;
it is not a bounded-memory export for arbitrarily large catalogs.

## Validation

```sh
./gradlew :microservices:product-service:test :microservices:order-service:order-service-main:compileJava
```

Server handler tests cover multiple messages followed by completion, an empty result,
and stopping sends when the call is cancelled. These tests use a mocked product
service; a live database and full network round trip require the manual demo above.

## Port configuration update

Product Service HTTP changed from `8085` to `2222`; its gRPC server changed
from `9095` to `3333`. Both Order Service and Product Service now configure
their product gRPC client address as `static://localhost:3333` in
`application.yaml`. Order Service HTTP remains on `3000`. No runtime port
overrides are required.
