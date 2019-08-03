/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompileResit2;

import java.util.ArrayList;

public class Lexer {

    public String[] createArray(String str, char c) {
        String[] str1 = str.split("");
        String ch = Character.toString(c);
        boolean flag = false;
        if (!str.equals("==") && !str.equals("!=") && !str.equals(">=") && !str.equals("<=")) {
            if (str.length() > 1 && str.contains(ch)) {
                if (str.charAt(0) == c && str.charAt(str.length() - 1) == c) {
                    String[] str2 = str.split(ch);
                    int m = str2.length + str2.length + 1;
                    str1 = new String[m];
                    str1[0] = ch;
                    int j = 0;
                    for (int i = 1; i < m - 1 && j < str2.length; i += 2, j++) {
                        str1[i] = str2[j];
                        if (j < str2.length - 1) {
                            str1[i + 1] = ch;
                        }
                    }
                    str1[m - 1] = ch;
                    flag = true;
                } else if (str.charAt(0) == c) {
                    String[] str2 = str.split(ch);
                    int m = str2.length + str2.length - 2;
                    str1 = new String[m];
                    str1[0] = ch;
                    int j = 1;
                    for (int i = 1; i < m && j < str2.length; i += 2, j++) {
                        str1[i] = str2[j];
                        if (j < str2.length - 1) {
                            str1[i + 1] = ch;
                        }
                    }
                    flag = true;
                } else if (str.charAt(str.length() - 1) == c) {
                    String[] str2 = str.split(ch);
                    int m = str2.length + str2.length;
                    str1 = new String[m];
                    int j = 0;
                    for (int i = 0; i < m - 1 && j < str2.length; i += 2, j++) {
                        str1[i] = str2[j];
                        if (j < str2.length - 1) {
                            str1[i + 1] = ch;
                        }
                    }
                    str1[m - 1] = ch;
                    flag = true;
                } else {
                    String[] str2 = str.split(ch);
                    int m = str2.length + str2.length - 1;
                    str1 = new String[m];
                    int j = 0;
                    for (int i = 0; i < m && j < str2.length; i += 2, j++) {
                        str1[i] = str2[j];
                        if (j < str2.length - 1) {
                            str1[i + 1] = ch;
                        }
                    }
                    flag = true;
                }
            }
        }
        if (!flag) {
            str1 = new String[1];
            str1[0] = str;
        }
        return str1;
    }

