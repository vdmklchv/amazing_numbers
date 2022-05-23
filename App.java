package numbers;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public void run() {
        /* main app logic */
        printGreeting();
        printInstructions();


        while (true) {
            long number;
            long quantityOfNumbersToSearch = 0;
            String[] request = getRequestFromUser();
            String[] properties = null;

            if (request == null) {
                printInstructions();
                continue;
            }

            if (request.length > 2) {
                properties = Arrays.copyOfRange(request, 2, request.length);
            }

            try {
                number = Long.parseLong(request[0]);
                if (number == 0) {
                    break;
                }
                if (number < 0) {
                    System.out.println("The first parameter should be a natural number or zero.");
                    System.out.println();
                    continue;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("The first parameter should be a natural number or zero.");
                continue;
            }


            try {
                quantityOfNumbersToSearch = Long.parseLong(request[1]);
                if (quantityOfNumbersToSearch < 0) {
                    System.out.println("The second parameter should be a natural number.");
                    System.out.println();
                    continue;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("The second parameter should be a natural number.");
                continue;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No second parameter provided.");
            }

            if (request.length < 3) {
                printNumberPropertiesNoFilter(number, quantityOfNumbersToSearch);
            } else {
                printNumbersWithProperties(number, quantityOfNumbersToSearch, properties);
            }
        }

        System.out.println();
        System.out.println("Goodbye!");
    }

    private void printGreeting() {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println();
    }

    private void printInstructions() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println(" * the first parameter represents a starting number;");
        System.out.println(" * the second parameters shows how many consecutive numbers are to be processed;");
        System.out.println("- two natural numbers and two properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit");
    }

    private void printNumberPropertiesNoFilter(long number, long numbersToFind) {
        /* prints numbers with all properties without filtering them. Method allows
        * to get result for 1 number or for a number of consecutive numbers starting from a set number */
        if (numbersToFind == 0) {
            String result = buildStringWithNumberProperties(number, numbersToFind);
            System.out.println(result);
        } else {
            for (int i = 0; i < numbersToFind; i++, number++) {
                System.out.println(buildStringWithNumberProperties(number, numbersToFind));
            }
        }
    }

    private String buildStringWithNumberProperties(long number, long numbersToFind) {
        /* determine properties of number and produce property string for 1 or multiple numbers */
        Properties props = new Properties(number);
        boolean isEven = props.isEven();
        boolean isBuzzNumber = props.isBuzzNumber();
        boolean isDuckNumber = props.isDuckNumber();
        boolean isPalindromic = props.isPalindromic();
        boolean isGapful = props.isGapful();
        boolean isSpy = props.isSpy();
        boolean isSunny = props.isSunny();
        boolean isSquare = props.isSquare();
        boolean isJumping = props.isJumping();
        boolean isHappy = props.isHappy();
        boolean isSad = !props.isHappy();

        StringBuilder sb = new StringBuilder();

        if (numbersToFind == 0) {
            sb.append("Properties of ").append(number).append("\n").append("even: ").append(isEven).append("\n").
                    append("odd: ").append(!isEven).append("\n").append("buzz: ").append(isBuzzNumber).append("\n").append("duck: ").
                    append(isDuckNumber).append("\n").append("palindromic: ").append(isPalindromic).append("\n").append("spy: ").
                    append(isSpy).append("\n").append("gapful: ").append(isGapful).append("\n").append("sunny: ").append(isSunny).
                    append("\n").append("square: ").append(isSquare).append("\n").append("jumping: ").append(isJumping).
                    append("\n").append("happy: ").append(isHappy).append("\n").append("sad: ").append(isSad).append("\n");
        } else {
            sb.append(number).append(" is ");
            if (isEven) {
                sb.append("even, ");
            }
            if (!isEven) {
                sb.append("odd, ");
            }
            if (isBuzzNumber) {
                sb.append("buzz, ");
            }
            if (isDuckNumber) {
                sb.append("duck, ");
            }
            if (isPalindromic) {
                sb.append("palindromic, ");
            }
            if (isSpy) {
                sb.append("spy, ");
            }
            if (isSunny) {
                sb.append("sunny, ");
            }
            if (isSquare) {
                sb.append("square, ");
            }
            if (isJumping) {
                sb.append("jumping, ");
            }
            if (isHappy) {
                sb.append("happy, ");
            }
            if (isSad) {
                sb.append("sad, ");
            }
            if (isGapful) {
                sb.append("gapful");
            }

            if (sb.charAt(sb.length() - 2) == ',') {
                sb.delete(sb.length() - 2, sb.length() - 1);
            }

        }

        return sb.toString();
    }

    private void printNumbersWithProperties(long startingNumber, long numbersToFind, String[] props) {
        /* this method prints short versions of numbers considering included and excluded properties */
        String[] nonExistingProperties = findNonExistingProperties(props);
        if (nonExistingProperties != null) {
            String placeholder;
            if (nonExistingProperties.length == 1) {
                System.out.printf("The property [%s] is wrong\n", nonExistingProperties[0]);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append("%s, ".repeat(nonExistingProperties.length));
                sb.delete(sb.length() - 2, sb.length() - 1);
                sb.append("]");
                placeholder = sb.toString();
                System.out.printf("The properties %s are wrong\n", placeholder);
            }
            System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, EVEN, ODD, JUMPING, HAPPY, SAD]");
            return;
        }

        String[] mutuallyExclusiveProperties = findMutuallyExclusiveProperties(props);

        if (mutuallyExclusiveProperties == null) {
            while (numbersToFind > 0) {
                if (numberContainsProperties(startingNumber, numbersToFind, props)) {
                    System.out.println(buildStringWithNumberProperties(startingNumber, numbersToFind));
                    numbersToFind--;
                }
                startingNumber++;
            }
        } else {
            System.out.printf("The request contains mutually exclusive properties: [%s, %s]\n", mutuallyExclusiveProperties[0], mutuallyExclusiveProperties[1]);
            System.out.println("There are no numbers with these properties.");
        }

    }

    private boolean numberContainsProperties(long number, long numbersToFind, String[] props) {
        /* checks if number has certain properties */
        String condensedPropertyListForNumber = buildStringWithNumberProperties(number, numbersToFind);
        for (String prop: props) {
            if (!condensedPropertyListForNumber.contains(prop.toLowerCase()) && prop.charAt(0) != '-') {
                return false;
            }
            if (prop.charAt(0) == '-') {
                String excludedProp = prop.substring(1);
                if (condensedPropertyListForNumber.contains(excludedProp)) {
                    return false;
                }
            }
        }
        return true;
    }

    private String[] getRequestFromUser() {
        /* requests user for command  input and return commands as array of strings */
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a request: ");
        String request = sc.nextLine().toLowerCase();
        if ("".equals(request)) {
            System.out.println();
            return null;
        }
        return request.split(" ");
    }

    private String[] findNonExistingProperties(String[] props) {
        /* finds invalid properties in array of entered properties and returns an array of them */
        String[] supportedProperties = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SUNNY", "SQUARE", "JUMPING", "HAPPY", "SAD"};
        StringBuilder sb = new StringBuilder();
        for (String prop: props) {
            if (prop.charAt(0) == '-') {
                prop = prop.substring(1);
            }
            if (!Arrays.asList(supportedProperties).contains(prop.toUpperCase())) {
                sb.append(prop.toUpperCase()).append(" ");
            }
        }
        if (sb.toString().length() == 0) {
            return null;
        } else {
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString().split(" ");
        }
    }

    private String[] findMutuallyExclusiveProperties(String[] props) {
        /* find mutually exclusive properties in terms of logic and return array of such properties */
        if (containsEqualOppositeProperties(props) != null) {
            String prop = containsEqualOppositeProperties(props);
            if (prop != null) {
                return new String[]{prop.toUpperCase(), "-" + prop.toUpperCase()};
            }
        }
        if (Arrays.asList(props).contains("odd") && Arrays.asList(props).contains("even")) {
            return new String[]{"ODD", "EVEN"};
        }
        if (Arrays.asList(props).contains("-odd") && Arrays.asList(props).contains("-even")) {
            return new String[]{"-ODD", "-EVEN"};
        }
        if (Arrays.asList(props).contains("duck") && Arrays.asList(props).contains("spy")) {
            return new String[]{"DUCK", "SPY"};
        }
        if (Arrays.asList(props).contains("-duck") && Arrays.asList(props).contains("-spy")) {
            return new String[]{"-DUCK", "-SPY"};
        }
        if (Arrays.asList(props).contains("sunny") && Arrays.asList(props).contains("square")) {
            return new String[]{"SUNNY", "SQUARE"};
        }
        if (Arrays.asList(props).contains("happy") && Arrays.asList(props).contains("sad")) {
            return new String[]{"HAPPY", "SAD"};
        }
        if (Arrays.asList(props).contains("-happy") && Arrays.asList(props).contains("-sad")) {
            return new String[]{"-HAPPY", "-SAD"};
        }
        return null;
    }

    private String containsEqualOppositeProperties(String[] props) {
        /* helper method to check if properties have equal opposite values */
        for (String prop: props) {
            if (prop.charAt(0) == '-') {
                prop = prop.substring(1);
                if (Arrays.asList(props).contains(prop)) {
                    return prop;
                }
            }
        }
        return null;
    }
}
