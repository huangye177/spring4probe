package data.yummynoodlebar.persistence.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Component;

@Component
public class MenuItemRepositoryImpl implements AnalyseIngredients
{

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * MongoDB provides a system to perform this kind of analysis, MapReduce.
     * 
     * To use MapReduce, you need to gain access to the MongoTemplate directly
     * (via @Autowired), and add this into the Repository that Spring Data is
     * currently managing for you.
     */
    @Override
    public Map<String, Integer> analyseIngredientsByPopularity()
    {
        /*
         * This class references two JavaScript functions, the mapper and the
         * reducer, respectively.
         */
        MapReduceResults<IngredientAnalysis> results = mongoTemplate.mapReduce("menu",
                "classpath:ingredientsmap.js",
                "classpath:ingredientsreduce.js",
                IngredientAnalysis.class);

        Map<String, Integer> analysis = new HashMap<String, Integer>();

        for (IngredientAnalysis ingredientAnalysis : results)
        {
            analysis.put(ingredientAnalysis.getId(), ingredientAnalysis.getValue());
        }

        return analysis;
    }

    private static class IngredientAnalysis
    {
        private String id;
        private int value;

        private void setId(String name)
        {
            this.id = name;
        }

        private void setValue(int count)
        {
            this.value = count;
        }

        public String getId()
        {
            return id;
        }

        public int getValue()
        {
            return value;
        }
    }
}
