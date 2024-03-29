package org.practice.domo.herarchy;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserInput {

        public static class TextInput {
            private List<Character> chars = new ArrayList<>();
            public void add(char c){
                chars.add(c);
            }

            public String getValue(){
                return chars.stream().map(Object::toString).collect(Collectors.joining());
            }
        }

        public static class NumericInput extends TextInput {

            @Override
            public void add(char c) {
                if (Character.isDigit(c)){
                    super.add(c);
                }
            }
        }

        public static void main(String[] args) {
            TextInput input = new NumericInput();
            input.add('1');
            input.add('a');
            input.add('0');
            System.out.println(input.getValue());
        }
}
