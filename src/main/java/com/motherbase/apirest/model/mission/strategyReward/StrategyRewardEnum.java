package com.motherbase.apirest.model.mission.strategyReward;

public enum StrategyRewardEnum {
    Normal(new NormalStrategyReward());

    private StrategyReward strategyReward;

    StrategyRewardEnum(StrategyReward strategyReward) {
        this.strategyReward = strategyReward;
    }

    public StrategyReward getStrategyReward() {
        return strategyReward;
    }

    public void setStrategyReward(StrategyReward strategyReward) {
        this.strategyReward = strategyReward;
    }
}
