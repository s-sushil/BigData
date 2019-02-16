
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import javax.xml.transform.Source;

import org.dmg.pmml.DerivedField;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.InlineTable;
import org.dmg.pmml.MapValues;
import org.dmg.pmml.PMML;
import org.dmg.pmml.Row;
import org.dmg.pmml.TransformationDictionary;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.evaluator.TargetField;
import org.jpmml.manager.PMMLManager;
import org.jpmml.model.JAXBUtil;
import org.xml.sax.InputSource;

public class ParsePMML {
    public static void main(String[] args) {

        try {
            // Load the file using our simple util function.
            PMML pmml = loadModel("/home/sushil/LogisticRegressionPMML.pmml");
            System.out.println("pmml object::"+pmml);

            Evaluator evaluator = ModelEvaluatorFactory.newInstance().newModelEvaluator(pmml);

            List<InputField> list2 = evaluator.getActiveFields();

            for(InputField f : list2) {
                System.out.println("features :"+f);
            }

            System.out.println("evaluator summary :"+evaluator.getSummary());
            System.out.println("evaluator type :"+evaluator.getMiningFunction());

           }
        catch (Exception e){
            System.out.println("Error is ::::::"+e);
        }

    }
    public final static PMML loadModel(final String file) throws Exception {
        PMML pmml = null;
        File inputFilePath = new File(file);
        try( InputStream in = new FileInputStream( inputFilePath ) ){
            Source source = org.jpmml.model.ImportFilter.apply(new InputSource(in));
            pmml = JAXBUtil.unmarshalPMML(source);
        } catch( Exception e) {
            System.out.println(e);
        }
        return pmml;
    }
}
