package com.motherbase.apirest.model.mission.strategyAffectStaff;

public enum StrategyAffectStaffEnum {

    Normal(new NormalStrategyAffectStaff());

    private StrategyAffectStaff strategyAffectStaff;

    StrategyAffectStaffEnum(StrategyAffectStaff strategyAffectStaff) {
        this.strategyAffectStaff = strategyAffectStaff;
    }

    public StrategyAffectStaff getStrategyAffectStaff() {
        return strategyAffectStaff;
    }

    public void setStrategyAffectStaff(StrategyAffectStaff strategyAffectStaff) {
        this.strategyAffectStaff = strategyAffectStaff;
    }
}
