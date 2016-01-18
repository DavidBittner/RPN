
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    static TextField doms[] = new TextField[4];

    static RPN translator;

    static GridPane grid;

    static void AddData()
    {
        ArrayList< Double > vals = RPN.GetGraphVals( xmin, xmax, Equation.getText() );

        if( vals == null )
        {

            AlertBox( "Error", "Invalid Function entered.", "Graphing Error" );

        }else
        {
            Graph.getData().clear();
            
            XYChart.Series Data = new XYChart.Series();

            for( int i = 0; i < vals.size(); i+=2 )
            {

                Data.getData().add( new XYChart.Data( vals.get(i), vals.get(i+1) ) );

            }

            Graph.getData().add( Data );

        }

    }

    public static int CountOcc( String in, String check )
    {

        int count = in.length() - in.replace(check, "").length();

        return count;

    }

    public static void main( String args[] )
    {

        launch( args );

    }

    public static void AlertBox( String title, String body, String header )
    {

        Alert alert = new Alert( AlertType.ERROR );
        alert.setTitle( title );
        alert.setHeaderText( header);
        alert.setContentText( body );
        
        alert.showAndWait();

    }

    @Override
    public void start( Stage primaryStage )
    {

        translator = new RPN();

        primaryStage.setTitle( "Graping Calculator" );
        
        grid = new GridPane();
        grid.setHgap( 10 );
        grid.setVgap( 10 );
        grid.setPadding( new Insets( 10, 10, 10, 10 ) );

        GridPane domsGrid = new GridPane();
        domsGrid.setPadding( new Insets( 10, 10, 10, 10 ) );

        Equation = new TextField("");
        Button GraphButton = new Button("Graph" );

        String promptText[] = {"X minimum","X maximum","Y minimum", "Y maximum" };

        for( int i = 0; i < doms.length; i++ )
        {

            doms[i] = new TextField();
            doms[i].setPromptText( promptText[i] );
        
        }

        GraphButton.setOnAction( new EventHandler<ActionEvent>()
        {

            public void handle( ActionEvent e ) 
            {
 
                final NumberAxis newXAxis = (NumberAxis)Graph.getXAxis();
                final NumberAxis newYAxis = (NumberAxis)Graph.getYAxis();

                try
                {
                    xmin = Integer.parseInt( doms[0].getText() );
                    xmax = Integer.parseInt( doms[1].getText() );
                    ymin = Integer.parseInt( doms[2].getText() );
                    ymax = Integer.parseInt( doms[3].getText() );

                    if( xmin > xmax )
                    {

                        AlertBox( "Error", "X Minimum cannot be more than the X Maximum.", "Graphing Error" );
                        NumberFormatException a = new NumberFormatException();
                        throw a;

                    }
                    if( ymin > ymax )
                    {

                        AlertBox( "Error", "Y Minimum cannot be more than the Y Maximum.", "Graphing Error" );
                        NumberFormatException a = new NumberFormatException();
                        throw a;

                    }
                    newXAxis.setLowerBound( xmin );
                    newXAxis.setUpperBound( xmax );
                    newYAxis.setLowerBound( ymin );
                    newYAxis.setUpperBound( ymax );
                }
                catch( NumberFormatException ev )
                {

                    System.out.println( "Invalid numbers entered. Continuing." );

                }

                if( Equation.getText().isEmpty() )
                {

                   AlertBox( "Invalid Function", "Please enter a function.", "Graphing Error" ); 

                }else if( CountOcc( Equation.getText(), "(" ) != CountOcc( Equation.getText(), ")" ) )
                {

                    AlertBox( "Error", "Mismatched parentheses.", "Graphing Error" );

                }
                else
                {
                    AddData();
                }

            }

        });

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setAutoRanging( false );
        yAxis.setAutoRanging( false );

        xAxis.setLowerBound( xmin );
        xAxis.setUpperBound( xmax );
        yAxis.setLowerBound( ymin );
        yAxis.setUpperBound( ymax );

        Graph = new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series data = new XYChart.Series(); 
        
        Graph.setPrefSize( 2000, 1000 );
        
        Graph.setCreateSymbols( false );
        Graph.setLegendVisible( false );

        domsGrid.add( doms[0], 0, 0 );
        domsGrid.add( doms[1], 0, 1 );
        domsGrid.add( doms[2], 1, 0 );
        domsGrid.add( doms[3], 1, 1 );

        domsGrid.setAlignment( Pos.CENTER );

        grid.add( domsGrid, 0, 0 );
        grid.add( Graph, 0, 1 );
        grid.add( Equation, 0, 2 );
        grid.add( GraphButton, 0, 3 ); 

        primaryStage.setScene( new Scene(grid, 800, 600 ) );
        primaryStage.show();

    }

}