    public String[] functionCall(String[] code1) {
        String[] output3;
        ArrayList<String[]> list1;
        char[] ch1 = new char[4];
        ch1[0] = '=';
        ch1[1] = '>';
        ch1[2] = '<';
        ch1[3] = '!';
        for (int o = 0; o < 4; o++) {
            list1 = new ArrayList();
            for (int i = 0; i < code1.length; i++) {
                list1.add(createArray(code1[i], ch1[o]));
            }
            int count = 0;
            for (int i = 0; i < list1.size(); i++) {
                count += list1.get(i).length;
            }
            output3 = new String[count];
            int k = 0;
            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list1.get(i).length; j++) {
                    output3[k] = list1.get(i)[j];
                    k++;
                }
            }
            code1 = output3;
        }
        return code1;
    }

    public static void main(String[] args) {
        // TODO code application logic here

        Lexer object = new Lexer();
        ArrayList<String> Tokens = new ArrayList<String>();

        //String CODE=jTextArea1.getText();
        String CODE = "{flo=at co==s, x, n<,<=12 term>!!=, eps, alt;";

        String code = CODE.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", "")
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("*", " * ")
                .replace("/", " / ")
                .replace("==", " == ")
                .replace(">=", " >= ")
                .replace("<=", " <= ")
                .replace("!=", " != ")
                .replace("(", " ( ")
                .replace(")", " ) ")
                .replace("{", " { ")
                .replace("}", " } ")
                .replace("[", " [ ")
                .replace("]", " ] ")
                .replace(".", " . ")
                .replace(",", " , ")
                .replace(";", " ; ")
                .replace(":", " ; ");

        String[] output = code.split("\\s+");
        output = object.functionCall(output);
        for (int i = 0; i < output.length; i++) {
            switch (output[i]) {
                //DataTypes
                case ("int"):
                    Tokens.add("int");
                    System.out.println("int --> DATA TYPE");
                    break;
                case ("float"):
                    Tokens.add("float");
                    System.out.println("float --> DATA TYPE");
                    break;

                //Reserved Words
                case ("for"):
                    Tokens.add("for");
                    System.out.println("for --> Reserved Word");
                    break;
                case ("while"):
                    Tokens.add("while");
                    System.out.println("while --> Reserved Word");
                    break;
                case ("if"):
                    Tokens.add("if");
                    System.out.println("if --> Reserved Word ");
                    break;
                case ("else"):
                    Tokens.add("else");
                    System.out.println("else --> Reserved Word ");
                    break;
                //Binary Operators
                case ("+"):
                    Tokens.add("+");
                    System.out.println("+ -->Binary Operator ");
                    break;
                case ("-"):
                    Tokens.add("-");
                    System.out.println("- -->Binary Operator ");
                    break;
                case ("*"):
                    Tokens.add("*");
                    System.out.println("* -->Binary Operator ");
                    break;
                case ("/"):
                    Tokens.add("/");
                    System.out.println("/ -->Binary Operator ");
                    break;

                //Symbols 
                case (""):
                    Tokens.add("");
                    System.out.println("");
                    break;
                case ("="):
                    Tokens.add("=");
                    System.out.println("= -->Symbol ");
                    break;
                case ("!"):
                    Tokens.add("!");
                    System.out.println("! -->Symbol ");
                    break;
                case ("<"):
                    Tokens.add("<");
                    System.out.println("< -->Symbol ");
                    break;
                case (">"):
                    Tokens.add(">");
                    System.out.println("> -->Symbol ");
                    break;
                case ("("):
                    Tokens.add("(");
                    System.out.println("( -->Symbol ");
                    break;
                case (")"):
                    Tokens.add(")");
                    System.out.println(") -->Symbol ");
                    break;
                case ("{"):
                    Tokens.add("{");
                    System.out.println("{ -->Symbol ");
                    break;
                case ("}"):
                    Tokens.add("}");
                    System.out.println("} -->Symbol ");
                    break;
                case (";"):
                    Tokens.add(";");
                    System.out.println("; -->Symbol ");
                    break;
                case (","):
                    Tokens.add(",");
                    System.out.println(", -->Symbol ");
                    break;
                case ("."):
                    Tokens.add(".");
                    System.out.println(". -->Symbol ");
                    break;
                case ("<="):
                    Tokens.add("<=");
                    System.out.println("<= -->Symbol ");
                    break;
                case (">="):
                    Tokens.add(">=");
                    System.out.println(">= -->Symbol ");
                    break;
                case ("!="):
                    Tokens.add("!=");
                    System.out.println("!= -->Symbol ");
                    break;
                case ("=="):
                    Tokens.add("==");
                    System.out.println("== -->Symbol ");
                    break;
                default:
                    String c = output[i];

                    char ch[] = c.toCharArray();

                    if (Character.isDigit(ch[0])) {
                        Tokens.add("Integer Lateral");
                        System.out.println(output[i] + " --> Integer Lateral ");
                    } else if (Character.isLetter(ch[0])) {
                        Tokens.add("Identifier");
                        System.out.println(output[i] + " --> Identifier ");
                    } //                            else if(c.contains("="))
                    //                            {
                    //                               // symbols.contains(Character.toString(code.charAt(i)))
                    //                                System.out.println(output[i] + " --> Symbol ");
                    //                            }
                    else {
                        Tokens.add("Invalid Token");
                        System.out.println(output[i] + " --> Invalid Token ");
                    }
                    break;
            }
        }

    }
}
