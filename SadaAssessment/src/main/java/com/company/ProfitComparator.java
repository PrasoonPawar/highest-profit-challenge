package com.company;

import java.util.Comparator;

public class ProfitComparator implements Comparator<OrganizationData> {
    //Compares the profit between two organization data to sort
    @Override
    public int compare(OrganizationData o1, OrganizationData o2) {
        return Float.compare(o1.getProfit(), o2.getProfit())*-1;
    }
}