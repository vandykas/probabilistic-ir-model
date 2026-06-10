public class Stemmer {
    private char[] buffer;
    private int wordLen;
    private int tempWordLen;

    public String stem(String word) {
        this.buffer = word.toCharArray();
        this.wordLen = buffer.length;
        this.tempWordLen = buffer.length;

        step1a();
        step1b();
        step1c();
        step2();
        step3();
        step4();
        step5a();
        step5b();

        return new String(buffer, 0, wordLen);
    }

    private void step1a() {
        if (endsWith("sses")) {
            wordLen -= 2;
        } else if (endsWith("ies")) {
            wordLen -= 2;
        } else if (!endsWith("ss") && endsWith("s")) {
            wordLen -= 1;
        }
    }

    private void step1b() {
        if (endsWith("eed") && measure() > 0) {
            wordLen -= 1;
        } else if (endsWith("ed") && isContainsVowel()) {
            wordLen -= 2;
            step1bPart2();
        } else if (endsWith("ing") && isContainsVowel()) {
            wordLen -= 3;
            step1bPart2();
        }
    }

    private void step1bPart2() {
        if (endsWith("at")) {
            replaceSuffix("ate");
        } else if (endsWith("bl")) {
            replaceSuffix("ble");
        } else if (endsWith("iz")) {
            replaceSuffix("ize");
        } else if (endsWithDoubleConsonant() &&
                !(endsWith("l") || endsWith("s") || endsWith("z"))) {
            wordLen -= 1;
        } else if (endsInCVC() && measure() == 1) {
            this.tempWordLen = wordLen;
            replaceSuffix("e");
        }
    }

    private void step1c() {
        if (endsWith("y") && isContainsVowel()) {
            replaceSuffix("i");
        }
    }

    private void step2() {
        if (isMatchAndCheckM("ational", 0)) {
            replaceSuffix("ate");
        } else if (isMatchAndCheckM("tional", 0)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("enci", 0)) {
            replaceSuffix("ence");
        } else if (isMatchAndCheckM("anci", 0)) {
            replaceSuffix("ance");
        } else if (isMatchAndCheckM("izer", 0)) {
            wordLen -= 1;
        } else if (isMatchAndCheckM("abli", 0)) {
            replaceSuffix("able");
        } else if (isMatchAndCheckM("alli", 0)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("entli", 0)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("eli", 0)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("ousli", 0)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("ization", 0)) {
            replaceSuffix("ize");
        } else if (isMatchAndCheckM("ation", 0)) {
            replaceSuffix("ate");
        } else if (isMatchAndCheckM("ator", 0)) {
            replaceSuffix("ate");
        } else if (isMatchAndCheckM("alism", 0)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("iveness", 0)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("fulness", 0)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("ousness", 0)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("aliti", 0)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("iviti", 0)) {
            replaceSuffix("ive");
        } else if (isMatchAndCheckM("biliti", 0)) {
            replaceSuffix("ble");
        }
    }

    private void step3() {
        if (isMatchAndCheckM("icate", 0)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ative", 0)) {
            wordLen -= 5;
        } else if (isMatchAndCheckM("alize", 0)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("iciti", 0)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ical", 0)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("ful", 0)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ness", 0)) {
            wordLen -= 4;
        }
    }

    private void step4() {
        if (isMatchAndCheckM("al", 1)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("ance", 1)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("ence", 1)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("er", 1)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("ic", 1)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("able", 1)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("ible", 1)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("ant", 1)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ement", 1)) {
            wordLen -= 5;
        } else if (isMatchAndCheckM("ment", 1)) {
            wordLen -= 4;
        } else if (isMatchAndCheckM("ent", 1)) {
            wordLen -= 3;
        } else if (endsWith("ion") && tempWordLen > 0 &&
                (buffer[tempWordLen - 1] == 's' || buffer[tempWordLen - 1] == 't')
                && measure() > 1) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ou", 1)) {
            wordLen -= 2;
        } else if (isMatchAndCheckM("ism", 1)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ate", 1)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("iti", 1)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ous", 1)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ive", 1)) {
            wordLen -= 3;
        } else if (isMatchAndCheckM("ize", 1)) {
            wordLen -= 3;
        }
    }

    private void step5a() {
        if (isMatchAndCheckM("e", 1)) {
            wordLen -= 1;
        } else if (endsWith("e") && measure() == 1 && !endsInCVC()) {
            wordLen -= 1;
        }
    }

    private void step5b() {
        if (isMatchAndCheckM("l", 1) && endsWithDoubleConsonant()) {
            wordLen -= 1;
        }
    }

    private boolean isMatchAndCheckM(String suffix, int m) {
        if (!endsWith(suffix)) {
            return false;
        }
        return measure() > m;
    }

    private void replaceSuffix(String newSuffix) {
        int start = this.tempWordLen;
        int end = start + newSuffix.length();
        for (int i = start; i < end; i++) {
            buffer[i] = newSuffix.charAt(i - start);
        }

        this.wordLen = this.tempWordLen + newSuffix.length();
    }

    private boolean isContainsVowel() {
        for (int i = 0; i < tempWordLen; i++) {
            if (isVowel(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean endsWithDoubleConsonant() {
        if (tempWordLen < 2 || !isConsonant(tempWordLen - 1)) {
            return false;
        }
        return buffer[tempWordLen - 1] == buffer[tempWordLen - 2];
    }

    private boolean endsWith(String suffix) {
        int suffixLen = suffix.length();
        if (wordLen < suffixLen) {
            return false;
        }

        int start = wordLen - suffixLen;
        for (int i = start; i < wordLen; i++) {
            if (buffer[i] != suffix.charAt(i - start)) {
                return false;
            }
        }

        tempWordLen = start;
        return true;
    }

    private boolean endsInCVC() {
        if (tempWordLen < 3) {
            return false;
        }

        char lastChar = buffer[tempWordLen - 1];
        if (lastChar == 'w' || lastChar == 'x' || lastChar == 'y') {
            return false;
        }

        return isConsonant(tempWordLen - 3) && isVowel(tempWordLen - 2)
                && isConsonant(tempWordLen - 1);
    }

    private int measure() {
        int measure = 0;
        boolean isPrevVowel = isVowel(0);
        for (int i = 1; i < tempWordLen; i++) {
            boolean isCurrentConsonant = isConsonant(i);
            if (isPrevVowel && isCurrentConsonant) {
                measure++;
            }
            isPrevVowel = !isCurrentConsonant;
        }
        return measure;
    }

    private boolean isVowel(int idxToCheck) {
        return !isConsonant(idxToCheck);
    }

    private boolean isConsonant(int idxToCheck) {
        return switch (buffer[idxToCheck]) {
            case 'a', 'i', 'u', 'e', 'o' -> false;
            case 'y' -> idxToCheck == 0 || !isConsonant(idxToCheck - 1);
            default -> true;
        };
    }
}
