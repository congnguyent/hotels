package vn.gtd.machinelearning.model;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;

public class ModelGenerator {
    public Instances loadDataset(final String path) {
        Instances dataset = null;
        try {
            dataset = ConverterUtils.DataSource.read(path);
            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public Classifier buidClassifier(Instances traindataset) {
        MultilayerPerceptron m = new MultilayerPerceptron();
        try {
            m.buildClassifier(traindataset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return m;
    }

    public String evaluateModel(Classifier model, Instances traindataset, Instances testdataset) {
        Evaluation eval = null;
        try {
            // Evaluate classifier with test dataset
            eval = new Evaluation(traindataset);
            eval.evaluateModel(model, testdataset);
        } catch (Exception ex) {
        }
        return eval.toSummaryString("", true);
    }

    public void saveModel(Classifier model, String modelpath) {

        try {
            SerializationHelper.write(modelpath, model);
        } catch (Exception ex) {
        }
    }
}
