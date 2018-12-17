package com.motherbase.apirest.model.motherbase.department;

public enum RankDepartment {
    Level0(0, 0, 0, 0, 0, 0),
    Level1(20, 30, 20, 20, 20, 20),
    Level2(30, 30, 20, 20, 20, 20),
    Level3(40, 30, 20, 20, 20, 20),
    Level4(50, 30, 20, 20, 20, 20);

    private Integer maxStuff;
    private Integer costDollar;
    private Integer costFuel;
    private Integer costBiology;
    private Integer costOre;
    private Integer costGem;

    RankDepartment(Integer maxStuff, Integer costDollar, Integer costFuel, Integer costBiology, Integer costOre, Integer costGem) {
        this.maxStuff = maxStuff;
        this.costDollar = costDollar;
        this.costFuel = costFuel;
        this.costBiology = costBiology;
        this.costOre = costOre;
        this.costGem = costGem;
    }

    public Integer getMaxStuff() {
        return maxStuff;
    }

    public Integer getCostDollar() {
        return costDollar;
    }

    public Integer getCostFuel() {
        return costFuel;
    }

    public Integer getCostBiology() {
        return costBiology;
    }

    public Integer getCostOre() {
        return costOre;
    }

    public Integer getCostGem() {
        return costGem;
    }
}
