package numbers;

class Properties {
    // FIELDS
    private final long number;

    // CONSTRUCTOR
    public Properties(long number) {
        this.number = number;
    }

    boolean isDuckNumber() {
        /* returns true if number has 0 in it */
        String numberAsString = String.valueOf(number);
        return numberAsString.contains("0");
    }

    boolean isBuzzNumber() {
        /* returns true if number is divisible by 7 or ends with 7 */
        boolean divisibleBySeven = number % 7 == 0;
        boolean endsWithSeven = number % 10 == 7;

        return divisibleBySeven || endsWithSeven;
    }

    boolean isEven() {
        return number % 2 == 0;
    }

    boolean isPalindromic() {
        /* returns true if number can be read the same from both sides */
        StringBuilder sb = new StringBuilder();
        String numberAsString = String.valueOf(number);
        sb.append(numberAsString);
        return numberAsString.equals(sb.reverse().toString());
    }

    boolean isGapful() {
        /* returns if number is not longer than 3 digits and can be equally divided by sum of its first and last digit */
        String numberAsString = String.valueOf(number);
        if (numberAsString.length() < 3) {
            return false;
        }
        int divisor = Integer.parseInt(String.valueOf(numberAsString.charAt(0)) + numberAsString.charAt(numberAsString.length() - 1));
        return number % divisor == 0;
    }

    boolean isSpy() {
        /* returns true if sum of digits equals product of digits */
        String numberAsString = String.valueOf(number);
        String[] digitsArrayAsString = numberAsString.split("");
        long sumOfDigits = 0;
        long productOfDigits = 1;
        for (String digitAsString: digitsArrayAsString) {
            long digit = Long.parseLong(digitAsString);
            sumOfDigits += digit;
            productOfDigits *= digit;
        }

        return sumOfDigits == productOfDigits;
    }

    boolean isSunny() {
        /* returns true if next number after given number has an integer square root */
        long numberToCheck = number + 1;
        return Math.sqrt(numberToCheck) % 1 == 0;
    }

    boolean isSquare() {
        /* returns true if given number has an integer square root */
        return Math.sqrt(number) % 1 == 0;
    }

    boolean isJumping() {
        /* returns true if every adjacent numbers (except 0 and 9) differ exactly by 1 */
        String numberAsString = String.valueOf(number);
        for (int i = numberAsString.length() - 1; i > 0; i--) {
            if (numberAsString.charAt(i) == '9' && numberAsString.charAt(i - 1) == '0' ||
            numberAsString.charAt(i) == '0' && numberAsString.charAt(i - 1) == '9') {
                return false;
            }

            int currentNumber = Integer.parseInt(String.valueOf(numberAsString.charAt(i)));
            int nextNumber = Integer.parseInt(String.valueOf(numberAsString.charAt(i - 1)));
            if (Math.abs(currentNumber - nextNumber) != 1) {
                return false;
            }
        }
        return true;
    }

    boolean isHappy() {
        /* returns true if iteration of sums of numbers powered by 2 lead to 1 */
        long iteration = iterate(number);
        while (iteration != 1) {
            iteration = iterate(iteration);
            if (iteration == 4) {
                return false;
            }
        }
        return true;
    }

    private long iterate (long number) {
        /* helper method to help determine if number is happy */
        long sum = 0;
        if (number / 10 == 0) {
            return (long) Math.pow(number, 2);
        }
        while (number > 0) {
            long digit = number % 10;
            sum += Math.pow(digit, 2);
            number /= 10;
        }
        return sum;
    }
}
