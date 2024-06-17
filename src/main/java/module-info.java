module br.fipp.botecofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.json;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires java.sql;
    requires jasperreports;

    opens br.fipp.botecofx to javafx.fxml;
    opens DB to javafx.fxml;

    exports br.fipp.botecofx;
    exports DB;
    exports DB.Dals;
    opens DB.Dals to javafx.fxml;
    exports DB.entidade;
    opens DB.entidade to javafx.fxml;

}