package com.totu.domain.market;


public class Estate extends AbstractItem {

    private Long price;
    private String rooms;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return String.format(
            "AbstractItem[%s, price=%s, rooms='%s']",
            super.toString(), price, rooms);
    }
}
