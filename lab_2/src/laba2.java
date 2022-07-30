import java.util.*;
public class laba2 {
    static void Empty_line(String[] arg) throws EmptyLineException
    {
        if (arg.length == 0)
        {
            System.out.println("ERROR:Line is empty");
            throw new EmptyLineException();
        }
    }
    public static void main(String[] args)
    {
        counter_cl arr = null;
        // System.out.print("Введите последовательность целых чисел через пробел: ");
        // Scanner scanner = new Scanner(System.in);
        try {
            Empty_line(args);
            arr = new counter_cl(args);
        }
        catch (EmptyLineException e)
        {
            System.exit(0);
        }
        arr.podschet();
        if (arr.count_ch_ot == arr.count_nch_ot && arr.count_ch_ot != 0)
            System.out.println("Odd and even negative numbers equally");
        else if (arr.count_ch_ot > arr.count_nch_ot)
            System.out.println("There are more even negative numbers");
        else if (arr.count_ch_ot < arr.count_nch_ot)
            System.out.println("There are more odd negative numbers");
        else
            System.out.println("There are no negative numbers in the sequence");
    }
}
class counter_cl implements IFunc_2, IConst_2
{
    int count_ch_ot;
    int count_nch_ot;
    int[] numArr;
    public counter_cl(String[] arg)
    {
        numArr = new int[arg.length];
        for (int i = 0; i < arg.length; i++)
        {
            try {
                numArr[i] = Integer.parseInt(arg[i]);
                if (arg.length == len_array7)
                    throw new len_array_check();
                else if (numArr[i] < min_int5)
                    throw new Min_integer();
                else if (numArr[i] == const_num8)
                    throw new const_number();

            }
            catch(NumberFormatException e)
            {
                System.out.println(e);
                System.exit(1);
            }
            catch (Min_integer e)
            {
                System.out.println(e);
                System.exit(1);
            }
            catch (len_array_check e)
            {
                System.out.println(e);
                System.exit(1);
            }
            catch (const_number e)
            {
                System.out.println(e);
                System.exit(1);
            }
            //System.out.print(numArr[i] + " ");
        }
    }
    public void odd_negative()
    {
        count_nch_ot++;
    }
    public void even_negative()
    {
        count_ch_ot++;
    }
    public void podschet()
    {
        for (int i = 0; i < numArr.length; i++)
        {
            //System.out.println(numArr[i] % 2 + " ");
            if (numArr[i] % 2 == 0 && numArr[i] < 0) {
                even_negative();
                //System.out.println("chet: " + numArr[i] + " ");
            }
            else if (numArr[i] % 2 == -1 && numArr[i] < 0) {
                odd_negative();
                //System.out.println("chet: " + numArr[i] + " ");
            }
        }
    }

}
interface IFunc_2
{

    public void odd_negative();
    public void even_negative();
    public void podschet();
}
interface IConst_2
{
    static final int min_int5 = -10;
    static final int len_array7 = 5;
    static final int const_num8 = 5;
}
class EmptyLineException extends Exception
{
    public EmptyLineException(){}
    public String toString (){return "ERROR:Line is Empty";}
}
class Min_integer extends Exception
{
    public Min_integer(){}
    public String toString (){return "ERROR:The number is less than the allowed";}
}
class len_array_check extends Exception
{
    public len_array_check(){}
    public String toString (){return "ERROR:The array has const length";}
}
class const_number extends Exception
{
    public const_number(){}
    public String toString (){return "ERROR:The number is equal to a constant number";}
}
