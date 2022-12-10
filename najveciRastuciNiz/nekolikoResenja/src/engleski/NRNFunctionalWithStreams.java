package engleski;

import glavnipaket.NajveciNizInterfejs;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class NRNFunctionalWithStreams implements NajveciNizInterfejs {

    @Override
    public ArrayList<Integer> dajNajveciRastuciNiz(int[] niz) {
        ArrayList<ArrayList<Integer>> allGrowingLists = findAllGrowingLists(niz);
        return findTheLongestList(allGrowingLists);
    }


    /*
    ArrayList<ArrayList<Integer>> growingLists = new ArrayList<>();
    for(int i=0; i<array.length; i++){
               growingLists = newListsAfterParsingTheNextNumber(growingLists, array[i]);
    }
    return growingLists;
     */
    public static ArrayList<ArrayList<Integer>> findAllGrowingLists(int[] array){
        final int START_INDEX = 0;
        return parseNewElement(array, START_INDEX, new ArrayList<ArrayList<Integer>>());
    }

    public static ArrayList<ArrayList<Integer>> parseNewElement(int[] array,
                                                                int currentIndex,
                                                                ArrayList<ArrayList<Integer>> oldGrowingLists){

        ArrayList<ArrayList<Integer>> newGrowingLists = newListsAfterParsingTheNextNumber(
                oldGrowingLists,
                array[currentIndex]);

        final int NEXT_INDEX = currentIndex + 1;
        if(NEXT_INDEX < array.length){
            return parseNewElement(array, NEXT_INDEX, newGrowingLists);
        }

        return newGrowingLists;
    }


    public static ArrayList<ArrayList<Integer>> newListsAfterParsingTheNextNumber(ArrayList<ArrayList<Integer>> oldGrowingLists, final int NEXT_NUMBER){

        final ArrayList<ArrayList<Integer>> listsThatMapToThemselves = oldGrowingLists.stream()
                .filter(aGrowingList -> getLastElement(aGrowingList) >= NEXT_NUMBER)
                .collect(Collectors.toCollection(ArrayList::new));

        final ArrayList<ArrayList<Integer>> newLists = oldGrowingLists.stream()
                .map(aGrowingList -> createNewList(aGrowingList, NEXT_NUMBER))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));

        newLists.addAll(listsThatMapToThemselves);

        newLists.add(createList(NEXT_NUMBER));
        return newLists;
    }


    /* Vraca null ako nijedan element liste nije manji od drugog ulaznog parametra.
     * U suprotnom, trazi se poslednji clan liste koji je manji od drugog ulaznog parametra (nazovimo ga ,,clan X").
     *  Deo liste do clana X se kopira (zakljucno sa clanom X), te se kopiranoj listi dodaje drugi ulazni parametar.
     *  Metoda vraca takvu listu. */
/* Imagine input being a growing array {1,3,10} and the new element being 4.
The output of this function should be {1,3,4}, because that's the longest growing array that could be created
from the input array with the number 4. If the new number is smaller than all the elements of the input array,
then this function should return null, as no new growing array can be created with the number provided.*/

    public static ArrayList<Integer> createNewList(ArrayList<Integer> oldList, int newElement){
        final int LAST_INDEX = oldList.size() -1;

        for(int i=LAST_INDEX; i>=0; i--){
            if(oldList.get(i) < newElement){
                final int NUMBER_OF_ELEMENTS_TO_COPY = i+1;
                ArrayList<Integer> newList = copyList(oldList, NUMBER_OF_ELEMENTS_TO_COPY);
                newList.add(newElement);
                return newList;
            }
        }
        return null;
    }

    public static <T> ArrayList<T> copyList(ArrayList<T> list, int numberOfElementsToCopy){
        if(list == null) return null;
        numberOfElementsToCopy = Math.min(numberOfElementsToCopy, list.size());

        ArrayList<T> newList = new ArrayList<>();
        for(int i=0; i<numberOfElementsToCopy; i++){
            newList.add(list.get(i));
        }

        return newList;
    }

    public static ArrayList<Integer> createList(int broj){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(broj);
        return list;
    }


    public static <T> ArrayList<T> findTheLongestList(ArrayList<ArrayList<T>> lists){
        if(lists == null) return null;

        ArrayList<T> longestList = new ArrayList<>();
        for(ArrayList<T> list : lists){
            if(list.size() > longestList.size()){
                longestList = list;
            }
        }

        return longestList;
    }

    public static int getLastElement(ArrayList<Integer> list){
        return list.get(list.size() - 1);
    }
}
