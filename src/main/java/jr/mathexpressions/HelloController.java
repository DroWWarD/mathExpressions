package jr.mathexpressions;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button execute;

    @FXML
    private TextField getData;

    @FXML
    private Text operations;

    @FXML
    private Text result;

    @FXML
    void initialize() {
        execute.setOnAction(actionEvent -> {
            recurse(getData.getText(), 0);

        });

    }

    public void recurse(final String expression, int countOperation) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String temp = expression;
        int newCount = countOperation;
        Pattern p = Pattern.compile("\\([^\\(]+?\\)");
        Matcher m = p.matcher(expression);
        if (m.find()) {
            temp = expression.substring(m.start() + 1, m.end() - 1);
        }
        if (temp.startsWith("-")) newCount++;
        if (temp.contains("--")){
            temp = temp.replaceAll("[-][-]", "+");
        }
        if (temp.startsWith("+")){
            temp = temp.replaceFirst("[+]", "");
        }
        if (temp.contains("++")){
            temp = temp.replaceAll("[+][+]", "+");
        }
        if (temp.contains(" ")){
            temp = temp.replaceAll("[ ]", "");
        }
        if (temp.contains(",")){
            temp = temp.replaceAll("[,]", ".");
        }

//--------------------------    SIN   COS   TAN    -------------------------------
        while (temp.contains("sin") || temp.contains("cos") || temp.contains("tan")){
            if (temp.contains("--")){
                temp = temp.replaceAll("[-][-]", "+");
            }
            if (temp.contains("++")){
                temp = temp.replaceAll("[+][+]", "+");
            }
            if (temp.contains(" ")){
                temp = temp.replaceAll("[ ]", "");
            }
            if (temp.contains(",")){
                temp = temp.replaceAll("[,]", ".");
            }
            Pattern p01 = Pattern.compile("sin[-]?\\d*[.]?\\d*");
            Matcher m01 = p01.matcher(temp);
            String temp01;
            while (m01.find()) {
                temp01 = temp.substring(m01.start(), m01.end());
                double sin = Math.sin(Math.toRadians(Double.parseDouble(temp01.split("sin")[1])));
                temp = temp.substring(0, m01.start()) + decimalFormat.format(sin) + temp.substring(m01.end());
                newCount++;
                break;
            }
            Pattern p02 = Pattern.compile("cos[-]?\\d*[.]?\\d*");
            Matcher m02 = p02.matcher(temp);
            String temp02;
            while (m02.find()) {
                temp02 = temp.substring(m02.start(), m02.end());
                String degree = temp02.split("cos")[1];
                double cos = Math.cos(Math.toRadians(Double.parseDouble(degree)));
                temp = temp.substring(0, m02.start()) + decimalFormat.format(cos) + temp.substring(m02.end());
                newCount++;
                break;
            }
            Pattern p03 = Pattern.compile("tan[-]?\\d*[.]?\\d*");
            Matcher m03 = p03.matcher(temp);
            String temp03;
            while (m03.find()) {
                temp03 = temp.substring(m03.start(), m03.end());
                double tan = Math.tan(Math.toRadians(Double.parseDouble(temp03.split("tan")[1])));
                temp = temp.substring(0, m03.start()) + decimalFormat.format(tan) + temp.substring(m03.end());
                newCount++;
                break;
            }
        }
//-------------------------- ВОЗВЕДЕНИЕ В СТЕПЕНЬ -------------------------------
        while (temp.contains("^")) {
            if (temp.contains("--")){
                temp = temp.replaceAll("[-][-]", "+");
            }
            if (temp.contains("++")){
                temp = temp.replaceAll("[+][+]", "+");
            }
            if (temp.contains(" ")){
                temp = temp.replaceAll("[ ]", "");
            }
            if (temp.contains(",")){
                temp = temp.replaceAll("[,]", ".");
            }

            Pattern p1 = Pattern.compile("\\d*[.]?\\d*[\\^][-]?\\d*[.]?\\d*");
            Matcher m1 = p1.matcher(temp);
            String temp1;
            if (m1.find()) {
                temp1 = temp.substring(m1.start(), m1.end());
                double pow = Math.pow(Double.parseDouble(temp1.split("\\^")[0]), Double.parseDouble(temp1.split("\\^")[1]));
                temp = temp.substring(0, m1.start()) + decimalFormat.format(pow) + temp.substring(m1.end());
                newCount++;
            }
        }
