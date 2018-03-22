import java.util.Stack;
import java.util.StringTokenizer;

public class HelloWorld {

	public static boolean isNumeric(String str)
	{
		try
		{
			int k = Integer.parseInt(str);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World!");

		// stack example

		Stack<Integer> stack = new Stack<>();
		System.out.println("Empty stack : "  + stack);
		System.out.println("Empty stack : "  + stack.isEmpty());
		// Exception in thread "main" java.util.EmptyStackException
		// System.out.println("Empty stack : Pop Operation : "  + stack.pop());
		stack.push(1001);
		stack.push(1002);
		stack.push(1003);
		stack.push(1004);
		System.out.println("Non-Empty stack : "  + stack);
		System.out.println("Non-Empty stack: Pop Operation : "  + stack.pop());
		System.out.println("Non-Empty stack : After Pop Operation : "  + stack);
		System.out.println("Non-Empty stack : search() Operation : "  + stack.search(1002));
		System.out.println("Non-Empty stack : "  + stack.isEmpty());

		// infix expression Q
		String Q = "5 * ( 6 + 2 ) - 12 / 4";
		// String Q = "5*(6+2)";

		// convert this infix expression into postfix expression P

		String P = "";

		// stack to store parenthesis and operators
		Stack<String> operator_or_paranthesis = new Stack<>();

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
			}

			// if left parenthesis is found, then, push it on the stack

			if (ch == '(') {
				operator_or_paranthesis.push(String.valueOf(ch));
				System.out.println("Stack found (: " + operator_or_paranthesis.toString());
			}

			// if a right parenthesis is found

			if (ch == ')') {
				System.out.println("Right parenthesis found 1");
				System.out.println(P);
				System.out.println("Stack: " + operator_or_paranthesis.toString());
				while (!operator_or_paranthesis.isEmpty() && !"(".equals(operator_or_paranthesis.peek())) {
					P = P + operator_or_paranthesis.pop();
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
					System.out.println("Stack: " + operator_or_paranthesis.toString());
				}
				else if ("(".equals(operator_or_paranthesis.peek()))
				{
					System.out.println("ajit2");
					operator_or_paranthesis.push(String.valueOf(ch)); // push the operator onto the stack
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

						P = P + operator_or_paranthesis.pop();
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
					System.out.println("Stack: " + operator_or_paranthesis.toString());
				}
			}

		}

		while (!operator_or_paranthesis.isEmpty()) {
			P = P + " " + operator_or_paranthesis.pop();
			//System.out.println("POP 3");
		}

		System.out.println("Infix expression is: " + Q);
		System.out.println("Postfix expression is: " + P);

		// evaluate postfix expr P

		Stack<Integer> operands = new Stack<>();

		StringTokenizer tokens = new StringTokenizer(P, " ");
		String[] splited = new String[tokens.countTokens()];
		int index = 0;
		while(tokens.hasMoreTokens()){
			splited[index] = tokens.nextToken();
			++index;
		}

		for (int i=0; i < splited.length; i++) {

			System.out.println("Array is: " + i + "-" + splited[i] + "-");

			// if operand is found, push it on stack
			if(isNumeric(splited[i])) {
				operands.push(Integer.parseInt(splited[i]));
			}

			// if operator is found
			if("+".equals(splited[i]) || "-".equals(splited[i]) || "*".equals(splited[i]) || "/".equals(splited[i])) {
				int A = operands.pop();
				int B = operands.pop();
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

		if (!operands.isEmpty()) {
			System.out.println("Value of expression is: " + operands.pop());
		}


	}

}