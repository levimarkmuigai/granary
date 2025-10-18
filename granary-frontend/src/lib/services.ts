import type { OrderServicePort } from "./application/OrderServicePort";
import type { ProductServicePort } from "./application/ProductServicePort";
import { HttpOrderAdapter } from "./infrastrucuture/api/HttpOrderAdapter";
import { HttpProductAdapter } from "./infrastrucuture/api/HttpProductAdapter";

const orderAdapterInstance: OrderServicePort = new HttpOrderAdapter();

const productAdapterInstance: ProductServicePort = new HttpProductAdapter()

export const services = {
    orderService: orderAdapterInstance,
    productService: productAdapterInstance
}