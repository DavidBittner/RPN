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

            //System.out.print( array.get(i) + " " );

        }

        System.out.println( EvalRPN( array ) );

    }

    public static int getPrecedence( String op )
    {

        switch( op )
        {

            case "(":
            {
                return 5;
            }
            case ")":
            {
                return 5;
            }
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
    
            if( isNumeric( i ) )
            {

                output.add( i );

            }else if( !i.equals( "(" ) && !i.equals( ")" ) )
            {

                if( operator.size() > 0 && !operator.get( operator.size()-1 ).equals("(") )
                {

                    //left
                    if( getAssoc( i ) )
                    {

                        while( operator.size() > 0 )
                        {

                            if( getPrecedence( i ) <= getPrecedence( operator.get(operator.size()-1) ) )
                            {

                                output.add( operator.get( operator.size()-1 ) );
                                operator.remove( operator.size()-1 );

                            }else
                            {

                                break;
                                
                            }

                        }
                        operator.add(i);

                    }else
                    {

                        while( operator.size() > 0 )
                        {

                            if( getPrecedence( i ) < getPrecedence( operator.get( operator.size()-1 ) ) )
                            {

                                output.add( operator.get( operator.size()-1 ) );
                                operator.remove( operator.size()-1 );

                            }else
                            {

                                break;

                            }

                        }

                        operator.add(i);

                    }

                }else
                {

                    operator.add( i );

                }

            }else
            {

                if( i.equals("(") )
                {

                    operator.add( "(" );

                }else if( i.equals(")") )
                {


                    while( operator.size() > 0 && !operator.get(operator.size()-1).equals("(") )
                    {

                        output.add( operator.get(operator.size()-1) );
                        operator.remove( operator.size()-1 );

                    }
                    operator.remove( operator.size()-1 );

                }

            }

        }

        for( int i = operator.size()-1; i >= 0; i-- )
        {
            output.add( operator.get(i) );
        }

        System.out.println( " " );

        return output;

    }

    public static ArrayList<Double> GetVals( int count, ArrayList<Double> in )
    {

        ArrayList<Double> out = new ArrayList<>();

        if( count > in.size() )
        {

            System.out.println( "Not enough vals!" );
            return null;

        }

        for( int i = 0; i < count; i++ )
        {

            out.add( in.get( in.size()-1 ) );
            in.remove( in.size()-1 );

        }

        return out;

    }

    public static double EvalRPN( ArrayList<String> input )
    {

        ArrayList<Double> output = new ArrayList<>();
        ArrayList<String> ops = new ArrayList<>();

        ArrayList<Double> vals = new ArrayList<>();
        
        for( String i : input )
        {

            if( isNumeric( i ) )
            {

                output.add( Double.parseDouble( i ) );

            }else
            {

                switch( i )
                {

                    case "+":
                    {

                        vals = GetVals( 2, output );
                        output.add( vals.get(0) + vals.get(1) );
                        break;

                    }
                    case "-":
                    {

                        vals = GetVals( 2, output );
                        output.add( vals.get(1) - vals.get(0) );
                        break;

                    }
                    case "*":
                    {

                        vals = GetVals( 2, output );
                        output.add( vals.get(1) * vals.get(0) );
                        break;

                    }
                    case "/":
                    {

                        vals = GetVals( 2, output );
                        output.add( vals.get(1) / vals.get(0) );
                        break;

                    }
                    case "^":
                    {

                        vals = GetVals( 2, output );
                        output.add( Math.pow( vals.get(1), vals.get(0) ) );
                        break;

                    }

                }

            }

        }

        return output.get(0);

    }

    public static boolean isNumeric( String input )
    {

       try
       {

            Double.parseDouble( input );

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

            if( ( isNumeric( holder ) != isNumeric( Character.toString(input.charAt(i)) ) ) && !holder.isEmpty() && input.charAt( i ) != '.' )
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
