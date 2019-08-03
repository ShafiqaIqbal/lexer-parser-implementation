/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompileResit2;

import java.util.ArrayList;

class definedList {

    public boolean isInitialized;
    public boolean isVariable;
    public int arguments;
    public String Type;
    public String name;
    public String value;
}

public class Parser {

    private int i = 0, j = -1;
    private ArrayList<String> errors = new ArrayList();
    private String[][] array;
    private ArrayList<definedList> list1 = new ArrayList();

    // to check '=' operator
    public boolean isOperator1(String str) {
        if (str.equals("=")) {
            return true;
        }
        return false;
    }

    // to check '+' '-' '*' '/' operator
    public boolean isOperator2(String str) {
        if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
            return true;
        }
        return false;
    }

    // to check if it a condition or not
    public boolean isCondition(String str) {
        if (str.equals("<")) {
            return true;
        }
        if (str.equals(">")) {
            return true;
        }
        if (str.equals("==")) {
            return true;
        }
        if (str.equals(">=")) {
            return true;
        }
        if (str.equals("<=")) {
            return true;
        }
        if (str.equals("!=")) {
            return true;
        }
        return false;
    }

    // to check if it is a constant or not
    public boolean isConstant(String str) {
        for (int k = 0; k < str.length(); k++) {
            if (str.charAt(k) < '0' || str.charAt(k) > '9') {
                return false;
            }
        }
        return true;
    }

    // to check if it is a reserved word or not
    public boolean isVariableName(String str) {
        if (str.equals("float")) {
            return false;
        }
        if (str.equals("int")) {
            return false;
        }
        if (str.equals("if")) {
            return false;
        }
        if (str.equals("for")) {
            return false;
        }
        if (str.equals("while")) {
            return false;
        }
        if (str.equals("(")) {
            return false;
        }
        if (str.equals(")")) {
            return false;
        }
        if (str.equals("*")) {
            return false;
        }
        if (str.equals("/")) {
            return false;
        }
        if (str.equals("-")) {
            return false;
        }
        if (str.equals("+")) {
            return false;
        }
        if (str.equals("=")) {
            return false;
        }
        if (str.equals("!")) {
            return false;
        }
        if (str.equals("<")) {
            return false;
        }
        if (str.equals(">")) {
            return false;
        }
        if (str.equals("==")) {
            return false;
        }
        if (str.equals(">=")) {
            return false;
        }
        if (str.equals("<=")) {
            return false;
        }
        if (str.equals("!=")) {
            return false;
        }
        if (str.equals("&&")) {
            return false;
        }
        if (str.equals("{")) {
            return false;
        }
        if (str.equals("}")) {
            return false;
        }
        if (str.equals(",")) {
            return false;
        }
        if (str.equals(";")) {
            return false;
        }
        return true;
    }

    // to check if a variable is defined already in the list or not
    public boolean isdefined(String str, ArrayList<definedList> list) {
        for (definedList l : list) {
            if (l.name.equals(str)) {
                return true;
            }
        }
        return false;
    }

    // to check the data type
    public String getType(String str, ArrayList<definedList> list) {
        for (definedList l : list) {
            if (l.name.equals(str)) {
                return l.Type;
            }
        }
        return null;
    }

    // to check arguments of the function
    public int checkArguments(int count, ArrayList<definedList> list) {
        String name = "";
        j++;
        for (; i < array.length; i++) {
            for (; j < array[i].length; j++) {
                if (!array[i][j].equals("")) {
                    if (count == 0) {
                        if (name.equals("") && array[i][j].equals(")")) {
                            return 0;
                        } else {
                            errors.add("Syntax Error At Line : " + (i + 1) + " Missing ) ");
                            return -1;
                        }
                    }
                    if (!name.equals("") && array[i][j].equals(")") && count == 1) {
                        return 0;
                    } else if (name.equals("") && array[i][j].equals(")") && count != 0) {
                        return -1;
                    } else if (isVariableName(array[i][j]) && (isdefined(array[i][j], list) || isConstant(array[i][j])) && name.equals("")) {
                        name = array[i][j];
                    } else if (isVariableName(array[i][j]) && !name.equals("")) {
                        errors.add("Syntax Error At Line : " + (i + 1) + " Missing Operator ");
                        return -1;
                    } else if (array[i][j].equals(",") && !name.equals("")) {
                        return checkArguments(count - 1, list);
                    } else if (!isdefined(array[i][j], list) && isVariableName(array[i][j]) && !isConstant(array[i][j])) {
                        errors.add("Error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is not defined");
                        return -1;
                    } else {
                        errors.add("Syntax Error At Line : " + (i + 1));
                        return -1;
                    }
                }
            }
            j = 0;
            return -1;
        }
        return 0;
    }

    // to check if it is a function or not
    public boolean isFunction(String str, ArrayList<definedList> list) {
        for (definedList l : list) {
            if (l.name.equals(str)) {
                return !l.isVariable;
            }
        }
        return false;
    }

    // to set the arguments for the function
    public int getArguments(int count, ArrayList<definedList> list, String n, String t, boolean flag) {
        String name = "";
        String Type = "";
        definedList object = new definedList();
        j++;
        for (; i < array.length; i++) {
            for (; j < array[i].length; j++) {
                if (!array[i][j].equals("")) {
                    if (name.equals("") && Type.equals("") && array[i][j].equals(")") && !flag) {
                        object.name = n;
                        object.Type = t;
                        object.isVariable = false;
                        object.arguments = count;
                        list.add(object);
                        return 0;
                    } else if (!name.equals("") && !Type.equals("") && array[i][j].equals(")")) {
                        object.name = n;
                        object.Type = t;
                        object.isVariable = false;
                        object.arguments = count + 1;
                        list.add(object);
                        object = new definedList();
                        object.name = name;
                        object.Type = Type;
                        object.isVariable = true;
                        list.add(object);
                        return 0;
                    } else if (!name.equals("") && !Type.equals("") && array[i][j].equals(",")) {
                        object.name = name;
                        object.Type = Type;
                        object.isVariable = true;
                        list.add(object);
                        return getArguments(count + 1, list, n, t, true);
                    } else if (name.equals("") && Type.equals("") && (array[i][j].equals("int") || array[i][j].equals("float"))) {
                        Type = array[i][j];
                    } else if (isVariableName(array[i][j]) && !isConstant(array[i][j]) && !isdefined(array[i][j], list) && name.equals("") && !Type.equals("")) {
                        name = array[i][j];
                    } else if (Type.equals("") && !array[i][j].equals("int") && !array[i][j].equals("int")) {
                        errors.add("Syntax Error At Line : " + (i + 1) + " Missing datatype");
                        return -1;
                    } else if (isConstant(array[i][j])) {
                        errors.add("Syntax Error At Line : " + (i + 1) + " Not a variable");
                        return -1;
                    } else {
                        errors.add("Syntax Error At Line : " + (i + 1));
                        return -1;
                    }
                }
            }
            j = 0;
            errors.add("Syntax Error At Line : " + (i + 1));
            return -1;
        }
        return 0;
    }

    // to check total number of arguments for the function
    public int getArgumentsCount(String str, ArrayList<definedList> list) {
        for (definedList l : list) {
            if (l.name.equals(str)) {
                return l.arguments;
            }
        }
        return 0;
    }

    public void evaluate3(ArrayList<definedList> list, boolean flag) {
        definedList object = new definedList();
        String name = "";
        String Type = "";
        j++;
        int value = -1;
        boolean isfunction = false;
        for (; i < array.length; i++) {
            for (; j < array[i].length; j++) {
                if (!array[i][j].equals("")) {
                    if (flag && array[i][j].equals("}") && name.equals("") && Type.equals("")) {
                        return;
                    } else if (name.equals("") && Type.equals("") && array[i][j].equals("{")) {
                        ArrayList<definedList> list2 = new ArrayList();
                        for (definedList l : list) {
                            list2.add(l);
                        }
                        evaluate3(list2, true);
                    } else if (!Type.equals("") && !name.equals("") && array[i][j].equals(",")) {
                        object = new definedList();
                        object.name = name;
                        object.Type = Type;
                        list.add(object);
                        name = "";
                    } else if (Type.equals("") && (array[i][j].equals("int") || array[i][j].equals("float"))) {
                        Type = array[i][j];
                    } else if (name.equals("") && Type.equals("") && (array[i][j].equals("if") || array[i][j].equals("while") || array[i][j].equals("for"))) {
                        if (checkIfOrLoops(array[i][j], list) == -1) {
                            break;
                        }
                    } else if (array[i][j].equals(";") && !name.equals("") && !Type.equals("")) {
                        object = new definedList();
                        object.name = name;
                        object.Type = Type;
                        list.add(object);
                        name = "";
                        Type = "";
                    } else if (array[i][j].equals(";") && name.equals("") && Type.equals("")) {
                        name = "";
                        Type = "";
                    } else if (Type.equals("") && isVariableName(array[i][j]) && !isConstant(array[i][j]) && isdefined(array[i][j], list)) {
                        name = array[i][j];
                    } else if (!Type.equals("") && isVariableName(array[i][j]) && !isConstant(array[i][j]) && !isdefined(array[i][j], list)) {
                        name = array[i][j];
                    } else if (Type.equals("") && !name.equals("") && array[i][j].equals("(") && isFunction(name, list)) {
                        if (checkArguments(getArgumentsCount(name, list), list) == -1) {
                            name = "";
                            Type = "";
                            break;
                        }
                        name = "";
                        Type = "";
                    } else if (!Type.equals("") && !name.equals("") && array[i][j].equals("(")) {
                        if (getArguments(0, list, name, Type, false) == -1) {
                            name = "";
                            Type = "";
                            break;
                        }
                        name = "";
                        Type = "";
                    } else if (Type.equals("") && !name.equals("") && array[i][j].equals("=")) {
                        if (getType(name, list).equals("float")) {
                            if (checkValueFloat(list, false, ";") == -1) {
                                name = "";
                                Type = "";
                                break;
                            }
                            name = "";
                            Type = "";
                        } else {
                            if (checkValue(list, false, ";") == -1) {
                                name = "";
                                Type = "";
                                break;
                            }
                            name = "";
                            Type = "";
                        }
                    } else if (!Type.equals("") && !name.equals("") && array[i][j].equals("=")) {
                        object = new definedList();
                        object.name = name;
                        object.Type = Type;
                        list.add(object);
                        if (Type.equals("float")) {
                            if (checkValueFloat(list, false, ";") == -1) {
                                name = "";
                                Type = "";
                                break;
                            }
                            name = "";
                            Type = "";
                        } else {
                            if (checkValue(list, false, ";") == -1) {
                                name = "";
                                Type = "";
                                break;
                            }
                            name = "";
                            Type = "";
                        }
                    } else if (Type.equals("") && !isdefined(array[i][j], list) && isVariableName(array[i][j])) {
                        errors.add("Syntax error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is not defined");
                        break;
                    } else {
                        errors.add("Syntax error At Line : " + (i + 1) + " Invalid expression");
                        name = "";
                        Type = "";
                        break;
                    }
                }
            }
            j = 0;
        }
        return;
    }

    public void evaluate() {
        evaluate3(list1, false);
    }

    public int checkIfOrLoops(String str, ArrayList<definedList> list) {
        j++;
        if (j < array[i].length) {
            if (str.equals("for")) {
                while (j < array[i].length && array[i][j].equals(""));
                if (j == array[i].length) {
                    j = 0;
                    errors.add("Syntax Error At Line : " + (i + 1) + " in For Loop ");
                    return -1;
                }
                if (array[i][j].equals("(")) {
                    ArrayList<definedList> list2 = new ArrayList();
                    for (definedList l : list) {
                        list2.add(l);
                    }
                    if (checkCondition3(list2) == -1) {
                        return -1;
                    }
                    j++;
                    for (; i < array.length; i++) {
                        for (; j < array[i].length; j++) {
                            if (!array[i][j].equals("")) {
                                if (array[i][j].equals("{")) {
                                    evaluate3(list2, true);
                                    return 0;
                                } else if (isVariableName(array[i][j]) && isdefined(array[i][j], list)) {
                                    j--;
                                    return checkValue(list, false, ";");
                                } else if (isVariableName(array[i][j]) && !isdefined(array[i][j], list)) {
                                    errors.add("Syntax Error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is not defined in " + str + " statement ");
                                    return -1;
                                } else {
                                    errors.add("Syntax Error At Line : " + (i + 1) + " in for loop ");
                                    return -1;
                                }
                            }
                        }
                        j = 0;
                    }
                } else {
                    errors.add("Syntax Error At Line : " + (i + 1) + " Invalid start of expression ");
                    return -1;
                }
            } else {
                while (j < array[i].length && array[i][j].equals(""));
                if (j == array[i].length) {
                    j = 0;
                    return -1;
                }
                if (array[i][j].equals("(")) {
                    if (checkCondition(list, ")", false) == -1) {
                        return -1;
                    }
                    j++;
                    for (; i < array.length; i++) {
                        for (; j < array[i].length; j++) {
                            if (!array[i][j].equals("")) {
                                if (array[i][j].equals("{")) {
                                    ArrayList<definedList> list2 = new ArrayList();
                                    for (definedList l : list) {
                                        list2.add(l);
                                    }
                                    evaluate3(list2, true);
                                    return 0;
                                } else if (array[i][j].equals("int") || array[i][j].equals("float")) {
                                    return checkValue2(array[i][j], list);
                                } else if (isVariableName(array[i][j]) && isdefined(array[i][j], list)) {
                                    j--;
                                    return checkValue(list, false, ";");
                                } else if (isVariableName(array[i][j]) && !isdefined(array[i][j], list)) {
                                    errors.add("Syntax Error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is not defined in " + str + " statement ");
                                    return -1;
                                } else {
                                    errors.add("Syntax Error At Line : " + (i + 1) + " in " + str + " statement ");
                                    return -1;
                                }
                            }
                        }
                        j = 0;
                    }
                } else {
                    errors.add("Syntax Error At Line : " + (i + 1) + " in " + str + " statement ");
                    return -1;
                }
            }
        }
        return -1;
    }

    public int checkCondition3(ArrayList<definedList> list) {
        j++;
        while (j < array[i].length && array[i][j].equals(""));
        if (j == array[i].length) {
            return -1;
        }
        if (array[i][j].equals("int") || array[i][j].equals("float")) {
            checkValue2(array[i][j], list);
            checkCondition(list, ";", true);
            checkValue(list, true, ")");
        } else if (isVariableName(array[i][j]) && !isConstant(array[i][j]) && isdefined(array[i][j], list)) {
            j--;
            checkValue(list, true, ";");
            checkCondition(list, ";", true);
            checkValue(list, true, ")");
        } else if (array[i][j].equals(";")) {
            checkCondition(list, ";", true);
            checkValue(list, true, ")");
        }
        return 0;
    }

    public int checkCondition(ArrayList<definedList> list, String op, boolean And) {
        String variable1 = "";
        String variable2 = "", variable3 = "";
        boolean isFloat = false, point = false;
        j++;
        boolean operator = false;
        for (; j < array[i].length; j++) {
            if (!array[i][j].equals("")) {
                if (array[i][j].equals(op)) {
                    if (!variable1.equals("") && !variable2.equals("") && operator && !isFloat) {
                        return 0;
                    }
                    if (variable1.equals("") && variable2.equals("") && !isFloat) {
                        return 0;
                    }
                    errors.add("Syntax Error At Line : " + (i + 1) + " invalid expression ");
                    return -1;
                }
                if (variable1.equals("") && isVariableName(array[i][j]) && (isConstant(array[i][j]) || isdefined(array[i][j], list))) {
                    And = false;
                    variable1 = array[i][j];
                    if (getType(variable1, list).equals("float")) {
                        isFloat = true;
                    }
                } else if (!variable1.equals("") && isCondition(array[i][j])) {
                    operator = true;
                } else if (operator && isVariableName(array[i][j]) && (isConstant(array[i][j]) || isdefined(array[i][j], list)) && variable2.equals("")) {
                    variable2 = array[i][j];
                } else if (isFloat && !variable2.equals("") && !point && array[i][j].equals(".")) {
                    point = true;
                } else if (isFloat && !variable2.equals("") && point && isConstant(array[i][j])) {
                    variable3 = array[i][j];
                    isFloat = false;
                } else if (!And && !isFloat && !variable1.equals("") && !variable2.equals("") && operator && array[i][j].equals("&&")) {
                    return checkCondition(list, op, true);
                } else if (isVariableName(array[i][j]) && !isdefined(array[i][j], list)) {
                    errors.add("Syntax Error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is not defined");
                    return -1;
                } else {
                    errors.add("Syntax Error At Line : " + (i + 1) + " invalid expression ");
                    return -1;
                }
            }
        }
        j = 0;
        return -1;
    }

    public int checkValue2(String str, ArrayList<definedList> list) {
        String Type = str, name = "";
        j++;
        for (; j < array[i].length; j++) {
            if (!array[i][j].equals("")) {
                if (isVariableName(array[i][j]) && !isConstant(array[i][j]) && !isdefined(array[i][j], list)) {
                    name = array[i][j];
                    definedList object = new definedList();
                    object.Type = Type;
                    object.name = name;
                    list.add(object);
                } else if (!name.equals("") && array[i][j].equals("=")) {
                    return checkValue(list, false, ";");
                } else if (isVariableName(array[i][j]) && !isConstant(array[i][j]) && isdefined(array[i][j], list)) {
                    errors.add("Syntax Error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is already defined");
                    return -1;
                } else if (!name.equals("") && !Type.equals("") && array[i][j].equals(";")) {
                    return 0;
                } else {
                    errors.add("Syntax Error At Line : " + (i + 1));
                    return -1;
                }
            }
        }
        return -1;
    }

    public int checkValue(ArrayList<definedList> list, boolean constant1, String op) {
        String name = "";
        boolean operator = false;
        j++;
        for (; j < array[i].length; j++) {
            if (!array[i][j].equals("")) {
                if (constant1 && array[i][j].equals(op)) {
                    return 0;
                } else if (name.equals("") && isVariableName(array[i][j]) && (isConstant(array[i][j]) || isdefined(array[i][j], list))) {
                    name = array[i][j];
                    constant1 = true;
                    if (isConstant(array[i][j])) {
                        operator = true;
                    }
                } else if (isOperator1(array[i][j]) && !operator && !name.equals("")) {
                    return checkValue(list, false, op);
                } else if (!name.equals("") && isOperator2(array[i][j])) {
                    name = "";
                    operator = true;
                    constant1 = false;
                } else if (!isdefined(array[i][j], list) && isVariableName(array[i][j]) && !isConstant(array[i][j])) {
                    errors.add("Syntax Error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is not defined");
                    return -1;
                } else {
                    errors.add("Syntax Error At Line : " + (i + 1) + " invalid expression");
                    return -1;
                }
            }
        }
        j = 0;
        return -1;
    }

    public int checkValueFloat(ArrayList<definedList> list, boolean constant1, String op) {
        String name = "";
        boolean operator = false, point = false;
        String Constant2 = "", Constant3 = "";
        j++;
        for (; j < array[i].length; j++) {
            if (!array[i][j].equals("")) {
                if (constant1 && array[i][j].equals(op) && ((Constant2.equals("") && !point && Constant3.equals("")) || (point && !Constant2.equals("") && !Constant3.equals("")))) {
                    return 0;
                } else if (name.equals("") && isVariableName(array[i][j]) && (isConstant(array[i][j]) || isdefined(array[i][j], list))) {
                    name = array[i][j];
                    constant1 = true;
                    if (isConstant(array[i][j])) {
                        Constant2 = array[i][j];
                        operator = true;
                    }
                } else if (operator && !Constant2.equals("") && array[i][j].equals(".")) {
                    point = true;
                } else if (operator && !Constant2.equals("") && point && isConstant(array[i][j])) {
                    Constant3 = array[i][j];
                } else if (isOperator1(array[i][j]) && !operator && !name.equals("")) {
                    return checkValue(list, false, op);
                } else if (!name.equals("") && isOperator2(array[i][j]) && ((point && !Constant3.equals("") && !Constant2.equals("")) || (!point && Constant3.equals("") && Constant2.equals("")))) {
                    name = "";
                    operator = true;
                    constant1 = false;
                    point = false;
                    Constant3 = "";
                    Constant2 = "";
                } else if (!isdefined(array[i][j], list) && isVariableName(array[i][j]) && !isConstant(array[i][j])) {
                    errors.add("Syntax Error At Line : " + (i + 1) + " variable '" + array[i][j] + "' is not defined");
                    return -1;
                } else {
                    errors.add("Syntax Error At Line : " + (i + 1) + " invalid expression");
                    return -1;
                }
            }
        }
        j = 0;
        return -1;
    }

    public String showErrors() {
        String str = "";
        for (String str1 : errors) {
            str += str1;
            str += "\n";
        }
        return str;
    }

    public Parser(String[][] array) {
        this.array = array;
    }
}
