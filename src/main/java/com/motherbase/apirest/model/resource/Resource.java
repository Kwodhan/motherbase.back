package com.motherbase.apirest.model.resource;

import javax.persistence.Transient;

public enum Resource {
    Dollar(1, 1, 30000),
    Fuel(300, 600, 3000),
    Biology(300, 600, 3000),
    Ore(300, 600, 3000),
    Gem(600, 1200, 1000);


    private Integer sellPrice;
    private Integer buyPrice;
    private Integer initialStock;

    Resource(Integer sellPrice, Integer buyPrice, Integer initialStock) {

        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.initialStock = initialStock;
    }


    @Transient
    public Integer getSellPrice() {
        return sellPrice;
    }

    @Transient
    public Integer getBuyPrice() {
        return buyPrice;
    }

    @Transient
    public Integer getInitialStock() {
        return initialStock;
    }
}
