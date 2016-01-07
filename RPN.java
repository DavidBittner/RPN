import java.util.ArrayList;
import java.util.Scanner;

public class RPN
{

    private static String equat;
    private static ArrayList<String> finalEquat;

    RPN()
    {

        equat = "";
        finalEquat = new ArrayList<>();

    }

    public static void main( String []args )
    {

        Scanner in = new Scanner( System.in );
        String input = in.next();

        ArrayList<String> array = shuntingYard( tokenize( input ) );

        for( int i = 0; i < array.size(); i++ )
        {

            System.out.println( array.get(i) );

        }

    }

    public static ArrayList<String> shuntingYard( ArrayList<String> input )
    {

        ArrayList<String> output = new ArrayList<>(0);
        ArrayList<String> operator = new ArrayList<>(0);

        for( String i : input )
        {

            //System.out.println( i );

            if( isNumeric( i ) )
            {
 
                output.add( i );

            }else
            {

                operator.add( i );

            }

        }

        for( String i : operator )
        {

            output.add( i );

        }

        return output;

    }

    public static boolean isNumeric( String input )
    {

       try
       {

            Integer.parseInt( input );

       }
       catch( NumberFormatException e )
       {

            return false;

       }

       return true;

    }

    public static boolean isToken( String input )
    {

        String ops[] = {"+","-","/","*","^","(",")"};
        String funcs[] = {"e","sin","cos","tan","asin","acos","atan","abs","x"};

        for( int i = 0; i < ops.length; i++ )
        {

            if( input.equals( ops[i] ) )
            {

                return true;

            }

        }

        for( int i = 0; i < funcs.length; i++ )
        {

            if( input.equals( funcs[i] ) )
            {

                return true;

            }

        }

        return false;

    }

    public static ArrayList<String> tokenize( String input )
    {

        input.replace( " ", "" ); 
        ArrayList<String> tokens = new ArrayList<>(0);

        String holder = "";

        for( int i = 0; i < input.length(); i++ )
        {

            if( isToken( holder ) && !holder.isEmpty() )
            {

                tokens.add( holder );
                holder = "";

                holder += input.charAt(i);

                continue;

            }

            if( ( isNumeric( holder ) != isNumeric( Character.toString(input.charAt(i)) ) ) && !holder.isEmpty() )
            {

                tokens.add(holder);
                holder = "";

                holder += input.charAt(i);

                continue;

            }

            holder += input.charAt(i);

        }

        tokens.add(holder);

        return tokens;

    }

}
