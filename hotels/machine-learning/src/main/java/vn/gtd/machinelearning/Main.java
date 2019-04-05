package vn.gtd.machinelearning;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DataModel model = new FileDataModel(new File("D:/12-smart_search/hotels/machine-learning/src/main/resources/static/dataset-recsys.csv"));
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
                return new GenericItemBasedRecommender(model, similarity);
            }
        };

        Recommender recommender = recommenderBuilder.buildRecommender(model);
        List<RecommendedItem> recommendedations = recommender.recommend(9, 5);

        for (RecommendedItem item : recommendedations) {
            System.out.println("args = [" + item + "]");
        }

        RecommenderEvaluator evaluators = new RMSRecommenderEvaluator();
        double score = evaluators.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
        System.out.println("RMSE: " + score);

        RecommenderIRStatsEvaluator statsEvaluator  = new GenericRecommenderIRStatsEvaluator();

        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 4, 0.7);

        System.out.println("Precision: " + stats.getPrecision());
        System.out.println("Recall: " + stats.getRecall());
        System.out.println("F1 Score: " + stats.getF1Measure());

    }
}
