package customstringbuilder;

public class Test {
    public static void main(String args[]){
        
        custom_string_builder sb = new custom_string_builder();
        char[] str = "hello".toCharArray();
        sb.append(str);
        System.out.println(sb.value);
        
        //Testing constructors
        System.out.println(new custom_string_builder(34));
        System.out.println(new custom_string_builder("thisisgreat"));
        System.out.println(new custom_string_builder(new CompactCharSequence("This is a compact char sequence")));
        
        //Testing appends
        sb.append(new Object());
        System.out.println(sb.value);
        
        sb.append("This is a String");
        System.out.println(sb.value);
        
        sb.append(new StringBuffer("This is a StringBuffer"));
        System.out.println(sb.value);
        
        sb.append(new CompactCharSequence("This is an appended compact char sequence"));
        System.out.println(sb.value);
        
        
    }
}
