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

    public static int getPrecedence( String op )
    {

        switch( op )
        {

            case "^":
            {
                return 4;
            }
            case "*":
            {
                return 3;
            }
            case "/":
            {
                return 3;
            }
            case "+":
            {
                return 2;
            }
            case "-":
            {
                return 2;
            }

        }

        return -1;

    }

    public static boolean getAssoc( String op )
    {

        //false = right associativity
        //true  = left  associativity

        switch( op )
        {

            case "^":
            {
                return false;
            }
            case "*":
            {
                return true;
            }
            case "/":
            {
                return true;
            }
            case "+":
            {
                return true;
            }
            case "-":
            {
                return true;
            }

        }

        return false;

    }


    public static ArrayList<String> shuntingYard( ArrayList<String> input )
    {

        ArrayList<String> output = new ArrayList<>(0);
        ArrayList<String> operator = new ArrayList<>(0);

        for( String i : input )
        {

            if( isNumeric( i ) || i.equals("x") )
            {
 
                output.add( i );

            }else
            {

                if( i.equals( "(" ) )
                {

                    operator.add(i);

                }else if( i.equals(")" ) )
                {

                    if( operator.size() > 0 )
                    {

                        while( !operator.get(0).equals( "(" ) )
                        {

                            System.out.println( operator.get(0) );
                            output.add( operator.get(0) );
                            operator.remove( 0 );

                        }
                        operator.remove( 0 );

                    }

                    continue;

                }

                if( operator.size() > 0 )
                {

                    while( operator.size() > 0 )
                    {

                        //If the operator has a left association.
                        if( !getAssoc( i ) )
                        {

                            if( getPrecedence( i ) <= getPrecedence(operator.get(0)) )
                            {

                                output.add( operator.get(0) );
                                operator.remove( 0 );

                            }else
                            {

                                break;

                            }
                        
                        //If the operator has a right association.
                        }else if( getAssoc( i ) )
                        {

                            if( getPrecedence( i ) <= getPrecedence( operator.get(0) ) )
                            {

                                output.add( operator.get(0) );
                                operator.remove( 0 );

                            }else
                            {

                                break;

                            }

                        }

                    }

                    operator.add( i );

                }else
                {

                    operator.add( i );


                }

            }

        }

        for( int i = operator.size()-1; i >= 0; i-- )
        {
            output.add( operator.get(i) );
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
