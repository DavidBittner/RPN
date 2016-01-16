
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.chart.*;

import java.util.ArrayList;

public class GUI extends Application
{

    static LineChart<Number, Number> Graph;
    static Button GraphButton;
    static TextField Equation;

    static NumberAxis xAxis;
    static NumberAxis yAxis;

    static int xmin = -5, xmax = 5;
    static int ymin = -5, ymax = 5;

    static RPN translator;

    static GridPane grid;

    static void AddData()
    {

        Graph.getData().clear();
        
        XYChart.Series Data = new XYChart.Series();

        ArrayList< Double > vals = RPN.GetGraphVals( xmin, xmax, Equation.getText() );

        for( int i = 0; i < vals.size(); i+=2 )
        {

            Data.getData().add( new XYChart.Data( vals.get(i), vals.get(i+1) ) );

        }

        Graph.getData().add( Data );

    }

    static double a[] = {0.0};

    public static void main( String args[] )
    {

        launch( args );

    }

    @Override
    public void start( Stage primaryStage )
    {

        translator = new RPN();

        primaryStage.setTitle( "Hello, world!" );
        
        grid = new GridPane();
        grid.setHgap( 10 );
        grid.setVgap( 10 );
        grid.setPadding( new Insets( 0, 10, 0, 10 ) );

        Equation = new TextField("");
        Button GraphButton = new Button("Graph" );

        GraphButton.setOnAction( new EventHandler<ActionEvent>()
        {

            public void handle( ActionEvent e ) 
            {

                AddData();

            }

        });

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        Graph = new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series data = new XYChart.Series(); 
        
        Graph.setCreateSymbols( false );
        Graph.setAnimated( false );
        Graph.setLegendVisible( false );

        grid.add( Graph, 0, 0 );
        grid.add( Equation, 0, 1 );
        grid.add( GraphButton, 0, 2 ); 

        primaryStage.setScene( new Scene(grid, 300, 250 ) );
        primaryStage.show();

    }

}
