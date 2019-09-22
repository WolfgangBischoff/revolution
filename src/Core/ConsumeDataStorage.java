package Core;

import Core.Enums.IndustryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConsumeData
{
    Integer date;
    Map<IndustryType, Integer> consumeData = new HashMap<>();

    public ConsumeData(Integer date)
    {
        this.date = date;
    }

    public void consume(IndustryType type, Integer luxury)
    {
        consumeData.put(type, luxury);
    }
}

public class ConsumeDataStorage
{
    private final Integer PERIODS_REMEMBERED = 5;
    private List<ConsumeData> dataStorage = new ArrayList<>();
    private Person consumer;

    public ConsumeDataStorage(Person person)
    {
        consumer = person;
    }

    public void consume(IndustryType type, Integer luxury)
    {
        //check if today already exist
        if (dataStorage.isEmpty())
            dataStorage.add(0, new ConsumeData(0)); //For current period
        dataStorage.get(0).consume(type, luxury);

        deleteOldData();
    }

    private void deleteOldData()
    {
        if (dataStorage.size() > PERIODS_REMEMBERED)
            dataStorage.remove(dataStorage.size() - 1);
    }

    public String dataConsume()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dataStorage.size(); i++)
        {
            stringBuilder.append("\t" + dataConsume(i) + "\n");
        }
        return stringBuilder.toString();

    }

    public String dataConsume(Integer idx)
    {
        StringBuilder stringBuilder = new StringBuilder();
        ConsumeData data = dataStorage.get(idx);
        for (Map.Entry<IndustryType, Integer> entry : data.consumeData.entrySet())
            stringBuilder.append("Day: " + data.date + " " + entry.getKey() + " " + entry.getValue());
        return stringBuilder.toString();
    }
}
