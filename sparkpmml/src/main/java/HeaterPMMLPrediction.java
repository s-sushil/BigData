import org.dmg.pmml.FieldName;
import org.dmg.pmml.Model;
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

public class HeaterPMMLPrediction {

    private static PMML pmml = null;

    static String path = "/home/sushil/KMeansModel.pmml";

    private static final File inputpmmlFilePath = new File(path);

    private static Source source = null;
    private static String recordtoken[];
    private static Evaluator evaluator = null;
    private static HashMap<FieldName, Double> params = new HashMap<FieldName, Double>();
    private static List<InputField> feature_list = null;
    private static List<FieldName> fieldName = new ArrayList<FieldName>();

    public static void main(String[] args) throws Exception {
        String pred = makePrediction("5,20,30");
        System.out.println("Prediction is : "+pred);

    }
    public static String makePrediction(String record) throws Exception {

        recordtoken = record.split(",");
        // Load the PMML model only one time
        if (pmml == null) {
            pmml = loadPMMLModel();
        }

        System.out.println("pmml has models :"+pmml.hasModels());

        evaluator = ModelEvaluatorFactory.newInstance().newModelEvaluator(pmml);

        feature_list = evaluator.getActiveFields();

        System.out.println("evaluator summary : "+evaluator.getSummary());
        System.out.println("evaluator type : "+evaluator.getMiningFunction());

        for (InputField feature : feature_list) {
            System.out.println("feature :"+feature.getName());
            fieldName.add(feature.getName());
        }

        //System.out.println("Field name 1:"+fieldName.get(0));
      //  System.out.println("Field name 2:"+fieldName.get(1));
       // System.out.println("Field name 3:"+fieldName.get(2));
        //System.out.println("Field name :"+fieldName.get(3));



      params.put(fieldName.get(0), Double.parseDouble(recordtoken[0]));
      params.put(fieldName.get(1), Double.parseDouble(recordtoken[1]));
      params.put(fieldName.get(2), Double.parseDouble(recordtoken[2]));
      //params.put(fieldName.get(3), Double.parseDouble(recordtoken[3]));


        String prediction = evaluator.evaluate(params).toString();
        Map m = evaluator.evaluate(params);

        Set ss = m.keySet();
        for(Object s : ss){
            System.out.println("key :"+s+" value :"+m.get(s));
        }

        return prediction;

    }
    public final static PMML loadPMMLModel() throws Exception {
        System.out.println("loading Heater pmml file....");
        try (InputStream in = new FileInputStream(inputpmmlFilePath)) {
            source = org.jpmml.model.ImportFilter.apply(new InputSource(in));
            pmml = JAXBUtil.unmarshalPMML(source);
        } catch (Exception e) {
            System.out.println(e);
        }
        return pmml;
    }

}
