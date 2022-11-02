module jr.mathexpressions {
    requires javafx.controls;
    requires javafx.fxml;


    opens jr.mathexpressions to javafx.fxml;
    exports jr.mathexpressions;
}