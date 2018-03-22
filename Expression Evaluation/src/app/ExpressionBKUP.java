package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class ExpressionBKUP {

	public static String delims = " \t*+-/()[]";

	/**
	 * Populates the vars list with simple variables, and arrays lists with arrays
	 * in the expression. For every variable (simple or array), a SINGLE instance is created
	 * and stored, even if it appears more than once in the expression.
	 * At this time, values for all variables and all array items are set to
	 * zero - they will be loaded from a file in the loadVariableValues method.
	 *
	 * @param expr The expression
	 * @param vars The variables array list - already created by the caller
	 * @param arrays The arrays array list - already created by the caller
	 */
	public static void
	makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		/** COMPLETE THIS METHOD **/
		/** DO NOT create new vars and arrays - they are already created before being sent in
		 ** to this method - you just need to fill them in.
		 **/
		StringTokenizer st = new StringTokenizer(expr,delims);
		while(st.hasMoreTokens()){

			// add logic to determine if the token shall be added to vars or arrays
			String token = st.nextToken();
			int i = 0;
			i = expr.indexOf(token);
			System.out.println("Token " + token + " :" + i);


			if (expr.charAt(i) == '[') {
				arrays.add(new Array(token));
			} else {
				vars.add(new Variable(token));
			}
		}

	}

	/**
	 * Loads values for variables and arrays in the expression
	 *
	 * @param sc Scanner for values input
	 * @throws IOException If there is a problem with the input
	 * @param vars The variables array list, previously populated by makeVariableLists
	 * @param arrays The arrays array list - previously populated by makeVariableLists
	 */
	public static void
	loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays)
			throws IOException {
		while (sc.hasNextLine()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
			int numTokens = st.countTokens();
			String tok = st.nextToken();
			Variable var = new Variable(tok);
			Array arr = new Array(tok);
			int vari = vars.indexOf(var);
			int arri = arrays.indexOf(arr);
			if (vari == -1 && arri == -1) {
				continue;
			}
			int num = Integer.parseInt(st.nextToken());
			if (numTokens == 2) { // scalar symbol
				vars.get(vari).value = num;
			} else { // array symbol
				arr = arrays.get(arri);
				arr.values = new int[num];
				// following are (index,val) pairs
				while (st.hasMoreTokens()) {
					tok = st.nextToken();
					StringTokenizer stt = new StringTokenizer(tok," (,)");
					int index = Integer.parseInt(stt.nextToken());
					int val = Integer.parseInt(stt.nextToken());
					arr.values[index] = val;
				}
			}
		}
	}

	/**
	 * Evaluates the expression.
	 *
	 * @param vars The variables array list, with values for all variables in the expression
	 * @param arrays The arrays array list, with values for all array items
	 * @return Result of evaluation
	 */
	public static float
	evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		/** COMPLETE THIS METHOD **/
		// following line just a placeholder for compilation

		// infix expression Q
		// String Q = "5 * ( 6 + 2 ) - 12 / 4";
		// String Q = "5*(6+2)";
		String Q = expr;

		// change Q to numerical phrase
		int w =0;
		String newS = "";
		Stack<Character> brackets = new Stack<>();
		while(w < expr.length()){

			// parse expression and replace variables with their values
			String varName = "";
			String arrayName = "";
			String bracketsub = "";
			int arrayindex = 0;
			boolean hasBracket = false;

			// find variable name from expression

			for (int j=w; j < expr.length(); j++) {
				if(Character.isLetter(Q.charAt(j))) {
					varName = varName + String.valueOf(Q.charAt(j));
				}  else if(Q.charAt(j) == '['){
					arrayName = varName;
					String newQ = Q.substring(j+1);
					// find end bracket
					for(int s = 0; s < newQ.length(); s++){
						if(newQ.charAt(s) == '['){
							brackets.push('[');
						}else if(Q.charAt(s) == ']'){
							if(brackets.isEmpty()){
								System.out.println("Bracket not found");
								break;
							}if(brackets.peek() == '['){
								brackets.pop();
								hasBracket = true;
								// new expression within the original brackets
								bracketsub = newQ.substring(0,s);
							}
						}
						j++;
					}

					// create substring of expression inside brackets
					if(hasBracket){
						arrayindex = (int)evaluate(bracketsub, vars, arrays);
					}

				}else{
					break;
				}
			}


			// if a var name is found, then, loop thru vars and get its value
			// replace varName with its value in expression

			if (!varName.equals("")) {
				for(int k = 0; k < vars.size(); k++){
					if(varName.equals(vars.get(k).name)){
						System.out.println(vars.get(k).value);
						newS = newS + String.valueOf(vars.get(k).value);
					}
				}
			} else if(!arrayName.equals("")){
				for(int b = 0; b < arrays.size(); b++){
					if(arrayName.equals(arrays.get(b).name)){
						System.out.println(arrays.get(b).values[arrayindex]);
						newS = newS + arrays.get(b).values[arrayindex];
					}
				}
			} else {
				newS = newS + Q.charAt(w);
			}


			w++;
		}

		System.out.println("String Q is = " + newS);
		Q = newS;
		// convert this infix expression into postfix expression P

		String P = "";

		// stack to store parenthesis and operators
		java.util.Stack<String> operator_or_paranthesis = new java.util.Stack<>();

		// operator ranking

		int operator_in_expr = 0;
		int operator_in_stack = 0;

		// parse infix expression Q
		for (char ch: Q.toCharArray()) {
			System.out.println("Postfix expression is: " + P);
			System.out.println("Char is: " + String.valueOf(ch));

			if (ch == ' ') {
				P = P + String.valueOf(ch);
				continue;
			}

			// if an operand is found, add it to P
			if (Character.isDigit(ch)) {
				System.out.println("Digit: " + String.valueOf(ch));
				P = P + String.valueOf(ch);
				continue;
			}

			// if left parenthesis is found, then, push it on the stack

			if (ch == '(') {
				operator_or_paranthesis.push(String.valueOf(ch));
				P = P + " ";
				System.out.println("Stack found (: " + operator_or_paranthesis.toString());
			}

			// if a right parenthesis is found

			if (ch == ')') {
				System.out.println("Right parenthesis found 1");
				System.out.println(P);
				System.out.println("Stack: " + operator_or_paranthesis.toString());
				while (!operator_or_paranthesis.isEmpty() && !"(".equals(operator_or_paranthesis.peek())) {
					P = P + " " +  operator_or_paranthesis.pop() + " ";
					System.out.println("Right parenthesis found 2 POP 1-" + P);
				}

				System.out.println("Stack: " + operator_or_paranthesis.toString());

				if (!operator_or_paranthesis.isEmpty()) {
					if("(".equals(operator_or_paranthesis.peek())) {
						String tmp = operator_or_paranthesis.pop(); 		// pop & discard left parenthesis
					}
				}


			}

			// if an operator is found
			if (ch == '+' || ch == '-') {
				operator_in_expr = 0;
			} else if (ch == '*' || ch == '/'){
				operator_in_expr = 1;
			}

			if (ch == '+' || ch == '-' || ch == '*' || ch=='/') {
				System.out.println("Operator found" + String.valueOf(ch));
				if (!operator_or_paranthesis.isEmpty()) {
					System.out.println("top of stack is:" + operator_or_paranthesis.peek() + "---");
				}

				if (operator_or_paranthesis.isEmpty()) {
					System.out.println("ajit1");
					operator_or_paranthesis.push(String.valueOf(ch)); // push the operator onto the stack
					P = P + " ";
					System.out.println("Stack: " + operator_or_paranthesis.toString());
				}
				else if ("(".equals(operator_or_paranthesis.peek()))
				{
					System.out.println("ajit2");
					operator_or_paranthesis.push(String.valueOf(ch)); // push the operator onto the stack
					P = P + " ";
					System.out.println("Stack: " + operator_or_paranthesis.toString());
				}
				else {

					if ("+".equals(operator_or_paranthesis.peek()) || "-".equals(operator_or_paranthesis.peek())) {
						operator_in_stack = 0;
					} else if ("*".equals(operator_or_paranthesis.peek()) || "/".equals(operator_or_paranthesis.peek())){
						operator_in_stack = 1;
					}
					while (!operator_or_paranthesis.isEmpty() &&
							!"(".equals(operator_or_paranthesis.peek()) &&
							operator_in_expr <= operator_in_stack) {

						P = P + " " + operator_or_paranthesis.pop() + " ";
						//System.out.println("T or F" + operator_or_paranthesis.isEmpty());

						if (!operator_or_paranthesis.isEmpty()) {
							if ("+".equals(operator_or_paranthesis.peek()) || "-".equals(operator_or_paranthesis.peek())) {
								operator_in_stack = 0;
							} else if ("*".equals(operator_or_paranthesis.peek()) || "/".equals(operator_or_paranthesis.peek())){
								operator_in_stack = 1;
							}
						}

						//System.out.println("POP 2");
					}
					operator_or_paranthesis.push(String.valueOf(ch));
					P = P + " ";
					System.out.println("Stack: " + operator_or_paranthesis.toString());
				}
			}

		}

		while (!operator_or_paranthesis.isEmpty()) {
			P = P + " " + operator_or_paranthesis.pop() + " ";
			//System.out.println("POP 3");
		}

		System.out.println("Infix expression is: " + Q);
		System.out.println("Postfix expression is: " + P);

		// evaluate postfix expr P

		Stack<Float> operands = new Stack<>();


		StringTokenizer tokens = new StringTokenizer(P, " ");
		String[] splited = new String[tokens.countTokens()];
		int index = 0;
		while(tokens.hasMoreTokens()){
			splited[index] = tokens.nextToken();
			index++;
		}
		String number1 = "";
		for (int i=0; i < splited.length; i++) {

			System.out.println("Array is: " + i + "-" + splited[i] + "-");

			// check to see if splited[i] is a bigger number
			if(isNumeric(splited[i])) {// if operand is found, push it on stack
				number1 = number1 + splited[i];
				operands.push(Float.valueOf(number1));
				number1 = "";

			}else if("+".equals(splited[i]) || "-".equals(splited[i]) || "*".equals(splited[i]) || "/".equals(splited[i])) {// if operator is found
				float A = operands.pop();
				float B = operands.pop();
				if ("+".equals(splited[i])  ) {
					operands.push(B+A);
				} else if ("-".equals(splited[i])  ) {
					operands.push(B-A);
				} else if ("*".equals(splited[i])  ) {
					operands.push(B*A);
				} else if ("/".equals(splited[i])  ) {
					operands.push(B/A);
				}
			}
		}
		float res = 0;
		if (!operands.isEmpty()) {
			res = operands.pop();
			//System.out.println("Value of expression is: " + res);
		}

		return res;
	}


	// isNumeric Method
	private static boolean isNumeric(String str)
	{
		boolean isNumeric = false;
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == '0' || str.charAt(i) == '1' ||str.charAt(i) == '2' ||
					str.charAt(i) == '3' || str.charAt(i) == '4' || str.charAt(i) == '5' ||
					str.charAt(i) == '6' || str.charAt(i) == '7' || str.charAt(i) == '8' ||
					str.charAt(i) == '9'){
				isNumeric = true;
			}else{
				isNumeric = false;
			}
		}
		return isNumeric;
	}
}
