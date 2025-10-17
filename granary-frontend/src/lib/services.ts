import type { OrderServicePort } from "./application/OrderServicePort";
import { HttpOrderAdapter } from "./infrastrucuture/api/HttpOrderAdapter";
import { MockOrderAdapter } from "./infrastrucuture/api/MockOrderAdapter";

const USE_MOCK_ADAPTER = true;

const orderAdapterInstance: OrderServicePort = USE_MOCK_ADAPTER 
? new MockOrderAdapter : 
new HttpOrderAdapter();

export const services = {
    orderService: orderAdapterInstance
}