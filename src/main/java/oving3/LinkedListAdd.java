package oving3;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The type Linked list add.
 */
public class LinkedListAdd {


    /**
     * helper method for creating lists.
     *
     * @param num1 the num 1
     * @param num2 the num 2
     * @return the array list
     */
    public ArrayList<LinkedList<Integer>> andHelper(Long num1, Long num2) {
        ArrayList<LinkedList<Integer>> result = new ArrayList<>();
        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        for (int i = 0; i < num1.toString().length(); i++) {
            list1.add(num1.toString().charAt(i)-48);
        }
        for (int i = 0; i < num2.toString().length(); i++) {
            list2.add(num2.toString().charAt(i)-48);
        }
        int difference = list1.size()-list2.size();
        if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                list2.addFirst(0);
            }
        }

        else if (difference < 0) {
            for (int i = 0; i < Math.abs(difference); i++) {
                list1.addFirst(0);
            }
        }
        result.add(list1);
        result.add(list2);
        return result;
    }

    /**
     * method for adding.
     *
     * @param one the one
     * @param two the two
     * @return the long
     */
    public Long add(Long one, Long two) {
        ArrayList<LinkedList<Integer>>lists = andHelper(one, two);
        LinkedList<Integer> num1 = lists.get(0);
        LinkedList<Integer> num2 = lists.get(1);
        LinkedList<Integer> result = new LinkedList<>();
        while (num1.size()>0) {
            if (num1.getLast()+num2.getLast() > 9) {
                result.addFirst(num1.removeLast() + num2.removeLast()-10);

                if (num1.size() == 0) {
                    result.addFirst(1);
                }else {

                    num1.addLast(num1.removeLast()+1);
                }
            }else {
                    result.addFirst(num1.removeLast() + num2.removeLast());
            }
        }
        StringBuilder temp = new StringBuilder();
        for (Integer integer : result) {
            temp.append(integer);
        }
        return Long.parseLong(temp.toString());
    }

    /**
     * method for subtracting
     *
     * @param one the one
     * @param two the two
     * @return the long
     */
    public Long subtract(Long one, Long two) {

        ArrayList<LinkedList<Integer>>lists = andHelper(one, two);
        LinkedList<Integer> num1 = lists.get(0);
        LinkedList<Integer> num2 = lists.get(1);
        LinkedList<Integer> result = new LinkedList<>();
        StringBuilder temp = new StringBuilder();
        boolean isNegative = false;
        while (num1.size()>0) {
            if (num1.getLast()-num2.getLast() <0) {
                result.addFirst(num1.removeLast()+10 - num2.removeLast());
                if (num1.size() == 0) {
                    isNegative = true;
                }else {
                    num1.addLast(num1.removeLast()-1);
                }
            }else {
                result.addFirst(num1.removeLast() - num2.removeLast());
            }
        }
        for (Integer integer : result) {
            temp.append(integer);
        }
        if (isNegative) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(1);
            for (int i = 0; i < temp.toString().length(); i++) {
                stringBuilder.append(0);
            }
            return Long.parseLong(temp.toString()) - Long.parseLong(stringBuilder.toString());

        }
        return Long.parseLong(temp.toString());
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        LinkedListAdd linkedListAdd = new LinkedListAdd();
        System.out.println(linkedListAdd.add(99999999999L, 9L));
        System.out.println(linkedListAdd.subtract(99199999999999L, 999L));

    }
}
