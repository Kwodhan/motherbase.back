package com.motherbase.apirest.model.staff;

public enum RankStaff {
    E(1),
    D(2),
    C(3),
    B(4),
    A(5),
    S(6);

    private Integer point;

    RankStaff(Integer point) {
        this.point = point;
    }

    public Integer getPoint() {
        return point;
    }
}
