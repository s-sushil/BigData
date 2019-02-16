

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HeaterFileReader {

    public static void main(String[] args){
        main2();
        //readline();
    }

    public static void main2() {
        String line = "";
        String rec = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("/home/sushil/Desktop/testing_pgm/heater_data.json"));
            while ((line = reader.readLine()) != null) {
                JsonNode node = mapper.readTree(line);
                String sentOn = node.get("sentOn").toString();
                String tempExt = node.get("metrics").get("temperatureExternal").toString();
                String tempIntertnal = node.get("metrics").get("temperatureInternal").toString();
                String tempExaust = node.get("metrics").get("temperatureExhaust").toString();
                String erroCode = node.get("metrics").get("errorCode").toString();
                if(erroCode.equals("0"))
                    erroCode = "0";
                else
                    erroCode = "1";
                System.out.println(tempExt+","+tempIntertnal+","+tempExaust+","+erroCode);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void readline(){
        {
            String line = "";
            String line2="";
            String rec = "";
            int i2=0;
            String st="";
            String tok = "";
            ObjectMapper mapper = new ObjectMapper();
            String reg = "([0-9]{1}.[0-9]{1},[0-9]{1}.[0-9]{1},[0-9]{1}.[0-9]{1},[0-9]{1}.[0-9]{1}),(.*)";
            Pattern pattern = Pattern.compile(reg);
            try {
                BufferedReader reader = new BufferedReader(
                        new FileReader("/home/sushil/Desktop/testing_pgm/sample_libsvm_data.txt"));
                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);
                    String tok2[] = line.split(" ");
                    for(int i=0 ; i< 7 ;i++){
                        System.out.print(tok2[i]+" ");
                    }
                    System.out.println();
                }
                reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(st);
        }
    }
}