//-------------------------- УМНОЖЕНИЕ и ДЕЛЕНИЕ -------------------------------
        while (temp.contains("/") || temp.contains("*")) {
            if (temp.contains("--")){
                temp = temp.replaceAll("[-][-]", "+");
            }
            if (temp.contains("++")){
                temp = temp.replaceAll("[+][+]", "+");
            }
            if (temp.contains(" ")){
                temp = temp.replaceAll("[ ]", "");
            }
            if (temp.contains(",")){
                temp = temp.replaceAll("[,]", ".");
            }
            outer:
            {
                Pattern p2 = Pattern.compile("[-]?\\d*[.]?\\d*[*/][-]?\\d*[.]?\\d*");
                Matcher m2 = p2.matcher(temp);
                String temp2;
                while (m2.find()) {
                    temp2 = temp.substring(m2.start(), m2.end());
                    if (temp2.contains("*")) {
                        double multiply = Double.parseDouble(temp2.split("[*]")[0]) * Double.parseDouble(temp2.split("[*]")[1]);
                        temp = temp.substring(0, m2.start()) + decimalFormat.format(multiply) + temp.substring(m2.end());
                        newCount++;
                        break outer;
                    } else {
                        double dividing = Double.parseDouble(temp2.split("[/]")[0]) / Double.parseDouble(temp2.split("[/]")[1]);
                        temp = temp.substring(0, m2.start()) + decimalFormat.format(dividing) + temp.substring(m2.end());
                        newCount++;
                        break outer;
                    }
                }
            }
        }
//-------------------------- СЛОЖЕНИЕ и ВЫЧИТАНИЕ -------------------------------
        while (temp.substring(1).contains("-") || temp.contains("+")) {
            if (temp.contains("--")){
                temp = temp.replaceAll("[-][-]", "+");
            }
            if (temp.contains("++")){
                temp = temp.replaceAll("[+][+]", "+");
            }
            if (temp.contains(" ")){
                temp = temp.replaceAll("[ ]", "");
            }
            if (temp.contains(",")){
                temp = temp.replaceAll("[,]", ".");
            }
            Pattern p3 = Pattern.compile("[-]?\\d*[.]?\\d*[+-][-]?\\d*[.]?\\d*");
            Matcher m3 = p3.matcher(temp);
            String temp3;
            if (m3.find()) {
                temp3 = temp.substring(m3.start(), m3.end());
                if (temp3.contains("+")) {
                    double multiply = Double.parseDouble(temp3.split("[+]")[0]) + Double.parseDouble(temp3.split("[+]")[1]);
                    temp = temp.substring(0, m3.start()) + decimalFormat.format(multiply) + temp.substring(m3.end());
                    newCount++;
                } else if(temp3.substring(1).contains("-")){
                    if (temp3.startsWith("-")){
                        double dividing = Double.parseDouble(("-" + temp3.substring(1).split("[-]")[0])) - Double.parseDouble(temp3.split("\\d[-]")[1]);
                        temp = temp.substring(0, m3.start()) + decimalFormat.format(dividing) + temp.substring(m3.end());
                        newCount++;
                    }else {
                        double dividing = Double.parseDouble(temp3.split("[-]")[0]) - Double.parseDouble(temp3.split("[-]")[1]);
                        temp = temp.substring(0, m3.start()) + decimalFormat.format(dividing) + temp.substring(m3.end());
                        newCount++;
                    }
                }
            }
        }
//------------------------- СКОБКИ С МАКСИМАЛЬНЫМ ВЛОЖЕНИЕ ПОСЧИТАНЫ, ДЕЛАЕМ РЕКУРСИЮ, ЗАМЕНЯЯ ИХ--------------------------------
        if (temp.contains("--")){
            temp = temp.replaceAll("[-][-]", "+");
        }
        if (temp.contains("++")){
            temp = temp.replaceAll("[+][+]", "+");
        }
        if (temp.contains(" ")){
            temp = temp.replaceAll("[ ]", "");
        }
        if (temp.contains(",")){
            temp = temp.replaceAll("[,]", ".");
        }

        if (expression.contains("(")) {
            String newExpression = expression.substring(0, m.start()) + temp + expression.substring(m.end());
            recurse(newExpression, newCount);
        } else {
            String tempResult = decimalFormat.format(Double.parseDouble(temp));
            if (tempResult.contains(",")){
                tempResult = tempResult.replaceAll("[,]", ".");
            }
            if (tempResult.equalsIgnoreCase("-0")){
                tempResult = tempResult.replaceAll("[-]0", "0");
            }
            result.setText(tempResult);
            operations.setText(String.valueOf(newCount));
            if (countOperation != 0) return;
        }
//-------------------------------------------------------------------------------------------------------------------------------
    }

}
