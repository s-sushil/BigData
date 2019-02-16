import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.JAXBUtil;
import org.xml.sax.InputSource;

import javax.xml.transform.Source;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class RegressionPrediction {


    private static PMML pmml = null;

    static String path= "/home/sushil/Desktop/testing_pgm/machine learning/pmml/regression.pmml";
    private static final File inputFilePath = new File(path);



    private static Source source = null;
    private static String recordtoken[];
    private static Evaluator evaluator = null;
    private static HashMap<FieldName, Double> params = new HashMap<FieldName, Double>();
    private static List<InputField> feature_list = null;
    private static List<FieldName> fieldName = new ArrayList<FieldName>();

    public static void main(String[] args) throws Exception {
        String value = "1.2669476,-0.864466507337306";
        makePrediction(value);

    }
    public static void makePrediction(String record) throws Exception {

        recordtoken = record.split(",");
        // Load the PMML model only one time
        if (pmml == null) {
            pmml = loadPMMLModel();
        }
        evaluator = ModelEvaluatorFactory.newInstance().newModelEvaluator(pmml);

        feature_list = evaluator.getActiveFields();

        System.out.println("evaluator summary : "+evaluator.getSummary());
        System.out.println(evaluator.getMiningFunction());

        for (InputField feature : feature_list) {
            System.out.println("feature :"+feature.getName());
            fieldName.add(feature.getName());
        }

        params.put(fieldName.get(0), Double.parseDouble(recordtoken[0]));
        params.put(fieldName.get(1), Double.parseDouble(recordtoken[1]));
       // params.put(fieldName.get(2), Double.parseDouble(recordtoken[2]));
       // params.put(fieldName.get(3), Double.parseDouble(recordtoken[3]));

        String pred =  evaluator.evaluate(params).toString();
        System.out.println("Prdiction is : "+pred);

        Map<FieldName, ?> map = evaluator.evaluate(params);
        Set keyset = map.keySet();
        for (Object k : keyset) {
            if (k == null) {
                System.err.println("******************************");
            }
            System.out.println("key is:-" + k + " and value is :-" + map.get(k));
        }
    }
    public final static PMML loadPMMLModel() throws Exception {
        System.out.println("loading pmml file....");
        try (InputStream in = new FileInputStream(inputFilePath)) {
            source = org.jpmml.model.ImportFilter.apply(new InputSource(in));
            pmml = JAXBUtil.unmarshalPMML(source);
        } catch (Exception e) {
            System.out.println(e);
        }
        return pmml;
    }
}
