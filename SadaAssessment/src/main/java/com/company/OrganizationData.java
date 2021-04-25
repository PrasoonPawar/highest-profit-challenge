package com.company;

public class OrganizationData {
    private final int year;
    private final int rank;
    private final String companyName;
    private final float revenue;
    private final float profit;

    //parameterized constructor
    public OrganizationData(int year, int rank, String companyName, float revenue, float profit) {
        this.year = year;
        this.rank = rank;
        this.companyName = companyName;
        this.revenue = revenue;
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Companies [rank=" + rank + ", company=" + companyName + ", year=" + year + ", revenue=" + revenue + ", profit=" + profit + "]";
    }

    //getter to get the profit
    public float getProfit() {
        return profit;
    }
}
