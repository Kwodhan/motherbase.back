package com.motherbase.apirest.model.ressource;

public enum Ressources {
    Dollar(1, 1, 30000),
    Biology(300, 600, 3000),
    Fuel(300, 600, 3000),
    Ore(300, 600, 3000),
    Gem(600, 1200, 1000);


    private Integer sellPrice;
    private Integer buyPrice;
    private Integer initialStock;

    Ressources(Integer sellPrice, Integer buyPrice, Integer initialStock) {

        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.initialStock = initialStock;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public Integer getInitialStock() {
        return initialStock;
    }
}
