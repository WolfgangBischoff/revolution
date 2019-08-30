package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static Core.Util.*;

public abstract class Statistics
{
    static Double calcAvg(ArrayList<Integer> data)
    {
        if(data.size() == 0)
            return 0.0;

        Double dataSum = 0.0;
        for(Integer d : data)
        {
            dataSum += d;
        }

            return dataSum / data.size();
    }

    static Double calcMedian(ArrayList<Integer> data)
    {
        if(data.size() == 0)
            return 0.0;

        Collections.sort(data);
        if(data.size() % 2 == 1)
            return Double.valueOf(data.get((data.size()/2)));
        else
        {
            return (double) ((data.get((data.size() / 2) - 1) + data.get(data.size() / 2)) / 2);
        }
    }

    static Integer randomWithRatio(Integer[] ratios)
    {
        ArrayList<Integer> weightsum = new ArrayList<>();

        //Create borders by summing up ratios
        //Ratios:       2, 3, 4
        //Weightsum:    2, 5, 9
        weightsum.add(ratios[0]);
        for(int i=1; i<ratios.length; i++)
            weightsum.add(weightsum.get(i-1) + ratios[i]);

        //Highest possible Value    0 - (9 - 1)
        //                          0 - 8
        Integer res = getRandom().nextInt(weightsum.get(weightsum.size()-1)) -1;

        //0,1 => 0
        //2,3,4 => 1
        //5,6,7,8 => 2
        for(int j=0; j<weightsum.size(); j++)
        {
            if(res < weightsum.get(j))
                return j;
        }
        return -1;
    }

    static <E extends Enum<E>>  Map<E, Double> calcPercFromEnumCount(Map<E, Integer> input)
    {
        Map<E, Double> ret = new HashMap<>();
        Double polSum = 0.0;
        for(Map.Entry<E,Integer> entry : input.entrySet())
            polSum += entry.getValue();

     for(Map.Entry<E,Integer> entry : input.entrySet())
     {
         ret.put(entry.getKey(), entry.getValue() / polSum);
     }
        return ret;
    }

}
