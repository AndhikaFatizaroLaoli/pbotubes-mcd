package pbotubesmcd.utils;

import pbotubesmcd.enums.OrderType;

public class OrderSession {
    private static OrderType currentOrderType;

    public static OrderType getCurrentOrderType() {
        return currentOrderType;
    }

    public static void setCurrentOrderType(OrderType orderType) {
        currentOrderType = orderType;
    }

    public static void clearOrderType() {
        currentOrderType = null;
    }
}
