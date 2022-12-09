package glavnipaket;


import java.util.ArrayList;


public class NRNFunctional implements NajveciNizInterfejs {

    @Override
    public ArrayList<Integer> dajNajveciRastuciNiz(int[] array){
        ArrayList<ArrayList<Integer>> allGrowingLists = findAllGrowingLists(array);
        return findTheLongestList(allGrowingLists);
    }

    public static ArrayList<ArrayList<Integer>> findAllGrowingLists(int[] array){
        ArrayList<ArrayList<Integer>> growingLists = new ArrayList<>();
/*
        for(int i=0; i<array.length; i++){
            final int CURRENT_ARRAY_ELEMENT = array[i];
            growingLists = getGrowingListsAfterParsingNextElement(growingLists, CURRENT_ARRAY_ELEMENT);
            growingLists.add(createList(CURRENT_ARRAY_ELEMENT));
        }*/
        growingLists = parseNewElement(array, 0, growingLists);

        return growingLists;
    }


    public static ArrayList<ArrayList<Integer>> parseNewElement(int[] array,
                                                                int currentIndex,
                                                                ArrayList<ArrayList<Integer>> growingLists){
        if(currentIndex >= array.length) return growingLists;

        final int CURRENT_ARRAY_ELEMENT = array[currentIndex];
        growingLists = getGrowingListsAfterParsingNextElement(growingLists, CURRENT_ARRAY_ELEMENT);
        growingLists.add(createList(CURRENT_ARRAY_ELEMENT));

        final int NEXT_INDEX = currentIndex + 1;
        if(array.length > NEXT_INDEX){
            growingLists = parseNewElement(array, NEXT_INDEX, growingLists);
        }

        return growingLists;
    }

    public static ArrayList<ArrayList<Integer>> getGrowingListsAfterParsingNextElement(ArrayList<ArrayList<Integer>> lists, int newElement){
        ArrayList<ArrayList<Integer>> newLists = new ArrayList<>();

        for(ArrayList<Integer> oldList : lists){
            if(getLastElement(oldList) < newElement){
                newLists.add(createNewList(oldList, newElement));
            } else {
                newLists.add(oldList);
                ArrayList<Integer> newList = createNewList(oldList, newElement);
                if (newList != null) {
                    newLists.add(newList);
                }
            }
        }

        return newLists;
    }

    /* Vraca null ako nijedan element liste nije manji od drugog ulaznog parametra.
     * U suprotnom, trazi se poslednji clan liste koji je manji od drugog ulaznog parametra (nazovimo ga ,,clan X").
     *  Deo liste do clana X se kopira (zakljucno sa clanom X), te se kopiranoj listi dodaje drugi ulazni parametar.
     *  Metoda vraca takvu listu. */
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
