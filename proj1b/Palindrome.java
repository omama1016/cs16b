package com.wzu.wg.proj1B;

public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> deque = new ArrayDeque<>();
//        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    private boolean isPalindromeRecursive(Deque<Character> deque){
        if (deque.isEmpty() || deque.size() == 1) {
            return true;
        }
        if (!deque.removeFirst().equals(deque.removeLast()) ) {
            return false;
        }
        return isPalindromeRecursive(deque);
    }

    public boolean isPalindrome(String word){
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeRecursive(deque);
    }

    private boolean isPalindromeRecursiveOffByOne(Deque<Character> deque,
                                                  CharacterComparator cc){
        if (deque.isEmpty() || deque.size() == 1) {
            return true;
        }
        if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
            return false;
        }
        return isPalindromeRecursiveOffByOne(deque, cc);
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeRecursiveOffByOne(deque, cc);
    }
}
