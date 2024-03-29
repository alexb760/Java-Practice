package org.practice.domo.herarchy;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class MergeNames {



    public static String[] uniqueNames(String[] names1, String[] names2) {
       Set<String> merged = new LinkedHashSet<>();
        Collections.addAll(merged, names1);
        Collections.addAll(merged, names2);
        return merged.toArray(new String[0]);
    }



    public static void main(String[] args) {

        String[] names1 = new String[] {"Ava", "Emma", "Olivia"};

        String[] names2 = new String[] {"Olivia", "Sophia", "Emma"};

        System.out.println(String.join(", ", MergeNames.uniqueNames(names1, names2))); // should print Ava, Emma, Olivia, Sophia

    }

}